    CREATE TABLE `TB_CLIENTS_FAVORITE_PRODUCTS` (
    `id` int NOT NULL AUTO_INCREMENT,
    `client_id` int NOT NULL,
    `product_id` varchar(100) NOT NULL,
    PRIMARY KEY (`id`),
    KEY `TB_CLIENTS_FAVORITE_PRODUCTS_TB_CLIENTS_FK` (`client_id`),
    CONSTRAINT `TB_CLIENTS_FAVORITE_PRODUCTS_TB_CLIENTS_FK` FOREIGN KEY (`client_id`) REFERENCES `TB_CLIENTS` (`id`)
)