#maven 脚手架

通过maven的自定义archetype创建自定义的项目框架

执行命令把该项目安装到你的本地仓库 `mvn clean install`

再次回到你的workspace工程目录，执行命令选择本地仓库的archetype创建项目 `mvn archetype:generate -DarchetypeCatalog=local`

选择你的archetype的编号，然后根据提示输入你自己的groupId，artifactId，packaging，version

进入生成完成的工程目录`mvn eclipse:eclipse`

***-biz-facade    -- 业务层

***-biz-service   -- 服务层

***-biz-task -- 定时任务层

***-common-core -- 公共工具包

***-common-dal -- dal层

***-htdoc -- web页面，内嵌velocity

***-web -- 控制器层
