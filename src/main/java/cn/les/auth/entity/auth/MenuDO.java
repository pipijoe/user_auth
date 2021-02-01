package cn.les.auth.entity.auth;

import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable=false)
    private Long parentId;
    @Column(nullable=false)
    private Integer type;
    private String menuName;
    private String description;
    private String path;
    private String menuIcon;
    @Column(nullable=false)
    private Integer sort;
}
