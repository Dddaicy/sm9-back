/*
 Navicat Premium Data Transfer

 Source Server         : daicy
 Source Server Type    : MySQL
 Source Server Version : 50733
 Source Host           : localhost:3306
 Source Schema         : sm9

 Target Server Type    : MySQL
 Target Server Version : 50733
 File Encoding         : 65001

 Date: 29/06/2021 15:46:35
*/

SET NAMES utf8mb4;
SET
FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for backup
-- ----------------------------
DROP TABLE IF EXISTS `backup`;
CREATE TABLE `backup`
(
    `id`              int unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
    `backup_time`     timestamp(6) NULL DEFAULT NULL COMMENT '备份时间',
    `backup_user_id`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '操作备份用户',
    `backup_type`     varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备份类型',
    `backup_location` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备份路径',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX             `backup_user_id`(`backup_user_id`) USING BTREE,
    CONSTRAINT `backup_ibfk_1` FOREIGN KEY (`backup_user_id`) REFERENCES `user` (`user_id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of backup
-- ----------------------------

-- ----------------------------
-- Table structure for center_device
-- ----------------------------
DROP TABLE IF EXISTS `center_device`;
CREATE TABLE `center_device`
(
    `center_device_id`            varchar(6) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '中心侧设备id',
    `center_device_ip`            varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '中心侧设备ip',
    `center_device_name`          varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '中心侧设备名称',
    `center_device_number`        int(8) NULL DEFAULT NULL COMMENT '该中心侧设备下的终端数量',
    `center_device_state`         varchar(4) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '中心侧设备运行状态',
    `center_device_key_state`      varchar(4) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '中心测设备密钥状态',
    `center_device_password_state` varchar(4) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '中心侧设备随机密码状态',
    `center_device_password`      varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '中心侧设备随机密码',
    `center_device_description`      varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '中心侧设备描述',
    PRIMARY KEY (`center_device_id`) USING BTREE,
    INDEX                         `center_device_id`(`center_device_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of center_device
-- ----------------------------
INSERT INTO `center_device`
VALUES ('3358', '192.168.1.2', '中心测设备1', 100, 'ok', 'ok', 'ok', '123456', NULL),
       ('3359', '192.168.1.3', '中心测设备2', 10, 'ok', 'ok', 'ok', '123456', NULL);
-- ----------------------------
-- Table structure for terminal_device
-- ----------------------------
DROP TABLE IF EXISTS `terminal_device`;
CREATE TABLE `terminal_device`
(
    `terminal_device_id`               varchar(6) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '终端侧设备id（主键）',
    `terminal_device_ip`               varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '终端设备ip',
    `terminal_device_name`             varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '终端侧设备名称',
    `terminal_device_key_state`         varchar(4) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否接受密钥',
    `terminal_device_description`         varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '设备地点（描述）',
    PRIMARY KEY (`terminal_device_id`) USING BTREE,
    INDEX                              `terminal_device_id`(`terminal_device_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of terminal_device
-- ----------------------------
INSERT INTO `terminal_device`
VALUES ('1', '10.0.0.1', '终端侧设备1', '1', '北京'),
       ('2', '10.0.0.2', '终端侧设备2', '1', '上海'),
       ('3', '10.0.0.3', '终端侧设备3', '1', '天津');
-- ----------------------------
-- Table structure for device_key
-- ----------------------------
DROP TABLE IF EXISTS `device_key`;
CREATE TABLE `device_key`
(
    `id`                            int unsigned AUTO_INCREMENT NOT NULL,
    `terminal_device_public_key`    varchar(6) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '标识（公钥）（主键）',
    `terminal_device_signature_key` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '签名密钥',
    `terminal_device_encrypt_key`   varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '加密密钥',
    `terminal_device_exchange_key`  varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '交换密钥',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX                           `device_key_ibfk_2`(`terminal_device_public_key`) USING BTREE,
    CONSTRAINT `device_key_ibfk_1` FOREIGN KEY (`terminal_device_public_key`) REFERENCES `center_device` (`center_device_id`) ON DELETE SET NULL ON UPDATE CASCADE,
    CONSTRAINT `device_key_ibfk_2` FOREIGN KEY (`terminal_device_public_key`) REFERENCES `terminal_device` (`terminal_device_id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of device_key
-- ----------------------------

-- ----------------------------
-- Table structure for ip_config
-- ----------------------------
DROP TABLE IF EXISTS `ip_config`;
CREATE TABLE `ip_config`
(
    `id`          int unsigned AUTO_INCREMENT NOT NULL COMMENT '主键',
    `ip`          varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '设置ip',
    `subnet_mask` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '设置子网掩码',
    `gateway`     varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '设置默认网关',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of ip_config
-- ----------------------------

-- ----------------------------
-- Table structure for log
-- ----------------------------
DROP TABLE IF EXISTS `log`;
CREATE TABLE `log`
(
    `id`          int unsigned AUTO_INCREMENT NOT NULL COMMENT '主键，自增列',
    `user_id`     varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '生成日志的用户',
    `class_id`    varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '日志信息所属的类目（所在类的全名）',
    `message`     varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '日志信息',
    `log_level`   varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '日志级别',
    `create_time` datetime(0) NULL DEFAULT NULL COMMENT '记录创建时间',
    `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '日志详情',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX         `user_id`(`user_id`) USING BTREE,
    INDEX         `create_time`(`create_time`) USING BTREE,
    INDEX         `log_level`(`log_level`) USING BTREE,
    CONSTRAINT `log_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of log
-- ----------------------------

-- ----------------------------
-- Table structure for permission
-- ----------------------------
DROP TABLE IF EXISTS `permission`;
CREATE TABLE `permission`
(
    `permission_id`       varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '权限id',
    `menu_code`           varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '归属菜单',
    `menu_name`           varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '菜单的中文释义',
    `permission_code`     varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '权限代码/通配符',
    `permission_name`     varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '本权限的中文释义',
    `required_permission` tinyint(1) NULL DEFAULT NULL COMMENT '是否本菜单必选权限，1为必选，2为非必选',
    PRIMARY KEY (`permission_id`) USING BTREE,
    INDEX                 `permission_id`(`permission_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of permission
-- ----------------------------
INSERT INTO `permission`
VALUES (1, 'device', '设备管理', 'device:list', '列表', 1),
       (2, 'device', '设备管理', 'device:add', '新增', 2),
       (3, 'device', '设备管理', 'device:update', '修改', 2),
       (4, 'device', '设备管理', 'device:delete', '删除', 2),
       (5, 'user', '用户管理', 'user:list', '列表', 1),
       (6, 'user', '用户管理', 'user:add', '新增', 2),
       (7, 'user', '用户管理', 'user:update', '修改', 2),
       (8, 'user', '用户管理', 'user:delete', '删除', 2),
       (9, 'permission', '权限管理', 'permission:list', '列表', 1),
       (10, 'permission', '权限管理', 'permission:add', '新增', 2),
       (11, 'permission', '权限管理', 'permission:update', '修改', 2),
       (12, 'permission', '权限管理', 'permission:delete', '删除', 2),
       (13, 'data', '数据管理', 'data:list', '列表', 1),
       (14, 'data', '数据管理', 'data:add', '新增', 2),
       (15, 'data', '数据管理', 'data:update', '修改', 2),
       (16, 'data', '数据管理', 'data:delete', '删除', 2),
       (17, 'network', '网络配置', 'network:list', '列表', 1),
       (18, 'network', '网络配置', 'network:hard', '修改', 2),
       (19, 'monitor', '系统监控', 'monitor:hardware', '硬件资源占用监控', 2),
       (20, 'monitor', '系统监控', 'monitor:software', '软件系统的启动和监测', 2),
       (21, 'monitor', '系统监控', 'monitor:online', '在线用户监控', 2),
       (22, 'log', '日志管理', 'log:list', '列表', 1),
       (23, 'log', '日志管理', 'log:delete', '删除', 2);
-- ----------------------------
-- Table structure for relation
-- ----------------------------
DROP TABLE IF EXISTS `relation`;
CREATE TABLE `relation`
(
    `id`                 int unsigned AUTO_INCREMENT NOT NULL,
    `terminal_device_id` varchar(6) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '终端设备id',
    `center_device_id`   varchar(6) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '中心设备id',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX                `terminal_device_id`(`terminal_device_id`) USING BTREE,
    INDEX                `center_device_id`(`center_device_id`) USING BTREE,
    CONSTRAINT `relation_ibfk_1` FOREIGN KEY (`terminal_device_id`) REFERENCES `terminal_device` (`terminal_device_id`) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT `relation_ibfk_2` FOREIGN KEY (`center_device_id`) REFERENCES `center_device` (`center_device_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of relation
-- ----------------------------
INSERT INTO relation
VALUES (0, '1','3358'),
       (0, '2', '3359'),
       (0, '3', '3359');
-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role`
(
    `role_id`          varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '角色id',
    `role_name`        varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '角色名称',
    `role_description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '角色描述',
    PRIMARY KEY (`role_id`) USING BTREE,
    INDEX              `role_id`(`role_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role`
VALUES ('1', '管理员', '管理员');
INSERT INTO `role`
VALUES ('2', '普通用户', '普通用户');
INSERT INTO `role`
VALUES ('3', '超级管理员', '超级管理员');

-- ----------------------------
-- Table structure for role_permission
-- ----------------------------
DROP TABLE IF EXISTS `role_permission`;
CREATE TABLE `role_permission`
(
    `id`            int unsigned AUTO_INCREMENT NOT NULL,
    `role_id`       varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '角色id',
    `permission_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '权限id',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX           `role_id`(`role_id`) USING BTREE,
    INDEX           `permission_id`(`permission_id`) USING BTREE,
    CONSTRAINT `role_permission_ibfk_1` FOREIGN KEY (`role_id`) REFERENCES `role` (`role_id`) ON DELETE SET NULL ON UPDATE CASCADE,
    CONSTRAINT `role_permission_ibfk_2` FOREIGN KEY (`permission_id`) REFERENCES `permission` (`permission_id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of role_permission
-- ----------------------------
INSERT INTO `role_permission`
VALUES (0, '2', '1'),
       (0, '2', '17'),
       (0, '2', '19');
-- ----------------------------
-- Table structure for service_platform_info
-- ----------------------------
DROP TABLE IF EXISTS `service_platform_info`;
CREATE TABLE `service_platform_info`
(
    `id`                           int unsigned AUTO_INCREMENT NOT NULL COMMENT '主键',
    `signature_master_private_key` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '签名主私钥',
    `encrypt_master_private_key`   varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '加密主私钥',
    `exchange_master_private_key`  varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '交换主私钥',
    `signature_master_public_key`  varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '签名主公钥',
    `encrypt_master_public_key`    varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '加密主公钥',
    `exchange_master_public_key`   varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '交换主公钥',
    `platform_public_key`          varchar(6) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '平台公钥',
    `platform_signature_key`       varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '平台签名密钥',
    `platform_encrypt_key`         varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '平台加密密钥',
    `platform_exchange_key`        varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '平台交换密钥',
    `is_valid`                      varchar(2) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '当前有效',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of service_platform_info
-- ----------------------------

-- ----------------------------
-- Table structure for ukey_info
-- ----------------------------
DROP TABLE IF EXISTS `ukey_info`;
CREATE TABLE `ukey_info`
(
    `id`                      int unsigned AUTO_INCREMENT NOT NULL,
    `ukey_id`                 varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'UKeyid',
    `ukey_center_device_id`   varchar(6) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '该UKey所属的中心侧设备id',
    `ukey_terminal_device_id` varchar(6) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '该UKey所属的终端侧设备id',
    `ukey_modify_time`         timestamp(6) NULL DEFAULT NULL COMMENT 'UKey最后发送时间',
    `ukey_times`              varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'UKey发送次数',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX                     `ukey_center_device_id`(`ukey_center_device_id`) USING BTREE,
    INDEX                     `ukey_terminal_device_id`(`ukey_terminal_device_id`) USING BTREE,
    CONSTRAINT `ukey_info_ibfk_1` FOREIGN KEY (`ukey_center_device_id`) REFERENCES `center_device` (`center_device_id`) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT `ukey_info_ibfk_2` FOREIGN KEY (`ukey_terminal_device_id`) REFERENCES `terminal_device` (`terminal_device_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of ukey_info
-- ----------------------------

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`
(
    `user_id`          varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户id',
    `username`         varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户姓名',
    `password`         varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户密码',
    `user_unit`        varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户所属部门',
    `user_email`       varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户邮箱',
    `user_phone_number` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户手机号',
    `user_state`       varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '1' COMMENT '用户状态（是否启用）',
    `user_role_id`     varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户角色',
    PRIMARY KEY (`user_id`) USING BTREE,
    INDEX              `user_id`(`user_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user`
VALUES ('1', 'zrh', '123456', NULL, NULL, NULL, '1', NULL);

-- ----------------------------
-- Table structure for user_role
-- ----------------------------
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role`
(
    `id`      int unsigned AUTO_INCREMENT NOT NULL,
    `user_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户id',
    `role_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '角色id',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX     `user_id`(`user_id`) USING BTREE,
    INDEX     `role_id`(`role_id`) USING BTREE,
    CONSTRAINT `user_role_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE SET NULL ON UPDATE CASCADE,
    CONSTRAINT `user_role_ibfk_2` FOREIGN KEY (`role_id`) REFERENCES `role` (`role_id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_role
-- ----------------------------
INSERT INTO `user_role`
VALUES (0, '1', '1');

SET
FOREIGN_KEY_CHECKS = 1;
