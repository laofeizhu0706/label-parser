package com.laofeizhu.admin.common.util.getui;

import lombok.Data;

/**
 * @author 老肥猪
 * @since 2019/11/25
 * 推送的请求
 */
@Data
public class GeTuiPushDto {
    private String title;//推送标题

    private String text;//推送内容

    private Object payload;

    private PushShowTypeEnum pushShowType=PushShowTypeEnum.BASE; //推送展示模式 默认基础样式

    private String showText; //展示更多的内容 （可以是图片 也可以是文字）

    private PushTypeEnum pushTypeEnum=PushTypeEnum.INDEX; //推送类型 默认首页

    private String link; //链接

    private String[] linkArgs; //内链接的时候才会有

    private String logo="logo";//logo 可不填 需要提前内置到客户端

//    private String logoUrl;//logo地址 小米、华为有些机型不支持此参数
    private PushPlatformEnum pushPlatform = PushPlatformEnum.ALL; //默认安卓

    private IPushUser pushWho; //推送给谁

    private Integer badgeCount;
}
