/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50619
 Source Host           : localhost:3306
 Source Schema         : miaosha

 Target Server Type    : MySQL
 Target Server Version : 50619
 File Encoding         : 65001

 Date: 22/07/2020 18:30:00
*/
create database miaosha;
use miaosha;
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for goods
-- ----------------------------
DROP TABLE IF EXISTS `goods`;
CREATE TABLE `goods`  (
                          `id` bigint(20) NOT NULL,
                          `goods_name` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                          `goods_title` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                          `goods_img` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                          `goods_detail` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
                          `goods_price` decimal(10, 2) NULL DEFAULT NULL,
                          `goods_stock` int(11) NULL DEFAULT NULL,
                          PRIMARY KEY (`id`) USING BTREE
) ENGINE = MyISAM CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of goods
-- ----------------------------
INSERT INTO `goods` VALUES (1, 'iphoneX', 'AppleIphone ', '/img/iphonex.png', 'Apple iphoneX(asdasdasdad)', 8765.00, 100);
INSERT INTO `goods` VALUES (2, '华为Mate9', '华为 mate9 4G+32', '/img/mata10.png', '华为mate9 4GB', 3212.00, 100);

-- ----------------------------
-- Table structure for miaosha_goods
-- ----------------------------
DROP TABLE IF EXISTS `miaosha_goods`;
CREATE TABLE `miaosha_goods`  (
                                  `id` bigint(20) NOT NULL,
                                  `goods_id` bigint(20) NULL DEFAULT NULL,
                                  `miaosha_price` decimal(10, 2) NULL DEFAULT NULL,
                                  `stock_count` int(11) NULL DEFAULT NULL,
                                  `start_date` datetime(0) NULL DEFAULT NULL,
                                  `end_date` datetime(0) NULL DEFAULT NULL,
                                  PRIMARY KEY (`id`) USING BTREE
) ENGINE = MyISAM CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Fixed;

-- ----------------------------
-- Records of miaosha_goods
-- ----------------------------
INSERT INTO `miaosha_goods` VALUES (1, 1, 0.01, 10, '2020-07-22 18:20:39', '2020-07-22 18:20:44');
INSERT INTO `miaosha_goods` VALUES (2, 2, 0.01, 10, '2020-07-22 18:20:53', '2020-07-22 18:20:56');

-- ----------------------------
-- Table structure for miaosha_order
-- ----------------------------
DROP TABLE IF EXISTS `miaosha_order`;
CREATE TABLE `miaosha_order`  (
                                  `id` bigint(20) NOT NULL,
                                  `user_id` bigint(20) NULL DEFAULT NULL,
                                  `order_id` bigint(20) NULL DEFAULT NULL,
                                  `goods_id` bigint(20) NULL DEFAULT NULL,
                                  PRIMARY KEY (`id`) USING BTREE
) ENGINE = MyISAM CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Fixed;

-- ----------------------------
-- Table structure for miaosha_user
-- ----------------------------
DROP TABLE IF EXISTS `miaosha_user`;
CREATE TABLE `miaosha_user`  (
                                 `id` bigint(20) NOT NULL,
                                 `nickname` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                                 `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                                 `salt` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                                 `head` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                                 `register_date` datetime(0) NULL DEFAULT NULL,
                                 `last_login_date` datetime(0) NULL DEFAULT NULL,
                                 `login_count` int(11) NULL DEFAULT NULL,
                                 PRIMARY KEY (`id`) USING BTREE
) ENGINE = MyISAM CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of miaosha_user
-- ----------------------------
INSERT INTO `miaosha_user` VALUES (13751386481, 'weiqiang', '6e0a7fe692684372437c9e508508990d', '1a2b3c', NULL, '2020-07-22 15:29:44', '2020-07-22 15:29:46', 1);

-- ----------------------------
-- Table structure for order_info
-- ----------------------------
DROP TABLE IF EXISTS `order_info`;
CREATE TABLE `order_info`  (
                               `id` bigint(20) NOT NULL,
                               `user_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                               `goods_id` bigint(20) NULL DEFAULT NULL,
                               `delivery_addr_id` bigint(20) NULL DEFAULT NULL,
                               `goods_name` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                               `goods_count` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                               `goods_price` decimal(10, 2) NULL DEFAULT NULL,
                               `order_channel` tinyint(4) NULL DEFAULT NULL,
                               `status` tinyint(4) NULL DEFAULT NULL,
                               `create_date` datetime(0) NULL DEFAULT NULL,
                               `pay_date` datetime(0) NULL DEFAULT NULL,
                               PRIMARY KEY (`id`) USING BTREE
) ENGINE = MyISAM CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
