-- MySQL Administrator dump 1.4
--
-- ------------------------------------------------------
-- Server version	5.5.5-10.9.2-MariaDB


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


--
-- Create schema ranti
--

CREATE DATABASE IF NOT EXISTS ranti;
USE ranti;

--
-- Definition of table `company`
--

DROP TABLE IF EXISTS `company`;
CREATE TABLE `company` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `company_name` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
  `endpoint` varchar(200) COLLATE utf8mb4_unicode_ci NOT NULL,
  `state` tinyint(1) DEFAULT 1,
  `created_at` datetime NOT NULL,
  `updated_at` datetime NOT NULL,
  `port` int(5) unsigned NOT NULL,
  `image` varchar(300) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `email` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `category_id` int(10) NOT NULL,
  `ip` varchar(15) COLLATE utf8mb4_unicode_ci NOT NULL,
  `protocol` varchar(10) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT 'http://',
  `latitude` varchar(45) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `longitude` varchar(45) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `company`
--

/*!40000 ALTER TABLE `company` DISABLE KEYS */;
INSERT INTO `company` (`id`,`company_name`,`endpoint`,`state`,`created_at`,`updated_at`,`port`,`image`,`email`,`category_id`,`ip`,`protocol`,`latitude`,`longitude`) VALUES 
 (1,'EL PINTAO','el-pintao',1,'2022-04-15 20:40:00','2022-04-15 20:40:00',8082,'https://ss-static-01.esmsv.com/id/126798/galeriaimagenes/obtenerimagen/?width=275&height=67&id=sitio_logo&ultimaModificacion=2022-04-16+09%3A51%3A07','elpintao@sarasa.com',1,'181.1.184.85','http://','-27.788610','-64.267100'),
 (2,'MARYBE','marybe',1,'2022-04-21 20:40:00','2022-04-21 20:40:00',8083,'https://ss-static-01.esmsv.com/id/66992/galeriaimagenes/obtenerimagen/?id=1193&tipoEscala=stretch&width=512&height=276','marybe@gmail.com',2,'181.1.184.85','http://','-27.786740030698827','-64.26103460759413'),
 (3,'LE VINOTHEQUE','el-pintao',1,'2022-04-21 20:40:00','2022-04-21 20:40:00',8082,'https://levinotheque.com.ar/wp-content/uploads/2021/09/cropped-logo-identidad-levinotheque-1.jpg','levinoteque@gmail.com',3,'181.1.184.85','http://','-27.790036','-64.264409'),
 (4,'HORTENSIA MARTINELLI','el-pintao',1,'2022-04-29 20:40:00','2022-04-29 20:40:00',8082,'https://scontent.ftuc1-2.fna.fbcdn.net/v/t1.18169-9/16806898_1811764475715095_986284176339013808_n.png?_nc_cat=103&ccb=1-5&_nc_sid=09cbfe&_nc_ohc=5eGOcTQ1DGwAX_moyZb&_nc_ht=scontent.ftuc1-2.fna&oh=00_AT8qhMrf_LWZfj04LB1ipCKpH91vWAYYgscOOmkX_CDTww&oe=6291EF63','hortensiamboutique@gmail.com',7,'181.1.184.85','http://','-27.790036','-64.264409'),
 (5,'SUPER BARATO','el-pintao',1,'2022-04-29 20:40:00','2022-04-29 20:40:00',8082,'https://scontent.ftuc1-1.fna.fbcdn.net/v/t1.6435-9/104412636_103904464705405_5504988877045278503_n.jpg?_nc_cat=107&ccb=1-5&_nc_sid=09cbfe&_nc_ohc=e0fn0OuxstkAX96-ROA&_nc_ht=scontent.ftuc1-1.fna&oh=00_AT-aavnNuIkr9RP2OqvNvsOVJC2M0Tf8kIzpem-kiknhHw&oe=6290497B',NULL,4,'181.1.184.85','http://','-27.790036','-64.264409'),
 (6,'FERRETERIA SAN MARTIN','el-pintao',1,'2022-04-29 20:40:00','2022-04-29 20:40:00',8082,'https://scontent.ftuc1-1.fna.fbcdn.net/v/t31.18172-8/14589642_1127774203926309_7850145563546241553_o.jpg?_nc_cat=106&ccb=1-5&_nc_sid=09cbfe&_nc_ohc=QMQDf-_UpN0AX_bBkKs&_nc_ht=scontent.ftuc1-1.fna&oh=00_AT-8DWQtQiT2CrotDut6TB0HIcA9gWss3K3wjD0tb25-JQ&oe=629415C7',NULL,5,'181.1.184.85','http://','-27.790036','-64.264409'),
 (7,'GOLOSINAS MAILIN','el-pintao',1,'2022-04-29 20:40:00','2022-04-29 20:40:00',8082,'https://scontent.ftuc1-2.fna.fbcdn.net/v/t1.6435-9/147188358_364124264547121_2370013217032703640_n.png?_nc_cat=105&ccb=1-5&_nc_sid=973b4a&_nc_ohc=azTak05DhD0AX_08a2p&_nc_ht=scontent.ftuc1-2.fna&oh=00_AT82ovbpHAQRdrsNBZn_njpQG8ZKnv3Ms5i6d_OayEJaEA&oe=62960400',NULL,6,'181.1.184.85','http://','-27.790036','-64.264409'),
 (8,'CURTICUER','el-pintao',1,'2022-04-29 20:40:00','2022-04-29 20:40:00',8082,'https://news.agrofystatic.com/cuero_argentino_agrofy_news_2.jpg?d=620x375',NULL,8,'181.1.184.85','http://','-27.790036','-64.264409'),
 (9,'CIRCULO ODONTOLOGICO','el-pintao',1,'2022-05-02 20:40:00','2022-05-02 20:40:00',8082,'https://scontent.ftuc1-1.fna.fbcdn.net/v/t1.18169-9/12540925_1178898345472272_2615023554566325454_n.jpg?_nc_cat=107&ccb=1-5&_nc_sid=de6eea&_nc_ohc=EihIXDX1z44AX-XxzwW&_nc_ht=scontent.ftuc1-1.fna&oh=00_AT-e7IEhaf5MhBKQEL_ItINowUKJB5tikM1OI6PdJm1x_A&oe=6294C791',NULL,9,'181.1.184.85','http://','-27.790036','-64.264409');
