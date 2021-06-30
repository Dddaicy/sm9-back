package com.sm9.boot.config.exception;

import com.alibaba.fastjson.JSONObject;
import com.sm9.boot.util.ErrorEnum;
import com.sm9.boot.util.JsonUtils;

public class CommonJsonException extends RuntimeException {

    private final JSONObject json;

    public CommonJsonException(JSONObject json) {
        this.json = json;
    }
    public CommonJsonException(ErrorEnum errorEnum) {
        this.json = JsonUtils.errorJson(errorEnum);
    }

    public JSONObject getResult() {
        return json;
    }
}
