package com.sm9.boot.service;

import com.sm9.boot.config.exception.CommonJsonException;
import com.sm9.boot.dao.CenterDeviceDao;
import com.sm9.boot.pojo.CenterDevice;
import com.sm9.boot.util.StringUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

@Service
public class CenterDeviceService {

    private final CenterDeviceDao centerDeviceDao;

    public CenterDeviceService(CenterDeviceDao centerDeviceDao) {
        this.centerDeviceDao = centerDeviceDao;
    }

    public int getCenterDeviceCount() {
        return centerDeviceDao.getCenterDeviceCount();
    }

    public List<CenterDevice> listCenterDevice(int offset, int count) {
        return centerDeviceDao.getCenterDeviceList(offset, count);
    }

    public CenterDevice getCenterDeviceById(String centerDeviceId) {
        return centerDeviceDao.getCenterDeviceById(centerDeviceId);
    }

    public List<CenterDevice> getCenterDeviceListByState(String centerDeviceState, int offset, int count) {
        return centerDeviceDao.getCenterDeviceListByState(centerDeviceState, offset, count);
    }

    public void addCenterDevice(CenterDevice centerDevice) {
        try {
            centerDeviceDao.addCenterDevice(centerDevice);
        } catch (DuplicateKeyException e) {
            throw new CommonJsonException(e, "中心侧设备id重复");
        } catch (DataIntegrityViolationException e) {
            throw new CommonJsonException(e, "请传入完整的中心侧设备参数");
        }

    }

    public int deleteCenterDeviceById(String centerDeviceId) {
        return centerDeviceDao.deleteCenterDeviceById(centerDeviceId);
    }

    public int updateCenterDevice(CenterDevice centerDevice) {
        if (centerDevice.getCenterDeviceId() == null) {
            throw new CommonJsonException(new DataIntegrityViolationException(""), "中心侧设备名为空");
        }
        boolean isValid = false;
        try {
            for (Field field : centerDevice.getClass().getDeclaredFields()) {
                if (!field.getName().equals("centerDeviceId")) {
                    isValid = isValid || (centerDevice.getClass().getDeclaredMethod("get" + StringUtils.capitalize(field.getName())).invoke(centerDevice) != null);
                }
            }
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException exception) {
            throw new CommonJsonException(exception, exception.getMessage());
        }
        if (!isValid) {
            throw new CommonJsonException(new DataIntegrityViolationException(""), "无待更新字段");
        }
        return centerDeviceDao.updateCenterDevice(centerDevice);
    }

    public int deleteCenterDeviceByState(String centerDeviceState) {
        return centerDeviceDao.deleteCenterDeviceListByState(centerDeviceState);
    }
}
