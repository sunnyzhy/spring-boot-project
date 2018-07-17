package com.zhy.activitiserver5.service;

import com.zhy.activitiserver5.utils.FileUtil;
import com.zhy.activitiserver5.utils.ResponseVoUtil;
import com.zhy.activitiserver5.vo.ResponseVO;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Model;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

/**
 * @author zhy
 * @date 2018/7/17 16:41
 **/
@Service
public class ModelService {
    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    private ResponseVoUtil responseVoUtil;
    @Autowired
    private FileUtil fileUtil;
    @Value("${file.local.path}")
    private String fileLocalPath;
    @Value("${file.url}")
    private String fileUrl;

    /**
     * 生成工作流图片
     * @param modelId
     * @return
     */
    public ResponseVO<String> getDiagram(String modelId) {
        //查找model
        Model modelData = repositoryService.getModel(modelId);
        if (modelData == null) {
            return responseVoUtil.error(-1, "模型不存在");
        }

        //获取png的字节流
        byte[] pngBytes = repositoryService.getModelEditorSourceExtra(modelData.getId());

        String fileName = String.format("%s/activiti.%s.png", fileLocalPath, modelData.getDeploymentId());
        File file = fileUtil.init(fileLocalPath, fileName);

        //把png文件保存到nginx的文件目录
        try {
            ByteArrayInputStream inputStream = new ByteArrayInputStream(pngBytes);
            OutputStream outputStream = new FileOutputStream(file);
            IOUtils.copy(inputStream, outputStream);
            outputStream.close();
            inputStream.close();
            return responseVoUtil.success(String.format("%s/%s", fileUrl, new File(fileName).getName()));
        } catch (Exception e) {
            return responseVoUtil.error(-1, e.getMessage());
        }
    }
}
