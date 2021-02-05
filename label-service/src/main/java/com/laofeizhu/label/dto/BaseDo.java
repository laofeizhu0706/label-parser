package com.laofeizhu.label.dto;

import com.alibaba.fastjson.JSON;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Classname BaseDo
 * @Description TODO
 * @Date 2021/2/5 21:43
 * @Created by laofeizhu
 */
@Data
public class BaseDo implements Serializable {
    private String id;

    private Date createTime;

    private Date updateTime;

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
