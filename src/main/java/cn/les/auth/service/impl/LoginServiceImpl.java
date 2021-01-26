package cn.les.auth.service.impl;

import cn.les.auth.entity.ResultCode;
import cn.les.auth.entity.ResultJson;
import cn.les.auth.entity.UserDetail;
import cn.les.auth.entity.vo.UserVO;
import cn.les.auth.exception.CustomException;
import cn.les.auth.service.LoginService;
import cn.les.auth.utils.JwtUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

/**
 * @author Joetao
 * @time 2021/1/26 11:37 上午
 * @Email cutesimba@163.com
 */
@Service
public class LoginServiceImpl implements LoginService {
    private final JwtUtils jwtTokenUtil;
    private final AuthenticationManager authenticationManager;

    public LoginServiceImpl(JwtUtils jwtTokenUtil, AuthenticationManager authenticationManager) {
        this.jwtTokenUtil = jwtTokenUtil;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public UserVO login(String userName, String password) {
        //用户验证
        final Authentication authentication = authenticate(userName, password);
        //存储认证信息
        SecurityContextHolder.getContext().setAuthentication(authentication);
        //生成token
        final UserDetail userDetail = (UserDetail) authentication.getPrincipal();

        final String token = jwtTokenUtil.generateAccessToken(userDetail);
        final String refreshToken = jwtTokenUtil.generateRefreshToken(userDetail);
        return UserVO.builder()
                .id(userDetail.getId())
                .username(userDetail.getUsername())
                .token(token)
                .refreshToken(refreshToken)
                .build();
    }


    private Authentication authenticate(String username, String password) {
        try {
            //该方法会去调用userDetailsService.loadUserByUsername()去验证用户名和密码，如果正确，则存储该用户名密码到“security 的 context中”
            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException | BadCredentialsException e) {
            throw new CustomException(ResultJson.failure(ResultCode.UNAUTHORIZED, "用户名或密码无效"));
        }
    }
}
