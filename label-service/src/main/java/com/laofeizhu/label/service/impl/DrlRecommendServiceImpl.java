package com.laofeizhu.label.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Lists;
import com.laofeizhu.label.dao.LabelProductMapper;
import com.laofeizhu.label.dao.LabelProductSubTagMapper;
import com.laofeizhu.label.dao.LabelUserSubTagMapper;
import com.laofeizhu.label.dto.LabelProduct;
import com.laofeizhu.label.dto.LabelProductSubTag;
import com.laofeizhu.label.dto.LabelUserSubTag;
import com.laofeizhu.label.dto.TempProduct;
import com.laofeizhu.label.service.RecommendService;
import com.laofeizhu.service.IRecommendService;
import com.laofeizhu.service.impl.DefaultRecommendService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @Author laofeizhu
 * @Date 2021/1/29
 */
@Service
@Slf4j
public class DrlRecommendServiceImpl implements RecommendService {

    private List<IRecommendService> userRecommendServices;
    private List<IRecommendService> productRecommendServices;

    @Autowired
    private LabelProductSubTagMapper labelProductSubTagMapper;

    @Autowired
    private LabelUserSubTagMapper labelUserSubTagMapper;

    @Autowired
    private LabelProductMapper labelProductMapper;

    private static final String USER_LABEL_PRE = "user_";
    private static final String PRODUCT_LABEL_PRE = "product_";

    @PostConstruct
    public void init() {
        userRecommendServices = Lists.newArrayList();
        List<LabelUserSubTag> labelUserSubTags = labelUserSubTagMapper.selectList(new QueryWrapper<>());
        for (LabelUserSubTag label : labelUserSubTags) {
            try {
                IRecommendService recommendService = DefaultRecommendService.buildByVersion(USER_LABEL_PRE + label.getName(), label.getContent());
                userRecommendServices.add(recommendService);
            } catch (Exception e) {
                log.error("error to create userRecommendService",e);
            }
        }
        productRecommendServices = Lists.newArrayList();
        List<LabelProductSubTag> labelProductSubTags = labelProductSubTagMapper.selectList(new QueryWrapper<>());
        for (LabelProductSubTag label : labelProductSubTags) {
            try {
                IRecommendService recommendService = DefaultRecommendService.buildByVersion(PRODUCT_LABEL_PRE + label.getName(), label.getContent());
                productRecommendServices.add(recommendService);
            } catch (Exception e) {
                log.error("error to create productRecommendService",e);
            }
        }
    }

    @Override
    public List<IRecommendService> listUserRecommends() {
        return this.userRecommendServices;
    }

    @Override
    public List<IRecommendService> listProductRecommends() {
        return this.productRecommendServices;
    }


    @Override
    public void refresh() {
        log.debug("===================== start refresh recommendService =====================");
        if (Objects.nonNull(userRecommendServices)) {
            QueryWrapper<LabelUserSubTag> wrapper = new QueryWrapper<>();
            List<LabelUserSubTag> labelUserSubTags = labelUserSubTagMapper.selectList(wrapper);
            boolean isUpdate = false;
            if (labelUserSubTags.size() != userRecommendServices.size()) {
                isUpdate = true;
            } else {
                for (LabelUserSubTag labelDrl : labelUserSubTags) {
                    if (userRecommendServices.stream().noneMatch(o -> labelDrl.getContent().equals(o.getContent()))) {
                        isUpdate = true;
                        break;
                    }
                }
            }
            //全量更新
            if (isUpdate) {
                log.info("===================== refresh all user recommendService =====================");
                List<IRecommendService> temp = Lists.newArrayList();
                for (LabelUserSubTag label : labelUserSubTags) {
                    IRecommendService recommendService = DefaultRecommendService.buildByVersion(USER_LABEL_PRE + label.getName(), label.getContent());
                    temp.add(recommendService);
                }
                userRecommendServices = temp;
            } else {
                log.debug("===================== not refresh user recommendService =====================");
            }
        }
        if (Objects.nonNull(productRecommendServices)) {
            List<LabelProductSubTag> labelProductSubTags = labelProductSubTagMapper.selectList(new QueryWrapper<>());
            boolean isUpdate = false;
            if (labelProductSubTags.size() != productRecommendServices.size()) {
                isUpdate = true;
            } else {
                for (LabelProductSubTag labelDrl : labelProductSubTags) {
                    if (productRecommendServices.stream().noneMatch(o -> labelDrl.getContent().equals(o.getContent()))) {
                        isUpdate = true;
                        break;
                    }
                }
            }
            //全量更新
            if (isUpdate) {
                log.info("===================== refresh all product recommendService =====================");
                List<IRecommendService> temp = Lists.newArrayList();
                for (LabelProductSubTag label : labelProductSubTags) {
                    IRecommendService recommendService = DefaultRecommendService.buildByVersion(PRODUCT_LABEL_PRE + label.getName(), label.getContent());
                    temp.add(recommendService);
                }
                productRecommendServices = temp;
            } else {
                log.debug("===================== not refresh product recommendService =====================");
            }
        }
        log.debug("===================== end refresh recommendService =====================");
    }

    @Override
    public void refreshProduct() {
        log.debug("===================== start refresh products =====================");
        List<LabelProduct> products = labelProductMapper.selectList(new QueryWrapper<>());
        if (products != null && products.size() > 0) {
            Map<String, LabelProduct> productMap = products.stream().collect(Collectors.toMap(LabelProduct::getId, v->v));
            for (IRecommendService recommendService : productRecommendServices) {
                List<TempProduct> productList = (List<TempProduct>) recommendService.listProduct();
                if (CollectionUtil.isNotEmpty(productList)) {
                    boolean isReplace = false;
                    if (productMap.size() != productList.size()) {
                        isReplace = true;
                    } else {
                        for (TempProduct product : productList) {
                            if (productMap.containsKey(product.getId())) {
                                LabelProduct labelProduct = productMap.get(product.getId());
                                if (!product.equalsLabelProduct(labelProduct)) {
                                    isReplace = true;
                                    break;
                                }
                            } else {
                                isReplace = true;
                                break;
                            }
                        }
                    }
                    if (isReplace) {
                        log.info("===================== replace all product =====================");
                        recommendService.replaceProduct(products.stream().map(o -> {
                            TempProduct tempProduct = new TempProduct();
                            tempProduct.setTitle(o.getName());
                            tempProduct.setProductId(o.getProductId());
                            tempProduct.setId(o.getId());
                            tempProduct.setLabels(Lists.newArrayList(o.getLabelName().split(",")));
                            return tempProduct;
                        }).collect(Collectors.toList()));
                    }

                } else {
                    recommendService.replaceProduct(Lists.newArrayList());
                }

            }
        }
        log.debug("===================== end refresh products =====================");
    }


}
