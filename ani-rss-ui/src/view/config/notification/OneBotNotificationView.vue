<template>
  <template v-if="notificationConfig['notificationType'] === 'ONE_BOT'">
    <SettingsItem label="通知模版">
      <el-input v-model="notificationConfig['notificationTemplate']" :autosize="{ minRows: 2}"
                placeholder="${notification}" type="textarea"/>
    </SettingsItem>
    <SettingsItem label="Api">
      <el-input v-model="notificationConfig['oneBotApi']" placeholder="http://127.0.0.1:3000"/>
    </SettingsItem>
    <SettingsItem label="AccessToken">
      <el-input v-model="notificationConfig['oneBotAccessToken']" placeholder="可以为空"/>
    </SettingsItem>
    <SettingsItem label="消息类型">
      <el-select v-model="notificationConfig['oneBotMessageType']" class="notification-input-width">
        <el-option label="私聊" value="private"/>
        <el-option label="群聊" value="group"/>
      </el-select>
    </SettingsItem>
    <SettingsItem label="用户QQ" v-if="notificationConfig['oneBotMessageType'] !== 'group'">
      <el-input v-model="notificationConfig['oneBotUserId']" placeholder="123456789"/>
    </SettingsItem>
    <SettingsItem label="群号" v-else>
      <el-input v-model="notificationConfig['oneBotGroupId']" placeholder="123456789"/>
    </SettingsItem>
    <SettingsItem label="图片">
      <el-switch v-model="notificationConfig['oneBotImage']"/>
    </SettingsItem>
  </template>
</template>

<script setup>
import SettingsItem from "@/view/custom/SettingsItem.vue";

let props = defineProps(['notificationConfig', 'config'])
</script>
