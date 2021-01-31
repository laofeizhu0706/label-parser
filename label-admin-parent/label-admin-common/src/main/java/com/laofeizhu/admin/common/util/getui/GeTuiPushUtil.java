package com.laofeizhu.admin.common.util.getui;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.gexin.rp.sdk.base.IAliasResult;
import com.gexin.rp.sdk.base.IPushResult;
import com.gexin.rp.sdk.base.IQueryResult;
import com.gexin.rp.sdk.base.impl.AppMessage;
import com.gexin.rp.sdk.base.impl.Message;
import com.gexin.rp.sdk.base.impl.SingleMessage;
import com.gexin.rp.sdk.base.impl.Target;
import com.gexin.rp.sdk.base.notify.Notify;
import com.gexin.rp.sdk.base.payload.APNPayload;
import com.gexin.rp.sdk.base.payload.MultiMedia;
import com.gexin.rp.sdk.dto.GtReq;
import com.gexin.rp.sdk.http.IGtPush;
import com.gexin.rp.sdk.template.NotificationTemplate;
import com.gexin.rp.sdk.template.TransmissionTemplate;
import com.gexin.rp.sdk.template.style.AbstractNotifyStyle;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.MapUtils;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author 老肥猪
 * @since 2019/11/25
 * 推送服务
 */
@Slf4j
public class GeTuiPushUtil {

    private static final String appId = "KxT51IV4Bc9Tvj1MOs2NQ3";
    private static final String appKey = "RLfRpNLlGh7IcPcjVx7yoA";
    private static final String masterSecret = "f19yR0Uw8D6ly1Kk6MqID4";
    private static final String url = "http://sdk.open.api.igexin.com/apiex.htm";
    private static IGtPush push;

    static {
        push = new IGtPush(url, appKey, masterSecret);
    }

    /**
     * 推送
     *
     * @param dto
     */
    public static boolean push(GeTuiPushDto dto) {
        try {
            boolean flag;
            switch (dto.getPushPlatform()) {
                case ALL:
                    flag = getResult(iosPush(dto)) && getResult(androidPush(dto));
                    break;
                case ANDROID:
                    flag = getResult(androidPush(dto));
                    break;
                case IOS:
                    flag = getResult(iosPush(dto));
                    break;
                default:
                    flag = getResult(iosPush(dto)) && getResult(androidPush(dto));
                    break;
            }
            return flag;
        } catch (Exception e) {
            log.error("推送异常：[{}]", e.getMessage(), e);
            return false;
        }
    }

    /**
     * 透传
     *
     * @param dto
     */
    public static boolean transmission(TransmissionDto dto) {
        IPushResult pushResult = null;
        try {
            if (dto.isIos()) {
                pushResult = iosTransmission(dto);
            } else {
                pushResult = androidTransmission(dto);
            }
            return getResult(pushResult);
        } catch (Exception e) {
            log.error("推送异常：[{}]", e.getMessage(), e);
            return false;
        }
    }

    /**
     * 获得结果
     *
     * @param pushResult
     * @return
     */
    private static boolean getResult(IPushResult pushResult) {
        try {
            Map<String, Object> response = pushResult.getResponse();
            String result = MapUtils.getString(response, "result");
            if (Objects.nonNull(result)) {
                if (result.equals("ok")) {
                    return true;
                } else {
                    log.error("推送异常：[{}]", response.toString());
                    return false;
                }
            } else {
                if (response != null) {
                    log.error("推送异常：[{}]", response.toString());
                } else {
                    log.error("推送异常");
                }
                return false;
            }
        } catch (Exception e) {
            log.error("推送异常：[{}]", e.getMessage(), e);
            return false;
        }
    }

    /**
     * 基础的透传模板
     *
     * @return
     */
    private static TransmissionTemplate transmissionTemplate(String transmissionContent) {
        TransmissionTemplate template = new TransmissionTemplate();
        template.setAppId(appId);
        template.setAppkey(appKey);
        template.setTransmissionType(2);
        template.setTransmissionContent(transmissionContent);
        return template;
    }

