package com.lh.cloud.auth.controller;

import com.lh.cloud.auth.param.request.LoginForm;
import com.lh.cloud.auth.service.LoginService;
import com.lh.cloud.common.param.response.Result;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: lh
 * @date: 2022/9/30 16:52
 */
@RestController
@AllArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final LoginService loginService;
    @PostMapping("/login")
    public Result login(@RequestBody LoginForm loginForm){
        return loginService.login(loginForm.getAccount(),loginForm.getPassword());
    }

    @PostMapping("/logout")
    public Result logout(){
        return loginService.logout();
    }
}
