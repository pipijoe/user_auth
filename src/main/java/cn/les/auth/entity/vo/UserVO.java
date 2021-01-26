package cn.les.auth.entity.vo;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * @author Joetao
 * @time 2020/3/20 3:52 PM
 * @Email cutesimba@163.com
 */
@Data
@Builder
public class UserVO {
    private Long id;
    private String username;
    private String token;
    private String refreshToken;
}
