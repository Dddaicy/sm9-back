package com.sm9.boot.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sm9.boot.config.exception.CommonJsonException;
import com.sm9.boot.pojo.Relation;
import com.sm9.boot.service.RelationService;
import com.sm9.boot.util.JsonUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class RelationController {

    private final RelationService relationService;

    public RelationController(RelationService relationService) {
        this.relationService = relationService;
    }

    @GetMapping("/relations")
    public JSONObject listRelation(@RequestBody(required = false) JSONObject json,
                                   @RequestParam(value = "center-device-id", required = false)String centerDeviceId){
        List<Relation> relationList;
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
        int count = relationService.getRelationCount();
        if(centerDeviceId != null){
            relationList = relationService.getRelationListByCenterDeviceId(centerDeviceId, (pageNum - 1) * pageRow, pageRow);
        } else {
            relationList = relationService.getRelationList((pageNum - 1) * pageRow, pageRow);
        }
        return JsonUtils.successPage(JSON.toJSON(relationList), pageNum, pageRow, count);
    }

    @DeleteMapping("/relations")
    public JSONObject deleteRelation(String centerDeviceId, String... terminalDeviceIds){
        int rows = relationService.deleteRelation(centerDeviceId, terminalDeviceIds);
        return JsonUtils.successJson(new JSONObject(){{
            put("affectedRows", rows);
        }});
    }

    @PostMapping("/relations")
    public JSONObject addRelation(Relation relation){
        relationService.addRelation(relation);
        return JsonUtils.successJson(relation);
    }
}
