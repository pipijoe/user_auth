package cn.les.auth.entity.dto;

import lombok.Data;

import java.util.Set;

/**
 * @author joetao
 */
@Data
public class PermissionWithRoleDTO {
    private Long id;
    private String path;
    private String method;
    private Set<String> roles;
}
