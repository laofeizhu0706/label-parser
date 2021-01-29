package com.laofeizhu.service.impl;

import com.google.common.collect.Lists;
import com.laofeizhu.data.ICommonContent;
import com.laofeizhu.model.BaseProductVo;
import com.laofeizhu.service.IRecommendService;

import java.util.List;

/**
 * @Classname DroolsRecommendService
 * @Description 默认推荐服务（基于drools）
 * @Date 2021/1/16 14:36
 * @Created by laofeizhu
 */
public class DefaultRecommendService implements IRecommendService {

    private static final String DEFAULT_COMMON_PATH = "rules/common/common.drl";
    private static final String DEFAULT_LABEL_PATH = "rules/label/label.drl";


    public static IRecommendService buildCommon() {
        return buildCommon(DEFAULT_COMMON_PATH, false);
    }

    public static IRecommendService buildCommon(List<? extends BaseProductVo> baseProductVos) {
        return buildCommon(DEFAULT_COMMON_PATH, baseProductVos, false);
    }

    public static IRecommendService buildCommon(Boolean isReload) {
        return buildCommon(DEFAULT_COMMON_PATH, isReload);
    }

    public static IRecommendService buildCommon(String filePath) {
        return buildCommon(filePath, false);
    }

    public static IRecommendService buildCommon(String filePath, Boolean isReload) {
        return buildCommon(filePath, null, isReload);
    }

    public static IRecommendService buildCommon(String filePath, List<? extends BaseProductVo> baseProductVos) {
        return buildCommon(filePath, baseProductVos, false);
    }

    public static IRecommendService buildCommon(ICommonContent content) {
        return buildCommon(content, null);
    }

    public static IRecommendService buildCommon(String filePath, List<? extends BaseProductVo> baseProductVos, Boolean isReload) {
        return new CommonRecommendService(filePath, null, baseProductVos, isReload);
    }

    public static IRecommendService buildCommon(ICommonContent content, List<? extends BaseProductVo> baseProductVos) {
        return new CommonRecommendService(DEFAULT_COMMON_PATH, content, baseProductVos, true);
    }

    public static IRecommendService build() {
        return build(false);
    }

    public static IRecommendService build(List<? extends BaseProductVo> baseProductVos) {
        return build(DEFAULT_LABEL_PATH, baseProductVos, false);
    }

    public static IRecommendService build(Boolean isReload) {
        return build(DEFAULT_LABEL_PATH, isReload);
    }

    public static IRecommendService build(String filePath) {
        return build(filePath, false);
    }

    public static IRecommendService buildByVersion(String version, String drlContent) {
        return buildByVersion(version, drlContent, Lists.newArrayList());
    }

    public static IRecommendService buildByVersion(String version, String drlContent, List<? extends BaseProductVo> baseProductVos) {
        return new LabelRecommendService(version, drlContent, baseProductVos);
    }

    public static IRecommendService build(String filePath, Boolean isReload) {
        return build(filePath, null, isReload);
    }

    public static IRecommendService build(String filePath, List<? extends BaseProductVo> baseProductVos) {
        return build(filePath, baseProductVos, false);
    }

    public static IRecommendService build(String filePath, List<? extends BaseProductVo> baseProductVos, Boolean isReload) {
        return new LabelRecommendService(filePath, baseProductVos, isReload);
    }

    @Override
    public void addProductLabel(List<? extends BaseProductVo> baseProductVos) {

    }

    @Override
    public List<String> listMatchingLabel(List<String> userLabels) {
        return null;
    }

    @Override
    public List<? extends BaseProductVo> listMatchingProduct(List<String> userLabels) {
        return null;
    }

    @Override
    public String getContent() {
        return null;
    }

}
