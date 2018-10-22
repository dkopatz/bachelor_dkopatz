-- phpMyAdmin SQL Dump
-- version 4.0.10deb1
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Erstellungszeit: 12. Okt 2018 um 17:15
-- Server Version: 5.5.59-0ubuntu0.14.04.1
-- PHP-Version: 5.5.9-1ubuntu4.24

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Datenbank: `dkopatz`
--

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `bestueckungs_items`
--

CREATE TABLE IF NOT EXISTS `bestueckungs_items` (
  `Datum` date NOT NULL,
  `Stand_nr` varchar(4) NOT NULL,
  `Position` int(11) NOT NULL DEFAULT '0',
  `Sorte` varchar(20) DEFAULT NULL,
  `Menge` int(11) DEFAULT NULL,
  PRIMARY KEY (`Datum`,`Stand_nr`,`Position`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Daten für Tabelle `bestueckungs_items`
--


/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
