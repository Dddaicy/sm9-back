package com.sm9.boot.dao;

import com.alibaba.fastjson.JSONObject;
import com.sm9.boot.pojo.CenterDevice;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CenterDeviceDao {
    List<CenterDevice> getCenterDeviceList(int offset, int count);
    int getCenterDeviceCount();
    CenterDevice getCenterDeviceById(String centerDeviceId);
    void addCenterDevice(CenterDevice centerDevice);
    int updateCenterDevice(CenterDevice centerDevice);
    int deleteCenterDeviceById(String centerDeviceId);
    List<CenterDevice> getCenterDeviceListByState(String centerDeviceState, int offset, int count);
    int deleteCenterDeviceListByState(String centerDeviceState);
}
