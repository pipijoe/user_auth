package cn.les.auth.service.impl;

import cn.les.auth.dto.RoleDTO;
import cn.les.auth.entity.ResultCode;
import cn.les.auth.entity.ResultJson;
import cn.les.auth.entity.auth.MenuDO;
import cn.les.auth.entity.auth.RoleDO;
import cn.les.auth.entity.auth.RoleMenuDO;
import cn.les.auth.exception.CustomException;
import cn.les.auth.repo.IMenuDao;
import cn.les.auth.repo.IRoleDao;
import cn.les.auth.repo.IRoleMenuDao;
import cn.les.auth.service.RoleService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author Joetao
 * @time 2021/2/1 9:10 上午
 * @Email cutesimba@163.com
 */
@Service
public class RoleServiceImpl implements RoleService {
    private final IRoleDao roleDao;
    private final IRoleMenuDao roleMenuDao;
    private final IMenuDao menuDao;

    public RoleServiceImpl(IRoleDao roleDao, IRoleMenuDao roleMenuDao, IMenuDao menuDao) {
        this.roleDao = roleDao;
        this.roleMenuDao = roleMenuDao;
        this.menuDao = menuDao;
    }

    @Override
    public Long addRole(RoleDTO roleDTO) {
        Optional<RoleDO> roleDOOptional = roleDao.findByRoleName(roleDTO.getRoleName());
        if (roleDOOptional.isPresent()) {
            throw new CustomException(ResultJson.failure(ResultCode.BAD_REQUEST, "该角色已存在"));
        }
        List<Long> menuIds = roleDTO.getMenuIds();

        boolean hasMenuIds = null != menuIds && !menuIds.isEmpty();
        if (hasMenuIds) {
            List<MenuDO> menuDOList = menuDao.findByIdIn(menuIds);
            if (menuDOList.size() < menuIds.size()) {
                throw new CustomException(ResultJson.failure(ResultCode.BAD_REQUEST, "菜单设置不符合要求"));
            }
        }
        RoleDO roleDO = new RoleDO();
        BeanUtils.copyProperties(roleDTO, roleDO);
        roleDO = roleDao.save(roleDO);
        if (hasMenuIds) {
            List<RoleMenuDO> roleMenuDOS = new ArrayList<>();
            for (Long id : menuIds) {
                roleMenuDOS.add(RoleMenuDO.builder().roleId(roleDO.getId()).menuId(id).build());
            }
            roleMenuDao.saveAll(roleMenuDOS);
        }
        return roleDO.getId();
    }
}
