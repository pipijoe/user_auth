package cn.les.auth.service.impl;

import cn.les.auth.entity.UserDetail;
import cn.les.auth.entity.user.UserIndex;
import cn.les.auth.service.UserService;
import cn.les.auth.utils.JwtUtils;
import org.springframework.stereotype.Service;

/**
 * @author Joetao
 * @time 2021/1/28 3:03 下午
 * @Email cutesimba@163.com
 */
@Service
public class UserServiceImpl implements UserService {
    private final JwtUtils jwtUtils;

    public UserServiceImpl(JwtUtils jwtUtils) {
        this.jwtUtils = jwtUtils;
    }

    @Override
    public UserIndex getUserIndex() {
        UserDetail userDetail = jwtUtils.getUserDetailFromAuthContext();
        Long userId = userDetail.getId();

        return UserIndex.builder().id(userId).name(userDetail.getUsername()).nickname(userDetail.getNickname()).build();
    }
}
