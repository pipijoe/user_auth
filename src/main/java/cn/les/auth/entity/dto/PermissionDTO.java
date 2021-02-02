package cn.les.auth.entity.dto;

import lombok.Data;

@Data
public class PermissionDTO {
    private String name;
    private String path;
    private String method;
}
