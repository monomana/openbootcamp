-- MySQL Administrator dump 1.4
--
-- ------------------------------------------------------
-- Server version	5.7.22-log


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
  `state` tinyint(1) DEFAULT '1',
  `created_at` datetime NOT NULL,
  `updated_at` datetime NOT NULL,
  `port` int(5) unsigned NOT NULL,
  `image` varchar(300) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `email` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `category_id` int(10) NOT NULL,
  `ip` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `protocol` varchar(10) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT 'http://',
  `latitude` varchar(45) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `longitude` varchar(45) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `company`
--

/*!40000 ALTER TABLE `company` DISABLE KEYS */;
INSERT INTO `company` (`id`,`company_name`,`endpoint`,`state`,`created_at`,`updated_at`,`port`,`image`,`email`,`category_id`,`ip`,`protocol`,`latitude`,`longitude`) VALUES 
 (1,'EL PINTAO','marybe',1,'2022-04-15 20:40:00','2022-04-15 20:40:00',80,'https://ss-static-01.esmsv.com/id/126798/galeriaimagenes/obtenerimagen/?width=275&height=67&id=sitio_logo&ultimaModificacion=2022-04-16+09%3A51%3A07','elpintao@sarasa.com',1,'ranticlient-env-1.eba-h9ih2wdp.us-east-1.elasticbeanstalk.com','http://','-27.788610','-64.267100'),
 (2,'MARYBE','marybe',1,'2022-04-21 20:40:00','2022-04-21 20:40:00',80,'https://ss-static-01.esmsv.com/id/66992/galeriaimagenes/obtenerimagen/?id=1193&tipoEscala=stretch&width=512&height=276','marybe@gmail.com',2,'ranticlient-env-1.eba-h9ih2wdp.us-east-1.elasticbeanstalk.com','http://','-27.786740030698827','-64.26103460759413'),
 (3,'LE VINOTHEQUE','el-pintao',1,'2022-04-21 20:40:00','2022-04-21 20:40:00',8082,'https://levinotheque.com.ar/wp-content/uploads/2021/09/cropped-logo-identidad-levinotheque-1.jpg','levinoteque@gmail.com',3,'190.228.229.183','http://','-27.790036','-64.264409'),
 (4,'HORTENSIA MARTINELLI','el-pintao',1,'2022-04-29 20:40:00','2022-04-29 20:40:00',8082,'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS00VgUvYsnUTcybnuG_82JIgEUtsksypCazbns7APd6mlVlm4Eva7M3njFiLJdEci61wk&usqp=CAU','hortensiamboutique@gmail.com',7,'190.228.229.183','http://','-27.790036','-64.264409'),
 (5,'SUPER BARATO','el-pintao',1,'2022-04-29 20:40:00','2022-04-29 20:40:00',8082,'https://farm5.staticflickr.com/4419/37145249955_f3b7af4aae_b.jpg',NULL,4,'190.228.229.183','http://','-27.790036','-64.264409'),
 (6,'FERRETERIA SAN MARTIN','el-pintao',1,'2022-04-29 20:40:00','2022-04-29 20:40:00',8082,'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQY20QGsnwmpvALaR3u5nnZloV_f2__CgBibNNlxRnv1FAG4OeB7zfOptal1b6ZZn-TD2E&usqp=CAU',NULL,5,'190.228.229.183','http://','-27.790036','-64.264409'),
 (7,'GOLOSINAS MAILIN','el-pintao',1,'2022-04-29 20:40:00','2022-04-29 20:40:00',8082,'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRF41hrZqyBiE18alhxCaAv4roR8NZwsxQGNIcVghkZlel7J6LV7CiSRixvj9UVi3TsoYM&usqp=CAU',NULL,6,'190.228.229.183','http://','-27.790036','-64.264409'),
 (8,'CURTICUER','el-pintao',1,'2022-04-29 20:40:00','2022-04-29 20:40:00',8082,'https://news.agrofystatic.com/cuero_argentino_agrofy_news_2.jpg?d=620x375',NULL,8,'190.228.229.183','http://','-27.790036','-64.264409'),
 (9,'CIRCULO ODONTOLOGICO','el-pintao',1,'2022-05-02 20:40:00','2022-05-02 20:40:00',8082,'https://cosantiago.com.ar/controladores/resize.php?carpeta=actualidades&width=850&height=0&file=627e3e8500b51.png',NULL,9,'190.228.229.183','http://','-27.790036','-64.264409');
