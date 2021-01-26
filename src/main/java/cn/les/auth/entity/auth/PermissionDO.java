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
@Table(name="sys_permission")
@DynamicInsert
@DynamicUpdate
@Data
public class PermissionDO {
    @Id
    private Long id;
    private Long parentId;
    private Integer type;
    private String permissionName;
    private String path;
    private String method;
    private Integer sort;
}
