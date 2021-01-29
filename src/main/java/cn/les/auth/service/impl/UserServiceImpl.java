package cn.les.auth.service.impl;

import cn.les.auth.entity.UserDetail;
import cn.les.auth.entity.auth.MenuDO;
import cn.les.auth.entity.user.Menu;
import cn.les.auth.entity.user.UserIndex;
import cn.les.auth.repo.IMenuDao;
import cn.les.auth.service.UserService;
import cn.les.auth.utils.JwtUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Joetao
 * @time 2021/1/28 3:03 下午
 * @Email cutesimba@163.com
 */
@Service
public class UserServiceImpl implements UserService {
    private final JwtUtils jwtUtils;
    private final IMenuDao menuDao;

    public UserServiceImpl(JwtUtils jwtUtils, IMenuDao menuDao) {
        this.jwtUtils = jwtUtils;
        this.menuDao = menuDao;
    }

    @Override
    public UserIndex getUserIndex() {
        UserDetail userDetail = jwtUtils.getUserDetailFromAuthContext();
        Long userId = userDetail.getId();
        List<Long> menuIds = menuDao.findAllMenuIdsByUserId(userId);
        List<MenuDO> menuDOAll = menuDao.findAllByOrderBySort();
        List<Menu> userMenu = buildUserMenu(menuIds, menuDOAll);
        return UserIndex.builder()
                .id(userId)
                .menus(userMenu)
                .name(userDetail.getUsername())
                .nickname(userDetail.getNickname())
                .build();
    }

    private List<Menu> buildUserMenu(List<Long> menuIds, List<MenuDO> menuDOAll) {
        Map<Long, Menu> menuMap = menuDOAll.stream().map(menuDo -> {
            Menu menu = new Menu();
            BeanUtils.copyProperties(menuDo, menu);
            return menu;
        }).collect(Collectors.toMap(Menu::getId, menu -> menu));

        List<Menu> userMenu = new ArrayList<>();
        for (Long id: menuIds) {
            if (menuMap.containsKey(id)) {
                userMenu.add(menuMap.get(id));
            }
        }
        return parseMenuTree(userMenu);
    }

    private List<Menu> parseMenuTree(List<Menu> userMenu) {
        List<Menu> menuTree = new ArrayList<>();
        Iterator<Menu> menuIterator = userMenu.iterator();
        while (menuIterator.hasNext()) {
            Menu menu = menuIterator.next();
            if (menu.getParentId() == 0) {
                menuTree.add(menu);
                menuIterator.remove();
            }
        }
        menuTree.forEach(parent -> parent.setChildren(userMenu));
        return menuTree;
    }
}
