-- phpMyAdmin SQL Dump
-- version 4.6.6deb5ubuntu0.5
-- https://www.phpmyadmin.net/
--
-- Host: localhost:3306
-- Generation Time: Dec 25, 2020 at 12:53 PM
-- Server version: 5.7.32-0ubuntu0.18.04.1
-- PHP Version: 7.2.24-0ubuntu0.18.04.7

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";

CREATE DATABASE airport;
USE airport;


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `airport`
--

-- --------------------------------------------------------

--
-- Table structure for table `airport_user`
--

CREATE TABLE `airport_user` (
  `id` int(11) NOT NULL,
  `email` varchar(320) NOT NULL,
  `password` varchar(60) NOT NULL,
  `is_admin` tinyint(4) NOT NULL COMMENT '0-no;1-yes'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `airport_user`
--

INSERT INTO `airport_user` (`id`, `email`, `password`, `is_admin`) VALUES
(1, 'milica.radovanovic007@gmail.com', '$2y$08$SkNqbnl1Mi93U3VDZjV5Ru78ahKzSMi286ImAmb1srW4LXnAFoLMS', 1);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `airport_user`
--
ALTER TABLE `airport_user`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `airport_user`
--
ALTER TABLE `airport_user`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;


--
-- Table structure for table `gate`
--

CREATE TABLE `gate` (
  `id` int(11) NOT NULL,
  `name` varchar(50) NOT NULL,
  `status` tinyint(1) NOT NULL COMMENT '0-unavailable;1-available',
  `available_time_start` time DEFAULT NULL,
  `available_time_end` time DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `gate`
--

INSERT INTO `gate` (`id`, `name`, `status`, `available_time_start`, `available_time_end`) VALUES
(1, 'Gate 1', 0, '08:00:00', '17:00:00'),
(3, 'Gate 2', 0, '17:00:00', '23:00:00');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `gate`
--
ALTER TABLE `gate`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `gate`
--
ALTER TABLE `gate`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;

--
-- Table structure for table `flight`
--

CREATE TABLE `flight` (
  `flight_number` int(11) NOT NULL,
  `airline` varchar(50) NOT NULL,
  `gate_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `flight`
--

INSERT INTO `flight` (`flight_number`, `airline`, `gate_id`) VALUES
(1, 'Lufthansa', 1);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `flight`
--
ALTER TABLE `flight`
  ADD PRIMARY KEY (`flight_number`),
  ADD KEY `flight_fk_1` (`gate_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `flight`
--
ALTER TABLE `flight`
  MODIFY `flight_number` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;
--
-- Constraints for dumped tables
--

--
-- Constraints for table `flight`
--
ALTER TABLE `flight`
  ADD CONSTRAINT `flight_fk_1` FOREIGN KEY (`gate_id`) REFERENCES `gate` (`id`) ON UPDATE CASCADE;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
