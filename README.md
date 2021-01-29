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
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for label_drl
-- ----------------------------
DROP TABLE IF EXISTS `label_drl`;
CREATE TABLE `label_drl` (
  `id` int NOT NULL,
  `label_version` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL,
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `title` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `enable` bit(2) NOT NULL DEFAULT b'0',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='标签drl文件存储表';

-- ----------------------------
-- Records of label_drl
-- ----------------------------
BEGIN;
INSERT INTO `label_drl` VALUES (1, 'v1.0', 'package com.laofeizhu;\n\nimport com.laofeizhu.*;\nimport com.laofeizhu.model.*;\n\nrule \"label rule1\"\nwhen\n    r : Result( )\n    l : BaseUserVo(label==\"user_label_2\" )\nthen\n    r.add(\"product_label_1\");\nend\n\nrule \"label rule2\"\nwhen\n    r : Result( )\n    l : BaseUserVo( label==\"user_label_1\" )\nthen\n    r.add(\"product_label_3\");\nend', '测试1', b'01', NULL, NULL);
COMMIT;

-- ----------------------------
-- Table structure for label_drl_history
-- ----------------------------
DROP TABLE IF EXISTS `label_drl_history`;
CREATE TABLE `label_drl_history` (
  `id` int NOT NULL,
  `name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `label_version` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL,
  `content` text COLLATE utf8mb4_unicode_ci,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='历史记录表';

SET FOREIGN_KEY_CHECKS = 1;
```