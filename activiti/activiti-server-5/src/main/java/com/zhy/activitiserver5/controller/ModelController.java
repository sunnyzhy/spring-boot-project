package com.zhy.activitiserver5.controller;

import com.zhy.activitiserver5.service.ModelService;
import com.zhy.activitiserver5.vo.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhy
 * @date 2018/7/17 16:41
 **/
@RestController
@RequestMapping("/activi/model")
public class ModelController {
    @Autowired
    private ModelService modelService;

    /**
     * 生成工作流图片
     * @param modelId
     * @return
     */
    @RequestMapping(value = "/diagram/{modelId}")
    public ResponseVO<String> getDiagram(@PathVariable("modelId") String modelId) {
        return modelService.getDiagram(modelId);
    }
}
