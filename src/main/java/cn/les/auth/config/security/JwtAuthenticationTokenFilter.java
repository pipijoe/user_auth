package cn.les.auth.config.security;

import cn.les.auth.entity.UserDetail;
import cn.les.auth.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * token校验
 * @author: JoeTao
 * createAt: 2018/9/14
 */
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Value("${jwt.header}")
    private String tokenHeader;

    @Value("${jwt.refresh_token_header}")
    private String refreshTokenHeader;

    @Resource
    private JwtUtils jwtUtils;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        String token = request.getHeader(this.tokenHeader);
        final String authTokenStart = "Bearer ";

        if (StringUtils.hasLength(token) && token.startsWith(authTokenStart)) {
            token = token.substring(authTokenStart.length());
        } else {
            token = null;
        }
        if (token != null && jwtUtils.isAccessTokenExpired(token)) {
            String refreshToken = request.getHeader(this.refreshTokenHeader);
            token = jwtUtils.refreshToken(refreshToken);
            if (token == null) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.setCharacterEncoding("UTF-8");
                response.setContentType("application/json; charset=utf-8");
            } else {
                response.setStatus(HttpServletResponse.SC_CREATED);
                response.setCharacterEncoding("UTF-8");
                response.setContentType("application/json; charset=utf-8");
                response.setHeader("token", token);
            }
        }
        UserDetail userDetail = jwtUtils.getUserDetailFromToken(token);
        if (userDetail != null && jwtUtils.checkValidToken(token, String.valueOf(userDetail.getId())) && SecurityContextHolder.getContext().getAuthentication() == null) {
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetail, null, userDetail.getAuthorities());
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        chain.doFilter(request, response);
    }
}
