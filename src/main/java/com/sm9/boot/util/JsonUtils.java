package com.sm9.boot.util;

import com.alibaba.fastjson.JSONObject;
import com.sm9.boot.config.exception.CommonJsonException;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

import static com.sm9.boot.util.SuccessEnum.S_90000;

public class JsonUtils {

    public static JSONObject getParamsFromRequest(HttpServletRequest request) {
        JSONObject requestJson = new JSONObject();
        Enumeration<String> paramNames = request.getParameterNames();
        while (paramNames.hasMoreElements()) {
            String paramName = paramNames.nextElement();
            String[] pv = request.getParameterValues(paramName);
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < pv.length; i++) {
                if (pv[i].length() > 0) {
                    if (i > 0) {
                        sb.append(",");
                    }
                    sb.append(pv[i]);
                }
            }
            requestJson.put(paramName, sb.toString());
        }
        return requestJson;
    }


    public static boolean hasAllRequired(final JSONObject jsonObject, String... requiredColumns) {
        for (String column : requiredColumns) {
            Object val = jsonObject.get(column.trim());
            if (StringUtils.isNullOrEmpty(val)) {
                return false;
            }
        }
        return true;
//        if (!StringUtils.isNullOrEmpty(missCol.toString())) {
//            jsonObject.clear();
//            jsonObject.put("code", ErrorEnum.E_91003.getErrorCode());
//            jsonObject.put("msg", "缺少必填参数:" + missCol.toString().trim());
//            jsonObject.put("info", new JSONObject());
//            throw new CommonJsonException(jsonObject);
//        }
    }

    public static JSONObject errorJson(ErrorEnum errorEnum) {
        JSONObject resultJson = new JSONObject();
        resultJson.put("code", errorEnum.getErrorCode());
        resultJson.put("reason", errorEnum.getErrorReason());
        resultJson.put("info", errorEnum.getErrorMsg());
        return resultJson;
    }

    public static JSONObject successJson(Object info) {
        JSONObject resultJson = new JSONObject();
        resultJson.put("code", S_90000.getCode());
        resultJson.put("msg", S_90000.getMsg());
        resultJson.put("info", info);
        return resultJson;
    }
}
