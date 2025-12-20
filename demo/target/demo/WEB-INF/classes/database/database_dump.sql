-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server-Version:               11.7.2-MariaDB - mariadb.org binary distribution
-- Server-Betriebssystem:        Win64
-- HeidiSQL Version:             12.10.0.7000
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


-- Exportiere Datenbank-Struktur für geisternetz_db
CREATE DATABASE IF NOT EXISTS `geisternetz_db` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci */;
USE `geisternetz_db`;

-- Exportiere Struktur von Tabelle geisternetz_db.geisternetz
CREATE TABLE IF NOT EXISTS `geisternetz` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `BREITENGRAD` double DEFAULT NULL,
  `GROESSE` int(11) DEFAULT NULL,
  `LAENGENGRAD` double DEFAULT NULL,
  `STATUS` varchar(255) DEFAULT NULL,
  `STATUSDATUM` datetime(6) DEFAULT NULL,
  `berger_id` bigint(20) DEFAULT NULL,
  `melder_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_GEISTERNETZ_berger_id` (`berger_id`),
  KEY `FK_GEISTERNETZ_melder_id` (`melder_id`),
  CONSTRAINT `FK_GEISTERNETZ_berger_id` FOREIGN KEY (`berger_id`) REFERENCES `person` (`ID`),
  CONSTRAINT `FK_GEISTERNETZ_melder_id` FOREIGN KEY (`melder_id`) REFERENCES `person` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Exportiere Daten aus Tabelle geisternetz_db.geisternetz: ~31 rows (ungefähr)
INSERT INTO `geisternetz` (`ID`, `BREITENGRAD`, `GROESSE`, `LAENGENGRAD`, `STATUS`, `STATUSDATUM`, `berger_id`, `melder_id`) VALUES
	(1, 70.9, 60, 60.8, 'GEBORGEN', '2025-12-08 19:38:56.835538', NULL, 1),
	(2, 11, 12, 10, 'GEBORGEN', '2025-12-08 19:39:24.003396', NULL, 2),
	(3, 10, 10, 10, 'GEBORGEN', '2025-12-09 21:53:09.466972', NULL, 3),
	(4, 0, 0, 0, 'GEBORGEN', '2025-12-10 18:48:01.989174', NULL, 4),
	(5, 7567, 6767, 28, 'GEBORGEN', '2025-12-07 09:41:41.030001', NULL, 5),
	(6, 55.5, 100, 66.6, 'GEBORGEN', '2025-12-08 19:50:21.540123', NULL, 6),
	(7, 35, 60, 45, 'GEBORGEN', '2025-12-08 19:39:03.634219', NULL, 7),
	(8, 8.9, 10, 5.5, 'GEBORGEN', '2025-12-10 18:48:08.136899', NULL, 8),
	(9, 11.11, 12, 10.1, 'GEBORGEN', '2025-12-08 21:05:03.807410', NULL, 9),
	(10, 0, 0, 0, 'GEBORGEN', '2025-12-10 18:48:30.646953', NULL, 10),
	(11, 56, 7, 45, 'GEBORGEN', '2025-12-10 18:48:29.099656', NULL, 14),
	(12, 6, 6, 6, 'GEBORGEN', '2025-12-10 18:48:25.235521', 16, 17),
	(13, 1, 1, 1, 'GEBORGEN', '2025-12-10 18:50:30.041028', 16, 15),
	(14, 2, 2, 2, 'GEBORGEN', '2025-12-10 19:14:23.914650', 16, 15),
	(15, 3, 3, 3, 'GEBORGEN', '2025-12-10 19:23:11.567711', 16, 15),
	(16, 5, 5, 5, 'GEBORGEN', '2025-12-10 19:24:25.566280', 16, 15),
	(17, 7, 7, 7, 'GEBORGEN', '2025-12-10 19:28:00.820630', 16, 15),
	(18, 7.7, 7, 7.7, 'GEBORGEN', '2025-12-10 19:45:49.705974', 16, 15),
	(19, 8, 8, 8, 'GEBORGEN', '2025-12-10 20:02:26.332226', 16, 15),
	(20, 11, 11, 11, 'GEBORGEN', '2025-12-10 19:47:06.583175', 18, 15),
	(21, 10, 10, 10, 'GEBORGEN', '2025-12-10 20:09:01.381002', 16, 15),
	(22, 12, 12, 12, 'GEBORGEN', '2025-12-10 20:27:20.967009', 16, 15),
	(23, 13, 13, 13, 'GEBORGEN', '2025-12-10 20:52:16.111867', 16, 15),
	(24, 15, 15, 15, 'GEBORGEN', '2025-12-10 21:11:22.329511', 16, 20),
	(25, 16, 16, 16, 'GEBORGEN', '2025-12-10 21:14:04.694952', 18, 15),
	(26, 16, 16, 16, 'GEBORGEN', '2025-12-10 21:27:13.704185', 16, 15),
	(27, 17, 17, 17, 'GEBORGEN', '2025-12-10 21:27:24.116622', 16, 15),
	(28, 11, 11, 11, 'IN_BERGUNG', '2025-12-10 21:47:24.517287', 21, 22),
	(29, 70, 8, 60, 'IN_BERGUNG', '2025-12-16 19:38:07.493677', 21, 24),
	(30, 55, 5, 55, 'GEMELDET', '2025-12-16 19:34:21.843079', NULL, 15),
	(31, 7, 7, 77, 'IN_BERGUNG', '2025-12-16 19:38:53.780120', 25, 20);

