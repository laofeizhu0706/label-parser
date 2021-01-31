package com.laofeizhu.admin.modules.message.service.impl;

import com.laofeizhu.admin.modules.message.entity.SysMessage;
import com.laofeizhu.admin.modules.message.mapper.SysMessageMapper;
import com.laofeizhu.admin.modules.message.service.ISysMessageService;
import com.laofeizhu.admin.common.system.base.service.impl.JeecgServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @Description: 消息
 * @Author: ls
 * @Date:  2019-04-09
 * @Version: V1.0
 */
@Service
public class SysMessageServiceImpl extends JeecgServiceImpl<SysMessageMapper, SysMessage> implements
    ISysMessageService {

}
