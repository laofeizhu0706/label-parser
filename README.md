# label-parser

## 简介

实现规则动态配置获取，把规则模块抽离系统， 以规则脚本的形式存放在数据库中，使得规则的变更不需要修正代码重启机器就可以立即在线上环境生效。 基于标签规则的实现。


## 使用

```
#查找标签和查找商品的接口（当前无安全策略）
http://127.0.0.1:9981/searchProductLabel
http://127.0.0.1:9981/searchProduct 
```

## sql导入
```sql
-- 1.创建数据库 label_service
-- 2.导入sql sql/label_service.sql
```

## 部署
