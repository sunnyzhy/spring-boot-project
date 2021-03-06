package com.zhy.activitiserver5.utils;

import com.zhy.activitiserver5.vo.ResponseVO;
import org.springframework.stereotype.Component;

/**
 * @author zhy
 * @date 2018/7/17 16:41
 **/
@Component
public class ResponseVoUtil {
    public ResponseVO success() {
        ResponseVO resultVO = new ResponseVO();
        resultVO.setCode(0);
        resultVO.setMsg("success");
        return resultVO;
    }

    public ResponseVO success(Object data) {
        ResponseVO resultVO = new ResponseVO();
        resultVO.setCode(0);
        resultVO.setMsg("success");
        resultVO.setData(data);
        return resultVO;
    }

    public ResponseVO error(Integer code, String msg) {
        ResponseVO resultVO = new ResponseVO();
        resultVO.setCode(code);
        resultVO.setMsg(msg);
        return resultVO;
    }
}