-- Exportiere Struktur von Tabelle geisternetz_db.person
CREATE TABLE IF NOT EXISTS `person` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `NAME` varchar(255) DEFAULT NULL,
  `ROLLE` varchar(255) DEFAULT NULL,
  `TELEFON` varchar(255) DEFAULT NULL,
  `PASSWORT` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Exportiere Daten aus Tabelle geisternetz_db.person: ~24 rows (ungefähr)
INSERT INTO `person` (`ID`, `NAME`, `ROLLE`, `TELEFON`, `PASSWORT`) VALUES
	(1, 'eric', NULL, '1223455', NULL),
	(2, 'test', NULL, '12345678', NULL),
	(3, 'eric', NULL, '775676', NULL),
	(4, 'max', NULL, 'z4z54', NULL),
	(5, 'testperson5', NULL, '36356', NULL),
	(6, 'Eric', NULL, '', NULL),
	(7, 'Testfrau1', NULL, '2345678', NULL),
	(8, 'Testfrau2', NULL, '1234456', NULL),
	(9, 'eric4', NULL, '5678910', NULL),
	(10, 'eric4', NULL, '5678910', NULL),
	(11, 'TestpersonA', 'MELDER', '017688888888', 'TestpersonA'),
	(12, 'TestpersonB', 'BERGER', '017688888888', 'TestpersoB'),
	(13, 'TestpersonC', 'BERGER', '017688888888', 'TestpersonC'),
	(14, 'TestpersonA', NULL, NULL, NULL),
	(15, 'TestMelder', 'MELDER', '', 'TestMelder'),
	(16, 'TestBerger', 'BERGER', '12345678910', 'TestBerger'),
	(17, 'TestMelderB', 'MELDER', '', 'TestMelderB'),
	(18, 'TestBergerB', 'BERGER', '017688888888', 'TestBergerB'),
	(19, 'TestMelderB', 'MELDER', '', 'TestMelderB'),
	(20, 'TestMelderD', 'MELDER', '', 'TestMelderD'),
	(21, 'FinalBergerA', 'BERGER', '0176123456789', 'FinalBergerA'),
	(22, 'FinalMelder', 'MELDER', '', 'FinalMelder'),
	(23, 'FinalBergerB', 'BERGER', '015112345678', 'FinbalBergerB'),
	(24, 'FinalMelderC', 'MELDER', '', 'FinalMelderC'),
	(25, 'FinalBergerC', 'BERGER', '12345689099', 'FinalBergerC');

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
