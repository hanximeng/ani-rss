<template>
  <template v-if="notificationConfig['notificationType'] === 'WE_COM'">
    <SettingsItem label="通知模版">
      <el-input v-model="notificationConfig['notificationTemplate']" :autosize="{ minRows: 2}"
                placeholder="${notification}" type="textarea"/>
    </SettingsItem>
    <SettingsItem label="Webhook">
      <el-input v-model="notificationConfig['weComWebhook']"
                placeholder="https://qyapi.weixin.qq.com/cgi-bin/webhook/send?key=xxx"/>
    </SettingsItem>
    <SettingsItem label="消息类型">
      <el-select v-model="notificationConfig['weComMsgType']" class="notification-input-width">
        <el-option label="Markdown" value="markdown"/>
        <el-option label="Text" value="text"/>
      </el-select>
    </SettingsItem>
    <SettingsItem label="@手机号">
      <el-input-tag v-model="notificationConfig['weComAtMobiles']"/>
    </SettingsItem>
    <SettingsItem label="@所有人">
      <el-switch v-model="notificationConfig['weComAtAll']"/>
    </SettingsItem>
    <SettingsItem label="图片">
      <el-switch v-model="notificationConfig['weComImage']"/>
    </SettingsItem>
  </template>
</template>

<script setup>
import SettingsItem from "@/view/custom/SettingsItem.vue";

let props = defineProps(['notificationConfig', 'config'])
</script>
