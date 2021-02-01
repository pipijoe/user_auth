package cn.les.auth.controller;

import cn.les.auth.entity.dto.UserDTO;
import cn.les.auth.entity.ResultJson;
import cn.les.auth.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 1. 查看所有用户列表（管理员、超管）
 * 2. 查看自己的详细信息（登录后获取动态菜单和基本信息）
 * 3. 修改账户信息
 * 4. 添加用户（管理员、超管）
 * 5. 修改密码
 * 6. 重置密码（管理员）
 *
 * @author Joetao
 * @time 2021/1/26 11:21 上午
 * @Email cutesimba@163.com
 */
@RequestMapping("/api/v1")
@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public ResultJson getUsers() {
        return ResultJson.ok();
    }

    @GetMapping("/users/{id}")
    public ResultJson getUsersProfileById(@PathVariable String id) {
        return ResultJson.ok();
    }

    @PutMapping("/users")
    public ResultJson updateProfile() {
        return ResultJson.ok();
    }

    @PutMapping("/users/password")
    public ResultJson modifyPwd(@RequestParam("oldPwd") String oldPwd, @RequestParam("newPwd") String newPwd) {
        return ResultJson.ok();
    }

    @PostMapping("/users")
    public ResultJson<Long> addUsers(@Valid @RequestBody UserDTO userDTO) {
        Long userId = userService.addUser(userDTO);
        return ResultJson.ok(userId);
    }

    @PostMapping("/users/{id}/roles")
    public ResultJson addUserRoles(@PathVariable Long id, @RequestBody List<Long> roleIds) {
        userService.addUserRoles(id, roleIds);
        return ResultJson.ok();
    }
}