/*!40000 ALTER TABLE `company` ENABLE KEYS */;


--
-- Definition of table `company_category`
--

DROP TABLE IF EXISTS `company_category`;
CREATE TABLE `company_category` (
  `ID` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `DESCRIPTION` varchar(45) COLLATE utf8mb3_bin DEFAULT NULL,
  `ABREV` varchar(45) COLLATE utf8mb3_bin DEFAULT NULL,
  `CODE` varchar(45) COLLATE utf8mb3_bin DEFAULT NULL,
  `STATE` int(11) unsigned DEFAULT 0,
  `icon` varchar(45) COLLATE utf8mb3_bin DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin COMMENT='detalle de rubros para agrupar los productos';

--
-- Dumping data for table `company_category`
--

/*!40000 ALTER TABLE `company_category` DISABLE KEYS */;
INSERT INTO `company_category` (`ID`,`DESCRIPTION`,`ABREV`,`CODE`,`STATE`,`icon`) VALUES 
 (1,0x50494E5455524153,NULL,0x3130303031,1,0x307865326262),
 (2,0x50455246554D45524941,NULL,0x3130303032,1,0x307865356430),
 (3,0x42454249444153,NULL,0x31303033,1,0x30786630326334),
 (4,0x434F4D45535449424C4553,NULL,0x3130303034,1,0x30786630326334),
 (5,0x46455252455445524941,NULL,0x3130303035,1,0x30786630326334),
 (6,0x474F4C4F53494E4153,NULL,0x3130303036,1,0x30786630326334),
 (7,0x494E44554D454E5441524941,NULL,0x31303037,1,0x30786630326334),
 (8,0x54414C414241525445524941,NULL,0x3130303038,1,0x30786630326334),
 (9,0x53414C5544,NULL,0x313030303039,1,0x30786630326334);
/*!40000 ALTER TABLE `company_category` ENABLE KEYS */;


--
-- Definition of table `company_menu`
--

DROP TABLE IF EXISTS `company_menu`;
CREATE TABLE `company_menu` (
  `company_id` int(4) NOT NULL,
  `menu_id` int(4) NOT NULL,
  `state` tinyint(1) DEFAULT 1,
  PRIMARY KEY (`company_id`,`menu_id`),
  KEY `menu_id` (`menu_id`),
  CONSTRAINT `company_menu` FOREIGN KEY (`company_id`) REFERENCES `company` (`id`),
  CONSTRAINT `menu_company_ibfk_2` FOREIGN KEY (`menu_id`) REFERENCES `menu` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `company_menu`
--

/*!40000 ALTER TABLE `company_menu` DISABLE KEYS */;
INSERT INTO `company_menu` (`company_id`,`menu_id`,`state`) VALUES 
 (1,1,1),
 (1,2,1),
 (1,3,1),
 (1,4,1),
 (1,5,1),
 (1,6,1),
 (1,15,1),
 (1,16,1),
 (1,17,1);
/*!40000 ALTER TABLE `company_menu` ENABLE KEYS */;


--
-- Definition of table `menu`
--

DROP TABLE IF EXISTS `menu`;
CREATE TABLE `menu` (
  `id` int(4) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `url` varchar(45) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `icon` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT 'fa fa-open',
  `parent_id` int(4) DEFAULT NULL,
  `order` int(4) DEFAULT 0,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `menu`
--

/*!40000 ALTER TABLE `menu` DISABLE KEYS */;
INSERT INTO `menu` (`id`,`name`,`url`,`icon`,`parent_id`,`order`) VALUES 
 (1,'articulos','http:/articulos','fa fa-open',0,1),
 (2,'categorias','http:/categorias','fa fa-open',0,2),
 (3,'art_catego','http:/articulos/cat','fa fa-open',1,1),
 (4,'art_dos','http:/articulos/dos','fa fa-open',1,2),
 (5,'art_cat_1','http:/articulos/cat/cat1','fa fa-open',3,1),
 (6,'marcas',NULL,'fa fa-open',0,3),
 (15,'test_save','http:/articulos/cat','fa fa-open',2,1),
 (16,'test_save_15','http:/articulos/cat','fa fa-open',15,1),
 (17,'test_save_17','http:/articulos/cat','fa fa-open',0,1);
/*!40000 ALTER TABLE `menu` ENABLE KEYS */;


--
-- Definition of table `role`
--

DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `role`
--

/*!40000 ALTER TABLE `role` DISABLE KEYS */;
INSERT INTO `role` (`id`,`name`) VALUES 
 (1,'ROLE_ADMIN'),
 (2,'ROLE_MODERATOR'),
 (3,'ROLE_USER');
/*!40000 ALTER TABLE `role` ENABLE KEYS */;


--
-- Definition of table `shopping_history`
--

DROP TABLE IF EXISTS `shopping_history`;
CREATE TABLE `shopping_history` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `user_id` int(10) NOT NULL,
  `company_id` int(10) NOT NULL,
  `amount` decimal(12,4) DEFAULT NULL,
  `created_at` datetime NOT NULL,
  `user_company_id` int(10) NOT NULL,
  `order_id` int(10) unsigned NOT NULL,
  `state` int(2) unsigned NOT NULL DEFAULT 2 COMMENT '0:inactivo, 1:pendiente  2: aceptao',
  `suggestion` varchar(1000) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  KEY `company_id` (`company_id`),
  CONSTRAINT `shopping_history_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `shopping_history_ibfk_2` FOREIGN KEY (`company_id`) REFERENCES `company` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=72 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `shopping_history`
--

/*!40000 ALTER TABLE `shopping_history` DISABLE KEYS */;
INSERT INTO `shopping_history` (`id`,`user_id`,`company_id`,`amount`,`created_at`,`user_company_id`,`order_id`,`state`,`suggestion`) VALUES 
 (1,2,6,'1200.0000','2022-05-12 20:26:41',7,11,2,NULL),
 (2,1,1,'27.1300','2022-05-12 22:04:58',1,0,0,NULL),
 (3,1,1,'4251.4500','2022-05-12 22:06:12',1,0,1,NULL),
 (22,2,6,'1200.0000','2022-05-26 20:01:54',7,618,2,NULL),
 (24,1,6,'1200.0000','2022-05-26 20:04:24',7,618,2,NULL),
 (25,1,6,'1200.0000','2022-05-26 20:38:10',7,618,2,NULL),
 (26,1,1,'27.1300','2022-05-26 20:43:47',1,618,0,NULL),
 (27,1,1,'504.5700','2022-05-26 20:46:56',1,629,0,NULL),
 (28,1,1,'504.5700','2022-05-26 20:48:22',1,630,0,NULL),
 (29,1,1,'7497.0000','2022-05-26 20:53:35',1,631,0,NULL),
 (30,1,1,'18.8600','2022-05-26 21:08:44',1,632,0,NULL),
 (31,1,1,'27.1300','2022-05-26 21:23:45',1,633,0,NULL),
 (32,1,1,'27.1300','2022-05-27 01:04:25',1,634,0,NULL),
 (33,1,1,'5316.4700','2022-05-27 01:10:10',1,635,0,NULL),
 (34,1,1,'27.1300','2022-05-27 01:19:21',1,636,0,NULL),
 (35,1,1,'81.3800','2022-05-27 02:28:34',1,637,0,NULL),
 (36,1,1,'3748.5000','2022-05-27 21:32:45',1,638,0,NULL),
 (37,1,1,'504.5700','2022-05-30 20:34:09',1,639,0,NULL),
 (38,1,1,'1417.1500','2022-05-30 20:44:57',1,640,0,NULL),
 (39,1,1,'27.1300','2022-05-30 20:48:46',1,641,0,NULL),
 (40,1,2,'108.5100','2022-05-30 21:21:37',2,643,0,NULL),
 (41,1,2,'2126.8000','2022-05-30 21:45:14',2,644,0,NULL),
 (42,1,2,'0.0000','2022-05-30 23:22:17',2,645,0,NULL),
 (43,1,3,'5179.2800','2022-05-31 21:47:02',3,646,0,NULL),
 (44,1,1,'568.7900','2022-05-31 21:47:47',1,647,0,NULL),
 (45,1,5,'284.2100','2022-05-31 21:48:29',5,648,0,NULL),
 (46,1,9,'257.3500','2022-05-31 22:06:29',9,649,0,NULL),
 (47,1,6,'3074.4700','2022-05-31 22:09:22',6,650,0,NULL),
 (48,2,6,'1200.0000','2022-06-15 21:15:44',7,11,2,NULL),
 (49,2,6,'1200.0000','2022-06-15 21:18:36',7,11,2,NULL),
 (50,2,6,'1200.0000','2022-06-27 19:38:45',7,11,2,NULL),
 (51,2,6,'1200.0000','2022-06-27 20:11:38',7,11,2,NULL),
 (52,2,6,'1200.0000','2022-06-27 20:30:06',7,11,2,NULL),
 (53,2,6,'1200.0000','2022-06-27 20:34:13',7,11,2,NULL),
 (54,2,6,'1200.0000','2022-06-27 20:43:56',7,11,2,NULL),
 (55,2,6,'1200.0000','2022-06-28 00:03:34',7,11,2,NULL),
 (56,2,6,'1200.0000','2022-06-27 21:05:09',7,11,2,NULL),
 (57,2,6,'1200.0000','2022-06-28 00:07:15',7,11,2,NULL),
 (58,2,6,'1200.0000','2022-06-28 00:11:19',7,11,2,NULL),
 (59,2,6,'1200.0000','2022-06-28 00:13:02',7,11,2,NULL),
 (60,2,6,'1200.0000','2022-06-27 21:13:58',7,11,2,NULL),
 (61,2,6,'1200.0000','2022-06-27 21:31:26',7,11,2,NULL),
 (62,2,6,'1200.0000','2022-06-27 21:32:53',7,11,2,NULL),
 (63,2,6,'1200.0000','2022-06-27 21:34:13',7,11,2,NULL),
 (64,2,6,'1200.0000','2022-06-27 22:05:10',7,11,2,NULL),
 (65,2,6,'1200.0000','2022-06-27 22:06:01',7,11,2,NULL),
 (66,2,6,'1200.0000','2022-06-27 22:15:13',7,11,2,NULL),
 (67,2,6,'1200.0000','2022-06-27 22:18:09',7,11,2,NULL),
 (68,2,6,'1200.0000','2022-06-27 22:19:41',7,11,2,NULL),
 (69,2,6,'1200.0000','2022-06-27 19:21:49',7,11,2,NULL),
 (70,2,6,'1200.0000','2022-06-27 20:05:13',7,11,2,NULL),
 (71,2,6,'1200.0000','2022-07-01 18:10:54',7,11,2,'test sugerencia');
/*!40000 ALTER TABLE `shopping_history` ENABLE KEYS */;


--
-- Definition of table `user`
--

DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,
  `password` varchar(200) COLLATE utf8mb4_unicode_ci NOT NULL,
  `email` varchar(200) COLLATE utf8mb4_unicode_ci NOT NULL,
  `state` tinyint(1) NOT NULL DEFAULT 1,
  `created_at` datetime NOT NULL,
  `updated_at` datetime NOT NULL,
  `thumbnail` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `first_name` varchar(45) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `last_name` varchar(45) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `dni` varchar(9) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `role` int(3) unsigned DEFAULT 1 COMMENT '0: ADMIN 1: MANAGER 2: CUSTOMER 3: OPERATOR',
  `address` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `phone` varchar(45) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `provider` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `provider_user_id` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `user`
--

/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` (`id`,`username`,`password`,`email`,`state`,`created_at`,`updated_at`,`thumbnail`,`first_name`,`last_name`,`dni`,`role`,`address`,`phone`,`provider`,`provider_user_id`) VALUES 
 (1,'user','$2a$10$glnUvSJI3.DIJAj2EfAmpeBhGuE0FJOhxrniBMH0KLKYeh/aP6wbe','mmansilla@gmail.com',1,'2022-04-15 20:40:00','2022-04-15 20:40:00','https://concepto.de/wp-content/uploads/2015/03/paisaje-800x409.jpg','Luciano','Mansilla','35917512',0,'A LA BANDA A LA BANDA N 666','3855555555',NULL,NULL),
 (2,'testing','$2a$10$glnUvSJI3.DIJAj2EfAmpeBhGuE0FJOhxrniBMH0KLKYeh/aP6wbe','testranti@modularsoft.ar',1,'2022-04-16 09:40:00','2022-04-16 09:40:00',NULL,'monoat',NULL,'1235',3,'1234567',NULL,NULL,NULL),
 (3,'Admin','$2a$10$Md10d32RfSUa2Fuq83S2sut5Dw88f29/1K/tPuALYpYEjEaO9rM72','admin@modularsoft.ar',1,'2022-06-09 22:10:24','2022-06-09 22:10:24',NULL,NULL,NULL,NULL,1,NULL,NULL,'local',NULL),
 (4,'cris ti an','$2a$10$YM1oPZrVq/D/Y2FZgiDQguYciTJr8ELkXw1F0akHAiBm9CaQ7Rq5S','testfullname@modularsoft.ar',0,'2022-06-16 00:53:27','2022-06-16 00:53:27',NULL,'christisan','cassstro','66699966',1,'1234567',NULL,'local',NULL),
 (5,'skip-jobs','$2a$10$7GinAPkjatz8azBZAtxY5./igkXndl79fknvCY4NbhXf3sMUcSYiW','testfullnametok@modularsoft.ar',1,'2022-06-16 22:08:17','2022-06-16 22:08:17','https://estutoriales.com/wp-content/uploads/2019/11/como-hacer-una-imagen-hd-en-photoshop.jpg','Skip','Jobs','24111112',1,'pje america','3854568799','local',NULL),
 (6,'skip-jobs','$2a$10$XTV4I4TjFCheHN/g.TeZlehDd94anNPC4Wk0D48gf4BjTs1JXWlwG','testfullnametoken@modularsoft.ar',1,'2022-06-16 22:11:56','2022-06-16 22:11:56','https://estutoriales.com/wp-content/uploads/2019/11/como-hacer-una-imagen-hd-en-photoshop.jpg','Skip','Jobs','24111112',1,'pje america','3854568799','local',NULL),
 (7,'mjatmjat mjat','$2a$10$qUzfv.qQ.ZPOesw7U08i9O4TBCk5UMAeS0rTtMyMpdyuTZCIyZHbq','mjat.util@gmail.com',1,'2022-07-16 00:53:57','2022-07-16 00:53:57',NULL,NULL,NULL,NULL,1,NULL,NULL,'google','106842155605540570354'),
 (8,'Mauricio Aguero','$2a$10$mlo6UFsLETuDztkOFx0.ie0mxxP2VbsHCjGDVmC1S3P2mj8XrtNo.','mauriciojat@gmail.com',1,'2022-07-16 05:53:52','2022-07-16 05:53:52',NULL,NULL,NULL,NULL,1,NULL,NULL,'google','103970445994084919966');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;


--
-- Definition of table `user_company`
--

DROP TABLE IF EXISTS `user_company`;
CREATE TABLE `user_company` (
  `user_id` int(10) NOT NULL,
  `company_id` int(10) NOT NULL,
  `user_company_id` int(10) NOT NULL,
  PRIMARY KEY (`user_id`,`company_id`),
  KEY `FK_user_company_2` (`company_id`),
  CONSTRAINT `FK_user_company_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_user_company_2` FOREIGN KEY (`company_id`) REFERENCES `company` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `user_company`
--

/*!40000 ALTER TABLE `user_company` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_company` ENABLE KEYS */;


--
-- Definition of table `user_role`
--

DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role` (
  `user_id` int(10) NOT NULL,
  `role_id` int(10) NOT NULL,
  PRIMARY KEY (`user_id`,`role_id`),
  KEY `FKa68196081fvovjhkek5m97n3y` (`role_id`),
  CONSTRAINT `FK859n2jvi8ivhui0rl0esws6o` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FKa68196081fvovjhkek5m97n3y` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `user_role`
--

/*!40000 ALTER TABLE `user_role` DISABLE KEYS */;
INSERT INTO `user_role` (`user_id`,`role_id`) VALUES 
 (1,3),
 (2,3),
 (3,1),
 (3,2),
 (3,3),
 (4,3),
 (5,3),
 (6,3),
 (7,3),
 (8,3);
/*!40000 ALTER TABLE `user_role` ENABLE KEYS */;




/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;