# 阿里巴巴Arthas实践--jad/mc/redefine线上热更新一条龙


尽管在生产环境热更新代码，并不是很好的行为，很可能导致：热更不规范，同事两行泪。

但很多时候我们的确希望能热更新代码，比如：

线上排查问题，找到修复思路了，但应用重启之后，环境现场就变了，难以复现。怎么验证修复方案？

又比如：

本地开发时，发现某个开源组件有bug，希望修改验证。如果是自己编译开源组件再发布，流程非常的长，还不一定能编译成功。有没有办法快速测试？

Arthas是阿里巴巴开源的Java应用诊断利器，深受开发者喜爱。

### 下面介绍利用Arthas 3.1.0版本的 jad/mc/redefine 一条龙来热更新代码。

Arthas: https://github.com/alibaba/arthas
jad命令：https://alibaba.github.io/arthas/jad.html
mc命令：https://alibaba.github.io/arthas/mc.html
redefine命令：https://alibaba.github.io/arthas/redefine.html

