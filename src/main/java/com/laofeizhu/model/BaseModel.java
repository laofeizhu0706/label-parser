package com.laofeizhu.model;

import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;

import java.io.Serializable;

/**
 * @Classname BaseModel
 * @Description TODO
 * @Date 2021/1/16 12:08
 * @Created by laofeizhu
 */
public class BaseModel implements Serializable {
    private static final long serialVersionUID = 672098491304326953L;

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
