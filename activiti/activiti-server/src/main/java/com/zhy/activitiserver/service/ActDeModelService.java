package com.zhy.activitiserver.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zhy.activitiserver.mapper.ActDeModelMapper;
import com.zhy.activitiserver.model.ActDeModelWithBLOBs;
import com.zhy.activitiserver.model.ProcessModel;
import com.zhy.activitiserver.utils.ResponseVoUtil;
import com.zhy.activitiserver.vo.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ActDeModelService {
    @Autowired
    private ResponseVoUtil responseVoUtil;
    @Autowired
    private ActDeModelMapper actDeModelMapper;
    @Autowired
    private ObjectMapper objectMapper;

    /**
     * 新建工作流
     * @param model
     * @return
     */
    public ResponseVO<ActDeModelWithBLOBs> newModel(ActDeModelWithBLOBs model) {
        ActDeModelWithBLOBs entity = actDeModelMapper.selectByModelKey(model.getModelKey());
        if (entity == null) {
            model.setId(UUID.randomUUID().toString());
            model.setVersion(1);
            ProcessModel processModel = new ProcessModel(model.getModelKey(), model.getName());
            try {
                model.setModelEditorJson(objectMapper.writeValueAsString(processModel));
            } catch (Exception e) {
                return responseVoUtil.error(-1, "格式化数据错误");
            }
            actDeModelMapper.insertSelective(model);
            return responseVoUtil.success(model);
        } else {
            return responseVoUtil.error(-1, "工作流已存在");
        }
    }
}
