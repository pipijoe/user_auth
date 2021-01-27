package cn.les.auth.config.security;

import cn.les.auth.dto.RolePermissionDTO;
import cn.les.auth.entity.auth.PermissionDO;
import cn.les.auth.entity.auth.RoleDO;
import cn.les.auth.repo.IPermissionDao;
import cn.les.auth.repo.IRoleDao;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 权限验证
 * <p>
 * Date: 2019/2/28
 *
 * @author joetao
 */
@Service
public class MyInvocationSecurityMetadataSourceService implements FilterInvocationSecurityMetadataSource {
    @Resource
    private IPermissionDao permissionDao;

    @Resource
    private IRoleDao roleDao;

    private final Map<String, Set<PermissionDO>> rolePermissionMap = new HashMap<>();

    @Override
    public Collection<ConfigAttribute> getAttributes(Object o) throws IllegalArgumentException {
        HttpServletRequest request = ((FilterInvocation) o).getHttpRequest();
        //获取用户角色
        //获取该用户访问该资源权限，没有获取到则返回匿名用户权限
        Set<String> roles = new HashSet<>();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (SecurityProps.ANONYMOUS_USER.equals(authentication.getPrincipal())) {
            return SecurityConfig.createList(SecurityProps.ROLE_ANONYMOUS);
        }
        authentication.getAuthorities().forEach(a -> rolePermissionMap.get(a.getAuthority()).forEach(c -> {
            AntPathRequestMatcher matcher = new AntPathRequestMatcher(c.getPath(), c.getMethod());
            if (matcher.matches(request)) {
                roles.add(a.getAuthority());
            }
        }));
        if (roles.size() > 0) {
            return roles
                    .stream()
                    .map(SecurityConfig::new)
                    .collect(Collectors.toList());
        }
        return SecurityConfig.createList(SecurityProps.ROLE_ANONYMOUS);
    }

    /**
     * 加载所有权限资源
     *
     * @return 权限资源列表
     */
    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        initRolePermissionMap();
        return null;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }

    public void initRolePermissionMap() {
        List<RoleDO> roleDOList = roleDao.findAll();
        Map<Long, String> idRoleMap = new HashMap<>();
        roleDOList.forEach(c -> idRoleMap.put(c.getId(), c.getRoleName()));
        List<RolePermissionDTO> rolePermissionDTOList = permissionDao.findAllRolePermissions();
        rolePermissionDTOList.forEach(rp -> {
            String roleName = idRoleMap.get(rp.getRoleId());
            rolePermissionMap.putIfAbsent(roleName, new HashSet<>());
            Optional<PermissionDO> permissionDOOptional = permissionDao.findById(rp.getPermissionId());
            permissionDOOptional.ifPresent(permissionDO -> rolePermissionMap.get(roleName).add(permissionDO));
        });
    }
}
