package com.sm9.boot.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sm9.boot.config.exception.CommonJsonException;
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
     * 查询参数中传入center-device-id，则按center-device-id查询并返回设备列表
     * 传pageNum和pageRow时返回特定页数的设备列表
     *
     * @param json 前端RequestBody数据
     * @param terminalDeviceId 终端侧设备Id
     * @param CenterDeviceId 终端侧设备所属的中心侧设备的Id
     * @return 根据不同的传入参数返回不同的设备（列表）
     */
    @GetMapping(value = "/terminal-devices")
    public Object getTerminalDeviceList(@RequestBody(required = false) JSONObject json,
                                      @RequestParam(value = "id", required = false) String terminalDeviceId,
                                      @RequestParam(value = "center-device-id", required = false) String CenterDeviceId) {
        List<TerminalDevice> deviceList;
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
        int count = terminalDeviceService.getTerminalDeviceCount();
        if(terminalDeviceId != null){
            return JsonUtils.successJson(terminalDeviceService.getTerminalDeviceById(terminalDeviceId));
        }else if(CenterDeviceId != null){
            deviceList = terminalDeviceService.getTerminalDeviceListByCenterDeviceId(CenterDeviceId, (pageNum - 1) * pageRow, pageRow);
        } else
            deviceList = terminalDeviceService.listTerminalDevice((pageNum - 1) * pageRow, pageRow);
        return JsonUtils.successPage(JSON.toJSON(deviceList), pageNum, pageRow, count);
    }

    @DeleteMapping("/terminal-devices")
    public JSONObject deleteTerminalDevice(@RequestParam(value = "id", required = false) String terminalDeviceId,
                                         @RequestParam(value = "center-device-id", required = false) String centerDeviceId){
        if(terminalDeviceId != null)
            terminalDeviceService.deleteTerminalDeviceById(terminalDeviceId);
        else if (centerDeviceId != null)
            terminalDeviceService.deleteTerminalDeviceByCenterDeviceId(centerDeviceId);
        return JsonUtils.successJson(null);
    }

    @PostMapping("/terminal-devices")
    public JSONObject addTerminalDevice(TerminalDevice terminalDevice){
        terminalDeviceService.addTerminalDevice(terminalDevice);
        return JsonUtils.successJson(terminalDevice);
    }

    @PutMapping("/terminal-devices")
    public JSONObject updateCenterDevice(TerminalDevice terminalDevice){
        terminalDeviceService.updateTerminalDevice(terminalDevice);
        return JsonUtils.successJson(null);
    }
}
