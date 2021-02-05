package com.laofeizhu.admin.modules.label.service;

import com.laofeizhu.admin.common.system.base.service.JeecgService;
import com.laofeizhu.admin.modules.label.entity.LabelProductSubTag;
import com.laofeizhu.admin.modules.label.entity.LabelUserSubTag;

/**
 * @Description: 商品子标签
 * @Author: laofeizhu
 * @Date: 2021-02-02
 * @Version: V1.0
 */
public interface ILabelProductSubTagService extends JeecgService<LabelProductSubTag> {

    void addTag(LabelProductSubTag labelProductSubTag);

    void updateTag(LabelProductSubTag labelProductSubTag);
}
