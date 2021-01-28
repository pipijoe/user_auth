package cn.les.auth.controller;

import cn.les.auth.entity.ResultJson;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Joetao
 * @time 2021/1/28 2:48 下午
 * @Email cutesimba@163.com
 */
@RequestMapping("/api/v1")
@RestController
public class PermissionController {
    @GetMapping("/permissions")
    public ResultJson getPermissions() {
        return ResultJson.ok();
    }
}
