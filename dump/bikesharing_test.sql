-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Creato il: Gen 17, 2022 alle 17:21
-- Versione del server: 10.4.21-MariaDB
-- Versione PHP: 8.0.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `bikesharing`
--

-- --------------------------------------------------------

--
-- Struttura della tabella `abbonamento`
--

CREATE TABLE `abbonamento` (
  `id` int(11) NOT NULL,
  `data_inizio` datetime NOT NULL,
  `tipoAbbonamento` enum('ANNUALE','GIORNALIERO','SETTIMANALE') NOT NULL,
  `utente` int(11) NOT NULL,
  `segnalazioni` int(11) NOT NULL DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dump dei dati per la tabella `abbonamento`
--

INSERT INTO `abbonamento` (`id`, `data_inizio`, `tipoAbbonamento`, `utente`, `segnalazioni`) VALUES
(2, '2022-01-05 00:00:00', 'ANNUALE', 2, 0);

-- --------------------------------------------------------

--
-- Struttura della tabella `bicicletta`
--

CREATE TABLE `bicicletta` (
  `id` int(11) NOT NULL,
  `tipo` enum('CLASSICA','ELETTRICA','ELETTRICA CON SEGGIOLINO') NOT NULL,
  `danneggiata` tinyint(1) NOT NULL DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dump dei dati per la tabella `bicicletta`
--

INSERT INTO `bicicletta` (`id`, `tipo`, `danneggiata`) VALUES
(46, 'ELETTRICA', 0),
(48, 'ELETTRICA', 0),
(49, 'ELETTRICA CON SEGGIOLINO', 0),
(50, 'ELETTRICA', 0),
(51, 'CLASSICA', 0),
(53, 'CLASSICA', 0),
(55, 'CLASSICA', 0),
(56, 'CLASSICA', 0),
(57, 'ELETTRICA', 0),
(58, 'ELETTRICA CON SEGGIOLINO', 0),
(59, 'ELETTRICA', 0),
(64, 'ELETTRICA', 0),
(65, 'ELETTRICA CON SEGGIOLINO', 0),
(66, 'ELETTRICA', 0),
(68, 'ELETTRICA', 0),
(70, 'CLASSICA', 0),
(71, 'CLASSICA', 0),
(72, 'CLASSICA', 0),
(73, 'CLASSICA', 0),
(74, 'CLASSICA', 0),
(75, 'CLASSICA', 0),
(76, 'ELETTRICA', 0),
(77, 'ELETTRICA CON SEGGIOLINO', 0),
(78, 'ELETTRICA', 0),
(79, 'ELETTRICA', 0),
(92, 'CLASSICA', 0),
(93, 'ELETTRICA', 0),
(94, 'ELETTRICA', 0),
(95, 'ELETTRICA', 0);

-- --------------------------------------------------------

--
-- Struttura della tabella `morsa`
--

CREATE TABLE `morsa` (
  `id` int(11) NOT NULL,
  `tipo` enum('CLASSICA','ELETTRICA') NOT NULL,
  `rastrelliera` int(11) NOT NULL,
  `bicicletta` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dump dei dati per la tabella `morsa`
--

INSERT INTO `morsa` (`id`, `tipo`, `rastrelliera`, `bicicletta`) VALUES
(52, 'ELETTRICA', 9, NULL),
(53, 'ELETTRICA', 9, 94),
(54, 'ELETTRICA', 9, NULL),
(55, 'ELETTRICA', 9, 95),
(56, 'ELETTRICA', 9, NULL),
(57, 'CLASSICA', 9, 55),
(58, 'CLASSICA', 9, 92),
(59, 'CLASSICA', 9, NULL),
(60, 'CLASSICA', 9, NULL),
(61, 'CLASSICA', 9, NULL),
(62, 'CLASSICA', 9, NULL),
(63, 'CLASSICA', 9, NULL),
(64, 'CLASSICA', 9, NULL),
(65, 'CLASSICA', 9, NULL),
(66, 'CLASSICA', 9, NULL),
(67, 'ELETTRICA', 10, 48),
(68, 'ELETTRICA', 10, 49),
(69, 'ELETTRICA', 10, 93),
(70, 'ELETTRICA', 10, 50),
(71, 'ELETTRICA', 10, 46),
(72, 'ELETTRICA', 10, 58),
(73, 'ELETTRICA', 10, 64),
(74, 'ELETTRICA', 10, 57),
(75, 'ELETTRICA', 10, 68),
(76, 'ELETTRICA', 10, NULL),
(77, 'CLASSICA', 10, 53),
(78, 'CLASSICA', 10, NULL),
(79, 'CLASSICA', 10, NULL),
(80, 'CLASSICA', 10, NULL),
(81, 'CLASSICA', 10, NULL),
(82, 'CLASSICA', 10, NULL),
(83, 'CLASSICA', 10, NULL),
(84, 'CLASSICA', 10, NULL),
(85, 'CLASSICA', 10, NULL),
(86, 'CLASSICA', 10, NULL),
(87, 'CLASSICA', 10, NULL),
(88, 'CLASSICA', 10, NULL),
(89, 'CLASSICA', 10, NULL),
(90, 'CLASSICA', 10, NULL),
(91, 'CLASSICA', 10, NULL),
(92, 'ELETTRICA', 11, NULL),
(93, 'ELETTRICA', 11, 66),
(94, 'ELETTRICA', 11, 59),
(95, 'CLASSICA', 11, NULL),
(96, 'CLASSICA', 11, 51),
(97, 'CLASSICA', 11, 56),
(126, 'ELETTRICA', 13, 76),
(127, 'ELETTRICA', 13, 77),
(128, 'ELETTRICA', 13, 78),
(129, 'ELETTRICA', 13, 79),
(130, 'ELETTRICA', 13, NULL),
(131, 'ELETTRICA', 13, NULL),
(132, 'ELETTRICA', 13, NULL),
(133, 'ELETTRICA', 13, NULL),
(134, 'ELETTRICA', 13, NULL),
(135, 'ELETTRICA', 13, NULL),
(136, 'ELETTRICA', 13, NULL),
(137, 'ELETTRICA', 13, NULL),
(138, 'ELETTRICA', 13, NULL),
(139, 'ELETTRICA', 13, NULL),
(140, 'ELETTRICA', 13, NULL),
(141, 'CLASSICA', 13, 70),
(142, 'CLASSICA', 13, 71),
(143, 'CLASSICA', 13, 72),
(144, 'CLASSICA', 13, 73),
(145, 'CLASSICA', 13, 74),
(146, 'CLASSICA', 13, 75),
(147, 'CLASSICA', 13, NULL),
(148, 'CLASSICA', 13, NULL),
(149, 'CLASSICA', 13, NULL),
(150, 'CLASSICA', 13, NULL),
(151, 'CLASSICA', 13, NULL),
(152, 'CLASSICA', 13, NULL),
(153, 'CLASSICA', 13, NULL),
(154, 'CLASSICA', 13, NULL),
(155, 'CLASSICA', 13, NULL);

-- --------------------------------------------------------

--
-- Struttura della tabella `noleggio`
--

CREATE TABLE `noleggio` (
  `id` int(11) NOT NULL,
  `data_inizio` datetime NOT NULL,
  `data_fine` datetime DEFAULT NULL,
  `utente` int(11) NOT NULL,
  `bicicletta` int(11) NOT NULL,
  `rastrelliera` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Struttura della tabella `rastrelliera`
--

CREATE TABLE `rastrelliera` (
  `id` int(11) NOT NULL,
  `nome` varchar(100) NOT NULL,
  `latitudine` double NOT NULL,
  `longitudine` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dump dei dati per la tabella `rastrelliera`
--

INSERT INTO `rastrelliera` (`id`, `nome`, `latitudine`, `longitudine`) VALUES
(9, 'PIAZZALE LODI', 2412421.45325, 23654723724.325),
(10, 'PORTA VITTORIA', 549341720957.5802, 982430207540.3986),
(11, 'VIA VIENNA', 4543412643.1356535, 764233623.6314523),
(13, 'VIA RIPAMONTI', 214241.4245215, 32532512.53215135);

-- --------------------------------------------------------

--
-- Struttura della tabella `utente`
--

CREATE TABLE `utente` (
  `id` int(11) NOT NULL,
  `email` varchar(100) DEFAULT NULL,
  `nome` varchar(100) DEFAULT NULL,
  `cognome` varchar(100) DEFAULT NULL,
  `manutentore` tinyint(1) NOT NULL,
  `password` varchar(255) NOT NULL,
  `studente` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dump dei dati per la tabella `utente`
--

INSERT INTO `utente` (`id`, `email`, `nome`, `cognome`, `manutentore`, `password`, `studente`) VALUES
(1, NULL, NULL, NULL, 1, 'be186e73f0a07f17573d812eb1a9e9419b74cbce', 0),
(2, 'brioschiandrea@tiscali.it', 'Andrea', 'Brioschi', 0, 'be186e73f0a07f17573d812eb1a9e9419b74cbce', 1),
(12, 'test@test.it', 'Paolo', 'Prova', 0, '86f7e437faa5a7fce15d1ddcb9eaeaea377667b8', 0);

--
-- Indici per le tabelle scaricate
--

--
-- Indici per le tabelle `abbonamento`
--
ALTER TABLE `abbonamento`
  ADD PRIMARY KEY (`id`),
  ADD KEY `utente` (`utente`);

--
-- Indici per le tabelle `bicicletta`
--
ALTER TABLE `bicicletta`
  ADD PRIMARY KEY (`id`);

--
-- Indici per le tabelle `morsa`
--
ALTER TABLE `morsa`
  ADD PRIMARY KEY (`id`),
  ADD KEY `bicicletta` (`bicicletta`),
  ADD KEY `rastrelliera` (`rastrelliera`);

--
-- Indici per le tabelle `noleggio`
--
ALTER TABLE `noleggio`
  ADD PRIMARY KEY (`id`),
  ADD KEY `bicicletta` (`bicicletta`),
  ADD KEY `utente` (`utente`),
  ADD KEY `rastrelliera` (`rastrelliera`);

--
-- Indici per le tabelle `rastrelliera`
--
ALTER TABLE `rastrelliera`
  ADD PRIMARY KEY (`id`);

--
-- Indici per le tabelle `utente`
--
ALTER TABLE `utente`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT per le tabelle scaricate
--

--
-- AUTO_INCREMENT per la tabella `abbonamento`
--
ALTER TABLE `abbonamento`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT per la tabella `bicicletta`
--
ALTER TABLE `bicicletta`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=96;

--
-- AUTO_INCREMENT per la tabella `morsa`
--
ALTER TABLE `morsa`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=176;

--
-- AUTO_INCREMENT per la tabella `noleggio`
--
ALTER TABLE `noleggio`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;

--
-- AUTO_INCREMENT per la tabella `rastrelliera`
--
ALTER TABLE `rastrelliera`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=15;

--
-- AUTO_INCREMENT per la tabella `utente`
--
ALTER TABLE `utente`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- Limiti per le tabelle scaricate
--

--
-- Limiti per la tabella `abbonamento`
--
ALTER TABLE `abbonamento`
  ADD CONSTRAINT `abbonamento_ibfk_1` FOREIGN KEY (`utente`) REFERENCES `utente` (`id`);

--
-- Limiti per la tabella `morsa`
--
ALTER TABLE `morsa`
  ADD CONSTRAINT `morsa_ibfk_1` FOREIGN KEY (`bicicletta`) REFERENCES `bicicletta` (`id`),
  ADD CONSTRAINT `morsa_ibfk_2` FOREIGN KEY (`rastrelliera`) REFERENCES `rastrelliera` (`id`);

--
-- Limiti per la tabella `noleggio`
--
ALTER TABLE `noleggio`
  ADD CONSTRAINT `noleggio_ibfk_1` FOREIGN KEY (`bicicletta`) REFERENCES `bicicletta` (`id`),
  ADD CONSTRAINT `noleggio_ibfk_2` FOREIGN KEY (`utente`) REFERENCES `utente` (`id`),
  ADD CONSTRAINT `noleggio_ibfk_3` FOREIGN KEY (`rastrelliera`) REFERENCES `rastrelliera` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
