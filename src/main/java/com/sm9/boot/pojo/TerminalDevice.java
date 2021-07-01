package com.sm9.boot.pojo;

import lombok.Data;

@Data
public class TerminalDevice {
    private int id;
    private String terminalDeviceId;
    private String terminalDeviceIp;
    private String terminalDeviceName;
    private String terminalDeviceCenterDeviceId;
    private String terminalDeviceKeyState;
    private String terminalDeviceDescription;

}
