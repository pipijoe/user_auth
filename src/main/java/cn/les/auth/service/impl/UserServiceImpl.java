package cn.les.auth.service.impl;

import cn.les.auth.dto.UserDTO;
import cn.les.auth.entity.ResultCode;
import cn.les.auth.entity.ResultJson;
import cn.les.auth.entity.UserDetail;
import cn.les.auth.entity.auth.MenuDO;
import cn.les.auth.entity.auth.UserDO;
import cn.les.auth.entity.user.Menu;
import cn.les.auth.entity.vo.UserMenuVO;
import cn.les.auth.entity.vo.UserVO;
import cn.les.auth.exception.CustomException;
import cn.les.auth.repo.IMenuDao;
import cn.les.auth.repo.IUserDao;
import cn.les.auth.service.UserService;
import cn.les.auth.utils.JwtUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Joetao
 * @time 2021/1/28 3:03 下午
 * @Email cutesimba@163.com
 */
@Service
public class UserServiceImpl implements UserService {
    private final JwtUtils jwtUtils;
    private final IUserDao userDao;

    public UserServiceImpl(JwtUtils jwtUtils, IUserDao userDao) {
        this.jwtUtils = jwtUtils;

        this.userDao = userDao;
    }

    @Override
    public Long addUser(UserDTO userDTO) {
        Optional<UserDO> userDOOptional = userDao.findByUsernameAndDeleteAtEquals(userDTO.getUsername(), 0L);
        if (userDOOptional.isPresent()) {
            throw new CustomException(ResultJson.failure(ResultCode.BAD_REQUEST, "该用户已存在"));
        }
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String pwdEncode = encoder.encode(userDTO.getPassword());
        UserDO userDO = new UserDO();
        BeanUtils.copyProperties(userDTO, userDO);
        userDO.setPassword(pwdEncode);
        userDO = userDao.save(userDO);
        return userDO.getId();
    }



}
