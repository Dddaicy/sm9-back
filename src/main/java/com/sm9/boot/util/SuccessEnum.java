package com.sm9.boot.util;

public enum SuccessEnum {

    S_90001("90001", "登陆成功"),
    S_90000("90000", "操作成功");

    private final String code;
    private final String msg;

    SuccessEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

}
