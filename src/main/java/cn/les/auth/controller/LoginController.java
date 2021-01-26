package cn.les.auth.controller;

import cn.les.auth.dto.LoginUserDTO;
import cn.les.auth.entity.ResultJson;
import cn.les.auth.entity.vo.UserVO;
import cn.les.auth.service.LoginService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author Joetao
 * @time 2021/1/26 11:21 上午
 * @Email cutesimba@163.com
 */
@RequestMapping("/api/v1/login")
@RestController
public class LoginController {
    private final LoginService loginService;

    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping(value = "/")
    public ResultJson login(@Valid @RequestBody LoginUserDTO user) {
        UserVO userVO = loginService.login(user.getUsername(), user.getPassword());
        return ResultJson.ok(userVO);
    }
}
