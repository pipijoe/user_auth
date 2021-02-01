package cn.les.auth.entity.vo;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * @author Joetao
 * @time 2021/1/28 3:08 下午
 * @Email cutesimba@163.com
 */
@Data
@Builder
public class UserMenuVO {
    private Long id;
    private String name;
    private String nickname;
    private List<Menu> menus;
}
