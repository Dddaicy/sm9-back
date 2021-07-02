package com.sm9.boot.controller;


import com.sm9.boot.pojo.Ukey;
import com.sm9.boot.service.UkeyService;
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
    public Ukey makeUkey(@RequestParam("id")String centerDeviceId){
        return ukeyService.getUkeyByCenterDeviceId(centerDeviceId);
    }
}
