/*
 Navicat Premium Data Transfer

 Source Server         : 本机(56)
 Source Server Type    : MySQL
 Source Server Version : 50730
 Source Host           : localhost:3306
 Source Schema         : kits_market

 Target Server Type    : MySQL
 Target Server Version : 50730
 File Encoding         : 65001

 Date: 29/09/2020 15:04:33
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for app_info
-- ----------------------------
DROP TABLE IF EXISTS `app_info`;
CREATE TABLE `app_info`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `app_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `app_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '应用名称',
  `app_private_key` varchar(1000) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL COMMENT 'app申请私钥',
  `app_public_key` varchar(1000) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL COMMENT 'app申请公钥',
  `app_start_time` datetime(0) NULL DEFAULT NULL COMMENT '应用服务开始时间',
  `app_end_time` datetime(0) NULL DEFAULT NULL COMMENT '应用服务结束时间',
  `company_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '应用对应的公司名称',
  `created_at` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `updated_at` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  `deleted_at` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of app_info
-- ----------------------------
INSERT INTO `app_info` VALUES (1, 'app235622944347394048', 'zy测试123123', 'MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAIiTBrybn79cwSCqI+Y9QmbIG1gdyvi/5q7e2mKAfd5uI9f7QvURtPYbfyVHqJ1LelX4NprUPB+yx4yXn9EAM/7kFe40C64hAd9yfUpDI8mj430Md6Gmvr8yReJqYThgyVlJ+hyiOlGIN7OK77Ncq5sgtjwYU9LRQEJP4C5cISoFAgMBAAECgYBiCll9r6Hft3AUSM74iuVN7zxh5xE7vFaXFfdjWqYAtMQaWY7ZalqjiV2en3BVakdpy4M3Zj+66ZYCQR5C5Iht1ne0mEt03b3TW9afcACXoS6CG98T12IZB/1T80NC2FvFUo+xL402P+Pky+y0RFn8N5QTpw2Zs+f+UW0fjVhvAQJBAMpNTWJQ2VKCmu0164XASuwt8k6enCXmrkca0FdxlfzVYKDVhGqRftoObWxo6f42sAEx6oAsOgDmQ9mPNLaKHaECQQCs03CpgY8NGmMwzijYDT/epcxUxvJK19MMaeuIv5ty/WtjGwTa9MmCx+wKu61ApZsSBTiDi+EcsntvNnhHSgnlAkAL79HAGlsoZjJ0Crlx8+23z+I7R1qZVeo0od1nO3mJKKIB+3dLvngIr88/FVWxPVLXTzWyCGoHwY6BWNTYbH/hAkAqmsU/RC0bsdGaCniPKlUAcib5D0JTqPjiKoph37pWkpqSh+qef5Hdp6NM0g9XzHt56ceQxj7scCN1uxvTlzDxAkEAl5737LHypAcsGJzzFYdZYhXXF6AzqDHBpE3YdWnL/w1qFVa0oJIJAR1IW1reyV0nlXqHKbbvkGGJwHOPZfXQ3w==', 'MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCIkwa8m5+/XMEgqiPmPUJmyBtYHcr4v+au3tpigH3ebiPX+0L1EbT2G38lR6idS3pV+Daa1DwfsseMl5/RADP+5BXuNAuuIQHfcn1KQyPJo+N9DHehpr6/MkXiamE4YMlZSfocojpRiDeziu+zXKubILY8GFPS0UBCT+AuXCEqBQIDAQAB', '2020-09-29 14:54:25', NULL, '112311', '2020-09-29 14:54:24', '2020-09-29 14:54:24', NULL);

SET FOREIGN_KEY_CHECKS = 1;
