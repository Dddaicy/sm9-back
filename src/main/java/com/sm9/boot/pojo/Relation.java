package com.sm9.boot.pojo;

import lombok.Data;

@Data
public class Relation {
    private int id;
    private String centerDeviceId;
    private String terminalDeviceId;
}

