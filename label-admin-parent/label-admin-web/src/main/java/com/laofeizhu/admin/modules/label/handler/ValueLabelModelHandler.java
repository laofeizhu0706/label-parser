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
public class ValueLabelModelHandler implements ILabelModelHandler {

    private static final String MODEL = "{0}:UserLabelVo(label==\"{1}\" && value==\"{2}\")\n";

    @Override
    public String scene() {
        return LabelHandlerTypeEnum.VALUE.name();
    }

    @Override
    public String get(String content) {
        String[] split = content.split("\\(");
        String name = split[0];
        String value = split[1].replace(")", "");
        return MessageFormat.format(MODEL, name, name, value);
    }
}
