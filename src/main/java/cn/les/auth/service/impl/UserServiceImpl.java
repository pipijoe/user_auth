package cn.les.auth.service.impl;

import cn.les.auth.entity.UserDetail;
import cn.les.auth.entity.dto.UserDTO;
import cn.les.auth.entity.ResultCode;
import cn.les.auth.entity.ResultJson;
import cn.les.auth.entity.vo.UserVO;
import cn.les.auth.repo.RoleDO;
import cn.les.auth.repo.UserDO;
import cn.les.auth.repo.UserRoleDO;
import cn.les.auth.exception.CustomException;
import cn.les.auth.repo.IRoleDao;
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
    private final IRoleDao roleDao;

    public UserServiceImpl(JwtUtils jwtUtils, IUserDao userDao, IUserRoleDao userRoleDao, IRoleDao roleDao) {
        this.jwtUtils = jwtUtils;
        this.userDao = userDao;
        this.userRoleDao = userRoleDao;
        this.roleDao = roleDao;
    }

    @Override
    public Long addUser(UserDTO userDTO) {
        Optional<UserDO> userDOOptional = userDao.findByUsernameAndDeleteAtEquals(userDTO.getUsername(), 0L);
        if (userDOOptional.isPresent()) {
            throw new CustomException(ResultJson.failure(ResultCode.BAD_REQUEST, "该用户已存在"));
        }
        List<Long> roleIds = userDTO.getRoleIds();
        boolean hasRoleIds = roleIds != null && !roleIds.isEmpty();
        if (hasRoleIds) {
            List<RoleDO> roles = roleDao.findByIdIn(roleIds);
            if (roles.size() < roleIds.size()) {
                throw new CustomException(ResultJson.failure(ResultCode.BAD_REQUEST, "角色设置不符合要求"));
            }
        }
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String pwdEncode = encoder.encode(userDTO.getPassword());
        UserDO userDO = new UserDO();
        BeanUtils.copyProperties(userDTO, userDO);
        userDO.setPassword(pwdEncode);
        userDO = userDao.save(userDO);
        if (hasRoleIds) {
            List<UserRoleDO> userDOList = new ArrayList<>();
            for (Long id : roleIds) {
                userDOList.add(UserRoleDO.builder().roleId(id).userId(userDO.getId()).build());
            }
            userRoleDao.saveAll(userDOList);
        }
        return userDO.getId();
    }

    @Override
    public void addUserRoles(Long userId, List<Long> roleIds) {
        Optional<UserDO> userDOOptional = userDao.findById(userId);
        if (!userDOOptional.isPresent()) {
            throw new CustomException(ResultJson.failure(ResultCode.BAD_REQUEST, "该用户已存在"));
        }
        List<RoleDO> roles = roleDao.findByIdIn(roleIds);
        if (roleIds.size() == 0 || roles.size() < roleIds.size()) {
            throw new CustomException(ResultJson.failure(ResultCode.BAD_REQUEST, "角色设置不符合要求"));
        }
        List<UserRoleDO> userDOList = new ArrayList<>();
        for (Long roleId : roleIds) {
            userDOList.add(UserRoleDO.builder().roleId(roleId).userId(userId).build());
        }
        userRoleDao.saveAll(userDOList);
    }

    @Override
    public UserVO getMe() {
        UserDetail userDetail = jwtUtils.getUserDetailFromAuthContext();
        return UserVO
                .builder()
                .nickname(userDetail.getNickname())
                .id(userDetail.getId())
                .username(userDetail.getUsername())
                .token("")
                .refreshToken("")
                .build();
    }


}
