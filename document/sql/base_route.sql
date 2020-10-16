/*
 Navicat Premium Data Transfer

 Source Server         : 本机(56)
 Source Server Type    : MySQL
 Source Server Version : 50730
 Source Host           : localhost:3306
 Source Schema         : base_route

 Target Server Type    : MySQL
 Target Server Version : 50730
 File Encoding         : 65001

 Date: 16/10/2020 14:15:34
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for base_route
-- ----------------------------
DROP TABLE IF EXISTS `base_route`;
CREATE TABLE `base_route`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `route_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '路由id',
  `uri` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '真实路径',
  `type` int(11) NOT NULL COMMENT '指向路径地址类型,url类型 0:注册中心服务 1:其他服务',
  `order` int(11) NULL DEFAULT NULL COMMENT '优先级',
  `path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '对外路径',
  `strip_prefix` int(11) NULL DEFAULT NULL COMMENT '是否忽略前缀',
  `predicates` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '路由断言信息',
  `filters` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '路由过滤器信息',
  `enable` int(11) NOT NULL DEFAULT 0 COMMENT '是否启用(0:未启用,1:启用)',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of base_route
-- ----------------------------
INSERT INTO `base_route` VALUES (1, 'demo_oauth', 'demo-oauth-service', 0, 1, '/demo/auth/**', 2, NULL, NULL, 1, '2020-10-16 10:45:19', '2020-10-16 10:45:19');
INSERT INTO `base_route` VALUES (2, 'base_oauth', 'base-oauth-service', 0, 1, '/auth/**', 1, NULL, NULL, 1, '2020-10-16 10:47:31', '2020-10-16 11:44:39');

-- ----------------------------
-- Table structure for skip_route
-- ----------------------------
DROP TABLE IF EXISTS `skip_route`;
CREATE TABLE `skip_route`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '跳过认证路径名称',
  `url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '跳过认证路径',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of skip_route
-- ----------------------------
INSERT INTO `skip_route` VALUES (1, 'gateway内部路径', '/actuator/**', NULL);
INSERT INTO `skip_route` VALUES (2, 'token生成路径', '/auth/oauth/token', NULL);

SET FOREIGN_KEY_CHECKS = 1;
