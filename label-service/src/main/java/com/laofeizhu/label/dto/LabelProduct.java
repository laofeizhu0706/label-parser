package com.laofeizhu.label.dto;

import lombok.Data;

import java.util.Date;

/**
 * @Classname Product
 * @Description TODO
 * @Date 2021/2/1 23:10
 * @Created by laofeizhu
 */
@Data
public class LabelProduct {
    private String id;
    private String name;
    private String labelName;
    private Date createTime;
    private Date updateTime;
}
