package com.sm9.boot.service;

import com.sm9.boot.config.exception.CommonJsonException;
import com.sm9.boot.dao.RelationDao;
import com.sm9.boot.dao.TerminalDeviceDao;
import com.sm9.boot.pojo.Relation;
import com.sm9.boot.pojo.TerminalDevice;
import com.sm9.boot.util.StringUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

@Service
public class TerminalDeviceService {

    private final TerminalDeviceDao terminalDeviceDao;
    private final RelationDao relationDao;

    public TerminalDeviceService(TerminalDeviceDao terminalDeviceDao, RelationDao relationDao) {
        this.terminalDeviceDao = terminalDeviceDao;
        this.relationDao = relationDao;
    }

    public int getTerminalDeviceCount() {
        return terminalDeviceDao.getTerminalDeviceCount();
    }

    public TerminalDevice getTerminalDeviceById(String terminalDeviceId) {
        return terminalDeviceDao.getTerminalDeviceById(terminalDeviceId);
    }

    public List<TerminalDevice> getTerminalDeviceListByCenterDeviceId(String centerDeviceId, int offset, int count) {
        return terminalDeviceDao.getTerminalDeviceListByCenterDeviceId(centerDeviceId, offset, count);
    }

    public List<TerminalDevice> listTerminalDevice(int offset, int count) {
        return terminalDeviceDao.getTerminalDeviceList(offset, count);
    }

    @Transactional
    public void addTerminalDevice(TerminalDevice terminalDevice){
        try {
            terminalDeviceDao.addTerminalDevice(terminalDevice);
            Relation relation = new Relation(terminalDevice.getTerminalDeviceCenterDeviceId(), terminalDevice.getTerminalDeviceId());
            relationDao.addRelation(relation);
        } catch (DuplicateKeyException e){
            throw new CommonJsonException(e, "终端侧设备id重复");
        }
        catch (DataIntegrityViolationException e){
            throw new CommonJsonException(e, "请传入完整的终端侧设备参数");
        }
    }

    @Transactional
    public void updateTerminalDevice(TerminalDevice terminalDevice){
        if(terminalDevice.getTerminalDeviceId() == null){
            throw new CommonJsonException(new DataIntegrityViolationException(""), "终端侧侧设备名为空");
        }
        boolean isValid = false;
        boolean onlyUpdateCenterDeviceId = true;
//        List<String> props = new ArrayList<>();
//        props.add("terminalDeviceId");
        try {
            for (Field field : terminalDevice.getClass().getDeclaredFields()) {
                if(!field.getName().equals("terminalDeviceId")){
                    isValid = isValid || (terminalDevice.getClass().getDeclaredMethod("get"+ StringUtils.capitalize(field.getName())).invoke(terminalDevice) != null);
                }
                if(!field.getName().equals("terminalDeviceCenterDeviceId") && !field.getName().equals("terminalDeviceId")){
                    onlyUpdateCenterDeviceId = onlyUpdateCenterDeviceId && (terminalDevice.getClass().getDeclaredMethod("get"+ StringUtils.capitalize(field.getName())).invoke(terminalDevice) == null);
                }
            }
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException exception){
            throw new CommonJsonException(exception, exception.getMessage());
        }
        if(!isValid){
            throw new CommonJsonException(new DataIntegrityViolationException(""), "无待更新字段");
        }
        String CenterDeviceId = terminalDevice.getTerminalDeviceCenterDeviceId();
        if(!onlyUpdateCenterDeviceId)
            terminalDeviceDao.updateTerminalDevice(terminalDevice);
        if(CenterDeviceId != null)
            relationDao.updateRelation(CenterDeviceId, terminalDevice.getTerminalDeviceId());
    }

    public void deleteTerminalDeviceById(String terminalDeviceId){
        terminalDeviceDao.deleteTerminalDeviceById(terminalDeviceId);
    }

    public void deleteTerminalDeviceByCenterDeviceId(String centerDeviceId){
        List<Relation> relations = relationDao.getAllRelationsByCenterDeviceId(centerDeviceId);
        terminalDeviceDao.deleteTerminalDeviceByCenterDeviceId(relations);
    }


}
