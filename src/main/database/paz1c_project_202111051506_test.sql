
create database if not exists paz1c_project_test;

-- 
-- Disable foreign keys
-- 
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;

-- 
-- Set SQL mode
-- 
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

-- 
-- Set character set the client will use to send SQL statements to the server
--
SET NAMES 'utf8';

--
-- Set default database
--
USE paz1c_project_test;

--
-- Drop table `prosuctonposition`
--
DROP TABLE IF EXISTS prosuctonposition;

--
-- Drop table `positions`
--
DROP TABLE IF EXISTS positions;

--
-- Drop table `history`
--
DROP TABLE IF EXISTS history;

--
-- Drop table `productsinorder`
--
DROP TABLE IF EXISTS productsinorder;

--
-- Drop table `products`
--
DROP TABLE IF EXISTS products;

--
-- Drop table `categories`
--
DROP TABLE IF EXISTS categories;

--
-- Drop table `order`
--
DROP TABLE IF EXISTS `order`;

--
-- Drop table `user`
--
DROP TABLE IF EXISTS user;

--
-- Drop table `roles`
--
DROP TABLE IF EXISTS roles;

--
-- Set default database
--
USE paz1c_project_test;

--
-- Create table `roles`
--
CREATE TABLE roles (
  idRoles int NOT NULL AUTO_INCREMENT,
  role varchar(255) NOT NULL,
  PRIMARY KEY (idRoles)
)
ENGINE = INNODB,
AUTO_INCREMENT = 51,
CHARACTER SET utf8mb4,
COLLATE utf8mb4_0900_ai_ci;

--
-- Create table `user`
--
CREATE TABLE user (
  idUser int NOT NULL AUTO_INCREMENT,
  name varchar(50) NOT NULL,
  surname varchar(255) NOT NULL,
  dateOfBirth date NOT NULL,
  login varchar(255) NOT NULL,
  password varchar(255) NOT NULL,
  role int NOT NULL,
  PRIMARY KEY (idUser)
)
ENGINE = INNODB,
AUTO_INCREMENT = 51,
CHARACTER SET utf8mb4,
COLLATE utf8mb4_0900_ai_ci;

--
-- Create index `login` on table `user`
--
ALTER TABLE user
ADD UNIQUE INDEX login (login);

--
-- Create foreign key
--
ALTER TABLE user
ADD CONSTRAINT FK_user_role FOREIGN KEY (role)
REFERENCES roles (idRoles);

--
-- Create table `order`
--
CREATE TABLE `order` (
  idOrder int NOT NULL AUTO_INCREMENT,
  Name varchar(50) NOT NULL,
  Summ double NOT NULL,
  OrderStatus varchar(255) NOT NULL,
  SalesMan int NOT NULL,
  PRIMARY KEY (idOrder)
)
ENGINE = INNODB,
CHARACTER SET utf8mb4,
COLLATE utf8mb4_0900_ai_ci;

--
-- Create foreign key
--
ALTER TABLE `order`
ADD CONSTRAINT FK_order_SalesMan FOREIGN KEY (SalesMan)
REFERENCES user (idUser);

--
-- Create table `categories`
--
CREATE TABLE categories (
  idCategories int NOT NULL AUTO_INCREMENT,
  categoria varchar(255) NOT NULL,
  PRIMARY KEY (idCategories)
)
ENGINE = INNODB,
AUTO_INCREMENT = 51,
CHARACTER SET utf8mb4,
COLLATE utf8mb4_0900_ai_ci;

--
-- Create index `categoria` on table `categories`
--
ALTER TABLE categories
ADD UNIQUE INDEX categoria (categoria);

--
-- Create table `products`
--
CREATE TABLE products (
  idProduct int NOT NULL AUTO_INCREMENT,
  name varchar(50) NOT NULL,
  manufacture varchar(255) NOT NULL,
  EAN varchar(255) NOT NULL,
  weight double NOT NULL,
  taste varchar(255) DEFAULT NULL,
  height double NOT NULL,
  length double NOT NULL,
  width double NOT NULL,
  price int NOT NULL,
  piecesInPackage int NOT NULL,
  Categories int NOT NULL,
  PRIMARY KEY (idProduct)
)
ENGINE = INNODB,
AUTO_INCREMENT = 51,
CHARACTER SET utf8mb4,
COLLATE utf8mb4_0900_ai_ci;

--
-- Create foreign key
--
ALTER TABLE products
ADD CONSTRAINT FK_products_Categories FOREIGN KEY (Categories)
REFERENCES categories (idCategories);

--
-- Create table `productsinorder`
--
CREATE TABLE productsinorder (
  idOrder int NOT NULL,
  idProducts int NOT NULL,
  count int NOT NULL
)
ENGINE = INNODB,
CHARACTER SET utf8mb4,
COLLATE utf8mb4_0900_ai_ci;

--
-- Create foreign key
--
ALTER TABLE productsinorder
ADD CONSTRAINT FK_productsinorder_idOrder FOREIGN KEY (idOrder)
REFERENCES `order` (idOrder);

--
-- Create foreign key
--
ALTER TABLE productsinorder
ADD CONSTRAINT FK_productsinorder_idProducts FOREIGN KEY (idProducts)
REFERENCES products (idProduct);

