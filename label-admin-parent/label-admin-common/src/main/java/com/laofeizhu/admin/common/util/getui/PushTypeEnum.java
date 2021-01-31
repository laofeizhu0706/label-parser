package com.laofeizhu.admin.common.util.getui;

import lombok.Getter;

/**
 * @author 老肥猪
 * @since 2019/11/25
 * 推送类型
 */
public enum  PushTypeEnum {
    INDEX(Byte.valueOf("1")), //首页
    INNER_LINK(Byte.valueOf("2")),//内链接
    BROWSER(Byte.valueOf("3")); //浏览器

    @Getter
    private Byte type;

    PushTypeEnum(Byte type) {
        this.type = type;
    }
}
