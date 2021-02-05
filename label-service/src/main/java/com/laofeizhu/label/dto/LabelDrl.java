package com.laofeizhu.label.dto;

import lombok.Data;

import java.util.Date;

/**
 * @Author laofeizhu
 * @Date 2021/1/29
 */
@Data
public class LabelDrl extends BaseDo {

    private String labelVersion;

    private String content;

    private String title;

    private Boolean enable;
}
