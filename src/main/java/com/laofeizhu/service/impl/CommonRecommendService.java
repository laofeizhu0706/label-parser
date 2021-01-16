package com.laofeizhu.service.impl;

import cn.hutool.core.io.FileUtil;
import com.google.common.collect.Lists;
import com.laofeizhu.config.KieSessionHelper;
import com.laofeizhu.data.ICommonContent;
import com.laofeizhu.data.impl.DefaultCommonContent;
import com.laofeizhu.model.BaseProductVo;
import com.laofeizhu.model.BaseUserVo;
import com.laofeizhu.model.Result;
import com.laofeizhu.service.IRecommendService;
import org.kie.api.runtime.KieSession;

import java.util.List;
import java.util.Objects;

/**
 * @Classname CommonRecommendService
 * @Description TODO
 * @Date 2021/1/16 16:13
 * @Created by laofeizhu
 */
public class CommonRecommendService implements IRecommendService {

    private static final String LABEL_PATH = "label.txt";

    private String filePath;

    private Boolean isReload;

    private ICommonContent content;

    private List<BaseProductVo> baseProductVos;

    private static final String PRODUCT_DRL_PATH = "rules/product/product.drl";

    protected CommonRecommendService(String filePath, ICommonContent content, List<BaseProductVo> baseProductVos, Boolean isReload) {
        this.filePath = filePath;
        if (Objects.isNull(content)) {
            DefaultCommonContent defaultCommonContent = new DefaultCommonContent();
            List<String> labels = FileUtil.readUtf8Lines(LABEL_PATH);
            labels.forEach(labelString -> {
                String[] label = labelString.split("\\|");
                String[] userLabels = label[0].split(",");
                String[] productLabels = label[1].split(",");
                for (String userLabel : userLabels) {
                    for (String productLabel : productLabels) {
                        defaultCommonContent.put(userLabel, productLabel);
                    }
                }
            });
            this.content = defaultCommonContent;
        } else {
            this.content = content;
        }
        this.baseProductVos = baseProductVos;
        this.isReload = Objects.isNull(isReload) ? false : isReload;
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
            kieSession.insert(content);
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
