package com.laofeizhu.label.dto;

import com.laofeizhu.model.BaseProductVo;

/**
 * @Author laofeizhu
 * @Date 2021/1/29
 */
public class TempProduct extends BaseProductVo {
    private String title;

    private String productId;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }
}
