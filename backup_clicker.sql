-- MySQL dump 10.13  Distrib 9.1.0, for Win64 (x86_64)
--
-- Host: localhost    Database: clicker
-- ------------------------------------------------------
-- Server version	9.1.0

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
-- Current Database: `clicker`
--

CREATE DATABASE /*!32312 IF NOT EXISTS*/ `clicker` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `clicker`;

--
-- Table structure for table `answers`
--

DROP TABLE IF EXISTS `answers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `answers` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` varchar(100) DEFAULT NULL,
  `question_id` int DEFAULT NULL,
  `selected_option` char(1) DEFAULT NULL,
  `submitted_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=42 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `answers`
--

LOCK TABLES `answers` WRITE;
/*!40000 ALTER TABLE `answers` DISABLE KEYS */;
INSERT INTO `answers` VALUES (1,'1',1,'C','2025-04-03 11:05:36'),(2,'1',1,'A','2025-04-03 13:05:05'),(3,'1',1,'B','2025-04-03 13:06:14'),(4,'1',1,'A','2025-04-03 13:44:26'),(5,'1',1,'A','2025-04-03 13:49:44'),(6,'1',1,'D','2025-04-03 13:57:13'),(7,'1',1,'D','2025-04-03 13:57:39'),(8,'1',1,'B','2025-04-03 14:17:42'),(9,'1',1,'C','2025-04-04 13:02:19'),(10,'1',1,'C','2025-04-04 13:19:28'),(11,'1',1,'D','2025-04-04 13:24:54'),(12,'1',1,'B','2025-04-04 13:42:22'),(13,'1',1,'B','2025-04-05 15:16:38'),(14,'1',1,'B','2025-04-05 16:13:22'),(15,'2',1,'A','2025-04-09 01:47:41'),(16,'1',1,'A','2025-04-15 10:54:28'),(17,'3',1,'A','2025-04-15 13:58:09'),(18,'3',1,'A','2025-04-15 13:59:18'),(19,'3',1,'D','2025-04-15 14:03:11'),(20,'3',1,'D','2025-04-15 14:03:38'),(21,'3',1,'C','2025-04-15 14:03:46'),(22,'3',2,'D','2025-04-15 14:13:06'),(23,'3',3,'D','2025-04-15 14:15:37'),(24,'3',1,'D','2025-04-15 14:23:53'),(25,'3',4,'D','2025-04-15 14:41:22'),(26,'3',5,'D','2025-04-15 14:49:16'),(27,'3',5,'C','2025-04-15 14:57:24'),(28,'3',5,'A','2025-04-15 14:57:59'),(29,'3',5,'B','2025-04-15 15:01:18'),(30,'3',2,'A','2025-04-15 15:44:49'),(31,'3',5,'D','2025-04-15 16:15:59'),(32,'3',1,'C','2025-04-16 02:36:16'),(33,'3',1,'A','2025-04-16 02:58:49'),(34,'3',3,'D','2025-04-16 03:05:06'),(35,'3',4,'A','2025-04-16 03:05:39'),(36,'3',1,'B','2025-04-16 03:08:32'),(37,'3',1,'B','2025-04-16 03:09:56'),(38,'3',1,'B','2025-04-16 03:10:33'),(39,'3',1,'B','2025-04-16 03:10:45'),(40,'3',1,'A','2025-04-16 03:11:24'),(41,'3',1,'A','2025-04-16 03:11:37');
/*!40000 ALTER TABLE `answers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `questions`
--

DROP TABLE IF EXISTS `questions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `questions` (
  `question_id` int NOT NULL AUTO_INCREMENT,
  `question_text` text NOT NULL,
  `option_a` varchar(255) DEFAULT NULL,
  `option_b` varchar(255) DEFAULT NULL,
  `option_c` varchar(255) DEFAULT NULL,
  `option_d` varchar(255) DEFAULT NULL,
  `correct_option` char(1) DEFAULT NULL,
  PRIMARY KEY (`question_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `questions`
--

LOCK TABLES `questions` WRITE;
/*!40000 ALTER TABLE `questions` DISABLE KEYS */;
INSERT INTO `questions` VALUES (1,'What is 5 + 3?','6','7','8','9','C'),(2,'What is 12 - 4?','6','8','9','10','B'),(3,'What is 6 × 7?','42','36','48','40','A'),(4,'What is 15 ÷ 3?','4','5','6','3','B'),(5,'What is the square of 9?','18','72','81','27','C');
/*!40000 ALTER TABLE `questions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL,
  `password` varchar(255) NOT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `phone` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'3','3','2025-04-15 13:05:34','12345678'),(2,'1','1','2025-04-16 03:15:29','12345678');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-04-16 11:24:01
