package com.zhy.user.utils;

import com.zhy.user.vo.ResponseVO;
import org.springframework.stereotype.Component;

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
