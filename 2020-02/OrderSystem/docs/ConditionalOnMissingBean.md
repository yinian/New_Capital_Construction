## `springBootAnnotation`
    ### ConditionalOnMissingBean
    
运行项目：
```html
van：boy next door,do you like 玩游戏
Billy：吾乃新日暮里的王，三界哲学的主宰。
```
将Billy上的@Service注解注释掉，让springboot扫描不到该类

如果注解了`Billy.java`中的`@service`，让springboot扫描不到该类，在运行项目，结果如下：
```html
van：boy next door,do you like 玩游戏
Banana: 自由的气息，蕉迟但到
```
