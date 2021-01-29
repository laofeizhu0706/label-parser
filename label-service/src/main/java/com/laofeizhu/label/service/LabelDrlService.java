package com.laofeizhu.label.service;

import com.laofeizhu.label.dto.TempProduct;

import java.util.List;

/**
 * @Author laofeizhu
 * @Date 2021/1/29
 */
public interface LabelDrlService {

    List<String> searchProductLabel(List<String> userLabel);

    List<TempProduct> searchProduct(List<String> userLabel);
}
