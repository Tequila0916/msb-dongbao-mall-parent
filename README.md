[![马士兵教育-咚宝商城V1.0](https://img.shields.io/badge/%E9%A9%AC%E5%A3%AB%E5%85%B5%E6%95%99%E8%82%B2-%E5%92%9A%E5%AE%9D%E5%95%86%E5%9F%8EV1.0-red)]()
[![Version](https://img.shields.io/badge/JDK-1.8-green)]()
[![License](https://img.shields.io/badge/license-Apache%202-green.svg)](https://www.apache.org/licenses/LICENSE-2.0)
[![Build Status](https://travis-ci.org/xialonghua/kotmvp.svg?branch=master)](https://travis-ci.org/xialonghua/kotmvp) 
[![Download](https://api.bintray.com/packages/xialonghua/kotmvp/kotmvp/images/download.svg)](https://bintray.com/xialonghua/kotmvp/kotmvp/_latestVersion)

## 咚宝商城 V1.0


### 技术选型

1. [![JDK-1.8](https://img.shields.io/badge/JDK-1.8-green)]()
2. [![springboot-2.2.7.RELEASE](https://img.shields.io/badge/springboot-2.2.7.RELEASE-yellowgreen)]()
3. [![mysql-5.7.30](https://img.shields.io/badge/mysql-5.7.30-orange)]()
3. [![mybatis--plus-3.3.2](https://img.shields.io/badge/mybatis--plus-3.3.2-lightgrey)]()
4. [![RedisCluster-5.0.5](https://img.shields.io/badge/RedisCluster-5.0.5-brightgreen)]()                
5. [![elasticsearch-6.5](https://img.shields.io/badge/elasticsearch-6.5-brightgreen)]()               
6. [![knife4j-2.0.3](https://img.shields.io/badge/knife4j-2.0.3-yellowgreen)]()
7. RocketMQ

  
### 技术点

1. 统一参数校验 validator
2. 统一异常捕获 返回异常码
3. 统一错误码规划 根据业务、错误类型划分
4. 接口文档规范 swagger
5. 代码生成器，统一生成entity、dto、vo、controller、service、mapper.xml
6. 多环境Profile + Maven Filter 切换
7. 业务记录异常、错误日志写入文件，过 FileBeat 到 ES 落库
8. 统一用户操作日志处理,直接写入到Kafka，ES落库
9. mysql-es 同步，基于canal、kafka中间件，实现数据的增量、全量同步



### 代码模块介绍

- `msb-dongbao-common 公共包`
    - `msb-dongbao-common-base 公共基础类`
    - `msb-dongbao-common-util 工具类`
- `msb-dongbao-api 业务模块接口层`
    - `msb-dongbao-oms-api 订单中心接口`
    - `msb-dongbao-pms-api 商品中心接口`
    - `msb-dongbao-ums-api 用户中心接口`
    - `msb-dongbao-pay-api 支付中心接口`
    - `msb-dongbao-cart-api 购物车接口`
    - `msb-dongbao-dictionary-api 基础字典接口`
    - `msb-dongbao-sms-api 优惠中心接口`
    - `msb-dongbao-cms-api 内容中心接口`
- `msb-dongbao-service 业务模块实现层`
    - `msb-dongbao-oms 订单中心模块实现`
    - `msb-dongbao-pms 商品中心模块实现`
    - `msb-dongbao-ums 用户中心模块实现`
    - `msb-dongbao-pay 支付中心模块实现`
    - `msb-dongbao-cart 购物车模块实现`
    - `msb-dongbao-dictionary 基础字典模块实现`
    - `msb-dongbao-sms 优惠中心模块实现`
    - `msb-dongbao-cms 内容中心模块实现`
- `msb-dongbao-application web应用模块`
    - `msb-dongbao-manager-web  后台管理应用`
    - `msb-dongbao-portal-web   商城门户网站`

- `msb-dongbao-job 定时任务模块`
- `msb-dongbao-generator 代码生成器`



### 工程说明

1. 后台管理项目目前没有实现msb-dongbao-manager-web，暂时不用去学
2. 第一版主要就是用户端的项目，msb-dongbao-portal-web