package cn.les.auth.controller;

import cn.les.auth.entity.ResultJson;
import cn.les.auth.entity.dto.PermissionDTO;
import cn.les.auth.service.PermissionService;
import org.springframework.web.bind.annotation.*;

/**
 * @author Joetao
 * @time 2021/1/28 2:48 下午
 * @Email cutesimba@163.com
 */
@RequestMapping("/api/v1")
@RestController
public class PermissionController {
    private final PermissionService permissionService;

    public PermissionController(PermissionService permissionService) {
        this.permissionService = permissionService;
    }

    @GetMapping("/permissions")
    public ResultJson getPermissions() {
        return ResultJson.ok();
    }

    @PostMapping("/permissions")
    public ResultJson<Long> addPermissions(@RequestBody PermissionDTO permissionDTO) {
        Long permissionId = permissionService.addPermission(permissionDTO);
        return ResultJson.ok(permissionId);
    }
}
