package cn.les.auth.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author Joetao
 * @time 2021/1/29 9:11 上午
 * @Email cutesimba@163.com
 */
public interface IMenuDao extends JpaRepository<MenuDO, Long> {
    /**
     * 根据sort字段排序查询menu列表
     *
     * @return menu列表
     */
    List<MenuDO> findAllByOrderBySort();

    /**
     * 根据用户id查询menuId
     *
     * @param userId 用户id
     * @return 菜单id
     */
    @Query(value = "select menu.id from MenuDO menu, UserRoleDO userRole, RoleMenuDO roleMenu " +
            "where menu.id = roleMenu.menuId and roleMenu.roleId = userRole.roleId " +
            "and userRole.userId = ?1")
    List<Long> findAllMenuIdsByUserId(Long userId);

    /**
     * 根据菜单id查询菜单列表
     *
     * @param menuIds 菜单id列表
     * @return 菜单列表
     */
    List<MenuDO> findByIdIn(List<Long> menuIds);
}
