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
@Table(name="sys_role")
@DynamicInsert
@DynamicUpdate
@Data
public class RoleDO {
    @Id
    private Long id;
    private String roleName;
}
