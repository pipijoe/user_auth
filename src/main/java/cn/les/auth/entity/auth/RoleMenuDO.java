package cn.les.auth.entity.auth;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author joetao
 */
@Entity
@Table(name="sys_role_menu")
@Data
public class RoleMenuDO {
    @Id
    private Long id;
    private Long roleId;
    private Long menuId;
}
