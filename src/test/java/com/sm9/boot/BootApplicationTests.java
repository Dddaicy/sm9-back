package com.sm9.boot;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sm9.boot.pojo.CenterDevice;
import com.sm9.boot.service.CenterDeviceService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
@Slf4j
class BootApplicationTests {

    @Autowired
    CenterDeviceService centerDeviceService;
    @Test
    void contextLoads() {
    }


}
