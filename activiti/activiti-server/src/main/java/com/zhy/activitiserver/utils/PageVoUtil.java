package com.zhy.activitiserver.utils;

import com.zhy.activitiserver.vo.PageVO;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PageVoUtil<T> {
    public PageVO<T> create(List<T> records) {
        PageVO<T> pageVO = new PageVO<T>();
        pageVO.setRecords(records);
        pageVO.setCount(records.size());
        return pageVO;
    }
}
