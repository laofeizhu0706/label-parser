package com.laofeizhu.label.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Lists;
import com.laofeizhu.label.dao.LabelDrlMapper;
import com.laofeizhu.label.dao.LabelProductMapper;
import com.laofeizhu.label.dto.LabelDrl;
import com.laofeizhu.label.dto.LabelProduct;
import com.laofeizhu.label.dto.TempProduct;
import com.laofeizhu.label.service.RecommendService;
import com.laofeizhu.service.IRecommendService;
import com.laofeizhu.service.impl.DefaultRecommendService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @Author laofeizhu
 * @Date 2021/1/29
 */
@Service
@Slf4j
public class DrlRecommendServiceImpl implements RecommendService {

    private List<IRecommendService> recommendServices;

    @Autowired
    private LabelDrlMapper labelDrlMapper;

    @Autowired
    private LabelProductMapper labelProductMapper;

    @PostConstruct
    public void init() {
        recommendServices = Lists.newArrayList();
        QueryWrapper<LabelDrl> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(LabelDrl::getEnable, true);
        List<LabelDrl> labelDrls = labelDrlMapper.selectList(wrapper);
        for (LabelDrl labelDrl : labelDrls) {
            IRecommendService recommendService = DefaultRecommendService.buildByVersion(labelDrl.getLabelVersion(), labelDrl.getContent());
            recommendServices.add(recommendService);
        }
    }

    @Override
    public List<IRecommendService> listRecommends() {
        return this.recommendServices;
    }

    @Override
    public void refresh() {
        log.info("===================== start refresh recommendService =====================");
        if (Objects.nonNull(recommendServices)) {
            QueryWrapper<LabelDrl> wrapper = new QueryWrapper<>();
            wrapper.lambda().eq(LabelDrl::getEnable, true);
            List<LabelDrl> labelDrls = labelDrlMapper.selectList(wrapper);
            boolean isUpdate = false;
            if (labelDrls.size() != recommendServices.size()) {
                isUpdate = true;
            } else {
                for (LabelDrl labelDrl : labelDrls) {
                    if (recommendServices.stream().noneMatch(o-> labelDrl.getContent().equals(o.getContent()))) {
                        isUpdate = true;
                        break;
                    }
                }
            }
            //全量更新
            if (isUpdate) {
                log.info("===================== refresh all recommendService =====================");
                List<IRecommendService> temp = Lists.newArrayList();
                for (LabelDrl labelDrl : labelDrls) {
                    IRecommendService recommendService = DefaultRecommendService.buildByVersion(labelDrl.getLabelVersion(), labelDrl.getContent());
                    temp.add(recommendService);
                }
                recommendServices = temp;
            } else {
                log.info("===================== not refresh recommendService =====================");
            }
        }
        log.info("===================== end refresh recommendService =====================");
    }

    @Override
    public void refreshProduct() {
        List<LabelProduct> products = labelProductMapper.selectList(new QueryWrapper<>());
        if (recommendServices!=null && recommendServices.size()>0) {
            for (IRecommendService recommendService : recommendServices) {
                List<TempProduct> productList = products.stream().map(o -> {
                    TempProduct tempProduct = new TempProduct();
                    tempProduct.setTitle(o.getName());
                    tempProduct.setId(o.getId());
                    tempProduct.setLabels(Lists.newArrayList(o.getLabelName().split(",")));
                    return tempProduct;
                }).collect(Collectors.toList());
                recommendService.addProductLabel(productList);
            }
        }
    }


}
