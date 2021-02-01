package com.laofeizhu.admin.modules.label.service;

import com.laofeizhu.admin.common.system.base.service.JeecgService;
import com.laofeizhu.admin.modules.label.entity.LabelProductTag;
import com.baomidou.mybatisplus.extension.service.IService;
import com.laofeizhu.admin.modules.label.entity.LabelProductTag;

/**
 * @Description: 用户标签
 * @Author: laofeizhu
 * @Date:   2021-02-01
 * @Version: V1.0
 */
public interface ILabelProductTagService extends JeecgService<LabelProductTag> {

    void addTag(LabelProductTag tag);

    void editTag(LabelProductTag tag);

    void deleteTag(String id);

    void deleteByParentId(String parentId);
}
