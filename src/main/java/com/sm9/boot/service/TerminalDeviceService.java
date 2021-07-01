package com.sm9.boot.service;

import com.sm9.boot.dao.TerminalDeviceDao;
import com.sm9.boot.pojo.TerminalDevice;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TerminalDeviceService {

    private final TerminalDeviceDao terminalDeviceDao;

    public TerminalDeviceService(TerminalDeviceDao terminalDeviceDao) {
        this.terminalDeviceDao = terminalDeviceDao;
    }

    public int getTerminalDeviceCount() {
        return terminalDeviceDao.getTerminalDeviceCount();
    }

    public Object getTerminalDeviceById(String terminalDeviceId) {
        return terminalDeviceDao.getTerminalDeviceById(terminalDeviceId);
    }

    public List<TerminalDevice> getTerminalDeviceListByCenterDeviceId(String terminalDeviceCenterDeviceId) {
        return terminalDeviceDao.getTerminalDeviceListByCenterDeviceId(terminalDeviceCenterDeviceId);
    }

    public List<TerminalDevice> listTerminalDevice(int offset, int count) {
        return terminalDeviceDao.getTerminalDeviceList(offset, count);
    }

    public void addTerminalDevice(TerminalDevice terminalDevice){
        terminalDeviceDao.addTerminalDevice(terminalDevice);
    }

    public int updateTerminalDevice(TerminalDevice terminalDevice){
        return terminalDeviceDao.updateTerminalDevice(terminalDevice);
    }

    public int deleteTerminalDeviceById(String terminalDeviceId){
        return terminalDeviceDao.deleteTerminalDeviceById(terminalDeviceId);
    }

    public int deleteTerminalDeviceByCenterDeviceId(String centerDeviceId){
        return terminalDeviceDao.deleteTerminalDeviceByCenterDeviceId(centerDeviceId);
    }


}
