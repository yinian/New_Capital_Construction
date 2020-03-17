# **Spring boot自身实现问题，导致Bean被提前初始化**

Spring Boot里提供了@ConditionalOnBean，这个方便用户在不同条件下来创建bean。里面提供了判断是否存在bean上有某个注解的功能。

```java
@Target({ ElementType.TYPE, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Conditional(OnBeanCondition.class)
public @interface ConditionalOnBean {

	/**
	 * The class types of beans that should be checked. The condition matches when beans
	 * of all classes specified are contained in the {@link BeanFactory}.
	 * @return the class types of beans to check
	 */
	Class<?>[] value() default {};

	/**
	 * The class type names of beans that should be checked. The condition matches when
	 * beans of all classes specified are contained in the {@link BeanFactory}.
	 * @return the class type names of beans to check
	 */
	String[] type() default {};

	/**
	 * The annotation type decorating a bean that should be checked. The condition matches
	 * when all of the annotations specified are defined on beans in the
	 * {@link BeanFactory}.
	 * @return the class-level annotation types to check
	 */
	Class<? extends Annotation>[] annotation() default {};

	/**
	 * The names of beans to check. The condition matches when all of the bean names
	 * specified are contained in the {@link BeanFactory}.
	 * @return the names of beans to check
	 */
	String[] name() default {};

	/**
	 * Strategy to decide if the application context hierarchy (parent contexts) should be
	 * considered.
	 * @return the search strategy
	 */
	SearchStrategy search() default SearchStrategy.ALL;

	/**
	 * Additional classes that may contain the specified bean types within their generic
	 * parameters. For example, an annotation declaring {@code value=Name.class} and
	 * {@code parameterizedContainer=NameRegistration.class} would detect both
	 * {@code Name} and {@code NameRegistration<Name>}.
	 * @return the container types
	 * @since 2.1.0
	 */
	Class<?>[] parameterizedContainer() default {};

}
```

比如用户自己定义了一个Annotation：

```java
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface MyAnnotation {
}
```

然后用下面的写法来创建abc这个bean，意思是当用户显式使用了@MyAnnotation（比如放在main class上），才会创建这个bean。

```java
@Configuration
public class MyAutoConfiguration {

	@Bean
	// if comment this line, it will be fine.
	@ConditionalOnBean(annotation = { MyAnnotation.class })
	public String abc() {
		return "abc";
	}
}
```

这个功能很好，但是在spring boot 1.4.5 版本之前都有问题，会导致FactoryBean提前初始化。

在例子里，通过xml创建了javaVersion这个bean，想获取到java的版本号。这里使用的是spring提供的一个调用static函数创建bean的技巧。

```xml
<bean id="sysProps" class="org.springframework.beans.factory.config.MethodInvokingFactoryBean">
	<property name="targetClass" value="java.lang.System" />
	<property name="targetMethod" value="getProperties" />
</bean>
<bean id="javaVersion" class="org.springframework.beans.factory.config.MethodInvokingFactoryBean">
	<property name="targetObject" ref="sysProps" />
	<property name="targetMethod" value="getProperty" />
	<property name="arguments" value="${java.version.key}" />
</bean>
```

我们在代码里获取到这个javaVersion，然后打印出来：

```java
@SpringBootApplication
@ImportResource("classpath:/demo.xml")
public class DemoApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(DemoApplication.class, args);
		System.err.println(context.getBean("javaVersion"));
	}
}
```

在实际运行时，发现javaVersion的值是null。

这个其实是spring boot的锅，要搞清楚这个问题，先要看@ConditionalOnBean的实现。

- @ConditionalOnBean实际上是在ConfigurationClassPostProcessor里被处理的，它实现了BeanDefinitionRegistryPostProcessor
- BeanDefinitionRegistryPostProcessor是在spring早期被处理的
- @ConditionalOnBean的具体处理代码在org.springframework.boot.autoconfigure.condition.OnBeanCondition里
- OnBeanCondition在获取bean的Annotation时，调用了beanFactory.getBeanNamesForAnnotation

