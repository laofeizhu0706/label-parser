package com.laofeizhu.admin.modules.label.service;

import com.laofeizhu.admin.common.system.base.service.JeecgService;
import com.laofeizhu.admin.modules.label.entity.LabelUserSubTag;

/**
 * @Description: 用户子标签
 * @Author: laofeizhu
 * @Date: 2021-02-02
 * @Version: V1.0
 */
public interface ILabelUserSubTagService extends JeecgService<LabelUserSubTag> {

    void addTag(LabelUserSubTag labelUserSubTag);

    void updateTag(LabelUserSubTag labelUserSubTag);
}
