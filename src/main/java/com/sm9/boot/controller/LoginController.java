package com.sm9.boot.controller;


import com.alibaba.fastjson.JSONObject;
import com.sm9.boot.service.LoginService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/login")
public class LoginController {

    private final LoginService loginService;

    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping("/auth")
    public JSONObject authLogin(@RequestBody JSONObject json){
        return loginService.authLogin(json);
    }

    @PostMapping("/logout")
    public JSONObject logout(){
        return loginService.logout();
    }
}
