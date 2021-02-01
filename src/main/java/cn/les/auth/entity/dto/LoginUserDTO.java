package cn.les.auth.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;

/**
 * @author Joetao
 * @Desc 用户，一个用户可以有多个角色
 * @time 2020/3/19 3:18 PM
 * @Email cutesimba@163.com
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginUserDTO {
    @Size(min=5, max=16)
    private String username;
    @Size(min=6, max=16)
    private String password;
}
