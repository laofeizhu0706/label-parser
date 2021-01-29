package com.laofeizhu.service;

import com.laofeizhu.model.BaseProductVo;

import java.util.List;

/**
 * @Classname IRecommendService
 * @Description TODO
 * @Date 2021/1/16 14:29
 * @Created by laofeizhu
 */
public interface IRecommendService {

    /**
     * 新增商品标签接口
     * @param baseProductVos
     */
    void addProductLabel(List<? extends BaseProductVo> baseProductVos);

    /**
     * 根据用户标签，匹配商品标签
     * @param userLabels
     * @return
     */
    List<String> listMatchingLabel(List<String> userLabels);

    /**
     * 根据用户标签，匹配商品结果
     * @param userLabels
     * @return
     */
    List<? extends BaseProductVo> listMatchingProduct(List<String> userLabels);

    String getContent();

}
