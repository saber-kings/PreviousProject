/*
 Navicat Premium Data Transfer

 Source Server         : MySQL
 Source Server Type    : MySQL
 Source Server Version : 80012
 Source Host           : localhost:3306
 Source Schema         : blog

 Target Server Type    : MySQL
 Target Server Version : 80012
 File Encoding         : 65001

 Date: 08/05/2020 01:56:25
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for hibernate_sequence
-- ----------------------------
DROP TABLE IF EXISTS `hibernate_sequence`;
CREATE TABLE `hibernate_sequence`  (
  `next_val` bigint(20) NULL DEFAULT NULL
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of hibernate_sequence
-- ----------------------------
INSERT INTO `hibernate_sequence` VALUES (40);
INSERT INTO `hibernate_sequence` VALUES (40);
INSERT INTO `hibernate_sequence` VALUES (40);
INSERT INTO `hibernate_sequence` VALUES (40);
INSERT INTO `hibernate_sequence` VALUES (40);

-- ----------------------------
-- Table structure for t_blog
-- ----------------------------
DROP TABLE IF EXISTS `t_blog`;
CREATE TABLE `t_blog`  (
  `id` bigint(20) NOT NULL,
  `appreciation` bit(1) NOT NULL,
  `commentabled` bit(1) NOT NULL,
  `content` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL,
  `create_time` datetime(6) NULL DEFAULT NULL,
  `first_picture` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `flag` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `published` bit(1) NOT NULL,
  `recommend` bit(1) NOT NULL,
  `share_statement` bit(1) NOT NULL,
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `update_time` datetime(6) NULL DEFAULT NULL,
  `views` int(11) NULL DEFAULT NULL,
  `type_id` bigint(20) NULL DEFAULT NULL,
  `user_id` bigint(20) NULL DEFAULT NULL,
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `FK292449gwg5yf7ocdlmswv9w4j`(`type_id`) USING BTREE,
  INDEX `FK8ky5rrsxh01nkhctmo7d48p82`(`user_id`) USING BTREE,
  CONSTRAINT `FK292449gwg5yf7ocdlmswv9w4j` FOREIGN KEY (`type_id`) REFERENCES `t_type` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `FK8ky5rrsxh01nkhctmo7d48p82` FOREIGN KEY (`user_id`) REFERENCES `t_user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_blog
-- ----------------------------
INSERT INTO `t_blog` VALUES (14, b'1', b'1', '有时候，我们往服务器上部署项目时，本地数据库已有一些有用的数据需要放到服务器上，这时我们可以选择开启数据库远程连接使用navicat为MySQL导入sql文件，也可以使用命令行导入，下面讲述一下ubuntu服务器命令行导入数据库:\r\n#### 1. 首先建空数据库\r\n```sql\r\ncreate database abc;\r\n```\r\n#### 2. 导入数据库\r\n##### 方法一：\r\n**(1)选择数据库**\r\n```sql\r\nuse abc;\r\n```\r\n**(2)设置数据库编码**\r\n```sql\r\nset names utf8;\r\n```\r\n**(3)导入数据（注意sql文件的路径）**\r\n```sql\r\nsource /home/abc/abc.sql;\r\n```\r\n##### 方法二：\r\nmysql -u用户名 -p密码 数据库名 < 数据库名.sql\r\n在ubuntu命令行里输入：\r\n```bash\r\nmysql -u root -padmin123 < abc.sql\r\n```', '2019-10-30 20:54:30.588000', 'https://picsum.photos/id/0/600/450', '原创', b'1', b'1', b'1', 'MySQL命令行导入sql文件', '2020-03-03 22:08:46.884000', 16, 2, 1, '有时候，我们往服务器上部署项目时，本地数据库已有一些有用的数据需要放到服务器上，这时我们可以选择开启数据库远程连接使用navicat为MySQL导入sql文件，也可以使用命令行导入，本文主要是讲述ubuntu服务器命令行导入数据库');
INSERT INTO `t_blog` VALUES (15, b'1', b'0', '## 这是测试内容标题\r\n\r\n文本内容....\r\n\r\n修改一下', '2019-10-29 13:44:37.000000', 'https://picsum.photos/id/0/600/450', '原创', b'1', b'0', b'0', '测试', '2020-03-03 22:25:32.037000', 5, 7, 1, '这是测试');
INSERT INTO `t_blog` VALUES (24, b'1', b'1', '![](https://saber-blog.oss-accelerate.aliyuncs.com/images/2019-11/285d5-logo.png?Expires=1888214751&OSSAccessKeyId=LTAI4Fh7RwLaoKANuXojA5S5&Signature=%2F2sr5bSFlCk6qigk%2B6MgNAt5uKk%3D)', '2019-11-04 16:07:05.568000', 'https://picsum.photos/id/0/800/450', '原创', b'1', b'1', b'0', '测试图片上传', '2020-03-03 22:10:15.399000', 5, 7, 1, '测试图片上传的展示');
INSERT INTO `t_blog` VALUES (39, b'0', b'0', '华为黑白棋实战项目实战进过你的', '2020-03-07 18:13:50.731000', 'https://picsum.photos/id/0/5616/3744', '原创', b'1', b'0', b'0', '华为黑白棋实战项目实战进过你的', '2020-03-07 18:13:50.731000', 0, 7, 1, '华为黑白棋实战项目实战进过你的');

-- ----------------------------
-- Table structure for t_blog_tags
-- ----------------------------
DROP TABLE IF EXISTS `t_blog_tags`;
CREATE TABLE `t_blog_tags`  (
  `blogs_id` bigint(20) NOT NULL,
  `tags_id` bigint(20) NOT NULL,
  INDEX `FK5feau0gb4lq47fdb03uboswm8`(`tags_id`) USING BTREE,
  INDEX `FKh4pacwjwofrugxa9hpwaxg6mr`(`blogs_id`) USING BTREE,
  CONSTRAINT `FK5feau0gb4lq47fdb03uboswm8` FOREIGN KEY (`tags_id`) REFERENCES `t_tag` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `FKh4pacwjwofrugxa9hpwaxg6mr` FOREIGN KEY (`blogs_id`) REFERENCES `t_blog` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_blog_tags
-- ----------------------------
INSERT INTO `t_blog_tags` VALUES (14, 13);
INSERT INTO `t_blog_tags` VALUES (24, 13);
INSERT INTO `t_blog_tags` VALUES (15, 13);
INSERT INTO `t_blog_tags` VALUES (39, 12);

-- ----------------------------
-- Table structure for t_comment
-- ----------------------------
DROP TABLE IF EXISTS `t_comment`;
CREATE TABLE `t_comment`  (
  `id` bigint(20) NOT NULL,
  `admin_comment` bit(1) NOT NULL,
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `create_time` datetime(6) NULL DEFAULT NULL,
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `nickname` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `blog_id` bigint(20) NULL DEFAULT NULL,
  `parent_comment_id` bigint(20) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `FKke3uogd04j4jx316m1p51e05u`(`blog_id`) USING BTREE,
  INDEX `FK4jj284r3pb7japogvo6h72q95`(`parent_comment_id`) USING BTREE,
  CONSTRAINT `FK4jj284r3pb7japogvo6h72q95` FOREIGN KEY (`parent_comment_id`) REFERENCES `t_comment` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `FKke3uogd04j4jx316m1p51e05u` FOREIGN KEY (`blog_id`) REFERENCES `t_blog` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_comment
-- ----------------------------
INSERT INTO `t_comment` VALUES (16, b'0', 'https://picsum.photos/id/1010/100/100', '测试评论功能', '2019-10-31 21:09:38.315000', 'luanzhaofei@gmail.com', 'Saber污妖王', 14, NULL);
INSERT INTO `t_comment` VALUES (19, b'0', 'https://picsum.photos/id/1023/100/100', '新的评论', '2019-10-31 21:59:28.231000', 'luanzhaofei@gmail.com', '小黄', 14, NULL);
INSERT INTO `t_comment` VALUES (20, b'0', 'https://picsum.photos/id/1018/100/100', '你好啊', '2019-11-01 11:27:53.088000', '1983163238@qq.com', 'Saber污妖王', 14, 19);
INSERT INTO `t_comment` VALUES (31, b'1', 'https://picsum.photos/id/1005/100/100', '大家好', '2020-03-02 21:47:54.011000', 'luanzhaofei@outlook.com', 'saber污妖王', 14, NULL);
INSERT INTO `t_comment` VALUES (32, b'1', 'https://picsum.photos/id/1005/100/100', '废话', '2020-03-02 21:50:37.573000', 'luanzhaofei@outlook.com', 'saber污妖王', 14, NULL);

-- ----------------------------
-- Table structure for t_file
-- ----------------------------
DROP TABLE IF EXISTS `t_file`;
CREATE TABLE `t_file`  (
  `id` bigint(20) NOT NULL,
  `create_date` datetime(6) NULL DEFAULT NULL,
  `file_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `user_id` bigint(20) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `FK6ada9oq0wf8clsfuisknym0fy`(`user_id`) USING BTREE,
  CONSTRAINT `FK6ada9oq0wf8clsfuisknym0fy` FOREIGN KEY (`user_id`) REFERENCES `t_user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_file
-- ----------------------------
INSERT INTO `t_file` VALUES (33, '2020-03-02 21:52:24.199000', '新建文本文档1.txt', '测试', '测试', 1);

-- ----------------------------
-- Table structure for t_tag
-- ----------------------------
DROP TABLE IF EXISTS `t_tag`;
CREATE TABLE `t_tag`  (
  `id` bigint(20) NOT NULL,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_tag
-- ----------------------------
INSERT INTO `t_tag` VALUES (12, 'css');
INSERT INTO `t_tag` VALUES (13, 'Java');

-- ----------------------------
-- Table structure for t_type
-- ----------------------------
DROP TABLE IF EXISTS `t_type`;
CREATE TABLE `t_type`  (
  `id` bigint(20) NOT NULL,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_type
-- ----------------------------
INSERT INTO `t_type` VALUES (1, '错误日志');
INSERT INTO `t_type` VALUES (2, '认知升级');
INSERT INTO `t_type` VALUES (4, 'JavaScript');
INSERT INTO `t_type` VALUES (5, 'PHP');
INSERT INTO `t_type` VALUES (7, '折腾');
INSERT INTO `t_type` VALUES (8, 'Saber');
INSERT INTO `t_type` VALUES (9, '马明顺');
INSERT INTO `t_type` VALUES (10, 'css');
INSERT INTO `t_type` VALUES (25, '错误');

-- ----------------------------
-- Table structure for t_user
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user`  (
  `id` bigint(20) NOT NULL,
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `create_time` datetime(6) NULL DEFAULT NULL,
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `nickname` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `type` int(11) NULL DEFAULT NULL,
  `update_time` datetime(6) NULL DEFAULT NULL,
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_user
-- ----------------------------
INSERT INTO `t_user` VALUES (1, 'https://picsum.photos/id/1005', '2019-10-29 12:49:26.000000', 'luanzhaofei@outlook.com', 'saber污妖王', 'e088991d1fcc0c47bd5a5167094c374a', 1, '2019-10-29 12:50:28.000000', 'luanzhaofei@outlook.com');

SET FOREIGN_KEY_CHECKS = 1;
