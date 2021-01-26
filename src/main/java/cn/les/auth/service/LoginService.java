package cn.les.auth.service;

import cn.les.auth.entity.vo.UserVO;

/**
 * @author Joetao
 * @time 2021/1/26 11:35 上午
 * @Email cutesimba@163.com
 */
public interface LoginService {
    /**
     * 登录接口
     *
     * @param userName 用户名
     * @param password 密码
     * @return 用户基本信息
     */
    UserVO login(String userName, String password);
}
