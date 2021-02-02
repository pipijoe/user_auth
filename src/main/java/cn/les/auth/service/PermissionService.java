package cn.les.auth.service;

import cn.les.auth.entity.dto.PermissionDTO;

public interface PermissionService {
    Long addPermission(PermissionDTO permissionDTO);
}
