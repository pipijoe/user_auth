package cn.les.auth.service;

import cn.les.auth.entity.dto.MenuDTO;
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

    /**
     * 添加菜单
     *
     * @param menuDTO 菜单参数
     * @return 菜单编号
     */
    Long addMenu(MenuDTO menuDTO);
}
