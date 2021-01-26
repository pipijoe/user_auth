package cn.les.auth.config.security;

import cn.les.auth.dto.PermissionWithRoleDTO;
import cn.les.auth.dto.RolePermissionDTO;
import cn.les.auth.repo.IPermissionDao;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 权限验证
 *
 * Date: 2019/2/28
 * @author joetao
 */
@Service
public class MyInvocationSecurityMetadataSourceService implements FilterInvocationSecurityMetadataSource {
    @Resource
    private IPermissionDao permissionDao;

    @Override
    public Collection<ConfigAttribute> getAttributes(Object o) throws IllegalArgumentException {
        HttpServletRequest request = ((FilterInvocation) o).getHttpRequest();
        AntPathRequestMatcher matcher;
        List<RolePermissionDTO> rolePermissionDTOS = permissionDao.findAllRolePermissions();
        List<PermissionWithRoleDTO> permissionWithRoles = new ArrayList<>();
        //检查功能权限
        Set<Long> roleIds = new HashSet<>();
        boolean isMatch = false;
        for(PermissionWithRoleDTO permission : permissionWithRoles) {
            if (!StringUtils.hasLength(permission.getPath())) {
                continue;
            }
            matcher = new AntPathRequestMatcher(permission.getPath(), permission.getMethod());
            if (matcher.matches(request)) {
                isMatch = true;
                roleIds.addAll(permission.getRoleIds());
            }
        }
        if (isMatch) {
            roleIds.add(0L);
            return roleIds
                    .stream()
                    .map(roleId -> new SecurityConfig(roleId.toString()))
                    .collect(Collectors.toList());
        }
        return null;
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}
