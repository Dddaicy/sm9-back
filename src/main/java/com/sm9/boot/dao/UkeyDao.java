package com.sm9.boot.dao;

import com.sm9.boot.pojo.Ukey;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UkeyDao {

    @Select("select * from ukey_info where ukey_center_device_id=#{centerDeviceId}")
    Ukey getUkeyByCenterDeviceId(String centerDeviceId);
}
