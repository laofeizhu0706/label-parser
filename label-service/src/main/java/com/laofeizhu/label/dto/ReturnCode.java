package com.laofeizhu.label.dto;

/**
 * @Author laofeizhu
 * @Date 2021/1/29
 */
public enum  ReturnCode {
    SUCCESS(200),
    FAIL(10000);

    private Integer code;

    ReturnCode(Integer code) {
        this.code = code;
    }




}
