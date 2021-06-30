package com.sm9.boot.dao;

import com.alibaba.fastjson.JSONObject;
import com.sm9.boot.pojo.SessionUserInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;


@Mapper
public interface LoginDao {

    JSONObject login(@Param("username") String username, @Param("password") String password);

    JSONObject getUserIdByUserName(@Param("username") String username);

    SessionUserInfo getUserInfo(String username);

    Set<String> getAllMenu();

    List<String> getAllPermissionCode();
}
