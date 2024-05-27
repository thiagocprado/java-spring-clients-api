CREATE TABLE `TB_CLIENTS` (
    `id` int NOT NULL AUTO_INCREMENT,
    `name` varchar(100) NOT NULL,
    `email` varchar(100) NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `clients_unique` (`email`)
);