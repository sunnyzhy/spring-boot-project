package com.zhy.activitiserver.mapper;

import com.zhy.activitiserver.model.ActDeModel;
import com.zhy.activitiserver.model.ActDeModelWithBLOBs;

public interface ActDeModelMapper {
    int deleteByPrimaryKey(String id);

    int insert(ActDeModelWithBLOBs record);

    int insertSelective(ActDeModelWithBLOBs record);

    ActDeModelWithBLOBs selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ActDeModelWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(ActDeModelWithBLOBs record);

    int updateByPrimaryKey(ActDeModel record);

    ActDeModelWithBLOBs selectByModelKey(String modelKey);
}