package cn.les.auth.utils;

import cn.les.auth.entity.UserDetail;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Joetao
 * @time 2021/1/25 2:58 下午
 * @Email cutesimba@163.com
 */
@Component
public class JwtUtils {
    private static final String CLAIM_KEY_USER_ID = "userId";
    private static final String CLAIM_KEY_ROLE_ID = "roleId";
    private static final String CLAIM_KEY_ROLE_NAME = "roleName";
    private static final String CLAIM_KEY_ROLE_NAME_ZH = "roleNameZh";
    private static final String CLAIM_KEY_USER_NICKNAME = "nickname";
    private final Map<String, String> tokenMap = new HashMap<>();

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.access_token_expiration}")
    private Long accessTokenExpiration;

    @Value("${jwt.refresh_token_expiration}")
    private Long refreshTokenExpiration;

    private final SignatureAlgorithm SIGNATURE_ALGORITHM = SignatureAlgorithm.HS256;

    public UserDetail getUserDetailFromToken(String token) {
        final Claims claims = getClaimsFromToken(token);
        if (claims == null) {
            return null;
        }
        String username = claims.getSubject();
        String userId = claims.get(CLAIM_KEY_USER_ID).toString();
        return UserDetail.builder().id(Long.parseLong(userId)).username(username).build();
    }

    public UserDetail getUserDetailFromSecurityContext() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (UserDetail) authentication.getPrincipal();
    }

    public String generateAccessToken(UserDetail userDetail) {

        String token = generateToken(userDetail, accessTokenExpiration);
        tokenMap.put(String.valueOf(userDetail.getId()), token);
        return token;
    }

    public String generateRefreshToken(UserDetail userDetail) {
        return generateToken(userDetail, refreshTokenExpiration);
    }

    public Date getExpirationDateFromToken(String token) {
        Date expiration;
        try {
            final Claims claims = getClaimsFromToken(token);
            expiration = claims.getExpiration();
        } catch (Exception e) {
            expiration = null;
        }
        return expiration;
    }

    private Claims getClaimsFromToken(String token) {
        Claims claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            claims = null;
        }
        return claims;
    }

    private Date generateExpirationDate(long expiration) {
        return new Date(System.currentTimeMillis() + expiration * 1000);
    }

    public Boolean isAccessTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        if (expiration == null) {
            return true;
        }
        return expiration.before(new Date());
    }

    public Boolean isRefreshTokenExpired(String refreshToken) {
        Date expiration;
        try {
            expiration = Jwts.parser().setSigningKey(secret).parseClaimsJws(refreshToken).getBody().getExpiration();
        } catch (ExpiredJwtException e) {
            return true;
        }
        return expiration.before(new Date());
    }

    public String refreshToken(String refreshToken) {
        if (isRefreshTokenExpired(refreshToken)) {
            return null;
        }
        String token;
        synchronized (this) {
            final UserDetail userDetail = getUserDetailFromToken(refreshToken);
            if (userDetail == null) {
                return null;
            }
            String userId = String.valueOf(userDetail.getId());
            token = tokenMap.get(userId);
            if (isAccessTokenExpired(token)) {
                token = generateAccessToken(userDetail);
                tokenMap.put(userId, token);
            }
        }
        return token;
    }

    public boolean checkValidToken(String token, String userId) {
        return tokenMap.containsKey(userId) && tokenMap.get(userId).equals(token);
    }

    public boolean removeToken(String token) {
        final UserDetail userDetail = getUserDetailFromToken(token);
        if (tokenMap.containsKey(userDetail.getId()) && tokenMap.get(userDetail.getId()).equals(token)) {
            tokenMap.remove(userDetail.getId());
            return true;
        }
        return false;
    }

    private Map<String, Object> generateClaims(UserDetail userDetail) {
        Map<String, Object> claims = new HashMap<>(8);
        claims.put(CLAIM_KEY_USER_ID, String.valueOf(userDetail.getId()));
        return claims;
    }

    private String generateToken(UserDetail userDetail, long expiration) {
        Map<String, Object> claims = generateClaims(userDetail);
        String subject = userDetail.getUsername();
        String userId = String.valueOf(userDetail.getId());
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setId(userId)
                .setIssuedAt(new Date())
                .setExpiration(generateExpirationDate(expiration))
                .compressWith(CompressionCodecs.DEFLATE)
                .signWith(SIGNATURE_ALGORITHM, secret)
                .compact();
    }
}
