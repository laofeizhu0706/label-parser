package com.laofeizhu.admin.modules.label.handler;

import org.springframework.stereotype.Service;

import java.text.MessageFormat;

/**
 * @Author: laofeizhu (云辰)
 * @Description:
 * @Date: Created in 4:27 下午 2021/2/5
 * @Modified By:
 */
@Service
public class RangeLabelModelHandler implements ILabelModelHandler {

    private static final String MODEL = "{0}:UserLabelVo(label==\"{1}\" && Integer.valueOf(value)>={2} && Integer.valueOf(value)<={3} )\n";

    @Override
    public String scene() {
        return LabelHandlerTypeEnum.RANGE.name();
    }

    //身高(160~170),收入(5000~9000)
    @Override
    public String get(String content) {
        String[] split = content.split("\\(");
        String name = split[0];
        String value = split[1].replace(")","");
        String[] nums = value.split("~");
        return MessageFormat.format(MODEL, name, name, nums[0], nums[1]);
    }
}
