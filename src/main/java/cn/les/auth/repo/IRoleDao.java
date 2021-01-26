package cn.les.auth.repo;

import cn.les.auth.entity.auth.RoleDO;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author joetao
 */
public interface IRoleDao extends JpaRepository<RoleDO, Long> {
}
