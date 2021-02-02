package cn.les.auth.repo;

import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String path;
    private String method;
}
