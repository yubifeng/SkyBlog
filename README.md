<p align="center">
	<img src="https://img.shields.io/badge/JDK-1.8+-orange">
	<img src="https://img.shields.io/badge/SpringBoot-2.2.7.RELEASE-brightgreen">
	<img src="https://img.shields.io/badge/MyBatisPlus-3.5.5-red">
	<img src="https://img.shields.io/badge/Vue-2.6.11-brightgreen">
	<img src="https://img.shields.io/badge/license-MIT-blue">	
</p>

## 博客预览
博客地址：https://www.skymo.top 

后台：https://www.skymo.top/login 账号`Visitor` 密码`111111`

本项目长期维护，欢迎fork代码和star！。


## 简介

很早就有了做个人博客的想法，学习了springboot后，趁热打铁，就做了这个博客来练手。项目是基于 Spring Boot + Vue 的前后端分离博客系统。

## 前端

核心框架：
 - Vue框架：Vue2.6.11、Vue Router、Vuex、vue/cli4.5.12
 - UI框架：Element UI框架
 - 异步请求：axios

markdown：
 - 编辑器：mavon-editor
 - 解析渲染：markdown-it-vue(略作修改，以支持行号和代码黑色背景)
 - 样式：github-markdown-css （github dark风格的代码高亮）


## 后端

- 核心框架：Spring Boot
- 安全框架：shiro
- Token 认证：jwt
- 持久层框架：MyBatisPlus
- java版本：JDK8


## 功能



### 首页
- 最新文章： 按照发布时间来进行倒序排序展示
- 导航栏：首页、分类、归档、友链、关于我
- 网站公告
- 博主介绍展示


### 后台管理页面
- 登录功能
- 文章管理：
    - 文章发布
    - 文章编辑
    - 文章删除
- 评论功能
- 游客统计
- 手机端适配
- 样式美化
- 权限管理
### 未来需增加的功能
- 主题可换
- 后端优化

## 项目快速开始
1. 执行console.sql创建数据库 
2. 开启redis
3. idea中修改yml配置，然后运行后端项目
4. 安装npm 在idea中启动前端页面


## 项目线上部署
1. 使用docker-compose来编排 
2. 需要nginx、redis、后端项目、mysql
3. 配置证书，可申请腾讯、阿里的一年免费证书
4. nginx反向代理，将https请求通过http转给后端容器，避免接口暴露和http的不安全性

## 致谢
项目开发过程中参考了很多大佬的博客，比如[NBlog](https://github.com/Naccl/NBlog)
、[MyBlog](https://github.com/zhyocean/MyBlog)等等，也感谢[MarkerHub的视频](https://www.bilibili.com/video/BV17A411E7aE?t=39&p=9),
让我开始了解怎么做一个springboot的项目。

