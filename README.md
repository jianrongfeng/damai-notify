# damai-notify
大麦网演出开票检测

### 工作原理:
1. 获取演出项目id
1. 解析演出的详情界面返回的html
1. 判断是否有正确的票务信息
1. 发送邮件提醒

### 运行项目:
1. 修改``config.properties``配置文件的smtp参数(目前只实现了邮件提醒的方式，可以根据需要增加短信、语音等提醒)
1. 项目目录下执行``mvn package``打包成jar
1. 将``target/notify-1.0.jar``拷贝到服务器，执行``nohup java -jar notify-1.0.jar > notify.out &``
1. Have fun :)
