package com.sm9.boot.dao;

import com.alibaba.fastjson.JSONObject;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


@Mapper
public interface LoginDao {

    JSONObject login(@Param("username") String username, @Param("password") String password);
    JSONObject getUserIdByUserName(@Param("username") String username);

}
