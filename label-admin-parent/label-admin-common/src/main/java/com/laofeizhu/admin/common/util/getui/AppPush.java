package com.laofeizhu.admin.common.util.getui;

/**
 * @author 老肥猪
 * @since 2019/11/25
 */
public class AppPush implements IPushUser {
    @Override
    public String toPushString() {
        return "app";
    }
}
