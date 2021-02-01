package cn.les.auth.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author joetao
 */
public interface IUserRoleDao extends JpaRepository<UserRoleDO, Long> {
    /**
     * 用户id查询用户角色对象列表
     *
     * @param userId 用户id
     * @return 用户角色id对应关系
     */
    List<UserRoleDO> findByUserId(Long userId);

    /**
     * 角色id查询用户角色对象列表
     *
     * @param roleId 角色id
     * @return 用户角色id对应关系
     */
    List<UserRoleDO> findByRoleId(Long roleId);

}
