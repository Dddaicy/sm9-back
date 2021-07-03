package com.sm9.boot.controller;


import com.alibaba.fastjson.JSONObject;
import com.sm9.boot.pojo.Ukey;
import com.sm9.boot.service.UkeyService;
import com.sm9.boot.util.JsonUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UkeyController {

    private final UkeyService ukeyService;

    public UkeyController(UkeyService ukeyService) {
        this.ukeyService = ukeyService;
    }

    @GetMapping("/ukeys")
    public JSONObject makeUkey(@RequestParam("id")String centerDeviceId){
        return JsonUtils.successJson(ukeyService.getUkeyByCenterDeviceId(centerDeviceId));
    }
}
