/*
 Navicat Premium Data Transfer

 Source Server         : 本机(56)
 Source Server Type    : MySQL
 Source Server Version : 50730
 Source Host           : localhost:3306
 Source Schema         : gateway_route

 Target Server Type    : MySQL
 Target Server Version : 50730
 File Encoding         : 65001

 Date: 29/09/2020 15:04:44
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for gateway_route
-- ----------------------------
DROP TABLE IF EXISTS `gateway_route`;
CREATE TABLE `gateway_route`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `route_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '路由id',
  `uri` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '真实路径',
  `type` int(11) NOT NULL COMMENT '指向路径地址类型,url类型 0:注册中心服务 1:其他服务',
  `order` int(11) NULL DEFAULT NULL COMMENT '优先级',
  `path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '对外路径',
  `strip_prefix` int(11) NULL DEFAULT NULL COMMENT '是否忽略前缀',
  `predicates` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '路由断言信息',
  `filters` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '路由过滤器信息',
  `enable` int(11) NOT NULL DEFAULT 1 COMMENT '是否启用',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of gateway_route
-- ----------------------------
INSERT INTO `gateway_route` VALUES (1, 'nacos-config', 'demo-nacos-config-share', 0, 2, 'zy-nacos-config', 0, NULL, NULL, 1, '2020-09-28 16:59:42', '2020-09-28 17:00:49');

SET FOREIGN_KEY_CHECKS = 1;
