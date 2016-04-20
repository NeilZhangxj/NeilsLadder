#Free ladder to the brave new world

##Shadowsocks提供了梯子和使用梯子的免费账号， 账号会每六个小时更换一次用户名和密码。

###(所有业务基本均由Java实现， 目前实现了Windows系统)

####傻瓜式教学

1. 下载Shadowsocks  [传送门](https://github.com/shadowsocks/shadowsocks-csharp/releases/download/2.5.6/Shadowsocks-win-2.5.6.zip)
   2. E盘根目录创建文件夹shadowsocks
   3. 将exe文件解压在创建的文件夹内（后续版本加入配置文件支持修改路径）
1. 将项目生成为jar包，将jar包放在shadowsocks文件夹中（lib中有已经生成好的jar） 
1. 2. 将lib中的vbs文件放在启动项里 `C:\Users\‘username’\AppData\Roaming\Microsoft\Windows\Start Menu\Programs\Startup` 
   2. 将lib中的bat文件放在shadowsocks文件夹下
1. 双击vbs或重启计算机，完成!

####PostScript

1.使用**Jsoup** 获取Shadowsocks提供的免费账号并填写在本地的配置文件中，保证梯子永久有效。

    简单的标签获取已经实现了功能，关键字获取应该会更保险

1.使用**Quartz** 定时任务在规定时间重新获取并更改账号信息 

    时间设置方面我加了一分钟的延迟 懒得存啊

    不放心的话可以定时向FaceBook发包，超过响应时间就重新获取账号信息
3.将项目制作为jar包 配置为开机自动运行

    可以使用**vbs**将jar包注册为进程，把vbs放在windows的starup（启动项）中

    或者用**java service wrapper**把项目注册为系统服务
    
    
    
##**Good luck have fun**
