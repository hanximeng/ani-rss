package ani.rss.notification;

import ani.rss.commons.GsonStatic;
import ani.rss.entity.Ani;
import ani.rss.entity.NotificationConfig;
import ani.rss.entity.web.ContentType;
import ani.rss.enums.NotificationStatusEnum;
import ani.rss.util.basic.HttpReq;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.URLUtil;
import cn.hutool.crypto.digest.HMac;
import cn.hutool.crypto.digest.HmacAlgorithm;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 钉钉
 */
@Slf4j
public class DingTalkNotification implements BaseNotification {

    @Override
    public void test(NotificationConfig notificationConfig, Ani ani, String text, NotificationStatusEnum notificationStatusEnum) {
        send(notificationConfig, ani, text, notificationStatusEnum);
    }

    @Override
    public Boolean send(NotificationConfig notificationConfig, Ani ani, String text, NotificationStatusEnum notificationStatusEnum) {
        String webhook = notificationConfig.getDingTalkWebhook();
        String secret = notificationConfig.getDingTalkSecret();
        String msgType = notificationConfig.getDingTalkMsgType();
        List<String> atMobiles = notificationConfig.getDingTalkAtMobiles();
        Boolean atAll = notificationConfig.getDingTalkAtAll();
        Boolean image = notificationConfig.getDingTalkImage();

        if (StrUtil.isBlank(webhook)) {
            log.warn("钉钉 Webhook 为空");
            return false;
        }

        String content = replaceNotificationTemplate(ani, notificationConfig, text, notificationStatusEnum);

        if (Boolean.TRUE.equals(image) && StrUtil.isNotBlank(ani.getImage())) {
            content = StrUtil.format("{}\n\n![]({})", content, ani.getImage());
        }

        Map<String, Object> at = Map.of(
                "atMobiles", CollUtil.emptyIfNull(atMobiles),
                "isAtAll", Boolean.TRUE.equals(atAll)
        );

        Map<String, Object> message = new HashMap<>();
        message.put("at", at);

        if ("text".equalsIgnoreCase(msgType)) {
            message.put("msgtype", "text");
            message.put("text", Map.of("content", content));
        } else {
            message.put("msgtype", "markdown");
            message.put("markdown", Map.of(
                    "title", StrUtil.blankToDefault(ani.getTitle(), "ani-rss"),
                    "text", content
            ));
        }

        return HttpReq.post(signUrl(webhook, secret), message)
                .contentType(ContentType.JSON)
                .thenFunction(res -> {
                    if (!res.isOk()) {
                        log.error("钉钉通知失败 status: {}", res.getStatus());
                        return false;
                    }
                    DingTalkResult result = GsonStatic.fromJson(res.body(), DingTalkResult.class);
                    if (result == null || !Integer.valueOf(0).equals(result.getErrcode())) {
                        log.error("钉钉通知失败 {}", res.body());
                        return false;
                    }
                    return true;
                });
    }

    /**
     * 钉钉加签
     *
     * @param webhook webhook 地址
     * @param secret  加签密钥
     * @return 加签后的地址
     */
    private static String signUrl(String webhook, String secret) {
        if (StrUtil.isBlank(secret)) {
            return webhook;
        }
        long timestamp = System.currentTimeMillis();
        String stringToSign = timestamp + "\n" + secret;
        HMac hMac = new HMac(HmacAlgorithm.HmacSHA256, secret.getBytes(StandardCharsets.UTF_8));
        String sign = URLUtil.encode(hMac.digestBase64(stringToSign, false));
        String separator = webhook.contains("?") ? "&" : "?";
        return StrUtil.format("{}{}timestamp={}&sign={}", webhook, separator, timestamp, sign);
    }

    @Data
    @Accessors(chain = true)
    private static class DingTalkResult {
        private Integer errcode;
        private String errmsg;
    }
}
