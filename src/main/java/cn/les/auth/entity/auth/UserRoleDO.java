package cn.les.auth.entity.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author joetao
 */
@Entity
@Table(name="sys_user_role")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRoleDO {
    @Id
    private Long id;
    private Long userId;
    private Long roleId;
}
