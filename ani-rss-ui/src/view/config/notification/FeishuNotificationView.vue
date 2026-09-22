<template>
  <template v-if="notificationConfig['notificationType'] === 'FEISHU'">
    <SettingsItem label="通知模版">
      <el-input v-model="notificationConfig['notificationTemplate']" :autosize="{ minRows: 2}"
                placeholder="${notification}" type="textarea"/>
    </SettingsItem>
    <SettingsItem label="Webhook">
      <el-input v-model="notificationConfig['feishuWebhook']"
                placeholder="https://open.feishu.cn/open-apis/bot/v2/hook/xxx"/>
    </SettingsItem>
    <SettingsItem label="加签密钥">
      <el-input v-model="notificationConfig['feishuSecret']"
                placeholder="未开启签名校验可留空"/>
    </SettingsItem>
    <SettingsItem label="消息类型">
      <el-select v-model="notificationConfig['feishuMsgType']" class="notification-input-width">
        <el-option label="Text" value="text"/>
        <el-option label="Post" value="post"/>
      </el-select>
    </SettingsItem>
  </template>
</template>

<script setup>
import SettingsItem from "@/view/custom/SettingsItem.vue";

let props = defineProps(['notificationConfig', 'config'])
</script>
