package com.sm9.boot.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sm9.boot.pojo.CenterDevice;
import com.sm9.boot.service.CenterDeviceService;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.sm9.boot.util.SuccessEnum.S_90000;

@RestController
public class CenterDeviceController {

    private final CenterDeviceService centerDeviceService;

    public CenterDeviceController(CenterDeviceService centerDeviceService) {
        this.centerDeviceService = centerDeviceService;
    }

    /**
     * 查询参数中传入id，则按id查询并返回单个设备信息
     * 查询参数中传入center-device-state，则按center-device-state查询并返回设备列表
     * 前端pageRow传值null时，返回所有设备，传pageNum和pageRow时返回特定页数的设备列表
     *
     * @param json 前端数据
     * @return JSONObject
     */
    @GetMapping(value = "/center-devices")
    public Object getCenterDeviceList(@RequestBody JSONObject json,
                                      @RequestParam(value = "id", required = false) String centerDeviceId,
                                      @RequestParam(value = "center-device-state", required = false) String centerDeviceState) {
        List<CenterDevice> deviceList;
        int pageNum = (int) json.getOrDefault("pageNum", 1);
        Integer pageRow = (Integer) json.getOrDefault("pageRow", 10);
        int count = centerDeviceService.getCenterDeviceCount();
        if(centerDeviceId != null){
            return centerDeviceService.getCenterDeviceById(centerDeviceId);
        }else if(centerDeviceState != null){
            deviceList = centerDeviceService.getCenterDeviceListByState(centerDeviceState);
        }else if(pageRow == null){
            deviceList = centerDeviceService.listCenterDevice(0, count);
        } else
            deviceList = centerDeviceService.listCenterDevice((pageNum - 1) * pageRow, pageRow);
        JSONArray jsonArray = (JSONArray) JSON.toJSON(deviceList);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("list", jsonArray);
        jsonObject.put("pageNum", pageNum);
        jsonObject.put("pageRow", pageRow);
        jsonObject.put("totalCount", count);
        jsonObject.put("msg", S_90000.getMsg());
        jsonObject.put("code", S_90000.getCode());
        return jsonObject;
    }

    @PostMapping("/center-devices")
    public CenterDevice addCenterDevice(CenterDevice centerDevice){
        centerDeviceService.addCenterDevice(centerDevice);
        return centerDevice;
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
        return new JSONObject(){{
            put("affectedRows", rows);
        }};
    }

    @PutMapping("/center-devices")
    public JSONObject updateCenterDevice(CenterDevice centerDevice){
        int rows = centerDeviceService.updateCenterDevice(centerDevice);
        return new JSONObject(){{
            put("affectedRows", rows);
        }};
    }
}
