package com.laofeizhu.label.service;

import com.laofeizhu.label.dto.TempProduct;
import com.laofeizhu.model.UserLabelVo;

import java.util.List;

/**
 * @Author laofeizhu
 * @Date 2021/1/29
 */
public interface LabelDrlService {

    List<String> searchProductLabel(List<UserLabelVo> userLabel);

    List<TempProduct> searchProduct(List<UserLabelVo> userLabel);
}
