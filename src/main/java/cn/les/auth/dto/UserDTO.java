package cn.les.auth.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * @author joetao
 */
@Data
public class UserDTO {
    private Long id;
    @NotNull(message = "username不能为空")
    @NotEmpty(message = "username不能为空")
    @Size(min = 2, max = 16, message = "username长度在2-16之间")
    private String username;
    @NotNull(message = "nickname不能为空")
    @NotEmpty(message = "nickname不能为空")
    @Size(min = 2, max = 16, message = "nickname长度在2-16之间")
    private String nickname;
    @NotNull(message = "password不能为空")
    @NotEmpty(message = "password不能为空")
    @Size(min = 8, max = 16, message = "password长度在8-16之间")
    private String password;
    private List<Long> roleIds;
}
