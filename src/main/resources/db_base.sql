/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50725
 Source Host           : localhost:3306
 Source Schema         : db_base

 Target Server Type    : MySQL
 Target Server Version : 50725
 File Encoding         : 65001

 Date: 27/01/2021 15:50:30
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu` (
  `id` bigint(10) unsigned NOT NULL AUTO_INCREMENT,
  `parent_id` bigint(10) unsigned NOT NULL DEFAULT '0' COMMENT '父菜单id',
  `menu_name` varchar(255) NOT NULL COMMENT '菜单名称',
  `type` tinyint(4) NOT NULL DEFAULT '0' COMMENT '0-菜单组，1-功能菜单；菜单组下没有可访问功能菜单时不显示',
  `description` varchar(255) DEFAULT NULL COMMENT '菜单功能描述',
  `path` varchar(255) DEFAULT NULL COMMENT '菜单路径',
  `extra_path` varchar(255) DEFAULT NULL COMMENT '额外赋予权限的路径（部分功能需要切换页面）',
  `menu_icon` varchar(255) DEFAULT NULL COMMENT '在菜单中显示的图标（对非菜单功能无效）',
  `new_window` tinyint(1) DEFAULT '0' COMMENT '是否弹出新窗口',
  `sort` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '排序顺序',
  `gmt_create` datetime DEFAULT CURRENT_TIMESTAMP,
  `gmt_modified` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='菜单表\r\n\r\n\r\n';

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
BEGIN;
INSERT INTO `sys_menu` VALUES (1, 0, '首页', 1, '首页', '/', '', 'el-icon-s-home', 0, 1, '2020-10-14 16:41:34', '2021-01-26 11:14:01');
INSERT INTO `sys_menu` VALUES (2, 0, '系统管理', 0, '系统管理', '', '', '', 0, 3, '2020-10-14 17:10:14', '2021-01-26 11:14:11');
INSERT INTO `sys_menu` VALUES (3, 2, '用户管理', 1, '用户管理', '/system/userAdmin', '', '', 0, 1, '2020-10-15 08:49:21', '2021-01-26 11:14:33');
INSERT INTO `sys_menu` VALUES (4, 2, '角色管理', 1, '角色管理', '/system/roleAdmin', '', '', 0, 2, '2020-10-15 08:49:47', '2021-01-26 11:14:35');
INSERT INTO `sys_menu` VALUES (5, 2, '部门管理', 1, '部门管理', '/system/departAdmin', '', '', 0, 3, '2020-10-15 08:54:21', '2021-01-26 11:14:36');
INSERT INTO `sys_menu` VALUES (6, 2, '菜单管理', 1, '菜单管理', '/system/menuAdmin', '', '', 0, 4, '2020-10-15 08:54:44', '2021-01-26 11:14:38');
INSERT INTO `sys_menu` VALUES (7, 2, '权限管理', 1, '权限管理', '/system/permissionAdmin', '', '', 0, 5, '2020-10-15 08:55:05', '2021-01-26 11:14:40');
INSERT INTO `sys_menu` VALUES (8, 0, '示例', 0, '', '', '', '', 0, 2, '2021-01-15 08:33:00', '2021-01-26 11:14:19');
INSERT INTO `sys_menu` VALUES (9, 8, '参数与返回值加密', 1, '', '/demo/encrypt', '', '', 0, 1, '2021-01-15 08:33:48', '2021-01-26 11:14:56');
INSERT INTO `sys_menu` VALUES (10, 8, '文件上传', 1, '', '/demo/fileUpload', '', '', 0, 2, '2021-01-19 09:51:13', '2021-01-26 11:14:58');
COMMIT;

-- ----------------------------
-- Table structure for sys_menu_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu_permission`;
CREATE TABLE `sys_menu_permission` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `menu_id` bigint(20) unsigned NOT NULL,
  `permission_id` bigint(20) unsigned NOT NULL,
  `gmt_create` datetime DEFAULT CURRENT_TIMESTAMP,
  `gmt_modified` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `uk_menu_permission` (`menu_id`,`permission_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of sys_menu_permission
-- ----------------------------
BEGIN;
INSERT INTO `sys_menu_permission` VALUES (1, 3, 2, '2020-10-19 09:55:48', '2021-01-26 11:19:49');
INSERT INTO `sys_menu_permission` VALUES (2, 3, 4, '2020-10-19 09:55:48', '2021-01-26 11:19:51');
INSERT INTO `sys_menu_permission` VALUES (3, 3, 5, '2020-10-20 09:02:53', '2021-01-26 11:19:52');
INSERT INTO `sys_menu_permission` VALUES (4, 3, 6, '2021-01-27 14:01:26', '2021-01-27 14:01:26');
COMMIT;

-- ----------------------------
-- Table structure for sys_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_permission`;
CREATE TABLE `sys_permission` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `parent_id` bigint(20) unsigned NOT NULL DEFAULT '0',
  `type` tinyint(4) NOT NULL DEFAULT '0' COMMENT '0-权限组，1-权限',
  `permission_name` varchar(255) NOT NULL COMMENT '接口权限名称',
  `path` varchar(255) NOT NULL COMMENT '接口路径',
  `method` varchar(255) NOT NULL COMMENT 'GET，POST，PUT，DELETE等',
  `sort` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '排序顺序',
  `gmt_create` datetime DEFAULT CURRENT_TIMESTAMP,
  `gmt_modified` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of sys_permission
-- ----------------------------
BEGIN;
INSERT INTO `sys_permission` VALUES (1, 0, 0, '系统管理', '', '', 0, '2020-10-16 16:59:24', '2021-01-26 11:16:47');
INSERT INTO `sys_permission` VALUES (2, 3, 1, '新增用户', '/users', 'POST', 1, '2020-10-16 17:03:20', '2021-01-26 11:19:17');
INSERT INTO `sys_permission` VALUES (3, 0, 0, '用户', '', '', 1, '2020-10-16 17:03:31', '2021-01-26 11:19:24');
INSERT INTO `sys_permission` VALUES (4, 3, 1, '修改用户', '/users/*', 'PUT', 2, '2020-10-16 17:04:11', '2021-01-26 11:19:18');
INSERT INTO `sys_permission` VALUES (5, 3, 1, '删除用户', '/users', 'DELETE', 3, '2020-10-16 17:05:38', '2021-01-26 11:19:20');
INSERT INTO `sys_permission` VALUES (6, 0, 1, '查询用户', '/api/v1/users', 'GET', 4, '2021-01-27 14:01:05', '2021-01-27 14:30:50');
COMMIT;

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `role_name` varchar(255) NOT NULL,
  `role_name_zh` varchar(255) NOT NULL,
  `gmt_create` datetime DEFAULT CURRENT_TIMESTAMP,
  `gmt_modified` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=760526706111279105 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='角色表';

-- ----------------------------
-- Records of sys_role
-- ----------------------------
BEGIN;
INSERT INTO `sys_role` VALUES (1, 'ROLE_SUPER_ADMIN', '系统管理员', '2020-09-29 15:41:36', '2021-01-26 11:10:53');
COMMIT;

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `role_id` bigint(20) unsigned DEFAULT NULL,
  `menu_id` bigint(20) unsigned DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `uk_role_menu` (`role_id`,`menu_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of sys_role_menu
-- ----------------------------
BEGIN;
INSERT INTO `sys_role_menu` VALUES (1, 1, 1);
INSERT INTO `sys_role_menu` VALUES (2, 1, 2);
INSERT INTO `sys_role_menu` VALUES (3, 1, 3);
INSERT INTO `sys_role_menu` VALUES (4, 1, 4);
INSERT INTO `sys_role_menu` VALUES (5, 1, 5);
INSERT INTO `sys_role_menu` VALUES (6, 1, 6);
INSERT INTO `sys_role_menu` VALUES (7, 1, 7);
INSERT INTO `sys_role_menu` VALUES (8, 1, 8);
COMMIT;

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `username` varchar(255) NOT NULL COMMENT '账号名',
  `nickname` varchar(255) NOT NULL COMMENT '用户姓名',
  `password` varchar(255) NOT NULL COMMENT '密码',
  `state` tinyint(4) unsigned NOT NULL DEFAULT '0' COMMENT '状态，0-可用，1-禁用',
  `delete_at` bigint(20) NOT NULL DEFAULT '0' COMMENT '删除标志位，0-未删除，非0时为删除时间的毫秒数',
  `gmt_create` datetime DEFAULT CURRENT_TIMESTAMP,
  `gmt_modified` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `uk_username` (`username`,`delete_at`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='用户表';

-- ----------------------------
-- Records of sys_user
-- ----------------------------
BEGIN;
INSERT INTO `sys_user` VALUES (1, 'admin', '管理员', '$2a$10$VFx9IgNCFoNtDzK9MlqRV.bX0PhBZZi5Dsq2FzHNbKGQrcL7tb7mW', 0, 0, '2020-09-28 15:06:07', '2021-01-26 11:20:32');
COMMIT;

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) unsigned NOT NULL,
  `role_id` bigint(20) unsigned NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `uk_user_role` (`user_id`,`role_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='用户与角色关联表';

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
BEGIN;
INSERT INTO `sys_user_role` VALUES (1, 1, 1);
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
