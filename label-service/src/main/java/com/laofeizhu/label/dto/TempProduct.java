package com.laofeizhu.label.dto;

import com.laofeizhu.model.BaseProductVo;

import java.util.Objects;

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

    public boolean equalsLabelProduct(LabelProduct o) {
        return Objects.equals(title, o.getName()) &&
                Objects.equals(productId, o.getProductId());
    }

}
