package com.sm9.boot.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
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
        if(json != null){
            pageNum = (int) json.getOrDefault("pageNum", 1);
            pageRow = (int) json.getOrDefault("pageRow", 10);
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
        return new JSONObject(){{
            put("affectedRows", rows);
        }};
    }

    @PostMapping("/relations")
    public Relation addRelation(Relation relation){
        relationService.addRelation(relation.getCenterDeviceId(), relation.getTerminalDeviceId());
        return relation;
    }
}
