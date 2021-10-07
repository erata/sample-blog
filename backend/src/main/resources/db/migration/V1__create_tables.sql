CREATE DATABASE IF NOT EXISTS `blog`;
USE `blog`;

CREATE TABLE IF NOT EXISTS `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `about_user` varchar(255) DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  `email` varchar(100) NOT NULL,
  `image` varchar(255) DEFAULT NULL,
  `is_active` bit(1) DEFAULT NULL,
  `name` varchar(50) NOT NULL,
  `password` varchar(128) NOT NULL,
  `username` varchar(50) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_ob8kqyqqgmefl0aco34akdtpe` (`email`),
  UNIQUE KEY `UK_sb8bbouer5wak8vyiiy4pf2bx` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4;


CREATE TABLE IF NOT EXISTS `post` (
  `id` char(32) NOT NULL,
  `content` longtext NOT NULL,
  `created_at` datetime DEFAULT NULL,
  `is_published` bit(1) DEFAULT NULL,
  `title` varchar(100) NOT NULL,
  `updated_at` datetime DEFAULT NULL,
  `user_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_2jm25hjrq6iv4w8y1dhi0d9p4` (`title`),
  KEY `FK72mt33dhhs48hf9gcqrq4fxte` (`user_id`),
  CONSTRAINT `FK72mt33dhhs48hf9gcqrq4fxte` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS `category` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_46ccwnsi9409t36lurvtyljak` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS `comment` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `content` longtext NOT NULL,
  `created_at` datetime DEFAULT NULL,
  `email` varchar(100) NOT NULL,
  `name` varchar(50) NOT NULL,
  `post_id` char(32) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKs1slvnkuemjsq2kj4h3vhx7i1` (`post_id`),
  CONSTRAINT `FKs1slvnkuemjsq2kj4h3vhx7i1` FOREIGN KEY (`post_id`) REFERENCES `post` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS `aktivation_token` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `aktivation_token` varchar(255) DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  `user_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKhjrtky9wbd6lbk7mu9tuddqgn` (`user_id`),
  CONSTRAINT `FKhjrtky9wbd6lbk7mu9tuddqgn` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS `hibernate_sequence` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS `post_category` (
  `post_id` char(32) NOT NULL,
  `category_id` int(11) NOT NULL,
  PRIMARY KEY (`post_id`,`category_id`),
  KEY `FKqly0d5oc4npxdig2fjfoshhxg` (`category_id`),
  CONSTRAINT `FKqly0d5oc4npxdig2fjfoshhxg` FOREIGN KEY (`category_id`) REFERENCES `category` (`id`),
  CONSTRAINT `FKqr4dx4cx1lh4jfjchabytcakl` FOREIGN KEY (`post_id`) REFERENCES `post` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;