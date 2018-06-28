package com.zhy.activitiserver.controller;

import com.zhy.activitiserver.model.*;
import com.zhy.activitiserver.service.ActivitiService;
import com.zhy.activitiserver.vo.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

@RestController
@RequestMapping(value = "/vacation")
public class ActivitiController {
    @Autowired
    private ActivitiService activitiService;

    @RequestMapping(value = "/new",method = RequestMethod.POST)
    public ResponseVO newModel(@RequestBody ActDeModelWithBLOBs model){
        return activitiService.newModel(model);
    }

    @RequestMapping(value = "/apply", method = RequestMethod.POST)
    public ResponseVO apply(@RequestBody ApplyVO applyVO) {
        return activitiService.apply(applyVO);
    }

    @RequestMapping(value = "/task/{id}", method = RequestMethod.GET)
    public ResponseVO<List<TaskVO>> getTask(@PathVariable("id") String userId) {
        return activitiService.getTask(userId);
    }

    @RequestMapping(value = "/task/his/{id}", method = RequestMethod.GET)
    public ResponseVO<List<HisTaskVO>> getHisTask(@PathVariable("id") String userId) {
        return activitiService.getHisTask(userId);
    }

    @RequestMapping(value = "/approve", method = RequestMethod.POST)
    public ResponseVO approve(@RequestBody ApproveVO approveVO) {
        return activitiService.approve(approveVO);
    }

    @RequestMapping(value = "/diagram/{id}", method = RequestMethod.GET)
    public void diagram(@PathVariable("id") String processInstanceId, HttpServletResponse response) {
        try {
            InputStream is = activitiService.getDiagram(processInstanceId);
            if (is == null)
                return;
            response.setContentType("image/png");

            BufferedImage image = ImageIO.read(is);
            OutputStream out = response.getOutputStream();
            ImageIO.write(image, "png", out);

            is.close();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
    }
}
