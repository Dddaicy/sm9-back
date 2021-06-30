package com.sm9.boot.service;

import com.alibaba.fastjson.JSONObject;
import com.github.benmanes.caffeine.cache.Cache;
import com.sm9.boot.config.exception.CommonJsonException;
import com.sm9.boot.dao.LoginDao;
import com.sm9.boot.pojo.SessionUserInfo;
import com.sm9.boot.util.ErrorEnum;
import com.sm9.boot.util.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class LoginService {

    private final LoginDao loginDao;
    private final TokenService tokenService;
    private final Cache<String, SessionUserInfo> sessionUserInfoCache;
    private final Cache<String, String> userOnlineCache;

    public LoginService(LoginDao loginDao, TokenService tokenService, Cache<String, SessionUserInfo> sessionUserInfoCache, Cache<String, String> userOnlineCache) {
        this.loginDao = loginDao;
        this.tokenService = tokenService;
        this.sessionUserInfoCache = sessionUserInfoCache;
        this.userOnlineCache = userOnlineCache;
    }

    public JSONObject authLogin(JSONObject jsonObject) {
        String username = jsonObject.getString("username");
        String password = jsonObject.getString("password");
        // 字段必填，否则报错
        if(username == null || password == null){
            JSONObject errorJson = JsonUtils.errorJson(ErrorEnum.E_91003);
            throw new CommonJsonException(errorJson);
        }
        // 判断用户是否存在
        if(loginDao.getUserIdByUserName(username) == null){
            JSONObject errorJson = JsonUtils.errorJson(ErrorEnum.E_91001);
            throw new CommonJsonException(errorJson);
        }
        // 用户登录
        JSONObject user = loginDao.login(username, password);
        if (user == null) {
            throw new CommonJsonException(ErrorEnum.E_91002);
        }
        String token = MDC.get("token");
        JSONObject info = new JSONObject();
        if(token != null && sessionUserInfoCache.getIfPresent(token) != null){
            info.put("token", token);
            return info;
        }
        token = userOnlineCache.getIfPresent(username);
        if(token != null){
            info.put("token", token);
            return info;
        }
        token = tokenService.generateToken(username);
        info.put("token", token);
        userOnlineCache.put(username, token);
        return info;
    }

    public JSONObject logout() {
        tokenService.invalidateToken();
        return new JSONObject();
    }

}