    /**
     * 基础的推送模板
     *
     * @param style
     * @return
     */
    private static NotificationTemplate notificationTemplate(AbstractNotifyStyle style) {
        NotificationTemplate template = new NotificationTemplate();
        template.setAppId(appId);
        template.setAppkey(appKey);
        template.setStyle(style);
        return template;
    }

    /**
     * 获取基础的baseAPNload
     *
     * @return
     */
    private static APNPayload baseAPNPayload() {
        APNPayload payload = new APNPayload();
        payload.setAutoBadge("+1");
        payload.setContentAvailable(1);
        payload.setSound("default");
        return payload;
    }

    /**
     * ios推送
     *
     * @param dto
     */
    private static IPushResult iosPush(GeTuiPushDto dto) {
        TransmissionTemplate template = transmissionTemplate(dto.getTitle());
        PushShowTypeEnum type = dto.getPushShowType();
        APNPayload payload = baseAPNPayload();
        if (Objects.nonNull(dto.getPayload())) {
            JSONObject jsonObject = JSONUtil.parseObj(dto.getPayload());
            jsonObject.forEach(payload::addCustomMsg);
        }
        payload.setVoicePlayType(2);
        payload.setVoicePlayMessage(dto.getText());
        Message message = new Message();
        message.setOffline(false);
        if (type.equals(PushShowTypeEnum.BASE)) {
            APNPayload.SimpleAlertMsg msg = new APNPayload.SimpleAlertMsg(dto.getTitle());
            payload.setAlertMsg(msg);
        } else if (type.equals(PushShowTypeEnum.MORE_TEXT)) {
            APNPayload.DictionaryAlertMsg alertMsg = new APNPayload.DictionaryAlertMsg();
            alertMsg.setTitle(dto.getTitle());
            alertMsg.setSubtitle(dto.getShowText());
            alertMsg.setBody(dto.getText());
            payload.setAlertMsg(alertMsg);
        } else if (type.equals(PushShowTypeEnum.PICTURE)) {
            APNPayload.DictionaryAlertMsg alertMsg = new APNPayload.DictionaryAlertMsg();
            alertMsg.setTitle(dto.getTitle());
            alertMsg.setSubtitle(dto.getText());
            alertMsg.setBody(dto.getText());
            payload.setAlertMsg(alertMsg);
            payload.addMultiMedia(new MultiMedia().setResType(MultiMedia.MediaType.pic)
                    .setResUrl(dto.getShowText())
                    .setOnlyWifi(false));
        }
        template.setAPNInfo(payload);
        message.setData(template);
        return pushMessage(dto.getPushWho(), message);
    }

