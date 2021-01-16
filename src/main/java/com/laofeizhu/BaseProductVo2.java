package com.laofeizhu;

import com.laofeizhu.model.BaseProductVo;

import java.util.List;

/**
 * @Classname BaseProductVo2
 * @Description TODO
 * @Date 2021/1/16 19:27
 * @Created by laofeizhu
 */
public class BaseProductVo2 extends BaseProductVo {
    private String url;


    public BaseProductVo2(String id, List<String> labels, String url) {
        super(id, labels);
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
