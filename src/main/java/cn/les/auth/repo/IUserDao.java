package cn.les.auth.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author joetao
 */
public interface IUserDao extends JpaRepository<UserDO, Long> {
    /**
     * 根据用户名和删除状态查询用户
     *
     * @param username 用户名
     * @param deleteAt 删除状态
     * @return 用户信息
     */
    Optional<UserDO> findByUsernameAndDeleteAtEquals(String username, Long deleteAt);

    /**
     * 根据用户id和删除状态查询用户
     *
     * @param id 用户id
     * @param deleteAt 删除状态
     * @return 用户信息
     */
    Optional<UserDO> findByIdAndDeleteAtEquals(Long id, Long deleteAt);

}
