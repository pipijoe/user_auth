package cn.les.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author joetao
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RolePermissionDTO {
    private Long roleId;
    private Long permissionId;
}
