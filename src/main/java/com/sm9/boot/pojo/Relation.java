package com.sm9.boot.pojo;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Relation {
    private int id;
    private String centerDeviceId;
    private String terminalDeviceId;

    public Relation(String centerDeviceId, String terminalDeviceId) {
        this.centerDeviceId = centerDeviceId;
        this.terminalDeviceId = terminalDeviceId;
    }
}

