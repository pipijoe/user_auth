package cn.les.auth.service;

import cn.les.auth.dto.UserDTO;
import cn.les.auth.entity.vo.UserMenuVO;
import cn.les.auth.entity.vo.UserVO;

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
}
