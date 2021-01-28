package cn.les.auth.service;

import cn.les.auth.config.security.SecurityProps;
import cn.les.auth.entity.UserDetail;
import cn.les.auth.entity.auth.RoleDO;
import cn.les.auth.entity.auth.UserDO;
import cn.les.auth.repo.IRoleDao;
import cn.les.auth.repo.IUserDao;
import cn.les.auth.repo.IUserRoleDao;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 登陆身份认证
 * @author: JoeTao
 * createAt: 2018/9/14
 */
@Component(value="CustomUserDetailsService")
public class CustomUserDetailsServiceImpl implements UserDetailsService {
    @Resource
    private IUserDao userDao;

    @Resource
    private IRoleDao roleDao;
    @Resource
    private IUserRoleDao userRoleDao;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserDO> opt = userDao.findByUsernameAndDeleteAtEquals(username, 0L);
        if (!opt.isPresent()){
            throw new UsernameNotFoundException("用户名不存在");
        }
        UserDO user = opt.get();

        Set<GrantedAuthority> grantedAuthorities = userRoleDao.findByUserId(user.getId()).stream()
                .map(role -> new SimpleGrantedAuthority(roleDao.findById(role.getRoleId()).orElse(new RoleDO(0L, SecurityProps.ROLE_ANONYMOUS)).getRoleName()))
                .collect(Collectors.toSet());

        return UserDetail.builder()
                .id(user.getId())
                .username(user.getUsername())
                .password(user.getPassword())
                .nickname(user.getNickname())
                .enabled(user.getState() != 1)
                .authorities(Collections.unmodifiableSet(grantedAuthorities))
                .build();
    }
}
