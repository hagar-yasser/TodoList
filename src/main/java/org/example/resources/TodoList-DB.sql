CREATE DATABASE IF NOT EXISTS TodoList DEFAULT CHARSET = utf8;
USE TodoList;

DROP TABLE IF EXISTS TodoItem;
CREATE TABLE `TodoItem` (
  `Title` varchar(200) NOT NULL,
  `Description` varchar(200) DEFAULT NULL,
  `Category` varchar(45) DEFAULT NULL,
  `Priority` varchar(45) NOT NULL,
  `StartDate` date NOT NULL,
  `EndDate` date NOT NULL,
  `IsFavorite` tinyint NOT NULL DEFAULT '0',
  PRIMARY KEY (`Title`),
  UNIQUE KEY `Title_UNIQUE` (`Title`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
