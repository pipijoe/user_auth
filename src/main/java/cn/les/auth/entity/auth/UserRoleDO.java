package cn.les.auth.entity.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private Long roleId;
}
