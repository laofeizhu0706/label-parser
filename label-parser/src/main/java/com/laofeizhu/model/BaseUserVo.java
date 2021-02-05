package com.laofeizhu.model;

import lombok.Data;

/**
 * @Classname UserVo
 * @Description TODO
 * @Date 2021/1/16 12:03
 * @Created by laofeizhu
 */
public class BaseUserVo extends BaseModel {
    private static final long serialVersionUID = -6337327151630802475L;

    private String label;

    public BaseUserVo() {
    }

    public BaseUserVo(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

}
