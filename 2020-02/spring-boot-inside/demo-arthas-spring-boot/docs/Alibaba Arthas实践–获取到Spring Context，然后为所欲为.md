Arthas实践--获取到Spring Context，然后为所欲为
转载阿里加多 最后发布于2019-01-29 22:59:43 阅读数 82  收藏
背景
Arthas 是Alibaba开源的Java诊断工具，深受开发者喜爱。

https://github.com/alibaba/arthas

Arthas提供了非常丰富的关于调用拦截的命令，比如 trace/watch/monitor/tt 。但是很多时候我们在排查问题时，需要更多的线索，并不只是函数的参数和返回值。

下面介绍如何利用Arthas获取到spring context。

Demo: https://github.com/hengyunabc/spring-boot-inside/tree/master/demo-arthas-spring-boot

Arthas快速开始：https://alibaba.github.io/arthas/quick-start.html

使用tt命令获取到spring context
Demo是一个spring mvc应用，请求会经过一系列的spring bean处理，那么我们可以在spring mvc的类里拦截到一些请求。
启动Demo：
```text
 mvn spring-boot:run
```

使用Arthas Attach成功之后，执行tt命令来记录RequestMappingHandlerAdapter#invokeHandlerMethod的请求
> tt
> 方法执行数据的时空隧道，记录下指定方法每次调用的入参和返回信息，并能对这些不同的时间下调用进行观测
```text
tt -t org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter invokeHandlerMethod
```
然后访问一个网页： http://localhost:8080/

可以看到Arthas会拦截到这个调用，index是1000，并且打印出：
```xml
$ watch com.example.demo.Test * 'params[0]@sss'
$ tt -t org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter invokeHandlerMethod
Press Ctrl+C to abort.
Affect(class-cnt:1 , method-cnt:1) cost in 101 ms.
 INDEX  TIMESTAMP         COST(ms  IS-RE  IS-EX  OBJECT       CLASS                     METHOD
                          )        T      P
------------------------------------------------------------------------------------------------------------------
 1000   2019-01-27 16:31  3.66744  true   false  0x4465cf70   RequestMappingHandlerAda  invokeHandlerMethod
        :54                                                   pter
```
那么怎样获取到spring context？