/*!40000 ALTER TABLE `company` ENABLE KEYS */;


--
-- Definition of table `company_category`
--

DROP TABLE IF EXISTS `company_category`;
CREATE TABLE `company_category` (
  `ID` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `DESCRIPTION` varchar(45) COLLATE utf8_bin DEFAULT NULL,
  `ABREV` varchar(45) COLLATE utf8_bin DEFAULT NULL,
  `CODE` varchar(45) COLLATE utf8_bin DEFAULT NULL,
  `STATE` int(11) unsigned DEFAULT '0',
  `icon` varchar(45) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='detalle de rubros para agrupar los productos';

--
-- Dumping data for table `company_category`
--

/*!40000 ALTER TABLE `company_category` DISABLE KEYS */;
INSERT INTO `company_category` (`ID`,`DESCRIPTION`,`ABREV`,`CODE`,`STATE`,`icon`) VALUES 
 (1,0x50696E7475726173,NULL,0x3130303031,1,0x307865326262),
 (2,0x50657266756D65726961,NULL,0x3130303032,1,0x307865356430),
 (3,0x42656269646173,NULL,0x31303033,1,0x30786630326334),
 (4,0x436F6D65737469626C6573,NULL,0x3130303034,1,0x307866303439),
 (5,0x46657272657465726961,NULL,0x3130303035,1,0x307865353766),
 (6,0x476F6C6F73696E6173,NULL,0x3130303036,1,0x30786630356433),
 (7,0x496E64756D656E7461726961,NULL,0x31303037,1,0x30786630326334),
 (8,0x54616C616261727465726961,NULL,0x3130303038,1,0x30786630326334),
 (9,0x53616C7564,NULL,0x313030303039,1,0x307866306632);
/*!40000 ALTER TABLE `company_category` ENABLE KEYS */;


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
  `state` int(2) unsigned NOT NULL DEFAULT '2' COMMENT '0:inactivo, 1:pendiente  2: aceptao',
  `suggestion` varchar(1000) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  KEY `company_id` (`company_id`),
  CONSTRAINT `shopping_history_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `shopping_history_ibfk_2` FOREIGN KEY (`company_id`) REFERENCES `company` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=168 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `shopping_history`
--

/*!40000 ALTER TABLE `shopping_history` DISABLE KEYS */;
INSERT INTO `shopping_history` (`id`,`user_id`,`company_id`,`amount`,`created_at`,`user_company_id`,`order_id`,`state`,`suggestion`) VALUES 
 (112,1,2,'4927.6500','2022-06-15 22:52:38',2,742,1,NULL),
 (113,1,2,'1642.5500','2022-06-15 22:53:34',2,743,1,NULL),
 (114,1,2,'18446.7900','2022-06-16 00:24:34',2,744,1,NULL),
 (115,13,2,'3074.4700','2022-06-16 22:27:00',2,745,1,NULL),
 (116,1,2,'772.0400','2022-06-17 00:51:08',2,746,1,NULL),
 (117,15,2,'9223.4000','2022-06-17 17:00:07',2,747,1,NULL),
 (118,15,2,'6485.6000','2022-06-17 17:01:17',2,748,1,NULL),
 (119,18,2,'10627.3300','2022-06-18 03:56:05',2,749,1,NULL),
 (120,1,2,'15537.8500','2022-06-19 00:36:48',2,750,1,NULL),
 (121,1,2,'4823.8400','2022-06-19 00:40:04',2,751,1,NULL),
 (122,20,2,'514.6900','2022-06-19 01:58:53',2,752,1,NULL),
 (123,1,2,'6.1900','2022-06-20 02:54:29',2,753,1,NULL),
 (124,2,6,'1200.0000','2022-07-13 23:27:56',7,11,2,'test sugerencia'),
 (125,1,1,'3757.4900','2022-07-13 14:55:06',1,861,1,NULL),
 (126,1,1,'514.6900','2022-07-14 15:02:24',1,863,1,NULL),
 (127,1,2,'257.3500','2022-07-14 15:08:29',2,865,1,NULL),
 (128,1,2,'257.3500','2022-07-14 15:14:15',2,867,1,NULL),
 (129,1,2,'3074.4700','2022-07-14 15:16:27',2,868,0,NULL),
 (130,1,2,'257.3500','2022-07-14 15:33:30',2,870,1,NULL),
 (131,1,2,'1029.3800','2022-07-13 13:12:14',2,872,2,NULL),
 (132,21,2,'772.0400','2022-07-14 14:18:36',2,873,0,NULL),
 (133,21,2,'772.0400','2022-07-14 08:55:16',2,874,1,NULL),
 (134,1,2,'9434.0300','2022-07-14 09:08:06',2,875,1,NULL),
 (135,1,2,'13927.1600','2022-07-14 09:29:30',2,876,1,NULL),
 (136,5,2,'3215.8900','2022-07-15 11:04:35',2,877,1,NULL),
 (137,1,2,'34133.6600','2022-07-15 11:34:27',2,878,1,NULL),
 (138,1,2,'1419499.9300','2022-07-17 04:01:43',2,879,1,NULL),
 (139,1,2,'1029.3800','2022-07-17 01:35:41',2,880,1,NULL),
 (140,1,2,'772.0400','2022-07-18 02:43:48',2,881,1,NULL),
 (141,1,2,'1029.3800','2022-07-18 11:02:17',2,882,1,NULL),
 (142,1,2,'1029.3800','2022-07-18 08:19:14',2,883,1,NULL),
 (143,1,2,'29891.0400','2022-07-20 01:59:28',2,884,1,NULL),
 (144,5,2,'9434.0300','2022-07-20 09:54:41',2,885,1,NULL),
 (145,5,2,'3331.8100','2022-07-20 10:03:40',2,886,1,NULL),
 (146,5,2,'4717.0100','2022-07-20 10:08:24',2,887,1,NULL),
 (147,1,2,'67415.7400','2022-07-20 01:13:30',2,888,1,NULL),
 (148,1,2,'772.0400','2022-07-20 02:28:43',2,889,1,NULL),
 (149,1,2,'772.0400','2022-07-20 02:30:21',2,890,1,NULL),
 (150,1,2,'132971.4200','2022-07-21 02:22:46',2,891,1,NULL),
 (151,1,2,'772.0400','2022-07-22 12:19:05',2,892,1,NULL),
 (152,1,2,'257.3500','2022-07-22 11:11:12',2,893,1,NULL),
 (153,1,2,'4927.6500','2022-07-22 11:28:00',2,894,1,NULL),
 (154,1,1,'7131.6000','2022-07-22 08:52:46',1,895,1,NULL),
 (155,1,2,'257.3500','2022-07-22 09:30:51',2,896,1,NULL),
 (156,1,1,'12490.8900','2022-07-22 09:49:04',1,897,1,NULL),
 (157,25,2,'1029.3800','2022-07-23 03:42:21',2,898,1,NULL),
 (158,1,2,'22973.8300','2022-07-23 01:51:37',2,899,1,NULL),
 (159,1,2,'26979.6100','2022-07-23 02:02:41',2,900,1,NULL),
 (160,1,2,'31175.5700','2022-07-23 10:40:15',2,901,1,NULL),
 (161,1,2,'341593.9700','2022-07-23 10:41:56',2,902,1,NULL),
 (162,1,2,'1029.3800','2022-07-23 08:17:50',2,903,1,NULL),
 (163,1,2,'95385.0600','2022-07-23 08:19:17',2,904,1,NULL),
 (164,1,2,'8114.2700','2022-07-23 10:11:27',2,905,1,NULL),
 (165,1,2,'7318.0400','2022-07-25 08:24:10',2,906,1,NULL),
 (166,1,2,'1029.3800','2022-07-26 12:28:52',2,907,1,NULL),
 (167,1,1,'34391.0100','2022-07-26 09:42:58',1,908,1,NULL);
/*!40000 ALTER TABLE `shopping_history` ENABLE KEYS */;


--
-- Definition of table `user`
--

DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `password` varchar(200) COLLATE utf8mb4_unicode_ci NOT NULL,
  `email` varchar(200) COLLATE utf8mb4_unicode_ci NOT NULL,
  `state` tinyint(1) NOT NULL DEFAULT '1',
  `created_at` datetime NOT NULL,
  `updated_at` datetime NOT NULL,
  `thumbnail` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `first_name` varchar(45) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `last_name` varchar(45) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `dni` varchar(9) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `role` int(3) unsigned DEFAULT '1' COMMENT '0: ADMIN 1: MANAGER 2: CUSTOMER 3: OPERATOR',
  `address` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `phone` varchar(45) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `provider` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `provider_user_id` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `refresh_token` varchar(300) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `user`
--

/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` (`id`,`username`,`password`,`email`,`state`,`created_at`,`updated_at`,`thumbnail`,`first_name`,`last_name`,`dni`,`role`,`address`,`phone`,`provider`,`provider_user_id`,`refresh_token`) VALUES 
 (1,'nickname','$2a$10$UBeJqNsvEA3YB/kvH7RrSePIRw8RnJ2VLoPDGW9tE4.ho5JSD7QdW','admin@modularsoft.ar',1,'2022-04-15 20:40:00','2022-04-15 20:40:00','https://icon-library.com/images/icon-avatar/icon-avatar-19.jpg','Ranti','Modularsoft','3000000',0,'Libertad 853','3855707070','local',NULL,NULL),
 (2,'testing','$2a$10$AM8/CODdR0/Lm2LUbHAdDO5MvOqrdLsjGKi64hDNy.o7PfR12fUwq','testranti@modularsoft.ar',1,'2022-04-16 09:40:00','2022-04-16 09:40:00',NULL,'monoat',NULL,'1235',3,'1234567',NULL,'local',NULL,NULL),
 (3,'Luciano Mansilla','$2a$10$J85GU1.t8FAvn9mVl9GodOe2vE/AunyoUeJkmbMKrmCEE7BNtrKEG','mluciano.mansilla@gmail.com',1,'2022-06-15 00:33:11','2022-06-15 00:33:11','https://images.freeimages.com/images/large-previews/4d6/chugh-1171409.jpg','root','root','3855999',1,'Cordobaaaa','3855999999','local',NULL,NULL),
 (4,'mono','$2a$10$3yQ./TdMHv52B9K29J9wweewjcIHMdkj9tHzmrQF9Y9UdDwne0ree','mono@gmail.com',1,'2022-06-15 00:47:06','2022-06-15 00:47:06',NULL,NULL,NULL,NULL,1,NULL,NULL,'local',NULL,NULL),
 (5,'nickname','$2a$10$27whw/GUVlZEuf1Nk.gZfO94/zvGdLQ2D0KDUZdhbHX5yMy6v56mK','cristiansledesma@gmail.com',1,'2022-06-15 12:58:48','2022-06-15 12:58:48','https://icon-library.com/images/icon-avatar/icon-avatar-19.jpg','Cristiansss','Ledesma','39986584',1,'Libertad 882','3855707071','local',NULL,NULL),
 (6,'gaston','$2a$10$EbnnF3aoKY6yS2P5csH23O5ApDBimEnMc3dz3kquH9/6f/pAdzSwm','gastonccoronel@gmail.com',1,'2022-06-15 14:45:39','2022-06-15 14:45:39',NULL,NULL,NULL,NULL,1,NULL,NULL,'local',NULL,NULL),
 (7,'luciano','$2a$10$RA6KMyN58TyVvUk6aX9xTuPDmrVduiaLI2lYNs/aR1SA1eymBGmKq','mluciano.mansilla@gmail.com.ar',1,'2022-06-16 02:59:25','2022-06-16 02:59:25','https://images.freeimages.com/images/large-previews/4d6/chugh-1171409.jpg','Luciano','Mansilla','35917512',1,'Calle Falsa 123','3855725040','local',NULL,NULL),
 (8,'MataZurdos 5000','$2a$10$ytxY1BlveTPObIz.JNttW.rxZjvoFQ77UQQUojG7O.zDP0d7t6oTG','gaston.ec96@gmail.com',1,'2022-06-16 19:47:15','2022-06-16 19:47:15',NULL,NULL,NULL,NULL,1,NULL,NULL,'local',NULL,NULL),
 (9,'Meuri','$2a$10$ipTocW7f0tUcUlXSRg4QMu7zXRYFqo7GosieBRYC7F3OaRO3Pl94m','maguero@gmail.com',1,'2022-06-16 19:55:32','2022-06-16 19:55:32',NULL,NULL,NULL,NULL,1,NULL,NULL,'local',NULL,NULL),
 (10,'AleSu','$2a$10$QTsSKjOkDBu52PE/VfU/Gu3nT17xESC837sgFnIsCTtxTMk64jXrO','alesundblad@gmail.com',1,'2022-06-16 20:24:44','2022-06-16 20:24:44',NULL,'Alejandro','Sundblad',NULL,1,'','3855202020','local',NULL,NULL),
 (11,'Mar de Ano','$2a$10$9jg9tvr/ZD8tdllinp6CEuSUBowyPgyPPgu.pGQJJz.McgeYt2uO6','marianoc@gmail.com',1,'2022-06-16 20:40:40','2022-06-16 20:40:40',NULL,'Mariano Ernesto','Coronel','35980980',1,'Por la gueme','3855808080','local',NULL,NULL),
 (12,'Diegote','$2a$10$uyKXBJlQXnU8FTCd4uvxC.d5PtruLCYzD.7hyi39uF42ljet4eCD.','diegoperez@gmail.com',1,'2022-06-16 21:06:13','2022-06-16 21:06:13',NULL,'Diego Ramon Orlando','Perez','27390913',1,'Loma tuje','3855909090','local',NULL,NULL),
 (13,'meuri','$2a$10$AFkhMwHEYo5VqXpZrjXnG.AhWU7wKVXBvm9hynC9d32oO3zKwLagW','aguero@gmail.com',1,'2022-06-16 22:24:52','2022-06-16 22:24:52',NULL,'aguero','Aguero','2400000',1,'meuri','12345678','local',NULL,NULL),
 (14,'Shaba1106','$2a$10$xbiDL8uWWFeKptRqaNblRuFD2rv0q5XM0Xta235pXH3U38BJnLXiu','mluciano.mansilla91@gmail.com',1,'2022-06-17 16:09:40','2022-06-17 16:09:40',NULL,'Mario Luciano','Mansilla','35917512',1,'Calle 2 N  185 B San Fernando','3855725040','local',NULL,NULL),
 (15,'dpineda','$2a$10$b79R5epJFAmZyPPpY56ue.g.drAn45Fe.NknHJS9AiGk2xYZ/8G6S','dpineda@ddd.com',1,'2022-06-17 16:27:48','2022-06-17 16:27:48',NULL,'Dora','Pineda','12161616',1,'','3855000000','local',NULL,NULL),
 (16,'shabaroot','$2a$10$SNrXZsXdL5cA30hFSHMSQ.vTH/ncLd8obrz.tg1oTsK3qvAGNKEP.','mluciano@hotmail.com',1,'2022-06-17 19:52:17','2022-06-17 19:52:17',NULL,'Mario','Mansilla','35917512',1,'s/n','3855725040','local',NULL,NULL),
 (17,'asd','$2a$10$kXq0SpbWTpH3sKRICeY.dufm6mDSYvKEJj6JRlza8NIoQSdSsBniS','andres@algo.com',1,'2022-06-17 19:57:52','2022-06-17 19:57:52',NULL,'Andres','Mansilla','33000000',1,'','3855803605','local',NULL,NULL),
 (18,'shroot91','$2a$10$Yr9xrM7jVTxJtmCALx..1O.wa.4w4VWOStKz/C1slwE4uNNTuLmHa','mluciano@correocaliente.com',1,'2022-06-18 03:40:07','2022-06-18 03:40:07',NULL,'mario luciano','Mansilla','35917512',1,'','3855725040','local',NULL,NULL),
 (19,'rootperri','$2a$10$dICbkzZ/rYjpCWYVWEXF.OCH5gOrigvAmU7FK9DDSZ6rfy7u6EYpO','mluciano.mansilla@gmail.ar',1,'2022-06-18 22:14:24','2022-06-18 22:14:24',NULL,'mama','mama','38557200',1,'','3855725040','local',NULL,NULL),
 (20,'jLedesma','$2a$10$t6IRag2Ve6IxYZ9.7U5PK.shGdi5lfDdyNn1u3u3.3V52w.0A2fD.','jledesman@gmail.com',1,'2022-06-19 01:57:38','2022-06-19 01:57:38',NULL,'Javier','Ledesma','36000000',1,'','3855121212','local',NULL,NULL),
 (21,'shaba','$2a$10$U5tB3kV7tzUpk2axysnnTeTDghSpS9jWlkdoqeAG4E1J0atE0kN3.','mluciano.mansilla1106@gmail.com',1,'2022-07-14 02:52:17','2022-07-14 02:52:17',NULL,'Luciano','Mansilla','35917512',1,'','3855725040','local',NULL,NULL),
 (22,'Mariana','$2a$10$W61X7hpynLhhybGagIqxeemoi.F9aRMSGf0hj9g99YWu2xOMSnoqW','mariana@gmail.com',1,'2022-07-15 13:22:13','2022-07-15 13:22:13',NULL,'Mariana','Gomez','3800000',1,'','3855707070','local',NULL,NULL),
 (23,'Alejandro Sundblad','$2a$10$hjqSqXwmKHgkcbquX8GpFOk.Hfg8RRSOTR5yje/ZMPzR13k40rU6W','alesundblad@modularsoft.com.ar',1,'2022-07-22 20:54:24','2022-07-22 20:54:24',NULL,NULL,NULL,NULL,1,NULL,NULL,'google','101343982131848672406',NULL),
 (24,'mjatmjat mjat','$2a$10$uHzA1KXgZ4C6u3iKWIOiS.GUHZtZ3psc2WwVwo5s1zMOHYJyelR56','mjat.util@gmail.com',1,'2022-07-22 21:17:19','2022-07-22 21:17:19',NULL,NULL,NULL,NULL,1,NULL,NULL,'google','106842155605540570354',NULL),
 (25,'jHernandez','$2a$10$FXezXBsoxtIbLQ8RMMTqc.jbuYJ5kSlfUC.9FJYyUYDAx0Mg94nVK','jhernandez@gmail.com',1,'2022-07-23 03:41:37','2022-07-23 03:41:37',NULL,'Julian','Hernandez','12198584',1,'Av Belgrano Sur 2011','3855725050','local',NULL,NULL);
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
 (1,1),
 (1,2),
 (1,3),
 (2,3),
 (3,3),
 (4,3),
 (5,3),
 (6,3),
 (7,3),
 (8,3),
 (9,3),
 (10,3),
 (11,3),
 (12,3),
 (13,3),
 (14,3),
 (15,3),
 (16,3),
 (17,3),
 (18,3),
 (19,3),
 (20,3),
 (21,3),
 (22,3),
 (23,3),
 (24,3),
 (25,3);
/*!40000 ALTER TABLE `user_role` ENABLE KEYS */;




/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
