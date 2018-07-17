package com.zhy.activitiserver5.utils;

import org.springframework.stereotype.Component;

import java.io.File;

/**
 * @author zhy
 * @date 2018/7/17 16:41
 **/
@Component
public class FileUtil {
    public File init(String localPath, String fileName) {
        File dir = new File(localPath);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        File file = new File(fileName);
        if (file.exists()) {
            file.delete();
        }
        return file;
    }
}
