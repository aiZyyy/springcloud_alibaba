/*
 Navicat Premium Data Transfer

 Source Server         : 本机(56)
 Source Server Type    : MySQL
 Source Server Version : 50730
 Source Host           : localhost:3306
 Source Schema         : snowflake

 Target Server Type    : MySQL
 Target Server Version : 50730
 File Encoding         : 65001

 Date: 28/09/2020 16:59:10
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for common_keys
-- ----------------------------
DROP TABLE IF EXISTS `common_keys`;
CREATE TABLE `common_keys`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `key_name` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  `key_value` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  `unique_key` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  `deleted_at` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of common_keys
-- ----------------------------
INSERT INTO `common_keys` VALUES (1, 'zy|123', '232766072405757952', 'a16067d8eff935c10d3dd1346ffd6f86', '2020-09-28 16:58:32', '2020-09-28 16:58:32', NULL);
INSERT INTO `common_keys` VALUES (2, 'zyy|123456', '235275654130700288', '37be716415986024469a433943927820', '2020-09-28 16:58:32', '2020-09-28 16:58:32', NULL);

SET FOREIGN_KEY_CHECKS = 1;
