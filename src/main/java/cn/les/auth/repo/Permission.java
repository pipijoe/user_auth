package cn.les.auth.repo;

import lombok.Builder;
import lombok.Data;

/**
 * @author Joetao
 * @time 2021/1/27 11:36 上午
 * @Email cutesimba@163.com
 */
@Data
@Builder
public class Permission {
    private String path;
    private String method;
}
