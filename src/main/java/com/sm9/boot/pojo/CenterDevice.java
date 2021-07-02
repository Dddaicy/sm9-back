package com.sm9.boot.pojo;

import lombok.Data;

import java.io.Serializable;

@Data
public class CenterDevice implements Serializable {
    private String centerDeviceId;
    private String centerDeviceIp;
    private String centerDeviceName;
    private String centerDeviceNumber;
    private String centerDeviceState;
    private String centerDeviceKeyState;
    private String centerDevicePasswordState;
    private String centerDevicePassword;
    private String centerDeviceDescription;
}

