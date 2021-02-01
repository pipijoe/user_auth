package cn.les.auth.controller;

import cn.les.auth.entity.dto.MenuDTO;
import cn.les.auth.entity.ResultJson;
import cn.les.auth.entity.vo.UserMenuVO;
import cn.les.auth.service.MenuService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Joetao
 * @time 2021/1/28 2:47 下午
 * @Email cutesimba@163.com
 */
@RequestMapping("/api/v1")
@RestController
public class MenuController {
    private final MenuService menuService;

    public MenuController(MenuService menuService) {
        this.menuService = menuService;
    }

    @GetMapping("/menus")
    public ResultJson<UserMenuVO> getMenus() {
        return ResultJson.ok(menuService.findUserMenu());
    }

    @PostMapping("/menus")
    public ResultJson<Long> addMenus(MenuDTO menuDTO) {
        Long menuId = menuService.addMenu(menuDTO);
        return ResultJson.ok(menuId);
    }
}
