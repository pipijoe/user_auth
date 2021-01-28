package cn.les.auth.entity.user;

import lombok.Data;

import java.util.List;

/**
 * @author Joetao
 * @time 2021/1/28 3:06 下午
 * @Email cutesimba@163.com
 */
@Data
public class Menu {
    private String name;
    private String icon;
    private int type;
    private List<Menu> children;
}
