package ani.rss.notification;

import ani.rss.commons.GsonStatic;
import ani.rss.entity.Ani;
import ani.rss.entity.NotificationConfig;
import ani.rss.entity.web.ContentType;
import ani.rss.enums.NotificationStatusEnum;
import ani.rss.util.basic.HttpReq;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 企业微信
 */
@Slf4j
public class WeComNotification implements BaseNotification {

    @Override
    public void test(NotificationConfig notificationConfig, Ani ani, String text, NotificationStatusEnum notificationStatusEnum) {
        send(notificationConfig, ani, text, notificationStatusEnum);
    }

    @Override
    public Boolean send(NotificationConfig notificationConfig, Ani ani, String text, NotificationStatusEnum notificationStatusEnum) {
        String webhook = notificationConfig.getWeComWebhook();
        String msgType = notificationConfig.getWeComMsgType();
        List<String> atMobiles = notificationConfig.getWeComAtMobiles();
        Boolean atAll = notificationConfig.getWeComAtAll();
        Boolean image = notificationConfig.getWeComImage();

        if (StrUtil.isBlank(webhook)) {
            log.warn("企业微信 Webhook 为空");
            return false;
        }

        String content = replaceNotificationTemplate(ani, notificationConfig, text, notificationStatusEnum);

        if (Boolean.TRUE.equals(image) && StrUtil.isNotBlank(ani.getImage())) {
            content = StrUtil.format("{}\n\n![]({})", content, ani.getImage());
        }

        Map<String, Object> message = new HashMap<>();

        if ("text".equalsIgnoreCase(msgType)) {
            List<String> mentionedList = new ArrayList<>(CollUtil.emptyIfNull(atMobiles));
            if (Boolean.TRUE.equals(atAll)) {
                mentionedList.add("@all");
            }
            message.put("msgtype", "text");
            message.put("text", Map.of(
                    "content", content,
                    "mentioned_mobile_list", mentionedList
            ));
        } else {
            message.put("msgtype", "markdown");
            message.put("markdown", Map.of("content", content));
        }

        return HttpReq.post(webhook, message)
                .contentType(ContentType.JSON)
                .thenFunction(res -> {
                    if (!res.isOk()) {
                        log.error("企业微信通知失败 status: {}", res.getStatus());
                        return false;
                    }
                    WeComResult result = GsonStatic.fromJson(res.body(), WeComResult.class);
                    if (result == null || !Integer.valueOf(0).equals(result.getErrcode())) {
                        log.error("企业微信通知失败 {}", res.body());
                        return false;
                    }
                    return true;
                });
    }

    @Data
    @Accessors(chain = true)
    private static class WeComResult {
        private Integer errcode;
        private String errmsg;
    }
}
