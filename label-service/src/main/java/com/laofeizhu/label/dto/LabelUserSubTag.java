package com.laofeizhu.label.dto;

import lombok.Data;

/**
 * @Classname LabelUserSubTag
 * @Description TODO
 * @Date 2021/2/5 21:42
 * @Created by laofeizhu
 */
@Data
public class LabelUserSubTag extends BaseDo {
    private String name;

    private String subTag;

    private String content;
}
