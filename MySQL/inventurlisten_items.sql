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
-- Tabellenstruktur für Tabelle `inventurlisten_items`
--

CREATE TABLE IF NOT EXISTS `inventurlisten_items` (
  `Datum` date NOT NULL,
  `Kategorie` varchar(30) NOT NULL,
  `Produkt` varchar(100) NOT NULL,
  `Anzahl` int(11) DEFAULT '0',
  `Einheit` varchar(20) NOT NULL,
  `Bemerkung` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`Datum`,`Kategorie`,`Produkt`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Daten für Tabelle `inventurlisten_items`
--


/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
