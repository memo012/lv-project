/*
 Navicat Premium Data Transfer

 Source Server         : MySql
 Source Server Type    : MySQL
 Source Server Version : 50728
 Source Host           : localhost:3306
 Source Schema         : lv

 Target Server Type    : MySQL
 Target Server Version : 50728
 File Encoding         : 65001

 Date: 29/10/2019 20:51:49
*/
use lv;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for lv_check
-- ----------------------------
DROP TABLE IF EXISTS `lv_check`;
CREATE TABLE `lv_check`  (
  `lv_check_id` varchar(30) CHARACTER SET  'utf8'  NOT NULL COMMENT '唯一标识符',
  `lv_teacher_num` varchar(12) CHARACTER SET  'utf8'  NOT NULL COMMENT '老师工号',
  `lv_teacher_name` varchar(9) CHARACTER SET  'utf8'  NOT NULL COMMENT '老师姓名',
  `lv_user_num` varchar(12) CHARACTER SET  'utf8'  NOT NULL COMMENT '学生学号',
  `lv_check_pass` int(1) NOT NULL COMMENT '是否审核通过(1 -成功 0 -失败)',
  `lv_check_reason` varchar(255) CHARACTER SET  'utf8'  NOT NULL COMMENT '审批批注',
  `lv_id` varchar(30) CHARACTER SET  'utf8'  NOT NULL COMMENT '请假ID',
  `lv_check_create_time` varchar(20) CHARACTER SET  'utf8'  NOT NULL COMMENT '审核时间',
  PRIMARY KEY (`lv_check_id`) USING BTREE
) ENGINE = InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for lv_leave
-- ----------------------------
DROP TABLE IF EXISTS `lv_leave`;
CREATE TABLE `lv_leave`  (
  `lv_id` varchar(30) CHARACTER SET  'utf8'  NOT NULL COMMENT '请假ID',
  `lv_relative_type` varchar(10) CHARACTER SET  'utf8'  NOT NULL COMMENT '亲属关系',
  `lv_relative_name` varchar(9) CHARACTER SET  'utf8'  NOT NULL COMMENT '亲属姓名',
  `lv_relative_phone` char(11) CHARACTER SET  'utf8'  NOT NULL COMMENT '亲属电话',
  `lv_begin_time` varchar(20) CHARACTER SET  'utf8'  NOT NULL COMMENT '请假开始时间',
  `lv_day` int(3) NOT NULL COMMENT '请假天数',
  `lv_end_time` varchar(20) CHARACTER SET  'utf8'  NOT NULL COMMENT '请假截止时间',
  `lv_reason` varchar(100) CHARACTER SET  'utf8'  NOT NULL COMMENT '请假原因（病假，事假，比赛，活动，其他）',
  `lv_progress` int(1) NOT NULL COMMENT '请假进度（0 - 未通过 1 - 已通过）',
  `lv_picture` varchar(255) CHARACTER SET  'utf8'  NOT NULL COMMENT '上传附件',
  `lv_create_time` varchar(20) CHARACTER SET  'utf8'  NOT NULL COMMENT '请假创建时间',
  PRIMARY KEY (`lv_id`) USING BTREE
) ENGINE = InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for lv_teacher
-- ----------------------------
DROP TABLE IF EXISTS `lv_teacher`;
CREATE TABLE `lv_teacher`  (
  `lv_teacher_id` varchar(30) CHARACTER SET  'utf8'  NOT NULL COMMENT '唯一标识符',
  `lv_teacher_name` varchar(9) CHARACTER SET  'utf8'  NOT NULL COMMENT '老师姓名',
  `lv_teacher_num` varchar(12) CHARACTER SET  'utf8'  NOT NULL COMMENT '老师工号',
  `lv_teacher_password` varchar(20) CHARACTER SET  'utf8'  NOT NULL COMMENT '老师密码',
  `lv_teacher_phone` char(11) CHARACTER SET  'utf8'  NOT NULL COMMENT '老师手机号',
  `lv_teacher_location` varchar(255) CHARACTER SET  'utf8'  NOT NULL COMMENT '老师办公室地址',
  `lv_teacher_work_time` varchar(255) CHARACTER SET  'utf8'  NOT NULL COMMENT '老师工作时间',
  `lv_teacher_create_time` varchar(20) CHARACTER SET  'utf8'  NOT NULL COMMENT '老师创建时间',
  PRIMARY KEY (`lv_teacher_id`) USING BTREE
) ENGINE = InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for lv_user
-- ----------------------------
DROP TABLE IF EXISTS `lv_user`;
CREATE TABLE `lv_user`  (
  `lv_user_id` varchar(30) CHARACTER SET  'utf8'  NOT NULL COMMENT '唯一标识符',
  `lv_user_num` varchar(12) CHARACTER SET  'utf8'  NOT NULL COMMENT '学生学号',
  `lv_user_password` varchar(17) CHARACTER SET  'utf8'  NOT NULL COMMENT '学生密码',
  `lv_user_name` varchar(9) CHARACTER SET  'utf8'  NOT NULL COMMENT '学生姓名',
  `lv_user_phone` char(11) CHARACTER SET  'utf8'  NOT NULL COMMENT '学生手机号',
  `lv_teachaer_name` varchar(9) CHARACTER SET  'utf8'  NOT NULL,
  `lv_user_createtime` varchar(20) CHARACTER SET  'utf8'  NOT NULL COMMENT '学生创建时间',
  PRIMARY KEY (`lv_user_id`, `lv_user_num`) USING BTREE
) ENGINE = InnoDB DEFAULT CHARSET=utf8;

SET FOREIGN_KEY_CHECKS = 1;
