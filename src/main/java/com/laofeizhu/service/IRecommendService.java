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

    List<String> listMatchingLabel(List<String> userLabels);

    List<BaseProductVo> listMatchingProduct(List<String> userLabels);
}
