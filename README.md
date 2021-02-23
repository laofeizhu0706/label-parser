# label-parser

## 简介

实现规则动态配置获取，把规则模块抽离系统， 以规则脚本的形式存放在数据库中，使得规则的变更不需要修正代码重启机器就可以立即在线上环境生效。 基于标签规则的实现。


## 使用

```
#查找标签和查找商品的接口（当前无安全策略）
http://127.0.0.1:9981/searchProductLabel


http://127.0.0.1:9981/searchProduct 
输入参数：json(传入后台配置好的)
[
    {
        "label": "身高",
        "value": "165"
    },
    {
        "label": "收入",
        "value": "6000"
    },
    {
        "label": "是否祛痘",
        "value": "是"
    }
]
返回值：
{
    "id": 0,
    "labels":[{"标签"}],
    "productId": 0,
    "title": "asdfadf"
}
```
```text
用户标签配置规则：身高(160~170),收入(5000~9000),是否祛痘(是) 
ps: 带有~符号为范围值 所有符号都需要是英文符号
```
```text
商品标签配置规则：祛痘,养生
ps: 所有符号都需要是英文符号
```

## sql导入
```sql
-- 1.创建数据库 label_service
-- 2.导入sql sql/label_service.sql
```

## 部署
bash start.sh

## 账号密码
admin
123456