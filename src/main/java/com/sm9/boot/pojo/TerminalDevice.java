package com.sm9.boot.pojo;

import lombok.Data;

import java.io.Serializable;

@Data
public class TerminalDevice implements Serializable {
    private String terminalDeviceId;
    private String terminalDeviceIp;
    private String terminalDeviceName;
    private String terminalDeviceCenterDeviceId;
    private String terminalDeviceKeyState;
    private String terminalDeviceDescription;
}
