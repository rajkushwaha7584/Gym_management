-- MySQL dump 10.13  Distrib 8.0.33, for Win64 (x86_64)
--
-- Host: localhost    Database: gymdb
-- ------------------------------------------------------
-- Server version	8.0.33

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `app_user`
--

DROP TABLE IF EXISTS `app_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `app_user` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `enabled` bit(1) NOT NULL,
  `password` varchar(255) DEFAULT NULL,
  `role` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `app_user`
--

LOCK TABLES `app_user` WRITE;
/*!40000 ALTER TABLE `app_user` DISABLE KEYS */;
INSERT INTO `app_user` VALUES (6,_binary '','$2a$10$nUzXregTarZU7.Y.d47n0uf2xPCM5a5gjEO2JSwvBOi1GEDegFObC','ROLE_ADMIN','admin@gmail.com'),(21,_binary '','$2a$10$NHC5tHKye4lRRmrEq.AbNeXAfJBEJLQN0m5qiZ.1SVV7R1TZQbIrm','ROLE_MEMBER','rajputsanju2622@gmail.com'),(26,_binary '','$2a$10$CyPVZCpMa6ly6C8/Vwj.yOoUaLtrRZ12Ra4LAodOUGulg6/UL2La6','ROLE_MEMBER','roshanpatel0118@gmail.com'),(30,_binary '','$2a$10$LbD7TZt9rwLV1iFeATIVp.BmlP5MyrdTmR8grpMfqITsTc9YE0FKS','ROLE_MEMBER','0105ec221079@oriental.ac.in'),(32,_binary '','$2a$10$EboGSIbhZMqNmvgUD6aPWeLpFaz7rAhkdpo5kkja14ORsg4.zR61G','ROLE_MEMBER','RAJ24KUSH@GMAIL.COM'),(34,_binary '','$2a$10$m8Rt3IK0kADwDUk7TulG4e7LAZREK9FDfh/e6hWu.HKFI4TZrb3Ry','ROLE_MEMBER','0105al221154@oriental.ac.in');
/*!40000 ALTER TABLE `app_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `bmi_record`
--

