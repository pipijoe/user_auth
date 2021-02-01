package cn.les.auth.controller;

import cn.les.auth.entity.dto.RoleDTO;
import cn.les.auth.entity.ResultCode;
import cn.les.auth.entity.ResultJson;
import cn.les.auth.exception.CustomException;
import cn.les.auth.service.RoleService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Locale;

/**
 * @author Joetao
 * @time 2021/1/28 2:39 下午
 * @Email cutesimba@163.com
 */
@RequestMapping("/api/v1")
@RestController
public class RoleController {
    private final RoleService roleService;
    private final String rolePrefix = "ROLE_";

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

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
    public ResultJson addRole(@Valid @RequestBody RoleDTO roleDTO) {
        String roleName = roleDTO.getRoleName();
        if (!roleName.toUpperCase(Locale.ROOT).startsWith(rolePrefix)) {
            throw new CustomException(ResultJson.failure(ResultCode.BAD_REQUEST, "角色名称不正确，以ROLE_开头"));
        }
        Long roleId = roleService.addRole(roleDTO);
        return ResultJson.ok(roleId);
    }
}
