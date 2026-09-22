<div align="center">
<img alt="icon-512.png" height="80" src="ani-rss-ui/public/icon-512.png"/>
<h1 align="center" style="margin-top: 0">ANI-RSS</h1>
<p align="center">
<strong>基于RSS自动追番、订阅、下载、刮削、洗版</strong>
</p>

[快速开始](https://docs.wushuo.top/start)
|
[使用文档](https://docs.wushuo.top/add-rss)
|
[Docker部署](https://docs.wushuo.top/deploy/docker)
|
[常见问题](https://docs.wushuo.top/faq)
|
[参与开发](https://docs.wushuo.top/dev/basic)

[![GitHub](https://img.shields.io/badge/-GitHub-181717?logo=github)](https://github.com/hanximeng/ani-rss)
![GitHub License](https://img.shields.io/github/license/hanximeng/ani-rss)
[![GitHub release (latest SemVer)](https://img.shields.io/github/v/release/hanximeng/ani-rss?color=blue&label=download&sort=semver)](https://github.com/hanximeng/ani-rss/releases/latest)
[![GitHub all releases](https://img.shields.io/github/downloads/hanximeng/ani-rss/total?color=blue&label=github%20downloads)](https://github.com/hanximeng/ani-rss/releases)
[![ghcr.io](https://img.shields.io/badge/ghcr.io-hanximeng%2Fani--rss-2496ED?logo=docker&logoColor=white)](https://github.com/hanximeng/ani-rss/pkgs/container/ani-rss)
[![telegram](https://img.shields.io/static/v1?label=telegram&amp;message=ani_rss&amp;color=blue)](https://t.me/ani_rss)

</div>

![image](https://github.com/wushuo894/ani-rss-docs/raw/main/docs/image/screenshot/screenshot.webp#gh-light-mode-only)
![image](https://github.com/wushuo894/ani-rss-docs/raw/main/docs/image/screenshot/screenshot-dark.webp#gh-dark-mode-only)

## 本仓库说明

本仓库是 [wushuo894/ani-rss](https://github.com/wushuo894/ani-rss) 的分支，在上游功能基础上做了以下增强，上游的使用文档依然适用（见上方链接）。

### 本仓库特有改动

| 改动 | 说明 |
|---|---|
| **GitHub 加速** | 设置 → 其他中新增「GitHub 加速」配置（默认 `https://gh-proxy.org/`）。当 `github.com` 无法直连时，程序更新与 WebUI 更新的下载链接会自动套用该加速前缀；留空即关闭 |
| **通知渠道扩展** | 新增 **钉钉 / 企业微信 / OneBot / 飞书** 四种通知渠道（原有的邮件、Telegram、Bark、Server酱、WebHook、系统通知等全部保留） |
| **bgm.tv 镜像可配置** | 设置 → Bangumi 中新增「Bgm主站」「Bgm图片」，与「BgmApi」一起构成 **API / 主站 / 图片** 三项独立可配置，方便使用镜像站 |
| **更新检测源** | 检查更新指向本仓库，与上游互不影响 |
| **镜像仓库** | Docker 镜像仅发布到 **ghcr.io**，不依赖 Docker Hub |
| **自动构建发布** | 推送到 `main` 自动构建并发布 `jar` / `exe` / `dmg`，同时推送多架构 Docker 镜像 |
| **无 git 构建兼容** | 从源码 ZIP 下载（无 `.git` 目录）构建出的产物也能正常启动 |

### Docker 镜像

镜像地址（支持 `linux/amd64` 与 `linux/arm64`）：

```bash
docker pull ghcr.io/hanximeng/ani-rss:latest
```

| 标签 | 说明 |
|---|---|
| `ghcr.io/hanximeng/ani-rss:latest` | 最新版（Temurin JRE） |
| `ghcr.io/hanximeng/ani-rss:<版本>` | 指定版本，如 `v3.2.37` |
| `ghcr.io/hanximeng/ani-rss:openj9` | 最新版（OpenJ9，内存占用更低） |
| `ghcr.io/hanximeng/ani-rss:<版本>-openj9` | 指定版本 + OpenJ9 |

运行示例：

```bash
docker run -d \
  --name ani-rss \
  -p 7789:7789 \
  -v /path/to/config:/config \
  -e TZ=Asia/Shanghai \
  --restart unless-stopped \
  ghcr.io/hanximeng/ani-rss:latest
```

### 其他安装方式

除 Docker 外，也可直接从 [Releases](https://github.com/hanximeng/ani-rss/releases) 下载：

- `ani-rss.jar` — 需自行准备 Java 25 运行环境
- `ani-rss.exe` — Windows 可执行文件
- `ani-rss.dmg` — macOS 安装包

## 其他

### 推广须知

请不要在 **B站** 或 **中国大陆社交平台** 发布 视频/文章 宣传本项目

如确实有需要请尽量使用简称: **ASS**

### 相关文章

- [猫猫博客 Docker 部署 ani-rss 实现自动追番](https://catcat.blog/docker-ani-rss.html)

- [从零开始的NAS生活 第四回：ANI-RSS，自动追番！](https://www.wtsss.fun/archives/qhaQ3M7v)

- [自动化追番计划](http://jinghuashang.cn/posts/8f622332.html)

- [ANI-RSS：自动追番新姿势！](https://www.himiku.com/archives/ani-rss.html)

- [📺 彻底解放双手！2025年最新 Ani-RSS + qBittorrent 全自动追番保姆级教程](http://www.nuan1145.eu.cc/archives/wei-ming-ming-wen-zhang-75sNBWk0)

### 贡献者

<a href="https://github.com/hanximeng/ani-rss/graphs/contributors">
  <img src="https://contrib.rocks/image?repo=hanximeng/ani-rss" alt="contributors" />
</a>

## 爱发电

<a href="https://ifdian.net/a/wushuo894" target="_blank">
  <img src="https://github.com/wushuo894/ani-rss-docs/raw/main/docs/image/support_ifdian.svg" alt="support_ifdian">
</a>

您的每一次 star ⭐ 和 赞助 🎁 都是我持续优化的动力。让我们一起维护这个用爱发电的项目！

## 免责声明

### 项目性质

本工具为中立性技术辅助工具，通过自动化程序抓取互联网公开分享的种子文件链接 (非存储内容)，并向用户指定的下载工具 (如
qBittorrent、Transmission、Aria2 等)推送任务指令。工具本身不具备资源存储、分发及内容审查功能

### 用户责任

- 合法性承诺：用户需确保下载行为及文件使用符合所在国家/地区的《著作权法》《网络安全法》等法规，禁止用于盗版、非法传播等用途
- 自担风险：种子文件的合法性、安全性（如病毒、违规内容）由资源提供方独立负责，用户需自行验证并承担由此引发的法律与经济风险

### 开发者免责

- 技术中立性：开发者仅维护工具的功能实现，不参与种子文件的内容控制、编辑或优化，亦无法保证链接有效性、完整性与获取速率
- 免责范围
    - 用户因使用第三方种子导致的设备损害、数据丢失或法律纠纷
    - 因网络政策、技术更新或源站限制造成的服务中断或功能失效
- 例外追责
    - 若监管机构认定本工具违背技术中立原则，开发者保留终止服务的权利
