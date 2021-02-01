package cn.les.auth.repo;

import cn.les.auth.entity.auth.RoleDO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
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

    /**
     * 根据roleIds查询roles
     *
     * @param roleIds 角色id
     * @return 角色列表
     */
    List<RoleDO> findByIdIn(List<Long> roleIds);
}
