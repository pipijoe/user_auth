package cn.les.auth.entity;

import lombok.Data;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author Joetao
 * @time 2021/1/28 3:06 下午
 * @Email cutesimba@163.com
 */
@Data
public class Menu {
    private Long id;
    private Long parentId;
    private String menuName;
    private String menuIcon;
    private Integer type;
    private List<Menu> children = new ArrayList<>();

    public void setChildren(List<Menu> list) {
        if (list.isEmpty()) {
            return;
        }
        Iterator<Menu> menuIterator = list.iterator();
        while (menuIterator.hasNext()) {
            Menu menu = menuIterator.next();
            if (menu.getParentId().equals(this.id)) {
                children.add(menu);
                menuIterator.remove();
            }
        }
        children.forEach(menu -> menu.setChildren(list));
    }
}
