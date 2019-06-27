# Jenkins 企业微信通知插件

利用企业微信API，通过Jenkins向企业微信发送Markdown格式信息

**使用前请先阅读：**

[企业微信基本概念介绍](https://work.weixin.qq.com/api/doc#90000/90135/90665)

**准备：**

- 企业微信管理后台-我的企业
  - 查询得到企业ID（CorpID）
- 企业微信管理后台-应用与小程序-自建-创建应用（自定义名称，如Jenkins Notification）
  - 查询得到AgentID
  - 查询得到Secret

**安装：**

- Jenkins-Plugin Manager-Advanced-Upload Plugin
- 上传target目录的wechat-notification-plugin.hpi

**使用：**

- FreeStyle

  ![](../master/static/guide01.png)

- Pipeline

  ```groovy
  node {
     wechat corpid:"企业ID",secret:"应用密钥",agentid:"应用ID",toparty:"部门ID，需在后台查询，如583",markdown:"消息内容"
  }
  ```

**效果：**

![](../master/static/guide02.png)



**Refer：**

[企业微信获取access_token](https://work.weixin.qq.com/api/doc#90000/90135/91039)

[企业微信发送应用消息](https://work.weixin.qq.com/api/doc#90000/90135/90236/markdown%E6%B6%88%E6%81%AF)

[最最最全的Jenkins插件开发教程](https://blog.csdn.net/weixin_38652136/article/details/81116455)

[Jenkins企业微信通知开发](https://blog.csdn.net/weixin_42149254/article/details/85164277)



