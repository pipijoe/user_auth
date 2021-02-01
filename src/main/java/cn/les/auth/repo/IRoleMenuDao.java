package cn.les.auth.repo;

import cn.les.auth.entity.auth.RoleMenuDO;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Joetao
 * @time 2021/2/1 9:22 上午
 * @Email cutesimba@163.com
 */
public interface IRoleMenuDao extends JpaRepository<RoleMenuDO, Long> {
}
