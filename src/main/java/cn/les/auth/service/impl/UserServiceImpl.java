package cn.les.auth.service.impl;

import cn.les.auth.dto.UserDTO;
import cn.les.auth.entity.ResultCode;
import cn.les.auth.entity.ResultJson;
import cn.les.auth.entity.auth.UserDO;
import cn.les.auth.entity.auth.UserRoleDO;
import cn.les.auth.exception.CustomException;
import cn.les.auth.repo.IUserDao;
import cn.les.auth.repo.IUserRoleDao;
import cn.les.auth.service.UserService;
import cn.les.auth.utils.JwtUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author Joetao
 * @time 2021/1/28 3:03 下午
 * @Email cutesimba@163.com
 */
@Service
public class UserServiceImpl implements UserService {
    private final JwtUtils jwtUtils;
    private final IUserDao userDao;
    private final IUserRoleDao userRoleDao;

    public UserServiceImpl(JwtUtils jwtUtils, IUserDao userDao, IUserRoleDao userRoleDao) {
        this.jwtUtils = jwtUtils;

        this.userDao = userDao;
        this.userRoleDao = userRoleDao;
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
        List<Long> roleIds = userDTO.getRoleIds();
        if (!roleIds.isEmpty()) {
            List<UserRoleDO> userDOList = new ArrayList<>();
            for (Long id : roleIds) {
                userDOList.add(UserRoleDO.builder().roleId(id).userId(userDO.getId()).build());
            }
            userRoleDao.saveAll(userDOList);
        }
        return userDO.getId();
    }



}
