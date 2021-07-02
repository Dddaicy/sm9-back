package com.sm9.boot.dao;


import com.sm9.boot.pojo.Relation;
import com.sm9.boot.pojo.TerminalDevice;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TerminalDeviceDao {

    List<TerminalDevice> getTerminalDeviceList(int offset, int count);
    int getTerminalDeviceCount();
    TerminalDevice getTerminalDeviceById(String terminalDeviceId);
    void addTerminalDevice(TerminalDevice terminalDevice);
    void updateTerminalDevice(TerminalDevice terminalDevice);
    void deleteTerminalDeviceById(String terminalDeviceId);
    List<TerminalDevice> getTerminalDeviceListByCenterDeviceId(String terminalDeviceCenterDeviceId, int offset, int count);
    void deleteTerminalDeviceByCenterDeviceId(List<Relation> relations);

}
