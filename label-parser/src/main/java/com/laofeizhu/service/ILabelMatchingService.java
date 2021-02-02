package com.laofeizhu.service;

import com.laofeizhu.model.UserLabelVo;

import java.util.List;

/**
 * @Author: laofeizhu (云辰)
 * @Description:
 * @Date: Created in 5:12 下午 2021/2/2
 * @Modified By:
 */
public interface ILabelMatchingService {

    /**
     * 根据用户标签，匹配商品标签
     * @param userLabels
     * @return
     */
    String getMatchingLabel(List<UserLabelVo> userLabels);
}
