/*
SQLyog Community v12.5.1 (64 bit)
MySQL - 5.6.25 : Database - test
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`test` /*!40100 DEFAULT CHARACTER SET latin1 */;

USE `test`;

/*Table structure for table `excel_test` */

DROP TABLE IF EXISTS `excel_test`;

CREATE TABLE `excel_test` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(30) NOT NULL,
  `address` varchar(30) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=latin1;

/*Data for the table `excel_test` */

insert  into `excel_test`(`id`,`name`,`address`) values 
(1,'Jay Jay Okocha','Nigeria'),
(2,'Andy Cole','UK'),
(3,'James','UK'),
(4,'Jon','westeros'),
(5,'Sherlock','UK'),
(6,'Jack','USA'),
(7,'Tapiwa Nengomasha','Zim'),
(8,'Gift Muzadzi','Zim'),
(9,'Tapuwa Kapini','Zim'),
(10,'Harrington Shereni','Zim'),
(11,'Moses Chunga','Zim'),
(12,'Tapiwa Nengomasha','Zam'),
(13,'Gift Muzadzi','Zam'),
(14,'Tapuwa Kapini','Zam'),
(15,'Harrington Shereni','Zam'),
(16,'Moses Chunga','Zam'),
(17,'Musonda','null'),
(18,'Kampula','null');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