    /**
     * android推送
     *
     * @param dto
     */
    private static IPushResult androidPush(GeTuiPushDto dto) {
        PushShowTypeEnum pushShowType = dto.getPushShowType();
        Message message = new Message();
        message.setOffline(false);
        //如果是基础消息的话
        if (pushShowType.equals(PushShowTypeEnum.BASE)) {
            Notify notify = new Notify();
            notify.setTitle(dto.getTitle());
            notify.setContent(dto.getText());
            notify.setPayload(JSONUtil.toJsonStr(dto.getPayload()));
            String intent = "intent:#Intent;component=com.queyuan.qiqiyuan;S.UP-OL-SU=true;" +
                    "S.title=" + dto.getTitle() + ";" +
                    "S.content=" + dto.getText() + ";" +
                    "S.payload=" + JSONUtil.toJsonStr(dto.getPayload()) + ";" +
                    "launchFlags=0x04000000;end";
            notify.setIntent(intent);
            notify.setType(GtReq.NotifyInfo.Type._intent);
            TransmissionTemplate template = new TransmissionTemplate();
            template.setAppId(appId);
            template.setAppkey(appKey);
            template.setTransmissionType(2);
            template.setTransmissionContent(JSONUtil.toJsonStr(dto.getPayload()));
            template.set3rdNotifyInfo(notify);
            message.setData(template);
        }
//        if (pushShowType.equals(PushShowTypeEnum.BASE)) {
//            Style0 style0 = new Style0();
//            style0.setTitle(dto.getTitle());
//            style0.setText(dto.getText());
//            style0.setLogo(dto.getLogo());
//            NotificationTemplate notificationTemplate = notificationTemplate(style0);
//            notificationTemplate.setAppId(appId);
//            notificationTemplate.setAppkey(appKey);
//            message.setData(notificationTemplate);
//        } else if (pushShowType.equals(PushShowTypeEnum.MORE_TEXT)) {
//            Style6 style6 = new Style6();
//            style6.setTitle(dto.getTitle());
//            style6.setText(dto.getText());
//            style6.setLogo(dto.getLogo());
//            style6.setBigStyle2(dto.getShowText());
//            NotificationTemplate notificationTemplate = notificationTemplate(style6);
//            notificationTemplate.setAppId(appId);
//            notificationTemplate.setAppkey(appKey);
//            message.setData(notificationTemplate);
//        } else if (pushShowType.equals(PushShowTypeEnum.PICTURE)) {
//            Style6 style6 = new Style6();
//            style6.setTitle(dto.getTitle());
//            style6.setText(dto.getText());
//            style6.setLogo(dto.getLogo());
//            style6.setBigStyle1(dto.getShowText());
//            NotificationTemplate notificationTemplate = notificationTemplate(style6);
//            notificationTemplate.setAppId(appId);
//            notificationTemplate.setAppkey(appKey);
//            message.setData(notificationTemplate);
//        }

        return pushMessage(dto.getPushWho(), message);
    }

    /**
     * ios透传
     *
     * @param dto
     */
    private static IPushResult iosTransmission(TransmissionDto dto) {
        TransmissionTemplate iosTemplate = transmissionTemplate(dto.getText());
        Message message = new Message();
        message.setOffline(false);
        message.setData(iosTemplate);
        return pushMessage(dto.getPushWho(), message);
    }

    /**
     * android透传
     *
     * @param dto
     */
    private static IPushResult androidTransmission(TransmissionDto dto) {
        TransmissionTemplate androidTemplate = transmissionTemplate(dto.getText());
        Message message = new Message();
        message.setOffline(false);
        message.setData(androidTemplate);
        return pushMessage(dto.getPushWho(), message);
    }

    /**
     * 推送消息
     *
     * @param pushWho
     * @param message
     */
    private static IPushResult pushMessage(IPushUser pushWho, Message message) {
        IPushResult result = null;
        if (pushWho instanceof SinglePush) {
            SinglePush single = (SinglePush) pushWho;
            SingleMessage singleMessage = BeanUtil.toBean(message, SingleMessage.class);
            Target target = new Target();
            target.setAppId(appId);
//            target.setAlias(single.getMember());
            target.setClientId(single.getMember());
            result = push.pushMessageToSingle(singleMessage, target);
        } else if (pushWho instanceof AppPush) {
            AppMessage appMessage = BeanUtil.toBean(message, AppMessage.class);
            appMessage.setAppIdList(Collections.singletonList(appId));
            result = push.pushMessageToApp(appMessage);
        }
        return result;
    }


    public static void bindAlias(String alias, String clientId) {
        push.bindAlias(appId, alias, clientId);
    }

    public static void setBadgeByAlias(Integer badgeCount, String alias) {
        IAliasResult result = push.queryClientId(appId, alias);
        Map<String, Object> response = result.getResponse();
        if (Objects.nonNull(response) && "ok".equals(MapUtils.getString(response, "result"))) {
            List<String> cidList = (List<String>) MapUtils.getObject(response, "cidlist");
            IQueryResult badge = push.setBadgeForCID("+1", appId, cidList);
            System.out.println(badge.getResponse());
        }
    }

    public static void setBadgeByClientId(Integer badgeCount, String clientId) {
        push.setBadgeForCID(badgeCount.toString(), appId, Collections.singletonList(clientId));
    }
}
