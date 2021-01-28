package cn.les.auth.controller;

import cn.les.auth.entity.ResultJson;
import cn.les.auth.entity.user.UserIndex;
import cn.les.auth.service.UserService;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/users/me")
    public ResultJson getUsersProfile() {
        UserIndex userIndex = userService.getUserIndex();
        return ResultJson.ok(userIndex);
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
    public ResultJson addUsers() {
        return ResultJson.ok();
    }
}
