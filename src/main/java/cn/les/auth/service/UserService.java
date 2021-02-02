package cn.les.auth.service;

import cn.les.auth.entity.dto.UserDTO;
import cn.les.auth.entity.vo.UserVO;

import java.util.List;

/**
 * @author Joetao
 * @time 2021/1/28 3:02 下午
 * @Email cutesimba@163.com
 */
public interface UserService {
    /**
     * 添加用户
     *
     * @param userDTO 添加用户的信息
     * @return 新用户信息
     */
    Long addUser(UserDTO userDTO);

    /**
     * 给用户设置角色
     *
     * @param userId 用户id
     * @param roleIds 用户角色id列表
     */
    void addUserRoles(Long userId, List<Long> roleIds);

    /**
     * 查询当前登录用户信息
     *
     * @return 当前用户信息
     */
    UserVO getMe();
}
