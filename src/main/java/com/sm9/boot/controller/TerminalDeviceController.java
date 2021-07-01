package com.sm9.boot.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sm9.boot.pojo.CenterDevice;
import com.sm9.boot.pojo.TerminalDevice;
import com.sm9.boot.service.TerminalDeviceService;
import com.sm9.boot.util.JsonUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TerminalDeviceController {

    private final TerminalDeviceService terminalDeviceService;

    public TerminalDeviceController(TerminalDeviceService terminalDeviceService) {
        this.terminalDeviceService = terminalDeviceService;
    }

    /**
     * 查询参数中传入id，则按id查询并返回单个设备信息
     * 查询参数中传入terminal-device-center-device-id，则按terminal-device-center-device-id查询并返回设备列表
     * 前端pageRow传值null时，返回所有设备，传pageNum和pageRow时返回特定页数的设备列表
     *
     * @param json 前端RequestBody数据
     * @param terminalDeviceId 终端侧设备Id
     * @param terminalDeviceCenterDeviceId 终端侧设备所属的中心侧设备的Id
     * @return JSONObject
     */
    @GetMapping(value = "/terminal-devices")
    public Object getTerminalDeviceList(@RequestBody(required = false) JSONObject json,
                                      @RequestParam(value = "id", required = false) String terminalDeviceId,
                                      @RequestParam(value = "terminal-device-center-device-id", required = false) String terminalDeviceCenterDeviceId) {
        List<TerminalDevice> deviceList;
        int pageNum = 1;
        Integer pageRow = 10;
        if(json != null){
            pageNum = (int) json.getOrDefault("pageNum", 1);
            pageRow = (Integer) json.getOrDefault("pageRow", 10);
        }
        int count = terminalDeviceService.getTerminalDeviceCount();
        if(terminalDeviceId != null){
            return terminalDeviceService.getTerminalDeviceById(terminalDeviceId);
        }else if(terminalDeviceCenterDeviceId != null){
            deviceList = terminalDeviceService.getTerminalDeviceListByCenterDeviceId(terminalDeviceCenterDeviceId);
        }else if(pageRow == null){
            deviceList = terminalDeviceService.listTerminalDevice(0, count);
        } else
            deviceList = terminalDeviceService.listTerminalDevice((pageNum - 1) * pageRow, pageRow);
        return JsonUtils.successPage(JSON.toJSON(deviceList), pageNum, pageRow, count);
    }

    @DeleteMapping("/terminal-devices")
    public JSONObject deleteTerminalDevice(@RequestParam(value = "id", required = false) String terminalDeviceId,
                                         @RequestParam(value = "terminal-device-center-device-id", required = false) String centerDeviceId){
        int rows;
        if(terminalDeviceId != null)
            rows = terminalDeviceService.deleteTerminalDeviceById(terminalDeviceId);
        else if (centerDeviceId != null)
            rows = terminalDeviceService.deleteTerminalDeviceByCenterDeviceId(centerDeviceId);
        else
            rows = 0;
        return new JSONObject(){{
            put("affectedRows", rows);
        }};
    }

    @PostMapping("/terminal-devices")
    public TerminalDevice addTerminalDevice(TerminalDevice terminalDevice){
        terminalDeviceService.addTerminalDevice(terminalDevice);
        return terminalDevice;
    }

    @PutMapping("/terminal-devices")
    public JSONObject updateCenterDevice(TerminalDevice terminalDevice){
        int rows = terminalDeviceService.updateTerminalDevice(terminalDevice);
        return new JSONObject(){{
            put("affectedRows", rows);
        }};
    }
}
