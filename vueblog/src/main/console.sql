### 数据库设计
###只包含核心的blog type user三张表 其他表暂未设置
/*
 Navicat Premium Data Transfer

 Source Server         : mysql
 Source Server Type    : MySQL
 Source Server Version : 80018
 Source Host           : localhost:3306
 Source Schema         : blog
 Date: 10/04/2021 10:35:12
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

CREATE DATABASE vueblog;
use vueblog;
-- ----------------------------
-- Table structure for blog
-- ----------------------------
DROP TABLE IF EXISTS `blog`;
CREATE TABLE `blog`
(
    `id`            bigint(20)                                                    NOT NULL AUTO_INCREMENT,
    `title`         varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '文章标题',
    `first_picture` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci      DEFAULT NULL COMMENT '文章首图，用于随机文章展示',

    `description`   longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci     NOT NULL COMMENT '描述',
    `content`       longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci     NOT NULL COMMENT '文章正文',

    `create_time`   datetime(0)                                                   NOT NULL COMMENT '创建时间',
    `update_time`   datetime(0)                                                   NOT NULL COMMENT '更新时间',
    `views`         int(0)                                                        NOT NULL COMMENT '浏览次数',
    `words`         int(0)                                                        NOT NULL COMMENT '文章字数',

    `type_id`       bigint(20)                                                    NOT NULL COMMENT '文章分类id',
    `user_id`       bigint(20)                                                    NULL DEFAULT NULL COMMENT '文章作者id',
    `status`        tinyint(4)                                                         DEFAULT NULL,
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci
  ROW_FORMAT = Dynamic;
INSERT INTO `blog`
VALUES ('1', '关于我！！', 'https://cdn.jsdelivr.net/gh/Naccl/blog-resource/blogHosting/2020/12/B01/001.jpg', 'guanyuuwp',
        '关于我的信息如需哎', '2020-05-22 22:05:49', '2020-05-22 22:05:49', '32', '10', '1', '1', '1');
INSERT INTO `blog`
VALUES ('2', '友情链接', 'https://cdn.jsdelivr.net/gh/Naccl/blog-resource/blogHosting/2020/12/B01/001.jpg', 'guanyuuwp',
        '关于我的信息如需哎', '2020-05-22 22:05:49', '2020-05-22 22:05:49', '32', '10', '1', '1', '1');

INSERT INTO `blog`
VALUES ('5', '生活就像海洋，只有意志坚强的人才能到达彼岸', '', '这里是摘要哈哈哈', '内容？？？', '2020-05-21 22:08:42', '2020-05-21 22:08:42', '100',
        '10', '2', '1', '0');
INSERT INTO `blog`
VALUES ('3', '最值得学习的博客项目eblog', 'https://cdn.jsdelivr.net/gh/Naccl/blog-resource/blogHosting/2020/12/B01/001.jpg',
        'eblog是一个基于Springboot2.1.2开发的博客学习项目，为了让项目融合更多的知识点，达到学习目的，编写了详细的从0到1开发文档。主要学习包括：自定义Freemarker标签，使用shiro+redis完成了会话共享，redis的zset结构完成本周热议排行榜，t-io+websocket完成即时消息通知和群聊，rabbitmq+elasticsearch完成博客内容搜索引擎等。值得学习的地方很多！',
        '**推荐阅读：**\r\n\r\n[分享一套SpringBoot开发博客系统源码，以及完整开发文档！速度保存！](https://mp.weixin.qq.com/s/jz6e977xP-OyaAKNjNca8w)\r\n\r\n[Github上最值得学习的100个Java开源项目，涵盖各种技术栈！](https://mp.weixin.qq.com/s/N-U0TaEUXnBFfBsmt_OESQ)\r\n\r\n[2020年最新的常问企业面试题大全以及答案](https://mp.weixin.qq.com/s/lR5LC5GnD2Gs59ecV5R0XA)',
        '2020-05-28 09:36:38', '2020-05-28 09:36:38', '111', '10', '1', '1', '0');
INSERT INTO `blog`
VALUES ('4', '关注公众号JavaCat，回复xshell或navicat获取破解对应工具',
        'https://cdn.jsdelivr.net/gh/Naccl/blog-resource/blogHosting/2020/12/B01/001.jpg',
        '视频中所用到的xshell和navicat直接获取哈！',
        '### 工具获取\r\n\r\n* xshell 6 绿色破解版：关注公众号：JavaCat，回复 xshell 获取\r\n* Navicat 11 简体中文版：关注公众号：JavaCat，回复 navicat 获取\r\n\r\n公众号二维码：\r\n\r\n![JavaCat](//image-1300566513.cos.ap-guangzhou.myqcloud.com/upload/images/20201020/7fa16a1f957f4cfebe7be1f6675f6f36.png \"JavaCat\")\r\n\r\n直接扫码回复对应关键字\r\n\r\n**推荐阅读：**\r\n\r\n[B站86K播放量，SpringBoot+Vue前后端分离完整入门教程！](https://mp.weixin.qq.com/s/jGEkHTf2X8l-wUenc-PpEw)\r\n\r\n[分享一套SpringBoot开发博客系统源码，以及完整开发文档！速度保存！](https://mp.weixin.qq.com/s/jz6e977xP-OyaAKNjNca8w)\r\n\r\n[Github上最值得学习的100个Java开源项目，涵盖各种技术栈！](https://mp.weixin.qq.com/s/N-U0TaEUXnBFfBsmt_OESQ)\r\n\r\n[2020年最新的常问企业面试题大全以及答案](https://mp.weixin.qq.com/s/lR5LC5GnD2Gs59ecV5R0XA)',
        '2020-10-20 05:05:31', '2020-10-20 05:05:31', '101', '11', '1', '1', '0');
INSERT INTO `blog`
VALUES ('7', '你真的会写单例模式吗?', 'https://cdn.jsdelivr.net/gh/Naccl/blog-resource/blogHosting/2020/12/B01/001.jpg',
        '单例模式可能是代码最少的模式了，但是少不一定意味着简单，想要用好、用对单例模式，还真得费一番脑筋。本文对 Java 中常见的单例模式写法做了一个总结，如有错漏之处，恳请读者指正。',
        '> 作者：吃桔子的攻城狮 来源：http://www.tekbroaden.com/singleton-java.html\n\n\n单例模式可能是代码最少的模式了，但是少不一定意味着简单，想要用好、用对单例模式，还真得费一番脑筋。本文对 Java 中常见的单例模式写法做了一个总结，如有错漏之处，恳请读者指正。\n\n饿汉法\n===\n\n顾名思义，饿汉法就是在第一次引用该类的时候就创建对象实例，而不管实际是否需要创建。代码如下：\n\n```\npublic class Singleton {  \n    private static Singleton = new Singleton();\n    private Singleton() {}\n    public static getSignleton(){\n        return singleton;\n    }\n}\n\n```\n\n这样做的好处是编写简单，但是无法做到延迟创建对象。但是我们很多时候都希望对象可以尽可能地延迟加载，从而减小负载，所以就需要下面的懒汉法：\n',
        '2020-05-22 00:42:44', '2020-05-22 00:42:44', '10', '324', '1', '1', '0');
INSERT INTO `blog`
VALUES ('9', '真正理解Mysql的四种隔离级别@', '',
        '事务是应用程序中一系列严密的操作，所有操作必须成功完成，否则在每个操作中所作的所有更改都会被撤消。也就是事务具有原子性，一个事务中的一系列的操作要么全部成功，要么一个都不做。\n\n事务的结束有两种，当事务中的所以步骤全部成功执行时，事务提交。如果其中一个步骤失败，将发生回滚操作，撤消撤消之前到事务开始时的所以操作。',
        '### 什么是事务  \n\n> 事务是应用程序中一系列严密的操作，所有操作必须成功完成，否则在每个操作中所作的所有更改都会被撤消。也就是事务具有原子性，一个事务中的一系列的操作要么全部成功，要么一个都不做。\n> \n> 事务的结束有两种，当事务中的所以步骤全部成功执行时，事务提交。如果其中一个步骤失败，将发生回滚操作，撤消撤消之前到事务开始时的所以操作。\n\n**事务的 ACID**\n\n事务具有四个特征：原子性（ Atomicity ）、一致性（ Consistency ）、隔离性（ Isolation ）和持续性（ Durability ）。这四个特性简称为 ACID 特性。\n\n> 1 、原子性。事务是数据库的逻辑工作单位，事务中包含的各操作要么都做，要么都不做\n> \n> 2 、一致性。事 务执行的结果必须是使数据库从一个一致性状态变到另一个一致性状态。因此当数据库只包含成功事务提交的结果时，就说数据库处于一致性状态。如果数据库系统 运行中发生故障，有些事务尚未完成就被迫中断，这些未完成事务对数据库所做的修改有一部分已写入物理数据库，这时数据库就处于一种不正确的状态，或者说是 不一致的状态。',
        '2020-05-22 22:04:46', '2020-05-22 22:04:46', '323', '101', '1', '1', '0');
INSERT INTO `blog`
VALUES (null, '博客项目eblog讲解视频上线啦，长达17个小时！！',
        'https://cdn.jsdelivr.net/gh/Naccl/blog-resource/blogHosting/2020/12/B01/001.jpg',
        '1. 慕课网免费资源好久都没更新了，新教程大都付费\n2. B站上的视频繁多，通过收藏和弹幕数量通常很容易判断出视频是否优质\n3. 讲真，B站的弹幕文化，让我觉得，我不是一个在学习，自古人才出评论。哈哈哈\n4. B站视频通常广告少，up主的用心录制，通常只为了你关注他',
        'ok，再回到我们的eblog项目，源码、文档、视频我都开源出来了。来些基本操作：github上给个star，B站视频给个三连支持咧。\n\neblog源码：https://github.com/MarkerHub/eblog\n\n点击这里：[10+篇完整开发文档](https://mp.weixin.qq.com/mp/homepage?__biz=MzIwODkzOTc1MQ==&hid=1&sn=8e512316c3dfe140e636d0c996951166)\n\n![](//image-1300566513.cos.ap-guangzhou.myqcloud.com/upload/images/20200508/c290d945b7d24c79b172759bdb5b94e0.png)\n\n视频讲解：（记得关注我噢！）\n\nhttps://www.bilibili.com/video/BV1ri4y1x71A\n\n![](//image-1300566513.cos.ap-guangzhou.myqcloud.com/upload/images/20200508/983b5abc1c934360a1a1362347a275f7.png)\n\n项目其实还很多bug的，哈哈，我还需要进行二次迭代，到时候再发迭代文档出来。\n\n关注下我的B站，作为一个自媒体的自由职业者，没有什么比涨粉更让我开心的了，嘻嘻。\n\n近期即将推出的视频教程：\n\n1. 搭建脚手架，前后端分离首秀\n2. Shiro入门到精通教程\n3. SpringBoot2.2.6最新入门教程',
        '2020-05-22 22:05:49', '2020-05-22 22:05:49', '32', '10', '1', '1', '0');


-- ----------------------------
-- Table structure for type
-- ----------------------------
DROP TABLE IF EXISTS `type`;
CREATE TABLE `type`
(
    `id`        bigint(20)                                                    NOT NULL AUTO_INCREMENT,
    `type_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci
  ROW_FORMAT = Dynamic;

INSERT INTO `type`
VALUES ('1', '技术博客');
INSERT INTO `type`
VALUES ('2', '面试');
INSERT INTO `type`
VALUES ('3', '心情随写');
INSERT INTO `type`
VALUES ('4', '学习笔记');


-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`
(
    `id`          bigint(0)                                                     NOT NULL AUTO_INCREMENT,
    `username`    varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户名',
    `password`    varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '密码',
    `nickname`    varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '昵称',
    `avatar`      varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '头像地址',
    `email`       varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '邮箱',
    `status`      int(5)                                                        NOT NULL,
    `create_time` datetime(0)                                                   NOT NULL COMMENT '创建时间',
    `update_time` datetime(0)                                                   NOT NULL COMMENT '更新时间',

    `role`        varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '角色访问权限',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci
  ROW_FORMAT = Dynamic;

INSERT INTO `user`
VALUES ('1', 'root', '96e79218965eb72c92a549dd5a330112', 'root',
        'https://img.lanrentuku.com/img/allimg/1612/14831720501619.jpg', 'admin@naccl.top', '1', '2020-04-20 10:44:01',
        '2020-04-20 10:44:01', 'role_root');
INSERT INTO `user`
VALUES ('2', 'admin', '96e79218965eb72c92a549dd5a330112', 'Admin',
        'https://image-1300566513.cos.ap-guangzhou.myqcloud.com/upload/images/5a9f48118166308daba8b6da7e466aab.jpg',
        'admin@naccl.top', '1', '2020-04-20 10:44:01', '2020-04-20 10:44:01', 'role_admin');
INSERT INTO `user`
VALUES ('3', 'user', '96e79218965eb72c92a549dd5a330112', 'User',
        'https://img.lanrentuku.com/img/allimg/1612/14831720501619.jpg', 'admin@naccl.top', '1', '2020-04-20 10:44:01',
        '2020-04-20 10:44:01', 'role_user');

INSERT INTO `user`
VALUES ('4', 'Visitor', '96e79218965eb72c92a549dd5a330112', 'vistor',
        'https://img.lanrentuku.com/img/allimg/1612/14831720501619.jpg', 'admin@naccl.top', '1', '2020-04-20 10:44:01',
        '2020-04-20 10:44:01', 'role_guest');
-- ----------------------------
-- Table structure for comment
-- ----------------------------
DROP TABLE IF EXISTS `comment`;
CREATE TABLE `comment`
(
    `id`                      bigint(0)                                                     NOT NULL AUTO_INCREMENT,
    `nickname`                varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '昵称',
    `email`                   varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '邮箱',
    `content`                 varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '评论内容',
    `avatar`                  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '头像(图片路径)',
    `create_time`             datetime(0)                                                   NULL DEFAULT NULL COMMENT '评论时间',
    `ip`                      varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '评论者ip地址',
    `is_admin_comment`        int(1)                                                        NOT NULL COMMENT '博主回复',
    `is_published`            bit(1)                                                        NOT NULL DEFAULT TRUE COMMENT '公开或非公开',
    `blog_id`                 bigint(255)                                                   NULL DEFAULT NULL COMMENT '所属的文章',
    `parent_comment_id`       bigint(0)                                                     NULL DEFAULT NULL COMMENT '父评论id，-1为根评论',
    `website`                 varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '个人网站',
    `parent_comment_nickname` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '被回复昵称',
    `qq`                      varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '如果评论昵称为QQ号，则将昵称和头像置为QQ昵称和QQ头像，并将此字段置为QQ号备份',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci
  ROW_FORMAT = Dynamic;

INSERT INTO `comment`
VALUES ('1', '醉墨', '8949048964@qq.com', '测试1',
        'https://hbimg.huabanimg.com/8c7e98227a291c52e3951931b7a07158eec2acda27a8b-87wdWD_fw658/format/webp',
        '2020-05-22 22:05:49', '101,2,2,1', '0',TRUE, '19', '-1', '32', '范德萨', '894904866');
INSERT INTO `comment`
VALUES ('2', '醉墨', '8949048964@qq.com', '测试11',
        'https://hbimg.huabanimg.com/8c7e98227a291c52e3951931b7a07158eec2acda27a8b-87wdWD_fw658/format/webp',
        '2020-05-23 22:05:49', '101,2,2,1', '1', TRUE,'19', '1', '32', '范德萨', '894904866');
INSERT INTO `comment`
VALUES ('3', '醉墨', '8949048964@qq.com', '测试12',
        'https://hbimg.huabanimg.com/8c7e98227a291c52e3951931b7a07158eec2acda27a8b-87wdWD_fw658/format/webp',
        '2020-05-24 22:05:49', '101,2,2,1', '0', TRUE,'19', '1', '32', '范德萨', '894904866');
INSERT INTO `comment`
VALUES ('4', '醉墨', '8949048964@qq.com', '测试2',
        'https://hbimg.huabanimg.com/8c7e98227a291c52e3951931b7a07158eec2acda27a8b-87wdWD_fw658/format/webp',
        '2020-05-25 22:05:49', '101,2,2,1', '1',TRUE, '19', '-1', '32', '范德萨', '894904866');
INSERT INTO `comment`
VALUES ('5', '醉墨', '8949048964@qq.com', '测试21',
        'https://hbimg.huabanimg.com/8c7e98227a291c52e3951931b7a07158eec2acda27a8b-87wdWD_fw658/format/webp',
        '2020-05-26 22:05:49', '101,2,2,1', '0',TRUE, '19', '4', '32', '范德萨', '894904866');
INSERT INTO `comment`
VALUES ('6', '醉墨', '8949048964@qq.com', '测试3',
        'https://hbimg.huabanimg.com/8c7e98227a291c52e3951931b7a07158eec2acda27a8b-87wdWD_fw658/format/webp',
        '2020-05-27 22:05:49', '101,2,2,1', '1', TRUE,'19', '-1', '32', '范德萨', '894904866');


# -- Table structure for visit_log
# -- ----------------------------
# DROP TABLE IF EXISTS `visit_log`;
# CREATE TABLE `visit_log`  (
#                               `id` bigint(0) NOT NULL AUTO_INCREMENT,
#                               `url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '请求接口',
#                               `class_method` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '请求方式',
#                               `args` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '请求参数',
#                               `ip` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'ip',
#                               `ip_source` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'ip来源',
#                               `os` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '操作系统',
#                               `browser` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '浏览器',
#                               `create_time` datetime(0) NOT NULL COMMENT '访问时间',
#                               `user_agent` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'user-agent用户代理',
#                               PRIMARY KEY (`id`) USING BTREE
# ) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;
#
#




-- ----------------------------
-- Table structure for visit_log
-- ----------------------------
DROP TABLE IF EXISTS `visit_log`;
CREATE TABLE `visit_log`  (
                              `id` bigint(0) NOT NULL AUTO_INCREMENT,
                              `uuid` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '访客标识码',
                              `uri` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '请求接口',
                              `method` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '请求方式',
                              `param` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '请求参数',
                              `behavior` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '访问行为',
                              `content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '访问内容',
                              `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
                              `ip` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'ip',
                              `ip_source` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'ip来源',
                              `os` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '操作系统',
                              `browser` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '浏览器',
                              `times` int(0) NOT NULL COMMENT '请求耗时（毫秒）',
                              `create_time` datetime(0) NULL DEFAULT NULL COMMENT '访问时间',
                              `user_agent` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'user-agent用户代理',
                              PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

# -- ----------------------------
# -- Table structure for friend
# -- ----------------------------
# DROP TABLE IF EXISTS `friend`;
# CREATE TABLE `friend`  (
#                            `id` bigint(0) NOT NULL AUTO_INCREMENT,
#                            `nickname` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '昵称',
#                            `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '描述',
#                            `website` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '站点',
#                            `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '头像',
#                            `is_published` bit(1) NOT NULL COMMENT '公开或隐藏',
#                            `views` int(0) NULL DEFAULT NULL  COMMENT '点击次数',
#                            `create_time` datetime(0) NOT NULL COMMENT '创建时间',
#                            PRIMARY KEY (`id`) USING BTREE
# ) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;
-- ----------------------------
-- Table structure for friend
-- ----------------------------
DROP TABLE IF EXISTS `friend`;
CREATE TABLE `friend`  (
                           `id` bigint(0) NOT NULL AUTO_INCREMENT,
                           `nickname` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '昵称',
                           `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '描述',
                           `website` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '站点',
                           `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '头像',
                           `is_published` bit(1) NOT NULL DEFAULT 1 COMMENT '公开或隐藏',
                           `views` int(0) NOT NULL DEFAULT 0 COMMENT '点击次数',
                           `create_time` datetime(0)  NULL  DEFAULT NULL COMMENT '创建时间',
                           PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;



INSERT INTO `friend` VALUES ('1','SuiNian\'s Blog','你歪头眯眼一笑 我便沉溺无法逃离','https://blog.nianbroken.top','https://blog.nianbroken.top/favicon.png',1,'0','2020-05-05 08:05:49');
INSERT INTO `friend` VALUES ('2','听得入迷空间','记录，感受，表达','https://blog.tdrme.cn','https://cdn.jsdelivr.net/gh/tdrme/tdrme.github.io@master/20210504090204404.png',1,'0','2020-05-05 08:05:49');

INSERT INTO `friend` VALUES ('3','夕阳皎月的个人博客','When KobeBryant died,a piece of me died.','http://www.twilightjy.com/','https://cdn.jsdelivr.net/gh/yubifeng/blog-resource/bloghosting/website/friend/myavatar.jpg',1,'0','2020-05-05 08:05:49');

INSERT INTO `friend` VALUES ('4','空山柠语','歲時艱，行路難。形將破，意且存，勿忘我','http://www.lemjuice.cn/','http://www.lemjuice.cn/00000147.png',1,'0','2020-05-05 08:05:49');

INSERT INTO `friend` VALUES ('5','ZhaoQuinn''s Blog','热爱科技，热爱生活.','https://zhaoq.me','https://zhaoq.me/usr/uploads/2020/03/2395074381.jpg',1,'0','2020-05-05 08:05:49');



-- ----------------------------
-- Table structure for visitor
-- ----------------------------
DROP TABLE IF EXISTS `visitor`;
CREATE TABLE `visitor`  (
                            `id` bigint(0) NOT NULL AUTO_INCREMENT,
                            `uuid` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '访客标识码',
                            `ip` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'ip',
                            `ip_source` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'ip来源',
                            `os` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '操作系统',
                            `browser` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '浏览器',
                            `create_time` datetime(0) NOT NULL COMMENT '首次访问时间',
                            `last_time` datetime(0) NOT NULL COMMENT '最后访问时间',
                            `pv` int(0) NULL DEFAULT NULL COMMENT '访问页数统计',
                            `user_agent` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'user-agent用户代理',
                            PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;










# -- Table structure for blog_tag
# -- ----------------------------
# DROP TABLE IF EXISTS `blog_tag`;
# CREATE TABLE `blog_tag`  (
#                              `blog_id` bigint(20) NOT NULL,
#                              `tag_id` bigint(20) NOT NULL
# ) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;
#
# INSERT INTO `blog_tag` VALUES ('1','2');
#
# INSERT INTO `blog_tag` VALUES ('1','3');
# INSERT INTO `blog_tag` VALUES ('2','3');
# INSERT INTO `blog_tag` VALUES ('3','3');
# INSERT INTO `blog_tag` VALUES ('7','3');
# INSERT INTO `blog_tag` VALUES ('9','3');
# INSERT INTO `blog_tag` VALUES ('10','3');
#
#
# -- ----------------------------
# -- Table structure for tag
# -- ----------------------------
# DROP TABLE IF EXISTS `tag`;
# CREATE TABLE `tag`  (
#                         `id` bigint(20) NOT NULL AUTO_INCREMENT,
#                         `tag_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
#                         PRIMARY KEY (`id`) USING BTREE
# ) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;
#
# INSERT INTO `tag` VALUES ('1','python');
# INSERT INTO `tag` VALUES ('2','c++ ');
# INSERT INTO `tag` VALUES ('3','spring');

# -- ----------------------------
# -- Table structure for site_setting
# -- ----------------------------
# DROP TABLE IF EXISTS `site_setting`;
# CREATE TABLE `site_setting`  (
#                                  `id` bigint(0) NOT NULL AUTO_INCREMENT,
#                                  `name_en` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
#                                  `name_zh` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
#                                  `value` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL,
#                                  `type` int(0) NULL DEFAULT NULL COMMENT '1基础设置，2页脚徽标，3资料卡，4友链信息',
#                                  PRIMARY KEY (`id`) USING BTREE
# ) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;
#
# INSERT INTO `site_setting` VALUES (1, 'webTitleSuffix', '网页标题后缀', ' - Naccl\'s Blog', 1);
# INSERT INTO `site_setting` VALUES (2, 'blogName', '博客名称', 'Naccl\'s Blog', 1);
# INSERT INTO `site_setting` VALUES (3, 'footerImgTitle', '页脚图片标题', '手机看本站', 1);
# INSERT INTO `site_setting` VALUES (4, 'footerImgUrl', '页脚图片路径', '/img/qr.png', 1);
# INSERT INTO `site_setting` VALUES (5, 'copyright', 'Copyright', '{\"title\":\"Copyright © 2019 - 2020\",\"siteName\":\"NACCL\'S BLOG\"}', 1);
# INSERT INTO `site_setting` VALUES (6, 'beian', 'ICP备案号', '', 1);
# INSERT INTO `site_setting` VALUES (7, 'badge', '徽标', '{\"title\":\"由 Spring Boot 强力驱动\",\"url\":\"https://spring.io/projects/spring-boot/\",\"subject\":\"Powered\",\"value\":\"Spring Boot\",\"color\":\"blue\"}', 2);
# INSERT INTO `site_setting` VALUES (8, 'badge', '徽标', '{\"title\":\"Vue.js 客户端渲染\",\"url\":\"https://cn.vuejs.org/\",\"subject\":\"SPA\",\"value\":\"Vue.js\",\"color\":\"brightgreen\"}', 2);
# INSERT INTO `site_setting` VALUES (9, 'badge', '徽标', '{\"title\":\"UI 框架 Semantic-UI\",\"url\":\"https://semantic-ui.com/\",\"subject\":\"UI\",\"value\":\"Semantic-UI\",\"color\":\"semantic-ui\"}', 2);
# INSERT INTO `site_setting` VALUES (10, 'badge', '徽标', '{\"title\":\"阿里云提供服务器及域名相关服务\",\"url\":\"https://www.aliyun.com/\",\"subject\":\"VPS & DNS\",\"value\":\"Aliyun\",\"color\":\"blueviolet\"}', 2);
# INSERT INTO `site_setting` VALUES (11, 'badge', '徽标', '{\"title\":\"jsDelivr 提供 CDN 加速服务\",\"url\":\"https://www.jsdelivr.com/\",\"subject\":\"CDN\",\"value\":\"jsDelivr\",\"color\":\"orange\"}', 2);
# INSERT INTO `site_setting` VALUES (12, 'badge', '徽标', '{\"title\":\"GitHub 提供图床\",\"url\":\"https://github.com/\",\"subject\":\"OSS\",\"value\":\"GitHub\",\"color\":\"github\"}', 2);
# INSERT INTO `site_setting` VALUES (13, 'badge', '徽标', '{\"title\":\"本站点采用 CC BY 4.0 国际许可协议进行许可\",\"url\":\"https://creativecommons.org/licenses/by/4.0/\",\"subject\":\"CC\",\"value\":\"BY 4.0\",\"color\":\"lightgray\"}', 2);
# INSERT INTO `site_setting` VALUES (14, 'avatar', '图片路径', '/img/avatar.jpg', 3);
# INSERT INTO `site_setting` VALUES (15, 'name', '昵称', 'Naccl', 3);
# INSERT INTO `site_setting` VALUES (16, 'rollText', '滚动个签', '\"云鹤当归天，天不迎我妙木仙；\",\"游龙当归海，海不迎我自来也。\"', 3);
# INSERT INTO `site_setting` VALUES (17, 'github', 'GitHub地址', 'https://github.com/Naccl', 3);
# INSERT INTO `site_setting` VALUES (18, 'qq', 'QQ链接', 'http://sighttp.qq.com/authd?IDKEY=', 3);
# INSERT INTO `site_setting` VALUES (19, 'bilibili', 'bilibili链接', 'https://space.bilibili.com/', 3);
# INSERT INTO `site_setting` VALUES (20, 'netease', '网易云音乐', 'https://music.163.com/#/user/home?id=', 3);
# INSERT INTO `site_setting` VALUES (21, 'email', 'email', 'mailto:i@naccl.top', 3);
# INSERT INTO `site_setting` VALUES (22, 'favorite', '自定义', '{\"title\":\"最喜欢的动漫 📺\",\"content\":\"异度侵入、春物语、NO GAME NO LIFE、实力至上主义的教室、辉夜大小姐、青春猪头少年不会梦到兔女郎学姐、路人女主、Re0、魔禁、超炮、俺妹、在下坂本、散华礼弥、OVERLORD、慎勇、人渣的本愿、白色相簿2、死亡笔记、DARLING in the FRANXX、鬼灭之刃\"}', 3);
# INSERT INTO `site_setting` VALUES (23, 'favorite', '自定义', '{\"title\":\"最喜欢我的女孩子们 🤤\",\"content\":\"芙兰达、土间埋、食蜂操祈、佐天泪爷、樱岛麻衣、桐崎千棘、02、亚丝娜、高坂桐乃、五更琉璃、安乐冈花火、一色彩羽、英梨梨、珈百璃、时崎狂三、可儿那由多、和泉纱雾、早坂爱\"}', 3);
# INSERT INTO `site_setting` VALUES (24, 'favorite', '自定义', '{\"title\":\"最喜欢玩的游戏 🎮\",\"content\":\"Stellaris、巫师、GTA、荒野大镖客、刺客信条、魔兽争霸、LOL、PUBG\"}', 3);
# INSERT INTO `site_setting` VALUES (25, 'reward', '赞赏码路径', '/img/reward.jpg', 1);
# INSERT INTO `site_setting` VALUES (26, 'commentAdminFlag', '博主评论标识', '咕咕', 1);
# INSERT INTO `site_setting` VALUES (27, 'friendContent', '友链页面信息', '随机排序，不分先后。欢迎交换友链~(￣▽￣)~*\n\n* 昵称：Naccl\n* 一句话：游龙当归海，海不迎我自来也。\n* 网址：[https://naccl.top](https://naccl.top)\n* 头像URL：[https://naccl.top/img/avatar.jpg](https://naccl.top/img/avatar.jpg)\n\n仅凭个人喜好添加友链，请在收到我的回复邮件后再于贵站添加本站链接。原则上已添加的友链不会删除，如果你发现自己被移除了，恕不另行通知，只需和我一样做就好。\n\n', 4);
# INSERT INTO `site_setting` VALUES (28, 'friendCommentEnabled', '友链页面评论开关', '1', 4);
#
#

#
#
# -- ----------------------------
# -- Table structure for visit_log
# -- ----------------------------
# DROP TABLE IF EXISTS `visit_log`;
# CREATE TABLE `visit_log`  (
#                               `id` bigint(0) NOT NULL AUTO_INCREMENT,
#                               `uuid` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '访客标识码',
#                               `uri` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '请求接口',
#                               `method` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '请求方式',
#                               `param` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '请求参数',
#                               `behavior` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '访问行为',
#                               `content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '访问内容',
#                               `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
#                               `ip` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'ip',
#                               `ip_source` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'ip来源',
#                               `os` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '操作系统',
#                               `browser` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '浏览器',
#                               `times` int(0) NOT NULL COMMENT '请求耗时（毫秒）',
#                               `create_time` datetime(0) NOT NULL COMMENT '访问时间',
#                               `user_agent` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'user-agent用户代理',
#                               PRIMARY KEY (`id`) USING BTREE
# ) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;
#
#
# -- ----------------------------
# -- Table structure for visitor
# -- ----------------------------
# DROP TABLE IF EXISTS `visitor`;
# CREATE TABLE `visitor`  (
#                             `id` bigint(0) NOT NULL AUTO_INCREMENT,
#                             `uuid` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '访客标识码',
#                             `ip` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'ip',
#                             `ip_source` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'ip来源',
#                             `os` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '操作系统',
#                             `browser` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '浏览器',
#                             `create_time` datetime(0) NOT NULL COMMENT '首次访问时间',
#                             `last_time` datetime(0) NOT NULL COMMENT '最后访问时间',
#                             `pv` int(0) NULL DEFAULT NULL COMMENT '访问页数统计',
#                             `user_agent` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'user-agent用户代理',
#                             PRIMARY KEY (`id`) USING BTREE
# ) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;









# INSERT INTO `friend` VALUES ('1','SuiNian\'s Blog','你歪头眯眼一笑 我便沉溺无法逃离','https://blog.nianbroken.top','https://blog.nianbroken.top/icon-fa-gem.png',1,'0','2020-05-05 08:05:49');
# INSERT INTO `friend` VALUES ('2','SuiNian\'s Blog','你歪头眯眼一笑 我便沉溺无法逃离','https://blog.nianbroken.top','https://blog.nianbroken.top/icon-fa-gem.png',1,'0','2020-05-05 08:05:49');
# INSERT INTO `friend` VALUES ('3','听得入迷空间','记录，感受，表达','https://blog.tdrme.cn','https://cdn.jsdelivr.net/gh/tdrme/tdrme.github.io@master/20210504090204404.png',1,'0','2020-05-05 08:05:49');
# INSERT INTO `friend` VALUES ('4','听得入迷空间','记录，感受，表达','https://blog.tdrme.cn','https://cdn.jsdelivr.net/gh/tdrme/tdrme.github.io@master/20210504090204404.png',1,'0','2020-05-05 08:05:49');

