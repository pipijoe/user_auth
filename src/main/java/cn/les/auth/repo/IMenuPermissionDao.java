package cn.les.auth.repo;

import cn.les.auth.entity.auth.MenuPermissionDO;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Joetao
 * @time 2021/2/1 4:23 下午
 * @Email cutesimba@163.com
 */
public interface IMenuPermissionDao extends JpaRepository<MenuPermissionDO, Long> {
}
