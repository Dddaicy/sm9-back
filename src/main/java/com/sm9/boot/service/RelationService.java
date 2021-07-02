package com.sm9.boot.service;

import com.sm9.boot.dao.RelationDao;
import com.sm9.boot.pojo.Relation;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RelationService {

    private final RelationDao relationDao;

    public RelationService(RelationDao relationDao) {
        this.relationDao = relationDao;
    }

    public List<Relation> getRelationListByCenterDeviceId(String centerDeviceId, int offset, int count) {
        return relationDao.getRelationListByCenterDeviceId(centerDeviceId, offset, count);
    }

    public List<Relation> getRelationList(int offset, int count) {
        return relationDao.getRelationList(offset, count);
    }

    public int deleteRelation(String centerDeviceId, String... terminalDeviceIds) {
        return relationDao.deleteRelation(centerDeviceId, terminalDeviceIds);
    }


    public void addRelation(Relation relation){
        relationDao.addRelation(relation);
    }

    public int getRelationCount() {
        return relationDao.getRelationCount();
    }
}
