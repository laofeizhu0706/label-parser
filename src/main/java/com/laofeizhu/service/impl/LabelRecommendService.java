package com.laofeizhu.service.impl;

import com.google.common.collect.Lists;
import com.laofeizhu.config.KieSessionHelper;
import com.laofeizhu.model.BaseProductVo;
import com.laofeizhu.model.BaseUserVo;
import com.laofeizhu.model.Result;
import com.laofeizhu.service.IRecommendService;
import org.kie.api.runtime.KieSession;

import java.util.List;
import java.util.Objects;

/**
 * @Classname LabelRecommendService
 * @Description TODO
 * @Date 2021/1/16 16:14
 * @Created by laofeizhu
 */
public class LabelRecommendService implements IRecommendService {

    private String filePath;

    private Boolean isReload;


    private List<BaseProductVo> baseProductVos;

    private static final String PRODUCT_DRL_PATH = "rules/product/product.drl";

    protected LabelRecommendService(String filePath, List<BaseProductVo> baseProductVos, Boolean isReload) {
        this.filePath = filePath;
        this.baseProductVos = baseProductVos;
        this.isReload = Objects.isNull(isReload) ? false : isReload;
    }

    @Override
    public void addProductLabel(List<BaseProductVo> baseProductVos) {
        this.baseProductVos.addAll(baseProductVos);
    }

    @Override
    public List<String> listMatchingLabel(List<String> userLabels) {
        if (Objects.nonNull(userLabels) && userLabels.size() > 0) {
            KieSession kieSession = KieSessionHelper.getKieSessionByPath(filePath, isReload);
            for (String userLabel : userLabels) {
                kieSession.insert(new BaseUserVo(userLabel));
            }
            Result result = new Result();
            kieSession.insert(result);
            kieSession.fireAllRules();
            kieSession.dispose();
            return result.getResults();
        } else {
            return Lists.newArrayList();
        }
    }

    @Override
    public List<BaseProductVo> listMatchingProduct(List<String> userLabels) {
        if (Objects.nonNull(baseProductVos) &&
                baseProductVos.size() > 0) {
            List<String> productLabels = listMatchingLabel(userLabels);
            if (Objects.nonNull(productLabels) &&
                    productLabels.size() > 0) {
                KieSession kieSession = KieSessionHelper.getKieSessionByPath(PRODUCT_DRL_PATH);
                Result result = new Result();
                for (String productLabel : productLabels) {
                    kieSession.insert(new BaseUserVo(productLabel));
                }
                for (BaseProductVo productVo : baseProductVos) {
                    kieSession.insert(productVo);
                }
                kieSession.insert(result);
                kieSession.fireAllRules();
                kieSession.dispose();
                return result.getProductResults();
            }
        }
        return Lists.newArrayList();
    }
}
