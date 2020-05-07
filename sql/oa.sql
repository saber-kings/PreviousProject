/*
 Navicat Premium Data Transfer

 Source Server         : MySQL
 Source Server Type    : MySQL
 Source Server Version : 80012
 Source Host           : localhost:3306
 Source Schema         : oa

 Target Server Type    : MySQL
 Target Server Version : 80012
 File Encoding         : 65001

 Date: 08/05/2020 01:56:16
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for dept_inf
-- ----------------------------
DROP TABLE IF EXISTS `dept_inf`;
CREATE TABLE `dept_inf`  (
  `id` bigint(20) NOT NULL,
  `depart_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `create_date` datetime(6) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of dept_inf
-- ----------------------------
INSERT INTO `dept_inf` VALUES (5, '秘书部', '秘书部门，又称秘书机构，是指在各级各类社会组织中为领导机构和领导者处理综合事务、辅助管理的综合性机构。从决策体系角度看，秘书机构居于决策与执行的转换中介位置，既是决策层和执行层的综合部，又是决策层和职能管理层的纽带。', '2019-11-17 13:20:47.267000');
INSERT INTO `dept_inf` VALUES (8, '技术部', ' 技术部是一个在掌握行业客户详细信息基础上，根据用户的需求，为用户提供全面的解决方案（包括系统模式、技术路线和技术方案），并对项目售前、建设、验收过程进行统一管理的职能部门，它是为用户创造安全、文明、舒适、方便的工作环境和生活环境的基本保障和坚强后盾，是反应公司服务水准、良好形象和社会声誉的重要标志，是公司实现最终经济目标的核心部门之一。', '2019-11-25 18:59:27.654000');
INSERT INTO `dept_inf` VALUES (9, '宣传部', '宣传部是主管媒体宣传、营销策划、公司内促销活动等的制定与执行的部门，在总经理的直接领导下开展工作。', '2019-11-25 19:00:20.989000');

-- ----------------------------
-- Table structure for document_inf
-- ----------------------------
DROP TABLE IF EXISTS `document_inf`;
CREATE TABLE `document_inf`  (
  `id` bigint(20) NOT NULL,
  `create_date` datetime(6) NULL DEFAULT NULL,
  `file_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `user_id` bigint(20) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `FK36pfrq2iqsgi8fcpv5ijgv09v`(`user_id`) USING BTREE,
  CONSTRAINT `FK36pfrq2iqsgi8fcpv5ijgv09v` FOREIGN KEY (`user_id`) REFERENCES `user_inf` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of document_inf
-- ----------------------------
INSERT INTO `document_inf` VALUES (38, '2019-12-02 22:10:41.626000', '密钥.txt', '施工方', '昂奋v', 3);
INSERT INTO `document_inf` VALUES (53, '2019-12-08 17:54:07.884000', 'model.xls', '安抚', '约会大作战', 3);
INSERT INTO `document_inf` VALUES (54, '2019-12-08 17:55:35.583000', '2019-12-07_员工信息.xls', '沙发', '约会大作战', 3);

-- ----------------------------
-- Table structure for employee_inf
-- ----------------------------
DROP TABLE IF EXISTS `employee_inf`;
CREATE TABLE `employee_inf`  (
  `id` bigint(20) NOT NULL,
  `age` int(11) NULL DEFAULT NULL,
  `create_date` datetime(6) NULL DEFAULT NULL,
  `education` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `emp_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `phone` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `remark` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL,
  `sex` int(11) NULL DEFAULT 1,
  `wage` decimal(19, 2) NULL DEFAULT NULL,
  `department_id` bigint(20) NULL DEFAULT NULL,
  `job_id` bigint(20) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `FKhwbiw9fnrv4pixbatcyhksq5x`(`department_id`) USING BTREE,
  INDEX `FK715273x2vwihi2i184ihnbib7`(`job_id`) USING BTREE,
  CONSTRAINT `FK715273x2vwihi2i184ihnbib7` FOREIGN KEY (`job_id`) REFERENCES `job_inf` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `FKhwbiw9fnrv4pixbatcyhksq5x` FOREIGN KEY (`department_id`) REFERENCES `dept_inf` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of employee_inf
-- ----------------------------
INSERT INTO `employee_inf` VALUES (7, 18, '2019-11-25 18:30:57.374000', '本科', 'luanzhaofei@outlook.com', '000001', '栾兆飞', '13176848575', '备注', 1, 2000.00, 5, 6);
INSERT INTO `employee_inf` VALUES (29, 18, '2019-11-27 19:32:00.091000', '博士研究生', 'luanzhaofei@outlook.com', '000002', 'Saber', '13176848575', '备注', 0, 2000.00, 8, 6);
INSERT INTO `employee_inf` VALUES (40, 18, '2019-12-08 13:44:54.289000', '博士研究生', 'luanzhaofei@outlook.com', '000003', 'Saber', '13176848575', '备注', 0, 8000.01, 8, 6);
INSERT INTO `employee_inf` VALUES (41, 18, '2019-12-08 14:17:24.361000', '博士研究生', 'luanzhaofei@outlook.com', '000004', 'Saber', '13176848575', '备注', 0, 8000.01, 8, 6);
INSERT INTO `employee_inf` VALUES (51, 18, '2019-12-08 17:39:07.579000', '博士研究生', 'luanzhaofei@outlook.com', '000005', 'Saber', '13176848575', '备注', 0, 8000.01, 8, 6);
INSERT INTO `employee_inf` VALUES (52, 18, '2019-12-08 17:40:38.122000', '博士研究生', 'luanzhaofei@outlook.com', '000006', 'Saber', '13176848575', '备注', 0, 8000.01, 8, 6);
INSERT INTO `employee_inf` VALUES (55, 18, '2019-12-08 18:05:52.625000', '博士研究生', 'luanzhaofei@outlook.com', '000007', 'Saber', '13176848575', '备注', 0, 8000.01, 8, 6);
INSERT INTO `employee_inf` VALUES (56, 18, '2019-12-08 18:09:23.399000', '博士研究生', 'luanzhaofei@outlook.com', '000008', 'Saber', '13176848575', '备注', 0, 8000.01, 8, 6);
INSERT INTO `employee_inf` VALUES (57, 18, '2019-12-08 18:11:31.783000', '博士研究生', 'luanzhaofei@outlook.com', '000009', 'Saber', '13176848575', '备注', 0, 8000.01, 8, 6);
INSERT INTO `employee_inf` VALUES (58, 18, '2019-12-08 18:13:17.977000', '博士研究生', 'luanzhaofei@outlook.com', '000010', 'Saber', '13176848575', '备注', 0, 8000.01, 8, 6);
INSERT INTO `employee_inf` VALUES (59, 18, '2019-12-08 18:17:37.224000', '博士研究生', 'luanzhaofei@outlook.com', '000011', 'Saber', '13176848575', '备注', 0, 8000.01, 8, 6);
INSERT INTO `employee_inf` VALUES (60, 18, '2019-12-08 18:24:18.309000', '博士研究生', 'luanzhaofei@outlook.com', '000012', 'Saber', '13176848575', '备注', 0, 8000.01, 8, 6);
INSERT INTO `employee_inf` VALUES (61, 18, '2019-12-08 18:30:13.210000', '博士研究生', 'luanzhaofei@outlook.com', '000013', 'Saber', '13176848575', '备注', 0, 8000.01, 8, 6);
INSERT INTO `employee_inf` VALUES (62, 18, '2019-12-08 18:34:44.664000', '博士研究生', 'luanzhaofei@outlook.com', '000014', 'Saber', '13176848575', '备注', 0, 8000.01, 8, 6);
INSERT INTO `employee_inf` VALUES (63, 18, '2019-12-08 18:35:21.148000', '博士研究生', 'luanzhaofei@outlook.com', '000015', 'Saber', '13176848575', '备注', 0, 8000.01, 8, 6);
INSERT INTO `employee_inf` VALUES (64, 18, '2019-12-08 18:35:21.200000', '博士研究生', 'luanzhaofei@outlook.com', '000016', 'Saber', '13176848575', '备注', 0, 8000.01, 8, 6);
INSERT INTO `employee_inf` VALUES (65, 18, '2019-12-08 18:36:28.270000', '博士研究生', 'luanzhaofei@outlook.com', '000017', 'Saber', '13176848575', '备注', 0, 8000.01, 8, 6);
INSERT INTO `employee_inf` VALUES (66, 18, '2019-12-08 18:53:19.331000', '博士研究生', 'luanzhaofei@outlook.com', '000018', 'Saber', '13176848575', '备注', 0, 8000.01, 8, 6);

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
INSERT INTO `hibernate_sequence` VALUES (67);
INSERT INTO `hibernate_sequence` VALUES (67);
INSERT INTO `hibernate_sequence` VALUES (67);
INSERT INTO `hibernate_sequence` VALUES (67);
INSERT INTO `hibernate_sequence` VALUES (67);
INSERT INTO `hibernate_sequence` VALUES (67);

-- ----------------------------
-- Table structure for job_inf
-- ----------------------------
DROP TABLE IF EXISTS `job_inf`;
CREATE TABLE `job_inf`  (
  `id` bigint(20) NOT NULL,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `create_date` datetime(6) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of job_inf
-- ----------------------------
INSERT INTO `job_inf` VALUES (6, 'Java开发工程师', 'Java开发工程师，真香！', '2019-11-17 15:04:51.439000');

-- ----------------------------
-- Table structure for notice_inf
-- ----------------------------
DROP TABLE IF EXISTS `notice_inf`;
CREATE TABLE `notice_inf`  (
  `id` bigint(20) NOT NULL,
  `content` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL,
  `create_date` datetime(6) NULL DEFAULT NULL,
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `user_id` bigint(20) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `FKi9ali7o9238wx3qg6lxdib13s`(`user_id`) USING BTREE,
  CONSTRAINT `FKi9ali7o9238wx3qg6lxdib13s` FOREIGN KEY (`user_id`) REFERENCES `user_inf` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of notice_inf
-- ----------------------------
INSERT INTO `notice_inf` VALUES (36, '库我，漫画家客户回款集合框架好看好看好', '2019-11-29 20:43:07.177000', '测试', 3);

-- ----------------------------
-- Table structure for user_inf
-- ----------------------------
DROP TABLE IF EXISTS `user_inf`;
CREATE TABLE `user_inf`  (
  `id` bigint(20) NOT NULL,
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `create_date` datetime(6) NULL DEFAULT NULL,
  `login_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `status` bit(1) NOT NULL,
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_inf
-- ----------------------------
INSERT INTO `user_inf` VALUES (2, 'https://picsum.photos/id/1003', '2019-11-16 10:21:29.505000', 'saber', 'e10adc3949ba59abbe56e057f20f883e', b'1', 'Saber');
INSERT INTO `user_inf` VALUES (3, 'https://picsum.photos/id/1008', '2019-11-16 15:24:34.859000', 'admin', 'e10adc3949ba59abbe56e057f20f883e', b'1', '栾兆飞');

SET FOREIGN_KEY_CHECKS = 1;
