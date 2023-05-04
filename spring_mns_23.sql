-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1:3306
-- Généré le : jeu. 04 mai 2023 à 09:29
-- Version du serveur : 8.0.31
-- Version de PHP : 8.0.26

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données : `spring_mns_23`
--

-- --------------------------------------------------------

--
-- Structure de la table `entreprise`
--

DROP TABLE IF EXISTS `entreprise`;
CREATE TABLE IF NOT EXISTS `entreprise` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nom` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `entreprise`
--

INSERT INTO `entreprise` (`id`, `nom`) VALUES
(1, 'Google'),
(2, 'Amazon'),
(3, 'Facebook'),
(4, 'Microsoft');

-- --------------------------------------------------------

--
-- Structure de la table `job`
--

DROP TABLE IF EXISTS `job`;
CREATE TABLE IF NOT EXISTS `job` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nom` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `job`
--

INSERT INTO `job` (`id`, `nom`) VALUES
(1, 'developpeur'),
(2, 'testeur'),
(3, 'chef de projet'),
(4, 'stagiare');

-- --------------------------------------------------------

--
-- Structure de la table `pays`
--

DROP TABLE IF EXISTS `pays`;
CREATE TABLE IF NOT EXISTS `pays` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nom` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `pays`
--

INSERT INTO `pays` (`id`, `nom`) VALUES
(1, 'France'),
(2, 'Italie'),
(3, 'Allemagne'),
(4, 'Espagne');

-- --------------------------------------------------------

--
-- Structure de la table `role`
--

DROP TABLE IF EXISTS `role`;
CREATE TABLE IF NOT EXISTS `role` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nom` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `role`
--

INSERT INTO `role` (`id`, `nom`) VALUES
(1, 'ROLE_ADMINISTRATEUR'),
(2, 'ROLE_UTILISATEUR');

-- --------------------------------------------------------

--
-- Structure de la table `user_job`
--

DROP TABLE IF EXISTS `user_job`;
CREATE TABLE IF NOT EXISTS `user_job` (
  `utilisateur_id` int NOT NULL,
  `job_id` int NOT NULL,
  PRIMARY KEY (`utilisateur_id`,`job_id`),
  KEY `FKia2o1pm0plymfbt26ps56ox5l` (`job_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `user_job`
--

INSERT INTO `user_job` (`utilisateur_id`, `job_id`) VALUES
(2, 1),
(1, 3);

-- --------------------------------------------------------

--
-- Structure de la table `utilisateur`
--

DROP TABLE IF EXISTS `utilisateur`;
CREATE TABLE IF NOT EXISTS `utilisateur` (
  `id` int NOT NULL AUTO_INCREMENT,
  `created_date` date DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `image_name` varchar(255) DEFAULT NULL,
  `nom` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `prenom` varchar(255) DEFAULT NULL,
  `updated_date` datetime(6) DEFAULT NULL,
  `entreprise_id` int DEFAULT NULL,
  `pays_id` int DEFAULT NULL,
  `role_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK8fjtucbyo2t6agaejym2j764f` (`entreprise_id`),
  KEY `FKc1g8do1rrrp4bwytrl73elnou` (`pays_id`),
  KEY `FKaqe8xtajee4k0wlqrvh2pso4l` (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `utilisateur`
--

INSERT INTO `utilisateur` (`id`, `created_date`, `email`, `image_name`, `nom`, `password`, `prenom`, `updated_date`, `entreprise_id`, `pays_id`, `role_id`) VALUES
(1, '2022-12-03', 'john@doe', NULL, 'DOE', '$2a$10$iwpMHVP1MNyrRrCgnnoi2OhPHjrtl2Qi7y/mFewg3jUwoVPPXmR9O', 'John', '2023-04-28 09:44:49.000000', 1, 2, 2),
(2, '2023-02-25', 'bill@dozer', NULL, 'DOZER', '$2a$10$SoBuGcVPO3Qez/noYh1R1u.P/Bm0cvPIjkkGGzfnU5LYnGHuCc5T.', 'Bill', '2023-04-28 09:44:49.000000', 2, 3, 1),
(3, '2021-03-05', 'camais@leon', NULL, 'CAMAIS', '$2a$10$Fs/zQr2OL/gySgKIzxlQ4.jzyBSZiqZI6luPY0JXScmxE/JsFKsSC', 'Leon', '2023-04-28 09:44:49.000000', 3, 4, 2),
(4, '2023-04-28', 'hjhgl@jhgl', '4366933c-2413-4c8d-9f32-f80301a88a38_test.jpg', 'nhfkhjfjk', '$2a$10$uFzp.pJ21nw1M7zO1DRT7OTMq0R2tNqj0SjJF9JuC/nVwgoGQej3m', 'hfljh', '2023-04-28 09:46:05.731869', NULL, 1, 2);

--
-- Contraintes pour les tables déchargées
--

--
-- Contraintes pour la table `user_job`
--
ALTER TABLE `user_job`
  ADD CONSTRAINT `FKia2o1pm0plymfbt26ps56ox5l` FOREIGN KEY (`job_id`) REFERENCES `job` (`id`),
  ADD CONSTRAINT `FKlxpjha8cmosf65b0tyw7xmeom` FOREIGN KEY (`utilisateur_id`) REFERENCES `utilisateur` (`id`);

--
-- Contraintes pour la table `utilisateur`
--
ALTER TABLE `utilisateur`
  ADD CONSTRAINT `FK8fjtucbyo2t6agaejym2j764f` FOREIGN KEY (`entreprise_id`) REFERENCES `entreprise` (`id`),
  ADD CONSTRAINT `FKaqe8xtajee4k0wlqrvh2pso4l` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`),
  ADD CONSTRAINT `FKc1g8do1rrrp4bwytrl73elnou` FOREIGN KEY (`pays_id`) REFERENCES `pays` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
