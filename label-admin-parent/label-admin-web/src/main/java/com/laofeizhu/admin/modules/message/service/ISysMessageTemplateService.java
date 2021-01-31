package com.laofeizhu.admin.modules.message.service;

import com.laofeizhu.admin.modules.message.entity.SysMessageTemplate;
import java.util.List;

import com.laofeizhu.admin.common.system.base.service.JeecgService;

/**
 * @Description: 消息模板
 * @Author: ls
 * @Date:  2019-04-09
 * @Version: V1.0
 */
public interface ISysMessageTemplateService extends JeecgService<SysMessageTemplate> {
    List<SysMessageTemplate> selectByCode(String code);
}
