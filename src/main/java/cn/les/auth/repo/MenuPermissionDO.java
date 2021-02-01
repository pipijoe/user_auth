package cn.les.auth.repo;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author joetao
 */
@Entity
@Table(name="sys_menu_permission")
@Data
public class MenuPermissionDO {
    @Id
    private Long id;
    private Long menuId;
    private Long permissionId;
}
