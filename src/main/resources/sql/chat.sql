/*
 Navicat Premium Data Transfer

 Source Server         : 192.168.85.123
 Source Server Type    : MySQL
 Source Server Version : 80011
 Source Host           : 192.168.85.123:3306
 Source Schema         : chat

 Target Server Type    : MySQL
 Target Server Version : 80011
 File Encoding         : 65001

 Date: 13/05/2020 20:40:13
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `user_id` int(10) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户名',
  `passport` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '密码',
  `pic` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '图片',
  PRIMARY KEY (`user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic STATS_AUTO_RECALC = 1;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, 'admin', '123qwe', '/img/avatar/u1.jpg');
INSERT INTO `user` VALUES (2, 'test', '123qwe', '/img/avatar/u2.jpg');
INSERT INTO `user` VALUES (3, 'admin3', '123qwe', '/img/avatar/Member003.jpg');
INSERT INTO `user` VALUES (6, 'he', '123qwe', '/img/avatar/1260310299123257344.jpg');

-- ----------------------------
-- Table structure for user_friend
-- ----------------------------
DROP TABLE IF EXISTS `user_friend`;
CREATE TABLE `user_friend`  (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `user_id` int(10) NULL DEFAULT NULL COMMENT '用户id',
  `category` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '类别',
  `friend_id` int(10) NULL DEFAULT NULL COMMENT '好友用户id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_friend
-- ----------------------------
INSERT INTO `user_friend` VALUES (1, 1, '我的好友', 2);
INSERT INTO `user_friend` VALUES (2, 1, '我的好友', 3);
INSERT INTO `user_friend` VALUES (3, 2, '我的好友', 1);
INSERT INTO `user_friend` VALUES (4, 3, '我的好友', 1);
INSERT INTO `user_friend` VALUES (5, 6, '我的好友', 1);
INSERT INTO `user_friend` VALUES (6, 1, '我的好友', 6);

-- ----------------------------
-- Table structure for user_group
-- ----------------------------
DROP TABLE IF EXISTS `user_group`;
CREATE TABLE `user_group`  (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '分组id',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '分组名称',
  `user_id` int(10) NULL DEFAULT NULL COMMENT '分组创建人',
  `pic` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '分组图像',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_group
-- ----------------------------
INSERT INTO `user_group` VALUES (1, 'my群', 1, '/img/avatar/Group01.jpg');
INSERT INTO `user_group` VALUES (2, 'my群2', 1, '/img/avatar/Group02.jpg');

-- ----------------------------
-- Table structure for user_group_re
-- ----------------------------
DROP TABLE IF EXISTS `user_group_re`;
CREATE TABLE `user_group_re`  (
  `id` int(10) NOT NULL,
  `group_id` int(10) NULL DEFAULT NULL,
  `user_id` int(10) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_group_re
-- ----------------------------
INSERT INTO `user_group_re` VALUES (1, 1, 1);
INSERT INTO `user_group_re` VALUES (2, 1, 2);
INSERT INTO `user_group_re` VALUES (3, 2, 1);
INSERT INTO `user_group_re` VALUES (4, 2, 3);

SET FOREIGN_KEY_CHECKS = 1;
