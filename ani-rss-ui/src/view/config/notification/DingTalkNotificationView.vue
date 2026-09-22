<template>
  <template v-if="notificationConfig['notificationType'] === 'DING_TALK'">
    <SettingsItem label="通知模版">
      <el-input v-model="notificationConfig['notificationTemplate']" :autosize="{ minRows: 2}"
                placeholder="${notification}" type="textarea"/>
    </SettingsItem>
    <SettingsItem label="Webhook">
      <el-input v-model="notificationConfig['dingTalkWebhook']"
                placeholder="https://oapi.dingtalk.com/robot/send?access_token=xxx"/>
    </SettingsItem>
    <SettingsItem label="加签密钥">
      <el-input v-model="notificationConfig['dingTalkSecret']" placeholder="SECxxxxxx 未开启加签可留空"/>
    </SettingsItem>
    <SettingsItem label="消息类型">
      <el-select v-model="notificationConfig['dingTalkMsgType']" class="notification-input-width">
        <el-option label="Markdown" value="markdown"/>
        <el-option label="Text" value="text"/>
      </el-select>
    </SettingsItem>
    <SettingsItem label="@手机号">
      <el-input-tag v-model="notificationConfig['dingTalkAtMobiles']"/>
    </SettingsItem>
    <SettingsItem label="@所有人">
      <el-switch v-model="notificationConfig['dingTalkAtAll']"/>
    </SettingsItem>
    <SettingsItem label="图片">
      <el-switch v-model="notificationConfig['dingTalkImage']"/>
    </SettingsItem>
  </template>
</template>

<script setup>
import SettingsItem from "@/view/custom/SettingsItem.vue";

let props = defineProps(['notificationConfig', 'config'])
</script>
