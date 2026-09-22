package ani.rss.notification;

import ani.rss.commons.GsonStatic;
import ani.rss.entity.Ani;
import ani.rss.entity.NotificationConfig;
import ani.rss.entity.web.ContentType;
import ani.rss.enums.NotificationStatusEnum;
import ani.rss.util.basic.HttpReq;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.Header;
import cn.hutool.http.HttpRequest;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

/**
 * OneBot
 */
@Slf4j
public class OneBotNotification implements BaseNotification {

    @Override
    public void test(NotificationConfig notificationConfig, Ani ani, String text, NotificationStatusEnum notificationStatusEnum) {
        send(notificationConfig, ani, text, notificationStatusEnum);
    }

    @Override
    public Boolean send(NotificationConfig notificationConfig, Ani ani, String text, NotificationStatusEnum notificationStatusEnum) {
        String api = notificationConfig.getOneBotApi();
        String accessToken = notificationConfig.getOneBotAccessToken();
        String messageType = notificationConfig.getOneBotMessageType();
        String userId = notificationConfig.getOneBotUserId();
        String groupId = notificationConfig.getOneBotGroupId();
        Boolean image = notificationConfig.getOneBotImage();

        if (StrUtil.isBlank(api)) {
            log.warn("OneBot Api 为空");
            return false;
        }

        String message = replaceNotificationTemplate(ani, notificationConfig, text, notificationStatusEnum);

        if (Boolean.TRUE.equals(image) && StrUtil.isNotBlank(ani.getImage())) {
            message = StrUtil.format("{}[CQ:image,file={}]", message, ani.getImage());
        }

        Map<String, Object> body = new HashMap<>();
        body.put("message", message);

        String path;
        if ("group".equalsIgnoreCase(messageType)) {
            if (StrUtil.isBlank(groupId)) {
                log.warn("OneBot 群号 为空");
                return false;
            }
            path = "/send_group_msg";
            body.put("group_id", groupId);
        } else {
            if (StrUtil.isBlank(userId)) {
                log.warn("OneBot 用户QQ 为空");
                return false;
            }
            path = "/send_private_msg";
            body.put("user_id", userId);
        }

        HttpRequest request = HttpReq.post(StrUtil.removeSuffix(api, "/") + path, body)
                .contentType(ContentType.JSON);

        if (StrUtil.isNotBlank(accessToken)) {
            request.header(Header.AUTHORIZATION, "Bearer " + accessToken);
        }

        return request.thenFunction(res -> {
            if (!res.isOk()) {
                log.error("OneBot 通知失败 status: {}", res.getStatus());
                return false;
            }
            OneBotResult result = GsonStatic.fromJson(res.body(), OneBotResult.class);
            if (result == null || !Integer.valueOf(0).equals(result.getRetcode())) {
                log.error("OneBot 通知失败 {}", res.body());
                return false;
            }
            return true;
        });
    }

    @Data
    @Accessors(chain = true)
    private static class OneBotResult {
        private String status;
        private Integer retcode;
    }
}
