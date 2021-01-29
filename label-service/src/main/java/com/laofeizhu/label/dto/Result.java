package com.laofeizhu.label.dto;

/**
 * @Author laofeizhu
 * @Date 2021/1/29
 */
public class Result {

    private String code;

    private String msg;

    private Object data;

    public Result(String code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static Result success(Object data) {
        return new Result(ReturnCode.SUCCESS.name(), null, data);
    }

    public static Result fail(String msg) {
        return new Result(ReturnCode.FAIL.name(), msg, null);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
