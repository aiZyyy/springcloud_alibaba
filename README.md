# springcloud_alibaba
对于原来的微服务工程进行版本升级,注册中心采用nacos,微服务依赖主要采用alibaba

# zy-micro-service-common
公共服务类库的mybatis插件用idea的maven管理install后虽然可以成功,但是在实现generate.sh脚本时,会报找不到common-plugin的问题
可尝试在plugin工程下用临时命令行执行mvn -U install,idea的maven管理用这个命令有时候不起效果,别问我怎么知道的(试了好多次,开始还以为是我版本升级导致的...)

# pom依赖顺序
- zy-micro-services-base: zy-micro-service-parent:总父类
- zy-micro-services-base: config-service:配置中心(之前eureka用到.废弃)
- zy-micro-services-base: api-zuul:zuul总网关(后面都用gateway.废弃)
- zy-micro-services-base: api-gateway:gateway网关(准备写成服务网关)
- zy-micro-services-base: base-oauth-service(统一认证服务)
- zy-micro-services-base: base-route-service(动态路由服务)

- zy-micro-service-apps: common-plugin(lombok插件工具类,主要用于自动生成逆向工程代码)
- zy-micro-service-apps: zy-micro-service-common(应用服务类总工具类)
- zy-micro-service-apps: zy-micro-service-app-parent(应用服务父类)
- zy-micro-service-apps: zy-micro-service-app-api-parent(应用服务api父类)

- zy-micro-service-demo: demo-nacos-config-share-api(nacos共享配置demoAPI)
- zy-micro-service-demo: demo-nacos-config-share(nacos共享配置demo)
- zy-micro-service-demo: demo-client-service(服务相互调用demo)
- zy-micro-service-demo: demo-oauth-service(认证服务调用demo)

- zy-micro-service-gateway: gateway-checksign-common(网关验签SDK)
- zy-micro-service-gateway: gateway-market-service(应用网关)
- zy-micro-service-gateway: gateway-route-service(网关服务路由,动态)

- zy-micro-service-kits: kits-snowflake-service-api(雪花算法api)
- zy-micro-service-kits: kits-snowflake-service(雪花算法)
- zy-micro-service-kits: kits-market-service-api(应用市场api)
- zy-micro-service-kits: kits-market-service(应用市场)




