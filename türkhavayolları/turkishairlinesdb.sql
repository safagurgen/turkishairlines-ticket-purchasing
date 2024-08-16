-- MySQL dump 10.13  Distrib 8.0.34, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: turkishairlinesdb
-- ------------------------------------------------------
-- Server version	8.0.35

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `feedback`
--

DROP TABLE IF EXISTS `feedback`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `feedback` (
  `id` int NOT NULL AUTO_INCREMENT,
  `email` varchar(255) NOT NULL,
  `feedback` text NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `feedback`
--

LOCK TABLES `feedback` WRITE;
/*!40000 ALTER TABLE `feedback` DISABLE KEYS */;
INSERT INTO `feedback` VALUES (1,'safa@gmail.com','Merhaba'),(2,'safa@gmail.com','selamlar');
/*!40000 ALTER TABLE `feedback` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `flights`
--

DROP TABLE IF EXISTS `flights`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `flights` (
  `flight_id` int NOT NULL AUTO_INCREMENT,
  `from_location` varchar(100) NOT NULL,
  `to_location` varchar(100) NOT NULL,
  `flight_date` date NOT NULL,
  `passenger_count` int NOT NULL,
  `ticket_type` varchar(50) NOT NULL,
  `flight_type` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`flight_id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `flights`
--

LOCK TABLES `flights` WRITE;
/*!40000 ALTER TABLE `flights` DISABLE KEYS */;
INSERT INTO `flights` VALUES (1,'Istanbul','Milano','2024-06-01',180,'Ekonomi','Gidiş-Dönüş'),(2,'Istanbul','New York','2024-06-02',150,'Ekonomi','Tek Yön'),(3,'New York','Antalya','2024-06-03',200,'Ekonomi','İstanbul\'da Stopover'),(4,'Berlin','Ankara','2024-06-04',170,'Ekonomi','Çoklu Uçuş'),(5,'Amsterdam','Izmir','2024-06-05',160,'Ekonomi','Gidiş-Dönüş'),(6,'Istanbul','Moskova','2024-06-06',180,'Ekonomi','Gidiş-Dönüş'),(7,'Los Angeles','Antalya','2024-06-07',150,'Ekonomi','Tek Yön'),(8,'Istanbul','Kiev','2024-06-08',140,'Ekonomi','İstanbul\'da Stopover'),(9,'Viyana','Ankara','2024-06-09',190,'Ekonomi','Çoklu Uçuş'),(10,'Beyrut','Istanbul','2024-06-10',175,'Ekonomi','Gidiş-Dönüş'),(11,'Istanbul','Milano','2024-06-01',180,'Ekonomi','Gidiş-Dönüş'),(12,'Istanbul','New York','2024-06-02',150,'Ekonomi','Tek Yön'),(13,'New York','Antalya','2024-06-03',200,'Ekonomi','İstanbul\'da Stopover'),(14,'Berlin','Ankara','2024-06-04',160,'Ekonomi','Çoklu Uçuş'),(15,'Amsterdam','Izmir','2024-06-05',170,'Ekonomi','Gidiş-Dönüş'),(16,'Istanbul','Berlin','2024-06-06',180,'Ekonomi','Gidiş-Dönüş'),(17,'Istanbul','Paris','2024-06-07',150,'Ekonomi','Tek Yön'),(18,'New York','Istanbul','2024-06-08',200,'Ekonomi','İstanbul\'da Stopover'),(19,'Ankara','Berlin','2024-06-09',160,'Ekonomi','Çoklu Uçuş'),(20,'Izmir','Amsterdam','2024-06-10',170,'Ekonomi','Gidiş-Dönüş');
/*!40000 ALTER TABLE `flights` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `payments`
--

DROP TABLE IF EXISTS `payments`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `payments` (
  `payment_id` int NOT NULL AUTO_INCREMENT,
  `seat_id` int DEFAULT NULL,
  `card_number` varchar(16) DEFAULT NULL,
  `cvv` varchar(3) DEFAULT NULL,
  `expiry_date` varchar(5) DEFAULT NULL,
  `payment_amount` decimal(10,2) DEFAULT NULL,
  PRIMARY KEY (`payment_id`),
  KEY `seat_id` (`seat_id`),
  CONSTRAINT `payments_ibfk_1` FOREIGN KEY (`seat_id`) REFERENCES `seats` (`seat_id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `payments`
--

LOCK TABLES `payments` WRITE;
/*!40000 ALTER TABLE `payments` DISABLE KEYS */;
INSERT INTO `payments` VALUES (1,1,'123','123','123',2000.00),(2,1,'123','123','1234',2000.00),(3,1,'123','123','123',2000.00),(4,1,'123','123','123',6000.00),(5,6,'123','123','123',6000.00),(6,11,'123','123','123',6000.00),(7,1,'123','123','123',4000.00),(8,6,'123','123','123',4000.00),(9,1,'123','123','142',6000.00),(10,6,'123','123','142',6000.00),(11,7,'123','123','142',6000.00),(12,1,'123','123','132',14000.00),(13,6,'123','123','132',14000.00),(14,11,'123','123','132',14000.00),(15,16,'123','123','132',14000.00),(16,21,'123','123','132',14000.00),(17,22,'123','123','132',14000.00),(18,17,'123','123','132',14000.00);
/*!40000 ALTER TABLE `payments` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `seats`
--

DROP TABLE IF EXISTS `seats`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `seats` (
  `seat_id` int NOT NULL AUTO_INCREMENT,
  `flight_id` int DEFAULT NULL,
  `seat_number` varchar(10) DEFAULT NULL,
  `is_booked` tinyint(1) DEFAULT '0',
  `price` decimal(10,2) DEFAULT NULL,
  PRIMARY KEY (`seat_id`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `seats`
--

LOCK TABLES `seats` WRITE;
/*!40000 ALTER TABLE `seats` DISABLE KEYS */;
INSERT INTO `seats` VALUES (1,1,'Koltuk 1',1,2000.00),(2,1,'Koltuk 2',0,2000.00),(3,1,'Koltuk 3',0,2000.00),(4,1,'Koltuk 4',0,2000.00),(5,1,'Koltuk 5',0,2000.00),(6,1,'Koltuk 6',1,2000.00),(7,1,'Koltuk 7',1,2000.00),(8,1,'Koltuk 8',0,2000.00),(9,1,'Koltuk 9',0,2000.00),(10,1,'Koltuk 10',0,2000.00),(11,1,'Koltuk 11',1,2000.00),(12,1,'Koltuk 12',0,2000.00),(13,1,'Koltuk 13',0,2000.00),(14,1,'Koltuk 14',0,2000.00),(15,1,'Koltuk 15',0,2000.00),(16,1,'Koltuk 16',1,2000.00),(17,1,'Koltuk 17',1,2000.00),(18,1,'Koltuk 18',0,2000.00),(19,1,'Koltuk 19',0,2000.00),(20,1,'Koltuk 20',0,2000.00),(21,1,'Koltuk 21',1,2000.00),(22,1,'Koltuk 22',1,2000.00),(23,1,'Koltuk 23',0,2000.00),(24,1,'Koltuk 24',0,2000.00),(25,1,'Koltuk 25',0,2000.00);
/*!40000 ALTER TABLE `seats` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `user_id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `email` varchar(100) NOT NULL,
  `password` varchar(100) NOT NULL,
  `national_id` varchar(20) NOT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `email` (`email`),
  UNIQUE KEY `national_id` (`national_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'safa','safa@gmail.com','123','1234'),(3,'bora','bora@gmail.com','123','12345'),(5,'zeynel','zeynel@gmail','123','123');
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

-- Dump completed on 2024-05-20 13:34:11
