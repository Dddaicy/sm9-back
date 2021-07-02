package com.sm9.boot.service;

import com.alibaba.fastjson.JSONObject;
import com.github.benmanes.caffeine.cache.Cache;
import com.sm9.boot.config.exception.CommonJsonException;
import com.sm9.boot.dao.LoginDao;
import com.sm9.boot.pojo.SessionUserInfo;
import com.sm9.boot.util.ErrorEnum;
import com.sm9.boot.util.JsonUtils;
import com.sm9.boot.util.SuccessEnum;
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
            throw new CommonJsonException(ErrorEnum.E_91003);
        }
        // 判断用户是否存在
        if(loginDao.getUserIdByUserName(username) == null){
            throw new CommonJsonException(ErrorEnum.E_91001);
        }
        // 用户登录
        JSONObject user = loginDao.login(username, password);
        if (user == null) {
            throw new CommonJsonException(ErrorEnum.E_91002);
        }
        String token = MDC.get("token");
        if(token != null && sessionUserInfoCache.getIfPresent(token) != null){
            return JsonUtils.successJson(token);
        }
        token = userOnlineCache.getIfPresent(username);
        if(token != null){
            return JsonUtils.successJson(token);
        }
        token = tokenService.generateToken(username);
        userOnlineCache.put(username, token);
        return JsonUtils.successJson(token);
    }

    public JSONObject logout() {
        tokenService.invalidateToken();
        return JsonUtils.successJson(null);
    }

    public JSONObject getInfo() {
        SessionUserInfo userInfo = tokenService.getUserInfo();
        log.info(userInfo.toString());
        return JsonUtils.successJson(userInfo);
    }
}
