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
@Table(name="sys_user")
@DynamicInsert
@DynamicUpdate
@Data
public class UserDO {
    @Id
    private Long id;
    private String username;
    private String nickname;
    private String password;
    private Integer state;
    private Long deleteAt;
}