可以用tt命令的-i参数来指定index，并且用-w参数来执行ognl表达式来获取spring context：
```xml
[arthas@16104]$ tt -i 1000 -w 'target.getApplicationContext()'
@AnnotationConfigEmbeddedWebApplicationContext[
    reader=@AnnotatedBeanDefinitionReader[org.springframework.context.annotation.AnnotatedBeanDefinitionReader@1d56f505],
    scanner=@ClassPathBeanDefinitionScanner[org.springframework.context.annotation.ClassPathBeanDefinitionScanner@170f79ec],
    annotatedClasses=null,
    basePackages=null,
    logger=@SLF4JLocationAwareLog[org.apache.commons.logging.impl.SLF4JLocationAwareLog@6d1f3da0],
    DISPATCHER_SERVLET_NAME=@String[dispatcherServlet],
    embeddedServletContainer=@TomcatEmbeddedServletContainer[org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainer@7e7848ab],
    servletConfig=null,
    namespace=null,
    servletContext=@ApplicationContextFacade[org.apache.catalina.core.ApplicationContextFacade@542c74bc],
    themeSource=@ResourceBundleThemeSource[org.springframework.ui.context.support.ResourceBundleThemeSource@3880a7cc],
    beanFactory=@DefaultListableBeanFactory[org.springframework.beans.factory.support.DefaultListableBeanFactory@5c33f1a9: defining beans [org.springframework.context.annotation.internalConfigurationAnnotationProcessor,org.springframework.context.annotation.internalAutowiredAnnotationProcessor,org.springframework.context.annotation.internalRequiredAnnotationProcessor,org.springframework.context.annotation.internalCommonAnnotationProcessor,org.springframework.context.event.internalEventListenerProcessor,org.springframework.context.event.internalEventListenerFactory,demoArthasApplication,org.springframework.boot.autoconfigure.internalCachingMetadataReaderFactory,adminFilterConfig,helloWorldService,serviceMonitor,tomcatConfiguration,userController,welcomeController,testFilter,staticResourceCustomizer,org.springframework.boot.autoconfigure.AutoConfigurationPackages,org.springframework.boot.autoconfigure.context.PropertyPlaceholderAutoConfiguration,org.springframework.boot.autoconfigure.condition.BeanTypeRegistry,propertySourcesPlaceholderConfigurer,org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration$Jackson2ObjectMapperBuilderCustomizerConfiguration,standardJacksonObjectMapperBuilderCustomizer,spring.jackson-org.springframework.boot.autoconfigure.jackson.JacksonProperties,org.springframework.boot.context.properties.ConfigurationPropertiesBindingPostProcessor,org.springframework.boot.context.properties.ConfigurationPropertiesBindingPostProcessor.store,org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration$JacksonObjectMapperBuilderConfiguration,jacksonObjectMapperBuilder,org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration$JacksonObjectMapperConfiguration,jacksonObjectMapper,org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration,jsonComponentModule,org.springframework.boot.autoconfigure.websocket.WebSocketAutoConfiguration$TomcatWebSocketConfiguration,websocketContainerCustomizer,org.springframework.boot.autoconfigure.websocket.WebSocketAutoConfiguration,org.springframework.boot.autoconfigure.web.EmbeddedServletContainerAutoConfiguration$EmbeddedTomcat,tomcatEmbeddedServletContainerFactory,org.springframework.boot.autoconfigure.web.EmbeddedServletContainerAutoConfiguration,embeddedServletContainerCustomizerBeanPostProcessor,errorPageRegistrarBeanPostProcessor,org.springframework.boot.autoconfigure.web.DispatcherServletAutoConfiguration$DispatcherServletConfiguration,dispatcherServlet,spring.mvc-org.springframework.boot.autoconfigure.web.WebMvcProperties,org.springframework.boot.autoconfigure.web.DispatcherServletAutoConfiguration$DispatcherServletRegistrationConfiguration,dispatcherServletRegistration,org.springframework.boot.autoconfigure.web.DispatcherServletAutoConfiguration,org.springframework.boot.autoconfigure.validation.ValidationAutoConfiguration,defaultValidator,methodValidationPostProcessor,org.springframework.boot.autoconfigure.web.ErrorMvcAutoConfiguration$DefaultErrorViewResolverConfiguration,conventionErrorViewResolver,org.springframework.boot.autoconfigure.web.ErrorMvcAutoConfiguration,errorAttributes,basicErrorController,errorPageCustomizer,preserveErrorControllerTargetClassPostProcessor,spring.resources-org.springframework.boot.autoconfigure.web.ResourceProperties,org.springframework.boot.autoconfigure.web.WebMvcAutoConfiguration$EnableWebMvcConfiguration,requestMappingHandlerAdapter,requestMappingHandlerMapping,mvcValidator,mvcContentNegotiationManager,mvcPathMatcher,mvcUrlPathHelper,viewControllerHandlerMapping,beanNameHandlerMapping,resourceHandlerMapping,mvcResourceUrlProvider,defaultServletHandlerMapping,mvcConversionService,mvcUriComponentsContributor,httpRequestHandlerAdapter,simpleControllerHandlerAdapter,handlerExceptionResolver,mvcViewResolver,mvcHandlerMappingIntrospector,org.springframework.boot.autoconfigure.web.WebMvcAutoConfiguration$WebMvcAutoConfigurationAdapter$FaviconConfiguration,faviconHandlerMapping,faviconRequestHandler,org.springframework.boot.autoconfigure.web.WebMvcAutoConfiguration$WebMvcAutoConfigurationAdapter,defaultViewResolver,viewResolver,welcomePageHandlerMapping,requestContextFilter,org.springframework.boot.autoconfigure.web.WebMvcAutoConfiguration,hiddenHttpMethodFilter,httpPutFormContentFilter,org.springframework.boot.autoconfigure.jmx.JmxAutoConfiguration,mbeanExporter,objectNamingStrategy,mbeanServer,org.springframework.boot.autoconfigure.admin.SpringApplicationAdminJmxAutoConfiguration,springApplicationAdminRegistrar,org.springframework.boot.autoconfigure.aop.AopAutoConfiguration$JdkDynamicAutoProxyConfiguration,org.springframework.aop.config.internalAutoProxyCreator,org.springframework.boot.autoconfigure.aop.AopAutoConfiguration,org.springframework.boot.autoconfigure.context.ConfigurationPropertiesAutoConfiguration,org.springframework.boot.autoconfigure.web.HttpMessageConvertersAutoConfiguration$StringHttpMessageConverterConfiguration,stringHttpMessageConverter,spring.http.encoding-org.springframework.boot.autoconfigure.web.HttpEncodingProperties,org.springframework.boot.autoconfigure.web.JacksonHttpMessageConvertersConfiguration$MappingJackson2HttpMessageConverterConfiguration,mappingJackson2HttpMessageConverter,org.springframework.boot.autoconfigure.web.JacksonHttpMessageConvertersConfiguration,org.springframework.boot.autoconfigure.web.HttpMessageConvertersAutoConfiguration,messageConverters,org.springframework.boot.autoconfigure.freemarker.FreeMarkerAutoConfiguration$FreeMarkerWebConfiguration,freeMarkerConfigurer,freeMarkerConfiguration,freeMarkerViewResolver,org.springframework.boot.autoconfigure.freemarker.FreeMarkerAutoConfiguration,spring.freemarker-org.springframework.boot.autoconfigure.freemarker.FreeMarkerProperties,org.springframework.boot.autoconfigure.info.ProjectInfoAutoConfiguration,spring.info-org.springframework.boot.autoconfigure.info.ProjectInfoProperties,org.springframework.boot.autoconfigure.web.HttpEncodingAutoConfiguration,characterEncodingFilter,localeCharsetMappingsCustomizer,org.springframework.boot.autoconfigure.web.MultipartAutoConfiguration,multipartConfigElement,multipartResolver,spring.http.multipart-org.springframework.boot.autoconfigure.web.MultipartProperties,org.springframework.boot.autoconfigure.web.ServerPropertiesAutoConfiguration,serverProperties,duplicateServerPropertiesDetector,org.springframework.boot.autoconfigure.web.WebClientAutoConfiguration$RestTemplateConfiguration,restTemplateBuilder,org.springframework.boot.autoconfigure.web.WebClientAutoConfiguration]; root of factory hierarchy],
    resourceLoader=null,
    customClassLoader=@Boolean[false],
    refreshed=@AtomicBoolean[true],
    MESSAGE_SOURCE_BEAN_NAME=@String[messageSource],
    LIFECYCLE_PROCESSOR_BEAN_NAME=@String[lifecycleProcessor],
    APPLICATION_EVENT_MULTICASTER_BEAN_NAME=@String[applicationEventMulticaster],
    logger=@SLF4JLocationAwareLog[org.apache.commons.logging.impl.SLF4JLocationAwareLog@2c666f41],
    id=@String[application],
    displayName=@String[org.springframework.boot.context.embedded.AnnotationConfigEmbeddedWebApplicationContext@4ae3c1cd],
    parent=null,
    environment=@StandardServletEnvironment[StandardServletEnvironment {activeProfiles=[], defaultProfiles=[default], propertySources=[MapPropertySource {name='server.ports'}, StubPropertySource {name='servletConfigInitParams'}, ServletContextPropertySource {name='servletContextInitParams'}, MapPropertySource {name='systemProperties'}, SystemEnvironmentPropertySource {name='systemEnvironment'}, RandomValuePropertySource {name='random'}, PropertiesPropertySource {name='applicationConfig: [classpath:/application.properties]'}]}],
    beanFactoryPostProcessors=@ArrayList[isEmpty=false;size=3],
    startupDate=@Long[1584155108525],
    active=@AtomicBoolean[true],
    closed=@AtomicBoolean[false],
    startupShutdownMonitor=@Object[java.lang.Object@10674776],
    shutdownHook=@[Thread[Thread-14,5,main]],
    resourcePatternResolver=@ServletContextResourcePatternResolver[org.springframework.web.context.support.ServletContextResourcePatternResolver@27d95852],
    lifecycleProcessor=@DefaultLifecycleProcessor[org.springframework.context.support.DefaultLifecycleProcessor@1a5a25a2],
    messageSource=@DelegatingMessageSource[org.springframework.context.support.DelegatingMessageSource@5595dcf],
    applicationEventMulticaster=@SimpleApplicationEventMulticaster[org.springframework.context.event.SimpleApplicationEventMulticaster@2f2ff6e5],
    applicationListeners=@LinkedHashSet[isEmpty=false;size=14],
    earlyApplicationEvents=null,
    classLoader=@AppClassLoader[sun.misc.Launcher$AppClassLoader@18b4aac2],
    protocolResolvers=@LinkedHashSet[isEmpty=true;size=0],
]
Affect(row-cnt:1) cost in 254 ms.
```
```xml
$ tt -i 1000 -w 'target.getApplicationContext().getBean("helloWorldService").getHelloMessage()'
@String[Hello World]
Affect(row-cnt:1) cost in 5 ms.
```


更多的思路
在很多代码里都有static函数或者static holder类，顺滕摸瓜，可以获取很多其它的对象。比如在Dubbo里通过SpringExtensionFactory获取spring context：

```xml
$ ognl '#context=@com.alibaba.dubbo.config.spring.extension.SpringExtensionFactory@contexts.iterator.next, 
#context.getBean("userServiceImpl").findUser(1)'
@User[
    id=@Integer[1],
    name=@String[Deanna Borer],
]
```