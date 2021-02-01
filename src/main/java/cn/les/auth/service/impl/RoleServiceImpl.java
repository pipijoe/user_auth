package cn.les.auth.service.impl;

import cn.les.auth.dto.RoleDTO;
import cn.les.auth.entity.ResultCode;
import cn.les.auth.entity.ResultJson;
import cn.les.auth.entity.auth.RoleDO;
import cn.les.auth.entity.auth.RoleMenuDO;
import cn.les.auth.exception.CustomException;
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

    public RoleServiceImpl(IRoleDao roleDao, IRoleMenuDao roleMenuDao) {
        this.roleDao = roleDao;
        this.roleMenuDao = roleMenuDao;
    }

    @Override
    public Long addRole(RoleDTO roleDTO) {
        Optional<RoleDO> roleDOOptional = roleDao.findByRoleName(roleDTO.getRoleName());
        if (roleDOOptional.isPresent()) {
            throw new CustomException(ResultJson.failure(ResultCode.BAD_REQUEST, "该角色已存在"));
        }
        RoleDO roleDO = new RoleDO();
        BeanUtils.copyProperties(roleDTO, roleDO);
        roleDO = roleDao.save(roleDO);
        List<Long> menuIds = roleDTO.getMenuIds();
        if (!menuIds.isEmpty()) {
            List<RoleMenuDO> roleMenuDOS = new ArrayList<>();
            for (Long id : menuIds) {
                roleMenuDOS.add(RoleMenuDO.builder().roleId(roleDO.getId()).menuId(id).build());
            }
            roleMenuDao.saveAll(roleMenuDOS);
        }
        return roleDO.getId();
    }
}
