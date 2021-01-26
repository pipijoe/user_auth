package cn.les.auth.repo;

import cn.les.auth.entity.auth.UserRoleDO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author joetao
 */
public interface IUserRoleDao extends JpaRepository<UserRoleDO, Long> {
    List<UserRoleDO> findByUserId(Long userId);
    List<UserRoleDO> findByRoleId(Long roleId);
    void deleteByRoleIdIn(List<Long> roleIdList);
    void deleteByRoleId(Long roleId);
}
