package com.laofeizhu.admin.modules.label.handler;

import org.springframework.stereotype.Service;

import java.text.MessageFormat;

/**
 * @Author: laofeizhu (云辰)
 * @Description:
 * @Date: Created in 4:28 下午 2021/2/5
 * @Modified By:
 */
@Service
public class NonValueLabelModelHandler implements ILabelModelHandler {

    private static final String MODEL = "{0}:UserLabelVo(label==\"{1}\")\n";

    @Override
    public String scene() {
        return LabelHandlerTypeEnum.NON_VALUE.name();
    }

    @Override
    public String get(String content) {
        return MessageFormat.format(MODEL, content, content);
    }
}
