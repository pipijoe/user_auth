package cn.les.auth.repo;

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
