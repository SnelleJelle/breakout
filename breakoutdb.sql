-- phpMyAdmin SQL Dump
-- version 4.1.14
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Dec 18, 2014 at 11:59 PM
-- Server version: 5.6.17
-- PHP Version: 5.5.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `breakoutdb`
--

DELIMITER $$
--
-- Procedures
--
CREATE DEFINER=`root`@`localhost` PROCEDURE `getLevel`(IN `nr` INT(1) UNSIGNED)
    NO SQL
SELECT rows.rowid, rows.blockid, nrOfBlocksPerRow, verticalPadding, Width ,Height, Red, Green, Blue, Score, Density
FROM levels
JOIN rows ON levels.rowid = rows.rowid
JOIN blocks ON blocks.blockid = rows.blockid
WHERE levels.levelid = nr$$

DELIMITER ;

-- --------------------------------------------------------

--
-- Table structure for table `blocks`
--

CREATE TABLE IF NOT EXISTS `blocks` (
  `blockid` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `Width` int(11) unsigned NOT NULL,
  `Height` int(11) unsigned NOT NULL,
  `Red` int(11) unsigned NOT NULL,
  `Green` int(11) unsigned NOT NULL,
  `Blue` int(11) unsigned NOT NULL,
  `Score` int(11) unsigned NOT NULL,
  `Density` int(5) unsigned NOT NULL DEFAULT '1',
  PRIMARY KEY (`blockid`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=21 ;

--
-- Dumping data for table `blocks`
--

INSERT INTO `blocks` (`blockid`, `Width`, `Height`, `Red`, `Green`, `Blue`, `Score`, `Density`) VALUES
(1, 100, 30, 101, 48, 150, 23, 2),
(2, 80, 35, 55, 77, 35, 10, 1),
(3, 100, 35, 102, 23, 11, 35, 3),
(4, 80, 35, 10, 23, 74, 12, 1),
(5, 90, 35, 53, 122, 111, 25, 2),
(6, 70, 35, 120, 240, 95, 15, 1),
(7, 60, 30, 255, 0, 0, 7, 1),
(8, 110, 20, 100, 100, 100, 18, 2),
(9, 50, 35, 150, 240, 30, 8, 1),
(10, 180, 20, 0, 0, 0, 125, 5),
(11, 90, 40, 125, 200, 31, 10, 1),
(12, 100, 30, 25, 60, 100, 20, 2),
(13, 60, 20, 100, 100, 100, 8, 1),
(14, 95, 35, 250, 240, 19, 18, 2),
(15, 80, 20, 140, 151, 150, 9, 1),
(16, 100, 30, 174, 214, 50, 20, 2),
(17, 80, 40, 220, 14, 152, 20, 2),
(18, 70, 30, 12, 142, 250, 9, 1),
(19, 70, 35, 100, 150, 200, 11, 1),
(20, 120, 25, 214, 241, 231, 24, 2);

-- --------------------------------------------------------

--
-- Table structure for table `difficulty`
--

CREATE TABLE IF NOT EXISTS `difficulty` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `PowerUpChance` varchar(30) NOT NULL,
  `StartLives` int(11) NOT NULL,
  `PaddleLength` int(11) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=4 ;

--
-- Dumping data for table `difficulty`
--

INSERT INTO `difficulty` (`ID`, `PowerUpChance`, `StartLives`, `PaddleLength`) VALUES
(1, '0.7', 4, 130),
(2, '0.5', 3, 100),
(3, '0.3', 2, 70);

-- --------------------------------------------------------

--
-- Table structure for table `highscores`
--

CREATE TABLE IF NOT EXISTS `highscores` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `Nickname` varchar(12) NOT NULL,
  `Score` int(11) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=45 ;

--
-- Dumping data for table `highscores`
--

INSERT INTO `highscores` (`ID`, `Nickname`, `Score`) VALUES
(2, 'Jelle', 265),
(3, 'Tommy', 250),
(4, 'testnaam', 250),
(5, 'jelle', 9001),
(6, 'allo', 100),
(8, 'bob', 100),
(10, 'jan', 0),
(11, 'win', 140),
(12, 'Anonymous', 140),
(14, 'hallo', 120),
(16, 'mijnnaam', 100),
(23, 'hallobob', 280),
(24, 'domie', 1636),
(25, 'domie', 1636),
(26, 'multi', 1320),
(27, 'multi2', 1320),
(28, 'oei', 380),
(29, ' thatsbetter', 300),
(30, 'domie', 440),
(33, 'Anonymous', 340),
(34, 'domie', 480),
(35, 'nathalie', 80),
(36, 'nathalie', 960),
(37, 'vinnie', 240),
(38, 'vin', 180),
(39, 'tk', 1256),
(40, 'ffs', 1280),
(42, 'jjj', 60),
(43, 'vvvvv', 280),
(44, 'level een', 836);

-- --------------------------------------------------------

--
-- Table structure for table `highscoresmulti`
--

CREATE TABLE IF NOT EXISTS `highscoresmulti` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `NicknamePlayer1` varchar(12) NOT NULL,
  `ScorePlayer1` int(11) NOT NULL,
  `NicknamePlayer2` varchar(12) NOT NULL,
  `ScorePlayer2` int(11) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=35 ;

--
-- Dumping data for table `highscoresmulti`
--

INSERT INTO `highscoresmulti` (`ID`, `NicknamePlayer1`, `ScorePlayer1`, `NicknamePlayer2`, `ScorePlayer2`) VALUES
(2, 'Jan', 300, 'Bart', 500),
(3, 'speler1', 150, 'speler2', 150),
(4, 'speler1', 150, 'speler2', 150),
(5, 'speler1', 150, 'speler2', 150),
(6, 'speler1', 150, 'speler2', 150),
(7, 'speler1', 150, 'speler2', 150),
(8, 'speler1', 150, 'speler2', 150),
(9, 'speler1', 150, 'speler2', 150),
(10, 'speler1', 150, 'speler2', 150),
(11, 'speler1', 150, 'speler2', 150),
(12, 'speler1', 150, 'speler2', 150),
(13, 'speler1', 150, 'speler2', 150),
(14, 'speler1', 150, 'speler2', 150),
(15, 'speler1', 150, 'speler2', 150),
(16, 'speler1', 150, 'speler2', 150),
(17, 'speler1', 150, 'speler2', 150),
(18, 'testnaam1', 100, 'testnaam2', 150),
(19, 'testnaam1', 1000, 'testnaam2', 150),
(20, 'testnaam1', 750, 'testnaam2', 150),
(21, 'testnaam1', 500, 'testnaam2', 500),
(27, 'dominique', 0, 'nathalie', 0),
(28, 'ok1', 0, 'ok2', 0),
(29, 'jelly', 0, 'jelly2', 0),
(30, 'jelle', 80, 'domie', 80),
(31, 'Anonymous', 80, 'Anonymous2', 80),
(32, 'Anonymous', 80, 'Anonymous2', 80),
(33, 'winner', 300, 'loser:(', 260),
(34, 'ha', 260, 'dd', 240);

-- --------------------------------------------------------

--
-- Table structure for table `levels`
--

CREATE TABLE IF NOT EXISTS `levels` (
  `levelid` int(5) unsigned NOT NULL,
  `rowid` int(5) unsigned NOT NULL,
  PRIMARY KEY (`levelid`,`rowid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `levels`
--

INSERT INTO `levels` (`levelid`, `rowid`) VALUES
(1, 1),
(1, 2),
(1, 3),
(1, 4),
(1, 5),
(2, 2),
(2, 3),
(2, 6),
(2, 8),
(2, 9),
(3, 2),
(3, 4),
(3, 6),
(3, 7),
(3, 8),
(4, 16),
(4, 17),
(4, 18),
(4, 19),
(4, 20),
(5, 3),
(5, 6),
(5, 7),
(5, 9),
(5, 18),
(6, 2),
(6, 4),
(6, 10),
(6, 12),
(6, 15),
(7, 4),
(7, 5),
(7, 6),
(7, 8),
(7, 9),
(8, 3),
(8, 4),
(8, 6),
(8, 12),
(8, 17),
(9, 4),
(9, 7),
(9, 12),
(9, 13),
(9, 16),
(10, 2),
(10, 5),
(10, 6),
(10, 8),
(10, 15);

-- --------------------------------------------------------

--
-- Table structure for table `rows`
--

CREATE TABLE IF NOT EXISTS `rows` (
  `rowid` int(2) unsigned NOT NULL AUTO_INCREMENT,
  `blockid` int(5) unsigned NOT NULL,
  `nrOfBlocksPerRow` int(2) unsigned NOT NULL,
  `verticalPadding` int(2) unsigned NOT NULL,
  PRIMARY KEY (`rowid`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=21 ;

--
-- Dumping data for table `rows`
--

INSERT INTO `rows` (`rowid`, `blockid`, `nrOfBlocksPerRow`, `verticalPadding`) VALUES
(1, 1, 5, 10),
(2, 2, 11, 17),
(3, 3, 10, 17),
(4, 4, 13, 17),
(5, 10, 5, 10),
(6, 7, 14, 15),
(7, 15, 10, 15),
(8, 11, 6, 15),
(9, 10, 4, 15),
(10, 12, 8, 15),
(11, 9, 15, 10),
(12, 11, 10, 15),
(13, 8, 8, 10),
(14, 14, 9, 12),
(15, 13, 14, 8),
(16, 20, 8, 10),
(17, 19, 15, 12),
(18, 18, 12, 15),
(19, 1, 7, 15),
(20, 16, 8, 10);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
