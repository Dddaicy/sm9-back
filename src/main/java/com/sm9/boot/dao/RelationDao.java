package com.sm9.boot.dao;

import com.sm9.boot.pojo.Relation;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RelationDao {
    List<Relation> getRelationListByCenterDeviceId(String centerDeviceId, int offset, int count);

    List<Relation> getRelationList(int offset, int count);

    int deleteRelation(String centerDeviceId, String... terminalDeviceIds);

    void addRelation(String centerDeviceId, String terminalDeviceId);

    int getRelationCount();

//    int updateRelation(String centerDeviceId, String terminalDeviceId);
}
