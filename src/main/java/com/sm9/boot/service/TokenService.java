package com.sm9.boot.service;

import com.github.benmanes.caffeine.cache.Cache;
import com.sm9.boot.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
public class TokenService {
    private final Cache<String, String> cacheMap;

    public TokenService(Cache<String, String> cacheMap) {
        this.cacheMap = cacheMap;
    }

    public String generateToken(String username){
        String token = UUID.randomUUID().toString().replaceAll("-", "");
        cacheMap.put(token, username);
        return token;
    }

    public void invalidateToken() {
        String token = MDC.get("token");
        if (!StringUtils.isNullOrEmpty(token)) {
            cacheMap.invalidate(token);
        }
        log.info("退出登录,清除缓存:token={}", token);
    }
}
