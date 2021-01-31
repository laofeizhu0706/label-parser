package com.laofeizhu.admin.common.util.getui;

import lombok.Data;

/**
 * @author 老肥猪
 * @since 2019/11/26
 */
@Data
public class TransmissionDto {
    private String text;
    private IPushUser pushWho;
    private boolean isIos = false;
}
