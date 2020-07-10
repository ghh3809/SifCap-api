# SifCap-api

SIF国服个人数据站API

项目采用SpringBoot框架和MyBatis持久层框架，搭建简单的API服务。

## 快速开始

### 环境需求

项目需运行在Java 1.8，MySQL 5.6及以上环境

### 数据库配置

仿照下例添加文件：src/main/resources/application-prod.xml

``` yml
spring:
  datasource:
    name: sif
    url: jdbc:mysql://{host}:{port}/{db}?serverTimezone=UTC&useSSL=FALSE
    username: {user}
    password: {passwd}
```

### 编译

``` bash
sh build.sh
```

### 运行

``` bash
cd output
sh server-start.sh prod
```

## 如何贡献

欢迎提Issue和Pull request
