package com.sm9.boot.dao;

import com.sm9.boot.pojo.Relation;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RelationDao {
    List<Relation> getRelationListByCenterDeviceId(String centerDeviceId, int offset, int count);

    List<Relation> getAllRelationsByCenterDeviceId(String centerDeviceId);

    List<Relation> getRelationList(int offset, int count);

    int deleteRelation(String centerDeviceId, String... terminalDeviceIds);

    void addRelation(Relation relation);

    int getRelationCount();

    void updateRelation(String centerDeviceId, String terminalDeviceId);
}
