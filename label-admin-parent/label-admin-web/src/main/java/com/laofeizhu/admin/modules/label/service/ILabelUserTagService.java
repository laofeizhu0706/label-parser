package com.laofeizhu.admin.modules.label.service;

import com.laofeizhu.admin.common.system.base.service.JeecgService;
import com.laofeizhu.admin.modules.label.entity.LabelProductTag;
import com.laofeizhu.admin.modules.label.entity.LabelUserTag;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @Description: 用户标签
 * @Author: laofeizhu
 * @Date:   2021-02-01
 * @Version: V1.0
 */
public interface ILabelUserTagService extends JeecgService<LabelUserTag> {

    void addTag(LabelUserTag tag);

    void editTag(LabelUserTag tag);

    void deleteTag(String id);

    void deleteByParentId(String parentId);
}
