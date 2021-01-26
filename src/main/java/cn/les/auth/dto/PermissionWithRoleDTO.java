package cn.les.auth.dto;

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
    private Set<Long> roleIds;
}
