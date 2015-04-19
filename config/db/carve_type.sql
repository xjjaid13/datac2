/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50173
Source Host           : localhost:3306
Source Database       : datac

Target Server Type    : MYSQL
Target Server Version : 50173
File Encoding         : 65001

Date: 2015-04-19 23:19:43
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `carve_type`
-- ----------------------------
DROP TABLE IF EXISTS `carve_type`;
CREATE TABLE `carve_type` (
  `carveTypeId` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `url` varchar(1000) NOT NULL DEFAULT '0',
  `typeName` varchar(100) DEFAULT '0',
  `enable` tinyint(4) DEFAULT '0' COMMENT '0 disabled 1 enable',
  `content` text,
  `seqNum` int(11) DEFAULT NULL,
  `selector` varchar(100) DEFAULT NULL,
  `pattern` varchar(1000) DEFAULT '0',
  `hash` varchar(100) DEFAULT NULL,
  `patternGroup` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`carveTypeId`),
  KEY `carveTypeId` (`carveTypeId`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of carve_type
-- ----------------------------
INSERT INTO `carve_type` VALUES ('14', 'http://www.oschina.net/', 'oschina', '1', null, '0', '#IndustryNews .p1', '<a(.*?)href=\\\"(.*?)\\\"(.*?)>(.*?)</a>', 'c4197400af5db08220a8bf35965bfd0d', '2;4');
INSERT INTO `carve_type` VALUES ('15', 'http://sports.sina.com.cn/global/', '新浪足球', '1', null, '0', 'div.blk2 ul.ul-type1', '<a(.*?)href=\\\"(.*?)\\\"(.*?)>(.*?)</a>', 'e71c1f346378f190d01c2155e9b99fbb', '2;4');
INSERT INTO `carve_type` VALUES ('16', 'http://36kr.com', '36k', '1', null, '-1', '.articles .posts div:eq(2) h1', '<a(.*?)href=\\\"(.*?)\\\"(.*?)>(.*?)</a>', '0e699c23dfcb8625ff1c20a7484cbb5d', '2;4');
