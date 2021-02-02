package cn.les.auth.service.impl;

import cn.les.auth.entity.dto.PermissionDTO;
import cn.les.auth.repo.IPermissionDao;
import cn.les.auth.repo.PermissionDO;
import cn.les.auth.service.PermissionService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class PermissionServiceImpl implements PermissionService {
    private final IPermissionDao permissionDao;

    public PermissionServiceImpl(IPermissionDao permissionDao) {
        this.permissionDao = permissionDao;
    }

    @Override
    public Long addPermission(PermissionDTO permissionDTO) {
        PermissionDO permissionDO = new PermissionDO();
        BeanUtils.copyProperties(permissionDTO, permissionDO);
        permissionDO = permissionDao.save(permissionDO);
        return permissionDO.getId();
    }
}
