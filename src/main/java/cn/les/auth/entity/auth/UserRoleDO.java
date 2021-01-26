package cn.les.auth.entity.auth;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author joetao
 */
@Entity
@Table(name="sys_user_role")
@Data
public class UserRoleDO {
    @Id
    private Long id;
    private Long userId;
    private Long roleId;
}
