package com.laofeizhu.model;

import lombok.Data;

/**
 * @Author: laofeizhu (云辰)
 * @Description:
 * @Date: Created in 5:13 下午 2021/2/2
 * @Modified By:
 */
public class UserLabelVo extends BaseUserVo {
    private String value;

    public UserLabelVo() {
    }

    public UserLabelVo(String label) {
        super(label);
    }

    public UserLabelVo(String label, String value) {
        super(label);
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
