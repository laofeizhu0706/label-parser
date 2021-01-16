# label-parser

## 简介

实现规则动态配置获取，把规则模块抽离系统， 以规则脚本的形式存放在文件中，使得规则的变更不需要修正代码重启机器就可以立即在线上环境生效。 基于标签规则的实现。

## Latest Version: 

```xml
下载项目，先使用mvn clean install安装到本地仓库，然后在项目导入以下maven
<dependency>
    <groupId>com.laofeizhu</groupId>
    <artifactId>label-parser</artifactId>
    <version>1.0</version>
</dependency>
```

## 使用

```java
public class Main {
    public static void main(String[] args) {
        List<BaseProductVo> productVos = Lists.newArrayList(new BaseProductVo("1", Lists.newArrayList("product_label_2")),
                new BaseProductVo("2", Lists.newArrayList("product_label_1")),new BaseProductVo("3", Lists.newArrayList("product_label_3")));
        //支持动态配置规则模板（暂不支持excel）
        IRecommendService service = DefaultRecommendService.build(productVos);
        //获取跟用户相匹配的商品（如果构建的时候没有传入全部商品，则获取为空）
        List<BaseProductVo> products = service.listMatchingProduct(Lists.newArrayList("user_label_1"));
        //获取跟用户相匹配的标签
        List<String> productLabels = service.listMatchingLabel(Lists.newArrayList("user_label_1"));
        System.out.println(products);
        System.out.println(productLabels);
    }
}
```