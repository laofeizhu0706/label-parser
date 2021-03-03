package com.laofeizhu.service;

import com.laofeizhu.model.BaseProductVo;
import com.laofeizhu.model.UserLabelVo;

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
    <T extends BaseProductVo>  void addProduct(List<T> baseProductVos);


    /**
     * 替换商品标签接口
     * @param baseProductVos
     */
    <T extends BaseProductVo>  void replaceProduct(List<T> baseProductVos);


    /**
     * 获取列表
     * @return
     */
    List<? extends BaseProductVo>  listProduct();

    /**
     * 根据用户标签，匹配商品标签
     * @param userLabels
     * @return
     */
    List<String> listMatchingLabel(List<UserLabelVo> userLabels);

    /**
     * 根据用户标签，匹配商品结果
     * @param userLabels
     * @return
     */
    <T extends BaseProductVo> List<T> listMatchingProduct(List<UserLabelVo> userLabels);

    String getContent();

}
