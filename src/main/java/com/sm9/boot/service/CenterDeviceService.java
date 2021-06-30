package com.sm9.boot.service;

import com.sm9.boot.dao.CenterDeviceDao;
import com.sm9.boot.pojo.CenterDevice;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CenterDeviceService {

    private final CenterDeviceDao centerDeviceDao;

    public CenterDeviceService(CenterDeviceDao centerDeviceDao) {
        this.centerDeviceDao = centerDeviceDao;
    }

    public int getCenterDeviceCount(){
        return centerDeviceDao.getCenterDeviceCount();
    }

    public List<CenterDevice> listCenterDevice(int offset, int count){
        return centerDeviceDao.getCenterDeviceList(offset, count);
    }

    public CenterDevice getCenterDeviceById(String centerDeviceId){
        return centerDeviceDao.getCenterDeviceById(centerDeviceId);
    }

    public List<CenterDevice> getCenterDeviceListByState(String centerDeviceState){
        return centerDeviceDao.getCenterDeviceListByState(centerDeviceState);
    }

    public void addCenterDevice(CenterDevice centerDevice){
        centerDeviceDao.addCenterDevice(centerDevice);
    }

    public int deleteCenterDeviceById(String centerDeviceId){
        return centerDeviceDao.deleteCenterDeviceById(centerDeviceId);
    }

    public int updateCenterDevice(CenterDevice centerDevice){
        return centerDeviceDao.updateCenterDevice(centerDevice);
    }

    public int deleteCenterDeviceByState(String centerDeviceState) {
        return centerDeviceDao.deleteCenterDeviceListByState(centerDeviceState);
    }
}
