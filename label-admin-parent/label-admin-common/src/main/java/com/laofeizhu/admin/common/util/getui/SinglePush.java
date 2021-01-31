package com.laofeizhu.admin.common.util.getui;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author 老肥猪
 * @since 2019/11/25
 */
@Data
@AllArgsConstructor
public class SinglePush implements IPushUser{
    private String member;

    @Override
    public String toPushString() {
        return member;
    }
}
