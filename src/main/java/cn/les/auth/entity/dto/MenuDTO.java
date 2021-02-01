package cn.les.auth.entity.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * @author Joetao
 * @time 2021/2/1 2:35 下午
 * @Email cutesimba@163.com
 */
@Data
public class MenuDTO {
    private Long parentId;
    @NotBlank(message = "menuName不能为空")
    private String menuName;
    private Integer type;
    private String description;
    private String path;
    private String menuIcon;
    private Integer sort;
    private List<Long> permissionId;
}
