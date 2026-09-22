export let notificationTypeList = [
    {
        name: 'TELEGRAM',
        label: 'TG通知'
    },
    {
        name: 'MAIL',
        label: '邮箱通知'
    },
    {
        name: 'SERVER_CHAN',
        label: 'Server酱'
    },
    {
        name: "BARK",
        label: "Bark通知"
    },
    {
        name: 'SYSTEM',
        label: '系统通知'
    },
    {
        name: 'WEB_HOOK',
        label: 'WebHook'
    },
    {
        name: 'EMBY_REFRESH',
        label: 'Emby媒体库刷新'
    },
    {
        name: 'SHELL',
        label: '执行外部程序'
    },
    {
        name: 'FILE_MOVE',
        label: '文件移动'
    },
    {
        name: 'OPEN_LIST_UPLOAD',
        label: 'OpenList上传'
    },
    {
        name: 'DING_TALK',
        label: '钉钉通知'
    },
    {
        name: 'WE_COM',
        label: '企业微信'
    },
    {
        name: 'ONE_BOT',
        label: 'OneBot'
    },
    {
        name: 'FEISHU',
        label: '飞书通知'
    }
]

export let getLabel = (name) => {
    return notificationTypeList.filter(item => item.name === name)[0].label;
}
