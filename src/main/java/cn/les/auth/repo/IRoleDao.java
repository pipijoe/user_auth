package cn.les.auth.repo;

import cn.les.auth.entity.auth.RoleDO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author joetao
 */
public interface IRoleDao extends JpaRepository<RoleDO, Long> {
    /**
     * 根据角色名称查找角色
     *
     * @param roleName 角色名称
     * @return 角色
     */
    Optional<RoleDO> findByRoleName(String roleName);
}
