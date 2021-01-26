package cn.les.auth.repo;

import cn.les.auth.entity.auth.UserDO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author joetao
 */
public interface IUserDao extends JpaRepository<UserDO, Long> {
    Optional<UserDO> findByUsernameAndDeleteAtEquals(String username, Long deleteAt);

    Optional<UserDO> findByIdAndDeleteAtEquals(Long id, Long deleteAt);
}
