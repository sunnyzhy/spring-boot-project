package com.zhy.activitiserver5.controller;

import com.zhy.activitiserver5.service.ProcessService;
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
@RequestMapping("/activi/process")
public class ProcessController {
    @Autowired
    private ProcessService processService;

    /**
     * 生成工作流图片
     * @param processInstanceId
     * @return
     */
    @RequestMapping(value = "/diagram/{processInstanceId}")
    public ResponseVO<String> getDiagram(@PathVariable("processInstanceId") String processInstanceId) {
        return processService.getDiagram(processInstanceId);
    }
}
