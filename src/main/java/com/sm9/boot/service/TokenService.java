package com.sm9.boot.service;

import com.github.benmanes.caffeine.cache.Cache;
import com.sm9.boot.config.exception.CommonJsonException;
import com.sm9.boot.dao.LoginDao;
import com.sm9.boot.pojo.SessionUserInfo;
import com.sm9.boot.util.ErrorEnum;
import com.sm9.boot.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
public class TokenService {
    private final Cache<String, SessionUserInfo> cacheMap;
    private final LoginDao loginDao;

    public TokenService(Cache<String, SessionUserInfo> cacheMap, LoginDao loginDao) {
        this.cacheMap = cacheMap;
        this.loginDao = loginDao;
    }

    public String generateToken(String username){
        String token = UUID.randomUUID().toString().replaceAll("-", "");
        setCache(token, username);
        return token;
    }

    public void invalidateToken() {
        String token = MDC.get("token");
        if (!StringUtils.isNullOrEmpty(token)) {
            cacheMap.invalidate(token);
        }
        log.info("退出登录,清除缓存:token={}", token);
    }

    public SessionUserInfo getUserInfo() {
        String token = MDC.get("token");
        if (StringUtils.isNullOrEmpty(token)) {
            throw new CommonJsonException(ErrorEnum.E_91004);
        }
        log.debug("根据token从缓存中查询用户信息,{}", token);
        SessionUserInfo info = cacheMap.getIfPresent(token);
        if (info == null) {
            log.info("没拿到缓存 token={}", token);
            throw new CommonJsonException(ErrorEnum.E_91004);
        }
        return info;
    }

    private void setCache(String token, String username) {
        SessionUserInfo info = getUserInfoByUsername(username);
        log.info("设置用户信息缓存:token={} , username={}, info={}", token, username, info);
        cacheMap.put(token, info);
    }

    private SessionUserInfo getUserInfoByUsername(String username) {
        SessionUserInfo userInfo = loginDao.getUserInfo(username);
        if (userInfo.getRoleIds().contains("1")|| userInfo.getRoleIds().contains("2")) {
            //管理员,查出全部按钮和权限码
            userInfo.setMenuList(loginDao.getAllMenu());
            userInfo.setPermissionList(loginDao.getAllPermissionCode());
        }
        return userInfo;
    }
}
