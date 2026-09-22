package ani.rss.notification;

import ani.rss.commons.GsonStatic;
import ani.rss.entity.Ani;
import ani.rss.entity.NotificationConfig;
import ani.rss.entity.web.ContentType;
import ani.rss.enums.NotificationStatusEnum;
import ani.rss.util.basic.HttpReq;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.HMac;
import cn.hutool.crypto.digest.HmacAlgorithm;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 飞书
 */
@Slf4j
public class FeishuNotification implements BaseNotification {

    @Override
    public void test(NotificationConfig notificationConfig, Ani ani, String text, NotificationStatusEnum notificationStatusEnum) {
        send(notificationConfig, ani, text, notificationStatusEnum);
    }

    @Override
    public Boolean send(NotificationConfig notificationConfig, Ani ani, String text, NotificationStatusEnum notificationStatusEnum) {
        String webhook = notificationConfig.getFeishuWebhook();
        String secret = notificationConfig.getFeishuSecret();
        String msgType = notificationConfig.getFeishuMsgType();

        if (StrUtil.isBlank(webhook)) {
            log.warn("飞书 Webhook 为空");
            return false;
        }

        String content = replaceNotificationTemplate(ani, notificationConfig, text, notificationStatusEnum);

        Map<String, Object> message = new HashMap<>();

        if (StrUtil.isNotBlank(secret)) {
            String timestamp = String.valueOf(System.currentTimeMillis() / 1000);
            String stringToSign = timestamp + "\n" + secret;
            HMac hMac = new HMac(HmacAlgorithm.HmacSHA256, stringToSign.getBytes(StandardCharsets.UTF_8));
            message.put("timestamp", timestamp);
            message.put("sign", hMac.digestBase64("", false));
        }

        if ("post".equalsIgnoreCase(msgType)) {
            message.put("msg_type", "post");
            message.put("content", Map.of("post", Map.of("zh_cn", Map.of(
                    "title", StrUtil.blankToDefault(ani.getTitle(), "ani-rss"),
                    "content", List.of(List.of(Map.of("tag", "text", "text", content)))
            ))));
        } else {
            message.put("msg_type", "text");
            message.put("content", Map.of("text", content));
        }

        return HttpReq.post(webhook, message)
                .contentType(ContentType.JSON)
                .thenFunction(res -> {
                    if (!res.isOk()) {
                        log.error("飞书通知失败 status: {}", res.getStatus());
                        return false;
                    }
                    FeishuResult result = GsonStatic.fromJson(res.body(), FeishuResult.class);
                    Integer code = result == null ? null : result.getCode();
                    if (code == null && result != null) {
                        code = result.getStatusCode();
                    }
                    if (!Integer.valueOf(0).equals(code)) {
                        log.error("飞书通知失败 {}", res.body());
                        return false;
                    }
                    return true;
                });
    }

    @Data
    @Accessors(chain = true)
    private static class FeishuResult {
        private Integer code;
        @SerializedName("StatusCode")
        private Integer statusCode;
    }
}
