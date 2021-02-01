package cn.les.auth.service;

import cn.les.auth.dto.RoleDTO;

/**
 * @author Joetao
 * @time 2021/2/1 9:09 上午
 * @Email cutesimba@163.com
 */
public interface RoleService {
    /**
     * 添加角色
     *
     * @param roleDTO 角色
     * @return 角色id
     */
    Long addRole(RoleDTO roleDTO);
}
