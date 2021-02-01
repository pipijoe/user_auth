package cn.les.auth.repo;

import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

/**
 * @author joetao
 */
@Entity
@Table(name="sys_user")
@DynamicInsert
@DynamicUpdate
@Data
public class UserDO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String nickname;
    private String password;
    @Column(nullable=false)
    private Integer state;
    @Column(nullable=false)
    private Long deleteAt;
}
