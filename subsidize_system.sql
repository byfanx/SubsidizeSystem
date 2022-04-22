/*
 Navicat Premium Data Transfer

 Source Server         : 124.223.164.57-test
 Source Server Type    : MySQL
 Source Server Version : 80028
 Source Host           : 124.223.164.57:3308
 Source Schema         : subsidize_system

 Target Server Type    : MySQL
 Target Server Version : 80028
 File Encoding         : 65001

 Date: 21/04/2022 11:34:03
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for attention
-- ----------------------------
DROP TABLE IF EXISTS `attention`;
CREATE TABLE `attention` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'id',
  `user_id` int NOT NULL COMMENT '用户id',
  `followed_user_id` int NOT NULL COMMENT '被关注对象的id',
  `approve_identity` tinyint NOT NULL COMMENT '认证的身份  4 学生 5 学校',
  `status` tinyint DEFAULT NULL COMMENT '状态  0 删除  1 正常',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf16 COMMENT='关注信息表';

-- ----------------------------
-- Records of attention
-- ----------------------------
BEGIN;
INSERT INTO `attention` VALUES (1, 12, 1, 4, 0, '2022-04-07 14:57:06');
INSERT INTO `attention` VALUES (2, 12, 2, 4, 0, '2022-04-07 14:57:07');
INSERT INTO `attention` VALUES (3, 12, 3, 4, 0, '2022-04-07 14:57:08');
INSERT INTO `attention` VALUES (4, 12, 1, 5, 0, '2022-04-07 14:57:09');
INSERT INTO `attention` VALUES (5, 12, 2, 5, 0, '2022-04-07 14:57:05');
INSERT INTO `attention` VALUES (6, 12, 3, 5, 0, '2022-04-07 14:57:04');
COMMIT;

-- ----------------------------
-- Table structure for contribution
-- ----------------------------
DROP TABLE IF EXISTS `contribution`;
CREATE TABLE `contribution` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'id',
  `user_id` int NOT NULL COMMENT '捐款人id',
  `payee` int DEFAULT NULL COMMENT '捐款对象',
  `approve_identity` tinyint DEFAULT NULL COMMENT '认证的身份 3 平台 4 贫困生 5 贫困学校',
  `payment_amount` double(255,2) DEFAULT NULL COMMENT '捐款金额',
  `remarks` varchar(255) DEFAULT NULL COMMENT '捐款备注',
  `auditor_id` int DEFAULT NULL COMMENT '审核人id',
  `authorize_status` tinyint DEFAULT NULL COMMENT '捐款审核状态 0 待审核  1 通过  2 拒绝',
  `status` tinyint DEFAULT NULL COMMENT '状态 0 删除  1 正常',
  `create_time` datetime DEFAULT NULL COMMENT '捐款时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf16 COMMENT='捐款表';

-- ----------------------------
-- Records of contribution
-- ----------------------------
BEGIN;
INSERT INTO `contribution` VALUES (1, 12, 1, 4, 100.10, '我来做慈善', 1, 0, 0, '2022-04-07 14:57:05');
INSERT INTO `contribution` VALUES (2, 12, 2, 4, 100.20, '我来做慈善', 1, 0, 0, '2022-04-07 14:57:06');
INSERT INTO `contribution` VALUES (3, 12, 3, 4, 100.30, '我来做慈善', 1, 0, 0, '2022-04-07 14:57:07');
INSERT INTO `contribution` VALUES (4, 12, 1, 5, 100.40, '我来做慈善', 1, 0, 0, '2022-04-07 14:57:08');
INSERT INTO `contribution` VALUES (5, 12, 2, 5, 100.50, '我来做慈善', 1, 0, 0, '2022-04-07 14:57:09');
INSERT INTO `contribution` VALUES (6, 12, 3, 5, 100.60, '我来做慈善', 1, 0, 0, '2022-04-07 14:57:10');
INSERT INTO `contribution` VALUES (7, 12, 1, 3, 100.70, '我来做慈善', 1, 0, 0, '2022-04-07 14:57:11');
INSERT INTO `contribution` VALUES (8, 12, 1, 3, 100.80, '我来做慈善', 1, 0, 0, '2022-04-07 14:57:01');
INSERT INTO `contribution` VALUES (9, 12, 1, 3, 100.90, '我来做慈善', 1, 0, 0, '2022-04-07 14:57:02');
COMMIT;

-- ----------------------------
-- Table structure for notice
-- ----------------------------
DROP TABLE IF EXISTS `notice`;
CREATE TABLE `notice` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'id',
  `user_id` int DEFAULT NULL COMMENT '发布人',
  `title` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '标题',
  `content` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '发布内容',
  `status` tinyint DEFAULT NULL COMMENT '状态',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf16 COMMENT='公告表';

-- ----------------------------
-- Records of notice
-- ----------------------------
BEGIN;
INSERT INTO `notice` VALUES (1, 1, 'test1', '这是第一条公告', 1, '2022-04-05 23:32:24');
INSERT INTO `notice` VALUES (2, 1, 'test2', '这是第2条公告', 1, '2022-04-07 13:05:18');
INSERT INTO `notice` VALUES (3, 1, 'test3', '这是第3条公告', 1, '2022-04-07 13:08:32');
INSERT INTO `notice` VALUES (4, 1, '第四条公告', '这是本系统的第四条公告！', 1, '2022-04-07 14:57:05');
INSERT INTO `notice` VALUES (5, 1, '第五条公告', '这是本系统的第五条公告！', 1, '2022-04-07 14:57:06');
COMMIT;

-- ----------------------------
-- Table structure for recruit
-- ----------------------------
DROP TABLE IF EXISTS `recruit`;
CREATE TABLE `recruit` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'id',
  `user_id` int DEFAULT NULL COMMENT '用户id',
  `level` tinyint DEFAULT NULL COMMENT '招聘级别 1 系统 2 学校',
  `title` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '标题',
  `position` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '岗位',
  `salary` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '薪资',
  `content` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '内容',
  `contacts` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '联系人',
  `telephone` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '联系电话',
  `email` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '邮箱',
  `auditor_id` tinyint DEFAULT NULL COMMENT '审核人id',
  `authorize_status` tinyint DEFAULT NULL COMMENT '审核状态  0 待审核  1 通过  2 拒绝',
  `status` tinyint DEFAULT NULL COMMENT '招聘状态  0 删除  1 正常',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf16 COMMENT='招聘表';

-- ----------------------------
-- Records of recruit
-- ----------------------------
BEGIN;
INSERT INTO `recruit` VALUES (1, 1, 2, '招聘语文老师', '老师', '5K-10K', '找一个非常Nice的老师', '张三', '1234567890', '123456@163.com', 1, 0, 1, '2022-04-07 14:57:02');
INSERT INTO `recruit` VALUES (2, 2, 2, '招聘数学老师', '老师', '5K-10K', '找一个非常Nice的老师', '李四', '1234567890', '123456@163.com', 1, 0, 1, '2022-04-07 14:57:03');
INSERT INTO `recruit` VALUES (3, 3, 2, '招聘英语老师', '老师', '5K-10K', '找一个非常Nice的老师', '王二', '1234567890', '123456@163.com', 1, 0, 1, '2022-04-07 14:57:04');
INSERT INTO `recruit` VALUES (4, 4, 2, '招聘历史老师', '老师', '5K-10K', '找一个非常Nice的老师', '麻子', '1234567890', '123456@163.com', 1, 0, 1, '2022-04-07 14:57:05');
INSERT INTO `recruit` VALUES (5, 5, 2, '招聘物理老师', '老师', '5K-10K', '找一个非常Nice的老师', '赵五', '1234567890', '123456@163.com', 1, 0, 1, '2022-04-07 14:57:06');
INSERT INTO `recruit` VALUES (6, 1, 1, '招聘管理员', '管理员', '5K-10K', '找一个非常不错的管理员', '管理员', '1234567890', '123456@163.com', 1, 0, 1, '2022-04-07 14:57:07');
INSERT INTO `recruit` VALUES (7, 1, 1, '招聘运营', '运营', '5K-10K', '找一个非常不错的运营', '管理员', '1234567890', '123456@163.com', 1, 0, 1, '2022-04-07 14:57:08');
INSERT INTO `recruit` VALUES (8, 1, 1, '招聘产品', '产品', '5K-10K', '找一个非常不错的产品', '管理员', '1234567890', '123456@163.com', 1, 0, 1, '2022-04-07 14:57:09');
COMMIT;

-- ----------------------------
-- Table structure for school
-- ----------------------------
DROP TABLE IF EXISTS `school`;
CREATE TABLE `school` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'id',
  `user_id` int DEFAULT NULL COMMENT '用户id',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '学校名称',
  `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '学校简介',
  `address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '学校地址',
  `contacts` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '学校联系人',
  `email` varchar(255) DEFAULT NULL COMMENT '学校邮箱',
  `telephone` varchar(255) DEFAULT NULL COMMENT '学校联系电话',
  `authentication` varchar(255) DEFAULT NULL COMMENT '认证说明',
  `images` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '图片列表',
  `auditor_id` tinyint DEFAULT NULL COMMENT '审核人id',
  `authorize_status` tinyint DEFAULT NULL COMMENT '认证状态 0 待审核  1 通过  2 拒绝',
  `status` tinyint DEFAULT NULL COMMENT '状态 0 删除  1 正常',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf16 COMMENT='学校表';

-- ----------------------------
-- Records of school
-- ----------------------------
BEGIN;
INSERT INTO `school` VALUES (1, 2, '坡栏布刊学校', '很穷很穷', '郑州市', '张三', '123456@163.com', '1234567890', '我是一个很贫困的学校', 'images/bayMax.jpg,images/暂无图片.png', 1, 1, 1, '2022-04-07 14:57:02');
INSERT INTO `school` VALUES (2, 3, '第二小学', '很穷很穷', '郑州市', '李四', '123456@163.com', '1234567890', '我是一个很贫困的学校', 'images/bayMax.jpg,images/暂无图片.png', 1, 0, 1, '2022-04-07 14:57:02');
INSERT INTO `school` VALUES (3, 4, '第三小学', '很穷很穷', '郑州市', '王二', '123456@163.com', '1234567890', '我是一个很贫困的学校', 'images/bayMax.jpg,images/暂无图片.png', 1, 0, 1, '2022-04-07 14:57:02');
INSERT INTO `school` VALUES (4, 5, '第四小学', '很穷很穷', '郑州市', '麻子', '123456@163.com', '1234567890', '我是一个很贫困的学校', 'images/bayMax.jpg,images/暂无图片.png', 1, 0, 1, '2022-04-07 14:57:02');
INSERT INTO `school` VALUES (5, 6, '第五小学', '很穷很穷', '郑州市', '赵五', '123456@163.com', '1234567890', '我是一个很贫困的学校', 'images/bayMax.jpg,images/暂无图片.png', 1, 0, 1, '2022-04-07 14:57:02');
COMMIT;

-- ----------------------------
-- Table structure for students
-- ----------------------------
DROP TABLE IF EXISTS `students`;
CREATE TABLE `students` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'id',
  `user_id` int DEFAULT NULL COMMENT '用户id',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '姓名',
  `gender` tinyint DEFAULT NULL COMMENT '性别',
  `telephone` varchar(255) DEFAULT NULL COMMENT '电话',
  `email` varchar(255) DEFAULT NULL COMMENT '邮箱',
  `address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '地址',
  `authentication` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '认证说明',
  `images` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '图片列表',
  `auditor_id` tinyint DEFAULT NULL COMMENT '审核人id',
  `authorize_status` tinyint DEFAULT NULL COMMENT '认证状态 0 待审核  1 通过  2 拒绝',
  `status` tinyint DEFAULT NULL COMMENT '状态 0 删除 1 正常',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf16 COMMENT='学生表';

-- ----------------------------
-- Records of students
-- ----------------------------
BEGIN;
INSERT INTO `students` VALUES (1, 7, '李六', 2, '1234567890', '555555@163.com', '郑州市', '家徒四壁', 'images/bayMax.jpg,images/暂无图片.png', 1, 0, 1, '2022-04-07 14:57:02');
INSERT INTO `students` VALUES (2, 8, '钱一', 1, '1234567890', '666666@163.com', '郑州市', '家徒四壁', 'images/bayMax.jpg,images/暂无图片.png', 1, 0, 1, '2022-04-07 14:57:03');
INSERT INTO `students` VALUES (3, 9, '杨七', 2, '1234567890', '777777@163.com', '郑州市', '家徒四壁', 'images/bayMax.jpg,images/暂无图片.png', 1, 0, 1, '2022-04-07 14:57:04');
INSERT INTO `students` VALUES (4, 10, '徐八', 1, '1234567890', '888888@163.com', '郑州市', '家徒四壁', 'images/bayMax.jpg,images/暂无图片.png', 1, 0, 1, '2022-04-07 14:57:05');
INSERT INTO `students` VALUES (5, 11, '何九', 2, '1234567890', '999999@163.com', '郑州市', '家徒四壁', 'images/bayMax.jpg,images/暂无图片.png', 1, 0, 1, '2022-04-07 14:57:06');
COMMIT;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '登录名',
  `display_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '展示名',
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '密码',
  `role` tinyint DEFAULT NULL COMMENT '用户角色 1 管理员  2 普通用户',
  `gender` tinyint DEFAULT NULL COMMENT '性别  1 男  2 女',
  `telephone` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '手机号',
  `email` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '邮箱',
  `address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '地址',
  `security_question` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '密保问题',
  `security_question_answer` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '密保问题答案',
  `status` tinyint DEFAULT NULL COMMENT '状态 0 删除  1 正常',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf16 COMMENT='用户表';

-- ----------------------------
-- Records of user
-- ----------------------------
BEGIN;
INSERT INTO `user` VALUES (1, 'admin', '管理员', 'admin', 1, 1, '12345678910', 'byfanx@163.com', '郑州市', '1+1=？', '2', 1, '2022-04-07 13:09:14', '2022-04-07 13:09:14');
INSERT INTO `user` VALUES (2, 'zhangsan', '路人甲', '123456', 2, 1, '12345678910', '123456@163.com', '郑州市', '1+1=？', '2', 0, '2022-04-07 14:55:03', '2022-04-07 14:55:03');
INSERT INTO `user` VALUES (3, 'lisi', '路人乙', '123456', 2, 2, '12345678910', '222222@163.com', '郑州市', '1+1=？', '2', 0, '2022-04-07 14:55:04', '2022-04-07 14:55:03');
INSERT INTO `user` VALUES (4, 'wanger', '路人丙', '123456', 2, 1, '12345678910', '333333@163.com', '郑州市', '1+1=？', '2', 0, '2022-04-07 14:55:05', '2022-04-07 14:55:03');
INSERT INTO `user` VALUES (5, 'mazi', '路人丁', '123456', 2, 2, '12345678910', '333333@163.com', '郑州市', '1+1=？', '2', 0, '2022-04-07 14:55:06', '2022-04-07 14:55:03');
INSERT INTO `user` VALUES (6, 'zhaowu', '路人戊', '123456', 2, 1, '12345678910', '444444@163.com', '郑州市', '1+1=？', '2', 0, '2022-04-07 14:55:07', '2022-04-07 14:55:03');
INSERT INTO `user` VALUES (7, 'liliu', '路人己', '123456', 2, 2, '12345678910', '555555@163.com', '郑州市', '1+1=？', '2', 0, '2022-04-07 14:55:08', '2022-04-07 14:55:03');
INSERT INTO `user` VALUES (8, 'qianyi', '路人庚', '123456', 2, 1, '12345678910', '666666@163.com', '郑州市', '1+1=？', '2', 0, '2022-04-07 14:55:09', '2022-04-07 14:55:03');
INSERT INTO `user` VALUES (9, 'yangqi', '路人辛', '123456', 2, 2, '12345678910', '777777@163.com', '郑州市', '1+1=？', '2', 0, '2022-04-07 14:55:10', '2022-04-07 14:55:03');
INSERT INTO `user` VALUES (10, 'xuba', '路人壬', '123456', 2, 1, '12345678910', '888888@163.com', '郑州市', '1+1=？', '2', 0, '2022-04-07 14:55:11', '2022-04-07 14:55:03');
INSERT INTO `user` VALUES (11, 'hejiu', '路人癸', '123456', 2, 2, '12345678910', '999999@163.com', '郑州市', '1+1=？', '2', 0, '2022-04-07 14:55:12', '2022-04-07 14:55:03');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
