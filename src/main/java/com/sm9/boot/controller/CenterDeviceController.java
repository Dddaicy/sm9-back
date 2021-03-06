package com.sm9.boot.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sm9.boot.config.exception.CommonJsonException;
import com.sm9.boot.pojo.CenterDevice;
import com.sm9.boot.service.CenterDeviceService;
import com.sm9.boot.util.ErrorEnum;
import com.sm9.boot.util.JsonUtils;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Field;
import java.util.List;

@RestController
public class CenterDeviceController {

    private final CenterDeviceService centerDeviceService;

    public CenterDeviceController(CenterDeviceService centerDeviceService) {
        this.centerDeviceService = centerDeviceService;
    }

    /**
     * 查询参数中传入id，则按id查询并返回单个设备信息
     * 查询参数中传入center-device-state，则按center-device-state查询并返回设备列表
     * 传pageNum和pageRow时返回特定页数的设备列表
     *
     * @param json 前端RequestBody数据
     * @param centerDeviceId 中心侧设备Id
     * @param centerDeviceState 中心侧设备状态
     * @return 根据不同的传入参数返回不同的设备（列表）
     */
    @GetMapping(value = "/center-devices")
    public Object getCenterDeviceList(@RequestBody(required = false) JSONObject json,
                                      @RequestParam(value = "id", required = false) String centerDeviceId,
                                      @RequestParam(value = "state", required = false) String centerDeviceState) {
        List<CenterDevice> deviceList;
        int pageNum = 1;
        int pageRow = 10;
        try {
            if(json != null){
                pageNum = (int) json.getOrDefault("pageNum", 1);
                pageRow = (int) json.getOrDefault("pageRow", 10);
            }
        } catch (ClassCastException e){
            throw new CommonJsonException(e, "类型转换失败，请传入int类型参数");
        }
        int count = centerDeviceService.getCenterDeviceCount();
        if(centerDeviceId != null){
            return JsonUtils.successJson(centerDeviceService.getCenterDeviceById(centerDeviceId));
        }else if(centerDeviceState != null){
            deviceList = centerDeviceService.getCenterDeviceListByState(centerDeviceState, (pageNum - 1) * pageRow, pageRow);
        }else
            deviceList = centerDeviceService.listCenterDevice((pageNum - 1) * pageRow, pageRow);
        return JsonUtils.successPage(JSON.toJSON(deviceList), pageNum, pageRow, count);
    }

    @PostMapping("/center-devices")
    public JSONObject addCenterDevice(CenterDevice centerDevice){
        centerDeviceService.addCenterDevice(centerDevice);
        return JsonUtils.successJson(centerDevice);
    }

    @DeleteMapping("/center-devices")
    public JSONObject deleteCenterDevice(@RequestParam(value = "id", required = false) String centerDeviceId,
                                         @RequestParam(value = "center-device-state", required = false) String centerDeviceState){
        int rows;
        if(centerDeviceId != null)
            rows = centerDeviceService.deleteCenterDeviceById(centerDeviceId);
        else if (centerDeviceState != null)
            rows = centerDeviceService.deleteCenterDeviceByState(centerDeviceState);
        else
            rows = 0;
        return JsonUtils.successJson(new JSONObject(){{
            put("affectedRows", rows);
        }});
    }

    @PutMapping("/center-devices")
    public JSONObject updateCenterDevice(CenterDevice centerDevice){
        int rows = centerDeviceService.updateCenterDevice(centerDevice);
        return JsonUtils.successJson(new JSONObject(){{
            put("affectedRows", rows);
        }});
    }
}
