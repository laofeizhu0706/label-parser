package com.laofeizhu.label.service;

import com.laofeizhu.service.IRecommendService;

import java.util.List;

/**
 * @Author laofeizhu
 * @Date 2021/1/29
 */
public interface RecommendService {

    List<IRecommendService> listUserRecommends();

    List<IRecommendService> listProductRecommends();

    void refresh();

    void refreshProduct();
}