DROP TABLE IF EXISTS `bmi_record`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `bmi_record` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `age` int NOT NULL,
  `bmi` double NOT NULL,
  `category` varchar(255) DEFAULT NULL,
  `date` date DEFAULT NULL,
  `gender` varchar(255) DEFAULT NULL,
  `height` double NOT NULL,
  `weight` double NOT NULL,
  `member_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKf9w8rebwivcxoy425gmldray` (`member_id`),
  CONSTRAINT `FKf9w8rebwivcxoy425gmldray` FOREIGN KEY (`member_id`) REFERENCES `member` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bmi_record`
--

LOCK TABLES `bmi_record` WRITE;
/*!40000 ALTER TABLE `bmi_record` DISABLE KEYS */;
INSERT INTO `bmi_record` VALUES (1,55,0,'Underweight','2025-07-03','Male',5452,52,37),(2,55,0,'Underweight','2025-07-03','Male',5452,52,37),(3,55,0,'Underweight','2025-07-03','Male',5452,52,37),(4,55,0,'Underweight','2025-07-03','Male',5452,52,37),(5,55,0,'Underweight','2025-07-03','Male',5452,52,37),(6,55,0,'Underweight','2025-07-03','Male',5452,52,37),(7,55,0,'Underweight','2025-07-03','Male',5452,52,37),(8,55,0,'Underweight','2025-07-03','Male',5452,52,37),(9,41,23.9,'Normal','2025-07-03','Male',170,69,37),(10,0,0,'Underweight','2025-07-03',NULL,0,0,37),(11,0,17.3,'Underweight','2025-07-03',NULL,170,50,37),(12,50,17.3,'Underweight','2025-07-03',NULL,170,50,37),(13,50,17.3,'Underweight','2025-07-03',NULL,170,50,37),(14,50,17.3,'Underweight','2025-07-03',NULL,170,50,37),(15,50,17.3,'Underweight','2025-07-03',NULL,170,50,37),(16,0,0,'Underweight','2025-07-03',NULL,0,0,37),(17,0,1471.1,'Obese','2025-07-03',NULL,55,445,37),(18,0,1471.1,'Obese','2025-07-03',NULL,55,445,37),(19,1,0,'Underweight','2025-07-03',NULL,0,0,37),(20,1,15.7,'Underweight','2025-07-03','Male',180,51,37),(21,22,18.8,'Normal','2025-07-03','Male',180,61,37),(22,44,23.7,'Normal','2025-07-03','Male',172,70,37);
/*!40000 ALTER TABLE `bmi_record` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `deleted_member`
--

DROP TABLE IF EXISTS `deleted_member`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `deleted_member` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `address` varchar(255) DEFAULT NULL,
  `age` int NOT NULL,
  `amount_paid` double NOT NULL,
  `deleted_on` date DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `full_name` varchar(255) DEFAULT NULL,
  `gender` varchar(255) DEFAULT NULL,
  `height` double NOT NULL,
  `membership_duration` int NOT NULL,
  `mobile_number` varchar(255) DEFAULT NULL,
  `registration_date` date DEFAULT NULL,
  `weight` double NOT NULL,
  `deleted_at` datetime(6) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=39 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `deleted_member`
--

LOCK TABLES `deleted_member` WRITE;
/*!40000 ALTER TABLE `deleted_member` DISABLE KEYS */;
INSERT INTO `deleted_member` VALUES (20,'RAJ KUSHWAHA ,HOUSE NO-100/1 ,WARD 01 -SARKHARI ,GRAM- CHAINPURA ,SAGAR MADHYA PRADESH 470125',25,9660,'2025-06-28','0105al221154@oriental.ac.in','Raj Kushwaha ','Male',122,5,'6264100725','2025-06-28',52,NULL,NULL),(21,'RAJ KUSHWAHA ,HOUSE NO-100/1 ,WARD 01 -SARKHARI ,GRAM- CHAINPURA ,SAGAR MADHYA PRADESH 470125',25,9660,'2025-06-28','0105al221154@oriental.ac.in','Raj Kushwaha ','Male',122,5,'6264100725','2025-06-28',52,NULL,NULL),(22,'RAJ KUSHWAHA ,HOUSE NO-100/1 ,WARD 01 -SARKHARI ,GRAM- CHAINPURA ,SAGAR MADHYA PRADESH 470125',0,1002,'2025-06-29','RAJ24KUSH@GMAIL.COM','Raj Kushwaha ','Male',0,1,'0626410072','2025-06-28',0,NULL,NULL),(23,'roshan patel , makronia sagar (M.P)',22,5500,'2025-06-29','roshanpatel0118@gmail.com','Roshan','Female',172,12,'6266228519','2025-06-28',69,NULL,NULL),(24,'roshan patel , makronia sagar (M.P)',22,5500,'2025-06-29','roshanpatel0118@gmail.com','Roshan','Female',172,12,'6266228519','2025-06-28',69,NULL,NULL),(27,'RAJ KUSHWAHA ,HOUSE NO-100/1 ,WARD 01 -SARKHARI ,GRAM- CHAINPURA ,SAGAR MADHYA PRADESH 470125',5,525,'2025-06-29','RAJ24KUSH@GMAIL.COM','Raj Kushwaha ','',520,5,'0626410072','2025-06-29',52,NULL,NULL),(28,'RAJ KUSHWAHA ,HOUSE NO-100/1 ,WARD 01 -SARKHARI ,GRAM- CHAINPURA ,SAGAR MADHYA PRADESH 470125',0,0,'2025-06-29','RAJ24KUSH@GMAIL.COM','Raj Kushwaha ','',0,5,'6264100725','2025-06-29',0,NULL,NULL),(29,'RAJ KUSHWAHA ,HOUSE NO-100/1 ,WARD 01 -SARKHARI ,GRAM- CHAINPURA ,SAGAR MADHYA PRADESH 470125',0,0,'2025-06-29','RAJ24KUSH@GMAIL.COM','Raj Kushwaha ','',0,5,'0626410072','2025-06-29',0,NULL,NULL),(30,'RAJ KUSHWAHA ,HOUSE NO-100/1 ,WARD 01 -SARKHARI ,GRAM- CHAINPURA ,SAGAR MADHYA PRADESH 470125',0,0,'2025-06-29','abc@gmail.com','Raj Kushwaha ','',0,5,'6264100722','2025-06-29',0,NULL,NULL),(31,'RAJ KUSHWAHA ,HOUSE NO-100/1 ,WARD 01 -SARKHARI ,GRAM- CHAINPURA ,SAGAR MADHYA PRADESH 470125',0,0,'2025-06-29','RAJ24KUSH@GMAIL.COM','Raj Kushwaha ','',0,6,'6264100725','2025-06-29',0,NULL,NULL),(32,'RAJ KUSHWAHA ,HOUSE NO-100/1 ,WARD 01 -SARKHARI ,GRAM- CHAINPURA ,SAGAR MADHYA PRADESH 470125',0,0,'2025-06-29','RAJ24KUSH@GMAIL.COM','Raj','Male',0,1,'0626410072','2025-06-29',0,NULL,NULL),(33,'RAJ KUSHWAHA ,HOUSE NO-100/1 ,WARD 01 -SARKHARI ,GRAM- CHAINPURA ,SAGAR MADHYA PRADESH 470125',55,2020,'2025-06-29','RAJ24KUSH@GMAIL.COM','Raj','Male',158,522010,'6264100725','2025-06-29',51,NULL,NULL),(34,'RAJ KUSHWAHA ,HOUSE NO-100/1 ,WARD 01 -SARKHARI ,GRAM- CHAINPURA ,SAGAR MADHYA PRADESH 470125',58,3523,'2025-07-04','0105al221154@oriental.ac.in','Raj Kushwaha ','Male',100,1,'6264100725','2025-06-29',63,NULL,NULL),(36,'RAJ KUSHWAHA ,B-58 Patel Negar',0,0,'2025-07-03','RAJ24KUSH@GMAIL.COM','kk','',0,5,'6264100721','2025-07-03',0,NULL,NULL),(38,'sector 58 patel nager bhopal mp',0,0,'2025-07-03','kkJ24KUSH@GMAIL.COM','Raj Kushwaha ','Male',0,5,'1234567890','2025-07-03',0,NULL,NULL);
/*!40000 ALTER TABLE `deleted_member` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `gallery_image`
--

DROP TABLE IF EXISTS `gallery_image`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `gallery_image` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `category` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `filename` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `upload_date` varchar(255) DEFAULT NULL,
  `caption` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `gallery_image`
--

LOCK TABLES `gallery_image` WRITE;
/*!40000 ALTER TABLE `gallery_image` DISABLE KEYS */;
INSERT INTO `gallery_image` VALUES (2,'transformation','hard work','gym_logo.png','good','2025-07-04',NULL),(3,'transformation','mn ','gym logo.ico','2545','2025-07-04',NULL),(4,'member','good','gym_logo.png','work','2025-07-04',NULL),(5,'trainer','worl hard','gym logo.ico','asdfasfa','2025-07-04',NULL),(6,'trainer','vvvvvvvvvvvvvvvvv','a9dfc15c-593a-464d-89ec-b93e479a029f-removebg-preview.png','aaaaaaaaaaaaaaaaaaaaa','2025-07-04',NULL),(7,'member','asdfasffs','Raj.jpg','asdfafafffds','2025-07-04',NULL);
/*!40000 ALTER TABLE `gallery_image` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `member`
--

DROP TABLE IF EXISTS `member`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `member` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `address` varchar(255) DEFAULT NULL,
  `age` int NOT NULL,
  `amount_paid` double NOT NULL,
  `email` varchar(255) NOT NULL,
  `emergency_contact` varchar(255) DEFAULT NULL,
  `full_name` varchar(255) NOT NULL,
  `gender` varchar(255) DEFAULT NULL,
  `height` double NOT NULL,
  `membership_duration` int NOT NULL,
  `payment_mode` varchar(255) DEFAULT NULL,
  `payment_status` bit(1) NOT NULL,
  `phone` varchar(255) NOT NULL,
  `profile_picture` varchar(255) DEFAULT NULL,
  `registration_date` date DEFAULT NULL,
  `weight` double NOT NULL,
  `password` varchar(255) NOT NULL,
  `role` varchar(255) DEFAULT NULL,
  `expiry_date` date DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_email` (`email`),
  UNIQUE KEY `unique_phone` (`phone`)
) ENGINE=InnoDB AUTO_INCREMENT=40 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `member`
--

