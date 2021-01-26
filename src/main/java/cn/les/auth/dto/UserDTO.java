package cn.les.auth.dto;

import lombok.Data;

import java.util.List;

/**
 * @author joetao
 */
@Data
public class UserDTO {
    private Long id;
    private Long departId;
    private String departName;
    private String username;
    private String nickname;
    private String password;
    private Integer state;
    private List<String> roles;

    public UserDTO() { }

    public UserDTO(
            Long id,
            Long departId,
            String departName,
            String username,
            String nickname,
            Integer state) {
        this.id = id;
        this.departId = departId;
        this.departName = departName;
        this.username = username;
        this.nickname = nickname;
        this.state = state;
    }
}
