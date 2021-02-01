package cn.les.auth.entity.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * @author Joetao
 * @time 2021/2/1 9:03 上午
 * @Email cutesimba@163.com
 */
@Data
public class RoleDTO {
    private Long id;
    @NotNull(message = "name不能为空")
    @Size(min = 6, max = 16, message = "name长度在6-16之间")
    private String roleName;
    @NotNull(message = "roleName不能为空")
    @Size(min = 2, max = 12, message = "roleName长度在2-12之间")
    private String roleNameZh;
    private List<Long> menuIds;
}
