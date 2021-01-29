package com.laofeizhu.label.service.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.laofeizhu.label.dto.TempProduct;
import com.laofeizhu.label.service.LabelDrlService;
import com.laofeizhu.label.service.RecommendService;
import com.laofeizhu.service.IRecommendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

/**
 * @Author laofeizhu
 * @Date 2021/1/29
 */
@Service
@Order(value = Integer.MAX_VALUE)
public class LabelDrlServiceImpl implements LabelDrlService {


    @Autowired
    @Qualifier("drlRecommendServiceImpl")
    private RecommendService recommendService;

    @Override
    public List<String> searchProductLabel(List<String> userLabel) {
        List<IRecommendService> recommends = recommendService.listRecommends();
        HashSet<String> productLabelSets = Sets.newHashSet();
        for (IRecommendService recommendService : recommends) {
            productLabelSets.addAll(recommendService.listMatchingLabel(userLabel));
        }
        return Lists.newArrayList(productLabelSets);
    }

    @Override
    public List<TempProduct> searchProduct(List<String> userLabel) {
        List<IRecommendService> recommends = recommendService.listRecommends();
        HashSet<TempProduct> productLabelSets = Sets.newHashSet();
        for (IRecommendService recommendService : recommends) {
            productLabelSets.addAll((List<TempProduct>)recommendService.listMatchingProduct(userLabel));
        }
        return Lists.newArrayList(productLabelSets);
    }

}
