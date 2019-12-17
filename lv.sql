-- MySQL dump 10.13  Distrib 5.7.28, for Linux (x86_64)
--
-- Host: localhost    Database: leave
-- ------------------------------------------------------
-- Server version	5.7.28-0ubuntu0.18.04.4

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `lv_check`
--

DROP TABLE IF EXISTS `lv_check`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `lv_check` (
  `lv_check_id` varchar(30) NOT NULL COMMENT '唯一标识符',
  `lv_teacher_num` varchar(12) NOT NULL COMMENT '老师工号',
  `lv_user_num` varchar(12) NOT NULL COMMENT '学生学号',
  `lv_check_pass` int(1) NOT NULL COMMENT '是否审核通过(1 -成功 0 -失败)',
  `lv_check_reason` varchar(255) NOT NULL COMMENT '审批批注',
  `lv_id` varchar(30) NOT NULL COMMENT '请假ID',
  `lv_check_create_time` varchar(20) NOT NULL COMMENT '审核时间',
  PRIMARY KEY (`lv_check_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `lv_check`
--

LOCK TABLES `lv_check` WRITE;
/*!40000 ALTER TABLE `lv_check` DISABLE KEYS */;
/*!40000 ALTER TABLE `lv_check` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `lv_leave`
--

DROP TABLE IF EXISTS `lv_leave`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `lv_leave` (
  `lv_id` varchar(30) NOT NULL COMMENT '请假ID',
  `lv_relative_type` varchar(10) NOT NULL COMMENT '亲属关系',
  `lv_relative_name` varchar(9) NOT NULL COMMENT '亲属姓名',
  `lv_relative_phone` char(11) NOT NULL COMMENT '亲属电话',
  `lv_begin_time` varchar(20) NOT NULL COMMENT '请假开始时间',
  `lv_apply_detail` varchar(15) DEFAULT NULL COMMENT '请假天数',
  `lv_end_time` varchar(20) NOT NULL COMMENT '请假截止时间',
  `lv_reason` varchar(100) NOT NULL COMMENT '请假原因（病假，事假，比赛，活动，其他）',
  `lv_status` varchar(15) DEFAULT NULL COMMENT '请假进度(reject - 未通过 ing - 审批中 pass - 已通过）',
  `lv_picture` varchar(255) DEFAULT NULL COMMENT '上传附件',
  `lv_create_time` varchar(20) NOT NULL COMMENT '请假创建时间',
  `lv_user_num` varchar(12) NOT NULL COMMENT '学生学号',
  `lv_process_instance_Id` varchar(30) DEFAULT NULL COMMENT '流程定义id',
  `lv_end_time_long` varchar(45) DEFAULT NULL COMMENT '审批结束时间',
  `lv_length` varchar(45) DEFAULT NULL COMMENT '请假时长',
  PRIMARY KEY (`lv_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `lv_leave`
--

LOCK TABLES `lv_leave` WRITE;
/*!40000 ALTER TABLE `lv_leave` DISABLE KEYS */;
INSERT INTO `lv_leave` VALUES ('1574776244','父亲','雷庆','15536868609','2019-11-28 00:03:00',NULL,'2019-11-27 00:02:00','病假','pass',NULL,'2019-11-26','1713011332','122501','1574784120','一天以内'),('1574777761','父亲','大头','12345678942','2019-12-26 00:02:00',NULL,'2019-11-28 00:02:00','比赛','reject',NULL,'2019-11-26','1713011332','125006','1574870520','一天以上一周以内'),('1574835519','父亲','你猜','15383466853','2019-11-16 17:06:50',NULL,'2019-11-25 17:06:50','生病','ing',NULL,'2019-11-27','1713011332','127501','1574672810','一天以上一周以下');
/*!40000 ALTER TABLE `lv_leave` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `lv_permission`
--

DROP TABLE IF EXISTS `lv_permission`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `lv_permission` (
  `pid` int(2) NOT NULL COMMENT '权限id',
  `lv_pname` varchar(45) COLLATE utf8_unicode_ci NOT NULL COMMENT '权限名称',
  `lv_url` varchar(45) CHARACTER SET utf8 NOT NULL COMMENT '权限路径',
  PRIMARY KEY (`pid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `lv_permission`
--

LOCK TABLES `lv_permission` WRITE;
/*!40000 ALTER TABLE `lv_permission` DISABLE KEYS */;
INSERT INTO `lv_permission` VALUES (1,'学生','lv:student'),(2,'普通老师','lv:teacher'),(3,'书记','lv:secretary'),(4,'教学副院长','lv:dean'),(5,'管理员','lv:all');
/*!40000 ALTER TABLE `lv_permission` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `lv_role_permission`
--

DROP TABLE IF EXISTS `lv_role_permission`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `lv_role_permission` (
  `lv_rpid` varchar(45) COLLATE utf8_unicode_ci NOT NULL,
  `lv_rid` int(2) NOT NULL COMMENT '角色id',
  `lv_pid` int(2) NOT NULL COMMENT '权限id',
  PRIMARY KEY (`lv_rpid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `lv_role_permission`
--

LOCK TABLES `lv_role_permission` WRITE;
/*!40000 ALTER TABLE `lv_role_permission` DISABLE KEYS */;
INSERT INTO `lv_role_permission` VALUES ('1',1,1),('2',2,2),('3',3,3),('4',4,4),('5',5,5);
/*!40000 ALTER TABLE `lv_role_permission` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `lv_roles`
--

DROP TABLE IF EXISTS `lv_roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `lv_roles` (
  `rid` int(2) NOT NULL COMMENT '角色id',
  `lv_rname` varchar(45) COLLATE utf8_unicode_ci NOT NULL COMMENT '角色名称',
  PRIMARY KEY (`rid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `lv_roles`
--

LOCK TABLES `lv_roles` WRITE;
/*!40000 ALTER TABLE `lv_roles` DISABLE KEYS */;
INSERT INTO `lv_roles` VALUES (1,'user'),(2,'teacher'),(3,'secretary'),(4,'dean'),(5,'admin');
/*!40000 ALTER TABLE `lv_roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `lv_teacher`
--

DROP TABLE IF EXISTS `lv_teacher`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `lv_teacher` (
  `lv_teacher_id` varchar(30) NOT NULL COMMENT '唯一标识符',
  `lv_teacher_name` varchar(9) NOT NULL COMMENT '老师姓名',
  `lv_teacher_num` varchar(12) NOT NULL COMMENT '老师工号',
  `lv_teacher_password` varchar(20) NOT NULL COMMENT '老师密码',
  `lv_teacher_phone` char(11) NOT NULL COMMENT '老师手机号',
  `lv_teacher_location` varchar(255) NOT NULL COMMENT '老师办公室地址',
  `lv_teacher_work_time` varchar(255) NOT NULL COMMENT '老师工作时间',
  `lv_teacher_create_time` varchar(20) NOT NULL COMMENT '老师创建时间',
  `lv_role` int(2) NOT NULL COMMENT '老师角色',
  PRIMARY KEY (`lv_teacher_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `lv_teacher`
--

LOCK TABLES `lv_teacher` WRITE;
/*!40000 ALTER TABLE `lv_teacher` DISABLE KEYS */;
INSERT INTO `lv_teacher` VALUES ('111111','李院长','1234567','1','1','1','1','1',4),('12233','李书记','12345','1','1','1','1','1',3),('1223344','杜书记','123456','123456','123455','1','1','1',3),('123454','李老师','12345678','1234555','5','5','5','5',2),('1257','杜院长','1234','1','1','1','1','1',4);
/*!40000 ALTER TABLE `lv_teacher` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `lv_user`
--

DROP TABLE IF EXISTS `lv_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `lv_user` (
  `lv_user_id` varchar(30) NOT NULL COMMENT '唯一标识符',
  `lv_user_num` varchar(12) NOT NULL COMMENT '学生学号',
  `lv_user_password` varchar(60) NOT NULL COMMENT '学生密码',
  `lv_user_name` varchar(9) NOT NULL COMMENT '学生姓名',
  `lv_user_phone` char(11) NOT NULL COMMENT '学生手机号',
  `lv_teacher_num` varchar(9) NOT NULL COMMENT '老师工号',
  `lv_user_create_time` varchar(20) NOT NULL COMMENT '学生创建时间',
  `lv_role` varchar(45) NOT NULL COMMENT '学生角色id',
  PRIMARY KEY (`lv_user_id`,`lv_user_num`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `lv_user`
--

LOCK TABLES `lv_user` WRITE;
/*!40000 ALTER TABLE `lv_user` DISABLE KEYS */;
INSERT INTO `lv_user` VALUES ('1573290410','1713011331','123','5555','4444','12345678','2019-11-09 17:06:50','1'),('1573910644','1713011332','2b9cdebb444dbb2fe8380860104f0573','memo','1234567890','12345678','2019-11-16','1');
/*!40000 ALTER TABLE `lv_user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-12-01 20:16:18
