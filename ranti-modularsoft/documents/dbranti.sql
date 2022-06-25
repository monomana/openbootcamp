DROP TABLE IF EXISTS `ranti`.`company`;
CREATE TABLE  `ranti`.`company` (
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

DROP TABLE IF EXISTS `ranti`.`company_category`;
CREATE TABLE  `ranti`.`company_category` (
  `ID` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `DESCRIPTION` varchar(45) COLLATE utf8mb3_bin DEFAULT NULL,
  `ABREV` varchar(45) COLLATE utf8mb3_bin DEFAULT NULL,
  `CODE` varchar(45) COLLATE utf8mb3_bin DEFAULT NULL,
  `STATE` int(11) unsigned DEFAULT 0,
  `icon` varchar(45) COLLATE utf8mb3_bin DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin COMMENT='detalle de rubros para agrupar los productos';

DROP TABLE IF EXISTS `ranti`.`shopping_history`;
CREATE TABLE  `ranti`.`shopping_history` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `user_id` int(10) NOT NULL,
  `company_id` int(10) NOT NULL,
  `amount` decimal(12,4) DEFAULT NULL,
  `created_at` datetime NOT NULL,
  `user_company_id` int(10) NOT NULL,
  `order_id` int(10) unsigned NOT NULL,
  `state` int(2) unsigned NOT NULL DEFAULT 2 COMMENT '0:inactivo, 1:pendiente  2: aceptao',
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  KEY `company_id` (`company_id`),
  CONSTRAINT `shopping_history_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `shopping_history_ibfk_2` FOREIGN KEY (`company_id`) REFERENCES `company` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

DROP TABLE IF EXISTS `ranti`.`user`;
CREATE TABLE  `ranti`.`user` (
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
  `dni` varchar(9) COLLATE utf8mb4_unicode_ci NOT NULL,
  `role` int(3) unsigned DEFAULT 1 COMMENT '0: ADMIN 1: MANAGER 2: CUSTOMER 3: OPERATOR',
  `address` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `phone` varchar(45) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

DROP TABLE IF EXISTS `ranti`.`user_company`;
CREATE TABLE  `ranti`.`user_company` (
  `user_id` int(10) NOT NULL,
  `company_id` int(10) NOT NULL,
  `user_company_id` int(10) NOT NULL,
  PRIMARY KEY (`user_id`,`company_id`),
  KEY `FK_user_company_2` (`company_id`),
  CONSTRAINT `FK_user_company_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_user_company_2` FOREIGN KEY (`company_id`) REFERENCES `company` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
