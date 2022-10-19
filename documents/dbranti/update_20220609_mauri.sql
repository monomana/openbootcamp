DROP TABLE IF EXISTS `menu`;
CREATE TABLE  `menu` (
  `id` int(4) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `url` varchar(45) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `icon` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT 'fa fa-open',
  `parent_id` int(4) DEFAULT NULL,
  `order` int(4) DEFAULT 0,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

DROP TABLE IF EXISTS `company_menu`;
CREATE TABLE  `company_menu` (
  `company_id` int(4) NOT NULL,
  `menu_id` int(4) NOT NULL,
  `state` tinyint(1) DEFAULT 1,
  PRIMARY KEY (`company_id`,`menu_id`),
  KEY `menu_id` (`menu_id`),
  CONSTRAINT `company_menu` FOREIGN KEY (`company_id`) REFERENCES `company` (`id`),
  CONSTRAINT `menu_company_ibfk_2` FOREIGN KEY (`menu_id`) REFERENCES `menu` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


