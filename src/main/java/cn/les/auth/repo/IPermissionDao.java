package cn.les.auth.repo;

import cn.les.auth.entity.dto.RolePermissionDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author joetao
 */
public interface IPermissionDao extends JpaRepository<PermissionDO, Long> {
    /**
     * 查询用户具有的所有权限，不需要类型为权限组的权限
     *
     * @param userId 用户id
     * @return 权限列表
     */
    @Query(value="select per from PermissionDO per, " +
            "UserRoleDO userRole, RoleMenuDO roleMenu, MenuPermissionDO menuPer " +
            "where userRole.roleId = roleMenu.roleId " +
            "and roleMenu.menuId = menuPer.menuId " +
            "and menuPer.menuId = per.id and per.type = 0 and userRole.userId = ?1 ")
    List<PermissionDO> findAllByUserIdWithoutGroup(Long userId);

    /**
     * 查询所有用户角色权限
     *
     * @return 角色权限列表
     */
    @Query(value="select new cn.les.auth.dto.RolePermissionDTO(roleMenu.roleId, menuPer.permissionId) " +
            "from RoleMenuDO roleMenu, MenuPermissionDO menuPer " +
            "where roleMenu.menuId = menuPer.menuId")
    List<RolePermissionDTO> findAllRolePermissions();

    /**
     * 根据权限id列表查询权限
     *
     * @param permissionIds 权限id列表
     * @return 权限列表
     */
    List<PermissionDO> findByIdIn(List<Long> permissionIds);
}