--
-- Create table `history`
--
CREATE TABLE history (
  idprodukt int NOT NULL,
  iduser int NOT NULL,
  idposition int NOT NULL,
  added int DEFAULT NULL,
  deduct int DEFAULT NULL
)
ENGINE = INNODB,
CHARACTER SET utf8mb4,
COLLATE utf8mb4_0900_ai_ci;

--
-- Create foreign key
--
ALTER TABLE history
ADD CONSTRAINT FK_historia_idprodukt FOREIGN KEY (idprodukt)
REFERENCES products (idProduct);

--
-- Create foreign key
--
ALTER TABLE history
ADD CONSTRAINT FK_historia_iduser FOREIGN KEY (iduser)
REFERENCES user (idUser);

--
-- Create table `positions`
--
CREATE TABLE positions (
  id int NOT NULL AUTO_INCREMENT,
  floor int NOT NULL,
  positionNumber int NOT NULL,
  shelf varchar(255) NOT NULL,
  height double NOT NULL,
  weight double NOT NULL,
  lenght double NOT NULL,
  BearingCapacity double NOT NULL,
  PRIMARY KEY (id)
)
ENGINE = INNODB,
AUTO_INCREMENT = 51,
CHARACTER SET utf8mb4,
COLLATE utf8mb4_0900_ai_ci;

--
-- Create table `prosuctonposition`
--
CREATE TABLE prosuctonposition (
  idProduct int NOT NULL,
  idPosition int NOT NULL,
  count int NOT NULL
)
ENGINE = INNODB,
CHARACTER SET utf8mb4,
COLLATE utf8mb4_0900_ai_ci;

--
-- Create foreign key
--
ALTER TABLE prosuctonposition
ADD CONSTRAINT FK_prosuctonposition_idPosition FOREIGN KEY (idPosition)
REFERENCES positions (id);

--
-- Create foreign key
--
ALTER TABLE prosuctonposition
ADD CONSTRAINT FK_prosuctonposition_idProduct FOREIGN KEY (idProduct)
REFERENCES products (idProduct);

-- 
-- Dumping data for table roles
--
INSERT INTO roles VALUES
(1, 'skladnik\r\n'),
(2, 'admin\r\n'),
(3, 'veduci'),
(4, 'predajca');

-- 
-- Dumping data for table user
--
INSERT INTO user VALUES
(1, 'Ayanna518', 'Vanwinkle', '1979-10-06', 'Burton92', 'n2cl9nn1VcC/W8Hou1aHRw==', 3),
(2, 'Karima847', 'Shin', '1951-11-16', 'Andrade718', '1kOwmq+6of12T1MG7flbzg==', 4),
(3, 'Santos2004', 'Hutcherson', '1993-07-08', 'Stacy1964', 'XiF/Z1oFexcvQc5DMop+Ug==', 1),
(4, 'Erwin1951', 'Lowry', '1951-02-16', 'Amaya5', '7zhX1XzU1vyxym8PN473+w==', 2);

-- 
-- Dumping data for table categories
--
INSERT INTO categories VALUES
(1, 'AAAJQDXMRVHYII'),
(2, 'ANDLSSBPFEXZMDUPILKLU'),
(3, 'BNBSVW'),
(4, 'BUQN');


-- 
-- Dumping data for table positions
--
INSERT INTO positions VALUES
(1, 7, 4, 'o', 47, 14, 10, 641),
(2, 6, 2, 'f', 6, 12, 47, 892),
(3, 8, 2, 'a', 8, 32, 29, 553),
(4, 7, 8, 'e', 7, 18, 43, 756);

-- 
-- Dumping data for table `order`
--
-- Table paz1c_project_test.`order` does not contain any data (it is empty)

-- 
-- Dumping data for table products
--
INSERT INTO products VALUES
(1, 'Antaridge', 'Bayfazon', '944-6-940-37571-5', 25, NULL, 4, 1, 4,1, 25, 1),
(2, 'Monwoofridge', 'Venlamalol', '084-6-970-03691-1', 81, 'XIMWGREREFHUSRVKKDCCRUPOPVPAMEEBNGCOLRX', 42, 1, 42,1, 4, 2),
(3, 'Chartoplet', 'Naposoprofen', '446-4-047-69938-6', 7, 'WTRTLZHCHSHAAHUXDZDLYIDVVXYDIDPDEDJVWBHBAPZGQXNYTBFZIWNKBPQHZTLKZQDHTFW', 49, 40, 49,1, 18, 3),
(4, 'Prolifiar', 'Novotamsol', '844-8-051-48685-5', 17, 'SRHGDZLQQMYCOFWEBUOFNFPNJCMPBKHEYFBXVNFMYXMCMPTWDQHZSZNWDEQJFYXISKOIXTILHBWSKYUNKGUJGNPLRJRQHXMYWSIABYMXMIJBOLBGWWFATR', 27, 95, 27,1, 26, 4);

-- 
-- Dumping data for table prosuctonposition
--
-- Table paz1c_project_test.prosuctonposition does not contain any data (it is empty)

-- 
-- Dumping data for table productsinorder
--
-- Table paz1c_project_test.productsinorder does not contain any data (it is empty)

-- 
-- Dumping data for table history
--
-- Table paz1c_project_test.history does not contain any data (it is empty)

-- 
-- Restore previous SQL mode
-- 
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;

-- 
-- Enable foreign keys
-- 
/*!40014 SET FOREIGN_KEY_CHECKS = @OLD_FOREIGN_KEY_CHECKS */;