LOCK TABLES `member` WRITE;
/*!40000 ALTER TABLE `member` DISABLE KEYS */;
INSERT INTO `member` VALUES (25,'roshan patel , makronia sagar (M.P)',22,55400,'roshanpatel0118@gmail.com','9876543210','Roshan','Male',172,12,'Online',_binary '\0','6266228519','default_male.jpeg','2025-06-28',69,'$2a$10$CyPVZCpMa6ly6C8/Vwj.yOoUaLtrRZ12Ra4LAodOUGulg6/UL2La6','ROLE_MEMBER',NULL),(26,'bhopal ',22,5500,'rajputsanju2622@gmail.com','1000000000','sangram Rajput ','Male',170,1,'Online',_binary '','7489405485','default_male.jpeg','2025-06-28',50,'$2a$10$NHC5tHKye4lRRmrEq.AbNeXAfJBEJLQN0m5qiZ.1SVV7R1TZQbIrm','ROLE_MEMBER',NULL),(35,'patel nagar bhopal',22,5600,'0105ec221079@oriental.ac.in','1000000000','Veraj','Male',168,5,'Online',_binary '','8809391678','default_male.jpeg','2025-06-30',72,'$2a$10$LbD7TZt9rwLV1iFeATIVp.BmlP5MyrdTmR8grpMfqITsTc9YE0FKS','ROLE_MEMBER','2025-07-06'),(37,'RAJ KUSHWAHA ,HOUSE NO-100/1 ,WARD 01 -SARKHARI ,GRAM- CHAINPURA ,SAGAR MADHYA PRADESH 470125',55,754210,'RAJ24KUSH@GMAIL.COM','1000000000','Raj Kushwaha ','Other',1520,1,'Online',_binary '','0626410072','default_male.jpeg','2025-07-03',52,'$2a$10$EboGSIbhZMqNmvgUD6aPWeLpFaz7rAhkdpo5kkja14ORsg4.zR61G','ROLE_MEMBER','2025-07-11'),(39,'RAJ KUSHWAHA ,HOUSE NO-100/1 ,WARD 01 -SARKHARI ,GRAM- CHAINPURA ,SAGAR MADHYA PRADESH 470125',0,0,'0105al221154@oriental.ac.in','','Raj Kushwaha ','',0,1,'',_binary '\0','6264100727','default_male.jpeg','2025-07-04',0,'$2a$10$m8Rt3IK0kADwDUk7TulG4e7LAZREK9FDfh/e6hWu.HKFI4TZrb3Ry','ROLE_MEMBER',NULL);
/*!40000 ALTER TABLE `member` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `message_log`
--

DROP TABLE IF EXISTS `message_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `message_log` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `email` varchar(255) DEFAULT NULL,
  `error_message` text,
  `member_name` varchar(255) DEFAULT NULL,
  `message_content` varchar(1500) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `success` bit(1) NOT NULL,
  `timestamp` datetime(6) DEFAULT NULL,
  `error` varchar(255) DEFAULT NULL,
  `message` varchar(255) DEFAULT NULL,
  `date_time` datetime(6) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `message_log`
--

LOCK TABLES `message_log` WRITE;
/*!40000 ALTER TABLE `message_log` DISABLE KEYS */;
/*!40000 ALTER TABLE `message_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `reminder_log`
--

DROP TABLE IF EXISTS `reminder_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `reminder_log` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `days_before_expiry` int DEFAULT NULL,
  `reminder_date` date NOT NULL,
  `member_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKtl4embjykd20nd7vp3839gu04` (`member_id`,`reminder_date`),
  CONSTRAINT `FKbwors0k5lfxdcvj6refc00idd` FOREIGN KEY (`member_id`) REFERENCES `member` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `reminder_log`
--

LOCK TABLES `reminder_log` WRITE;
/*!40000 ALTER TABLE `reminder_log` DISABLE KEYS */;
INSERT INTO `reminder_log` VALUES (1,7,'2025-07-04',37);
/*!40000 ALTER TABLE `reminder_log` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-07-05  2:29:28
