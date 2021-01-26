package cn.les.auth.entity.auth;

import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author joetao
 */
@Entity
@Table(name="sys_menu")
@DynamicInsert
@DynamicUpdate
@Data
public class MenuDO {
    @Id
    private Long id;
    private Long parentId;
    private Integer type;
    private String menuName;
    private String description;
    private String path;
    private String extraPath;
    private String menuIcon;
    private Integer sort;
    private Boolean newWindow;
}
