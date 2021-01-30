package cn.les.auth.service;

import cn.les.auth.entity.vo.UserMenuVO;

/**
 * @author Joetao
 * @time 2021/1/30 8:24 下午
 * @Email cutesimba@163.com
 */
public interface MenuService {
    /**
     * 查询用户菜单
     *
     * @return 用户菜单
     */
    UserMenuVO findUserMenu();
}
