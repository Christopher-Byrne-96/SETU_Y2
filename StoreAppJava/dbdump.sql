-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Mar 31, 2023 at 11:48 PM
-- Server version: 10.4.27-MariaDB
-- PHP Version: 8.1.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `ca3`
--
CREATE DATABASE IF NOT EXISTS `ca3` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE `ca3`;

-- --------------------------------------------------------

--
-- Table structure for table `invoices`
--

CREATE TABLE `invoices` (
  `ID` int(11) NOT NULL,
  `Product_ID` int(11) NOT NULL,
  `Customer_ID` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `invoices`
--

INSERT INTO `invoices` (`ID`, `Product_ID`, `Customer_ID`) VALUES
(1, 1, 1),
(6, 4, 3),
(14, 5, 3),
(8, 6, 3),
(13, 6, 3),
(5, 7, 3),
(12, 7, 3),
(15, 7, 3),
(9, 8, 3),
(11, 8, 3),
(18, 8, 4),
(19, 8, 4),
(2, 9, 3),
(4, 9, 3),
(7, 9, 3),
(10, 9, 3),
(16, 9, 3),
(17, 9, 4),
(20, 9, 4),
(3, 10, 3),
(21, 10, 4);

-- --------------------------------------------------------

--
-- Table structure for table `products`
--

CREATE TABLE `products` (
  `ID` int(11) NOT NULL,
  `Name` varchar(50) NOT NULL,
  `Price` decimal(10,2) NOT NULL,
  `Short_Description` varchar(255) NOT NULL,
  `deleted` tinyint(1) DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `products`
--

INSERT INTO `products` (`ID`, `Name`, `Price`, `Short_Description`, `deleted`) VALUES
(1, 'AMD Ryzen 5 5600X', '299.99', '6-core, 12-thread desktop processor', 1),
(2, 'G.Skill Trident Z RGB 16GB (2 x 8GB) DDR4-3600', '29.99', 'High-speed memory kit for desktops', 1),
(3, 'Samsung 970 EVO Plus 1TB NVMe SSD', '199.99', 'High-performance solid-state drive for desktops and laptops', 1),
(4, 'MSI MPG B540 Gaming Edge WiFi ATX Motherboard', '179.99', 'Feature-packed motherboard for AMD Ryzen processors', 0),
(5, 'EVGA GeForce RTX 3070 FTW3 Ultra Gaming GPU', '999.99', 'High-end graphics card for gaming and content creation', 0),
(6, 'Corsair RM750x 750W 80+ Gold Certified Fully Modul', '139.99', 'High-quality power supply unit for desktops', 0),
(7, 'Noctua NH-D15 Chromax Black CPU Cooler', '109.99', 'Premium air cooler for high-end desktop processors', 0),
(8, 'Fractal Design Meshify C ATX Mid Tower Case', '99.99', 'Sleek and stylish computer case with excellent airflow', 0),
(9, 'Logitech G Pro X Mechanical Gaming Keyboard', '129.99', 'High-performance keyboard with customizable switches', 0),
(10, 'Logitech G502 HERO Wired Gaming Mouse', '69.99', 'High-precision gaming mouse with customizable weights', 0);

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `ID` int(11) NOT NULL,
  `Name` varchar(50) NOT NULL,
  `UserName` varchar(50) NOT NULL,
  `Password` varchar(50) NOT NULL,
  `user_type` varchar(10) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`ID`, `Name`, `UserName`, `Password`, `user_type`) VALUES
(1, 'John Byrne', 'jdoe', 'password', 'customer'),
(2, 'Mary Smith', 'msmith', 'password', 'customer'),
(3, 'Casper Ghost', 'spooky', 'password', 'customer'),
(4, 'James Deane', 'drifter', 'password', 'customer'),
(5, 'Chris Byrne', 'cbyrne199', 'password', 'admin');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `invoices`
--
ALTER TABLE `invoices`
  ADD PRIMARY KEY (`ID`,`Customer_ID`),
  ADD KEY `Product_ID` (`Product_ID`),
  ADD KEY `Customer_ID` (`Customer_ID`);

--
-- Indexes for table `products`
--
ALTER TABLE `products`
  ADD PRIMARY KEY (`ID`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`ID`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `invoices`
--
ALTER TABLE `invoices`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=22;

--
-- AUTO_INCREMENT for table `products`
--
ALTER TABLE `products`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `invoices`
--
ALTER TABLE `invoices`
  ADD CONSTRAINT `invoices_ibfk_1` FOREIGN KEY (`Product_ID`) REFERENCES `products` (`ID`),
  ADD CONSTRAINT `invoices_ibfk_2` FOREIGN KEY (`Customer_ID`) REFERENCES `users` (`ID`),
  ADD CONSTRAINT `invoices_ibfk_3` FOREIGN KEY (`Product_ID`) REFERENCES `products` (`ID`),
  ADD CONSTRAINT `invoices_ibfk_4` FOREIGN KEY (`Customer_ID`) REFERENCES `users` (`ID`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
