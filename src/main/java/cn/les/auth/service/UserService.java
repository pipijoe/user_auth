package cn.les.auth.service;

import cn.les.auth.entity.user.UserIndex;

/**
 * @author Joetao
 * @time 2021/1/28 3:02 下午
 * @Email cutesimba@163.com
 */
public interface UserService {
    /**
     * 获取登录用户的基本信息和菜单
     *
     * @return 用户登录信息
     */
    UserIndex getUserIndex();
}
