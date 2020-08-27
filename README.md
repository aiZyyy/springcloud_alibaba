# springcloud_alibaba
对于原来的微服务工程进行版本升级,注册中心采用nacos,微服务依赖主要采用alibaba

# zy-micro-service-common
公共服务类库的mybatis插件用idea的maven管理install后虽然可以成功,但是在实现generate.sh脚本时,会报找不到common-plugin的问题
可尝试在plugin工程下用临时命令行执行mvn -U install,idea的maven管理用这个命令有时候不起效果,别问我怎么知道的(试了好多次,开始还以为是我版本升级导致的...)
