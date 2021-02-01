package cn.les.auth.service.impl;

import cn.les.auth.entity.dto.MenuDTO;
import cn.les.auth.entity.ResultCode;
import cn.les.auth.entity.ResultJson;
import cn.les.auth.entity.UserDetail;
import cn.les.auth.repo.MenuDO;
import cn.les.auth.repo.MenuPermissionDO;
import cn.les.auth.repo.PermissionDO;
import cn.les.auth.entity.Menu;
import cn.les.auth.entity.vo.UserMenuVO;
import cn.les.auth.exception.CustomException;
import cn.les.auth.repo.IMenuDao;
import cn.les.auth.repo.IMenuPermissionDao;
import cn.les.auth.repo.IPermissionDao;
import cn.les.auth.service.MenuService;
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
 * @time 2021/1/30 8:27 下午
 * @Email cutesimba@163.com
 */
@Service
public class MenuServiceImpl implements MenuService {
    private final JwtUtils jwtUtils;
    private final IMenuDao menuDao;
    private final IMenuPermissionDao menuPermissionDao;
    private final IPermissionDao permissionDao;

    public MenuServiceImpl(JwtUtils jwtUtils, IMenuDao menuDao, IMenuPermissionDao menuPermissionDao, IPermissionDao permissionDao) {
        this.jwtUtils = jwtUtils;
        this.menuDao = menuDao;
        this.menuPermissionDao = menuPermissionDao;
        this.permissionDao = permissionDao;
    }

    @Override
    public UserMenuVO findUserMenu() {
        UserDetail userDetail = jwtUtils.getUserDetailFromAuthContext();
        Long userId = userDetail.getId();
        List<Long> menuIds = menuDao.findAllMenuIdsByUserId(userId);
        List<MenuDO> menuDOAll = menuDao.findAllByOrderBySort();
        List<Menu> userMenu = buildUserMenu(menuIds, menuDOAll);
        return UserMenuVO.builder()
                .id(userId)
                .menus(userMenu)
                .name(userDetail.getUsername())
                .nickname(userDetail.getNickname())
                .build();
    }

    @Override
    public Long addMenu(MenuDTO menuDTO) {
        List<Long> permissionIds = menuDTO.getPermissionId();
        if (permissionIds != null && !permissionIds.isEmpty()) {
            List<PermissionDO> permissionDOList = permissionDao.findByIdIn(permissionIds);
            if (permissionDOList.size() < permissionIds.size()) {
                throw new CustomException(ResultJson.failure(ResultCode.BAD_REQUEST, "权限设置不符合要求"));
            }
        }
        MenuDO menuDO = new MenuDO();
        BeanUtils.copyProperties(menuDTO, menuDO);
        menuDO = menuDao.save(menuDO);
        if (!permissionIds.isEmpty()) {
            List<MenuPermissionDO> menuPermissionDOList = new ArrayList<>();
            for (Long permissionId : permissionIds) {
                MenuPermissionDO menuPermissionDO = new MenuPermissionDO();
                menuPermissionDO.setMenuId(menuDO.getId());
                menuPermissionDO.setPermissionId(permissionId);
            }
            menuPermissionDao.saveAll(menuPermissionDOList);
        }
        return menuDO.getId();
    }

    private List<Menu> buildUserMenu(List<Long> menuIds, List<MenuDO> menuDOAll) {
        Map<Long, Menu> menuMap = menuDOAll.stream().map(menuDo -> {
            Menu menu = new Menu();
            BeanUtils.copyProperties(menuDo, menu);
            return menu;
        }).collect(Collectors.toMap(Menu::getId, menu -> menu));

        List<Menu> userMenu = new ArrayList<>();
        for (Long id : menuIds) {
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
