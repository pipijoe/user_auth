package cn.les.auth.controller;

import cn.les.auth.entity.ResultJson;
import org.springframework.web.bind.annotation.*;

/**
 * @author Joetao
 * @time 2021/1/28 2:39 下午
 * @Email cutesimba@163.com
 */
@RequestMapping("/api/v1")
@RestController
public class RoleController {
    @GetMapping("/roles")
    public ResultJson getRoles() {
        return ResultJson.ok();
    }

    @GetMapping("/roles/{id}")
    public ResultJson getRoleById(@PathVariable String id) {
        return ResultJson.ok();
    }

    @PutMapping("/roles")
    public ResultJson updateRole() {
        return ResultJson.ok();
    }

    @PostMapping("/roles")
    public ResultJson addRole() {
        return ResultJson.ok();
    }
}
