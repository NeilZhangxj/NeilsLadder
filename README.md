Free ladder to the brave new world

Shadowsocks提供了梯子和使用梯子的免费账号， 账号会每六个小时更换一次用户名和密码。

(所有业务基本均由Java实现， 目前实现了Windows系统)

使用Jsoup 获取Shadowsocks提供的免费账号并填写在本地的配置文件中，保证梯子永久有效。

简单的标签获取已经实现了功能，关键字获取应该会更保险

使用Quartz 定时任务在规定时间重新获取并更改账号信息

时间设置方面我加了一分钟的延迟 懒得存啊

不放心的话可以定时向FaceBook发包，超过响应时间就重新获取账号信息

将项目制作为jar包 配置为开机自动运行

可以使用vbs将jar包注册为进程，把vbs放在windows的starup（启动项）中

或者用java service wrapper把项目注册为系统服务
