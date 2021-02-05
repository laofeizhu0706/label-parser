package com.laofeizhu.service.impl;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.laofeizhu.config.KieSessionHelper;
import com.laofeizhu.config.RuleLoader;
import com.laofeizhu.data.ICommonContent;
import com.laofeizhu.data.impl.DefaultCommonContent;
import com.laofeizhu.enums.ReadFromType;
import com.laofeizhu.model.BaseProductVo;
import com.laofeizhu.model.BaseUserVo;
import com.laofeizhu.model.Result;
import com.laofeizhu.model.UserLabelVo;
import com.laofeizhu.service.IRecommendService;
import org.kie.api.runtime.KieSession;

import java.io.InputStream;
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

    private String version;

    private ReadFromType fromType;

    private List<? extends BaseProductVo> baseProductVos;

    private static final String PRODUCT_DRL_PATH = "rules/product/product.drl";

    protected <T extends BaseProductVo>CommonRecommendService(String filePath, ICommonContent content, List<T> baseProductVos, Boolean isReload) {
        this.fromType = ReadFromType.PATH;
        this.filePath = filePath;
        if (Objects.isNull(content)) {
            DefaultCommonContent defaultCommonContent = new DefaultCommonContent();
            List<String> labels = Lists.newArrayList();
            try {
                labels = FileUtil.readUtf8Lines(LABEL_PATH);
            } catch (Exception e) {
                try (InputStream stream = RuleLoader.class.getClassLoader().getResourceAsStream(LABEL_PATH)) {
                    labels = IoUtil.readUtf8Lines(stream, labels);
                } catch (Exception ex) {
                    throw new RuntimeException("no such file");
                }
            }
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

    protected CommonRecommendService(String version, String drlContent, ICommonContent content, List<? extends BaseProductVo> baseProductVos) {
        if (Objects.isNull(content)) {
            DefaultCommonContent defaultCommonContent = new DefaultCommonContent();
            List<String> labels = Lists.newArrayList();
            try {
                labels = FileUtil.readUtf8Lines(LABEL_PATH);
            } catch (Exception e) {
                try (InputStream stream = RuleLoader.class.getClassLoader().getResourceAsStream(LABEL_PATH)) {
                    labels = IoUtil.readUtf8Lines(stream, labels);
                } catch (Exception ex) {
                    throw new RuntimeException("no such file");
                }
            }
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
        this.version = version;
        this.fromType = ReadFromType.CONTENT;
        this.baseProductVos = baseProductVos;
        KieSessionHelper.loadInVersion(version, drlContent);
    }

    @Override
    public <T extends BaseProductVo>void addProductLabel(List<T> productVos) {
        if (this.baseProductVos != null && this.baseProductVos.size() > 0) {
            this.baseProductVos.addAll(Lists.newArrayList());
        } else {
            this.baseProductVos = Lists.newArrayList(Sets.newHashSet(productVos));
        }
    }

    @Override
    public List<String> listMatchingLabel(List<UserLabelVo> userLabels) {
        if (Objects.nonNull(userLabels) && userLabels.size() > 0) {
            KieSession kieSession = null;
            switch (this.fromType) {
                case PATH:
                    kieSession = KieSessionHelper.getKieSessionByPath(filePath, isReload);
                    break;
                case CONTENT:
                    kieSession = KieSessionHelper.getKieSessionByVersion(version);
                    break;
                default:
                    kieSession = KieSessionHelper.getKieSessionByVersion(version);
                    break;
            }
            for (UserLabelVo userLabel : userLabels) {
                kieSession.insert(userLabel);
            }
            Result<String> result = new Result<String>();
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
    public <T extends BaseProductVo> List<T> listMatchingProduct(List<UserLabelVo> userLabels) {
        if (Objects.nonNull(baseProductVos) &&
                baseProductVos.size() > 0) {
            List<String> productLabels = listMatchingLabel(userLabels);
            if (Objects.nonNull(productLabels) &&
                    productLabels.size() > 0) {
                KieSession kieSession = KieSessionHelper.getKieSessionByPath(PRODUCT_DRL_PATH);
                Result<T> result = new Result<T>();
                for (String productLabel : productLabels) {
                    kieSession.insert(new BaseUserVo(productLabel));
                }
                for (BaseProductVo productVo : baseProductVos) {
                    kieSession.insert(productVo);
                }
                kieSession.insert(result);
                kieSession.fireAllRules();
                kieSession.dispose();
                return result.getResults();
            }
        }
        return Lists.newArrayList();
    }

    @Override
    public String getContent() {
        switch (this.fromType) {
            case PATH:
                return KieSessionHelper.getSessionContentByPath(filePath);
            case CONTENT:
                return KieSessionHelper.getSessionContentByVersion(version);
            default:
                return KieSessionHelper.getSessionContentByVersion(version);
        }
    }
}
