package com.sm9.boot.dao;


import com.sm9.boot.pojo.TerminalDevice;

import java.util.List;

public interface TerminalDeviceDao {

    List<TerminalDevice> getTerminalDeviceList(int offset, int count);
    int getTerminalDeviceCount();
    TerminalDevice getTerminalDeviceById(String centerDeviceId);
    void addTerminalDevice(TerminalDevice centerDevice);
    int updateTerminalDevice(TerminalDevice centerDevice);
    int deleteTerminalDeviceById(String centerDeviceId);
    List<TerminalDevice> getTerminalDeviceListByCenterDeviceId(String centerDeviceState);
    int deleteTerminalDeviceByCenterDeviceId(String centerDeviceState);

}
