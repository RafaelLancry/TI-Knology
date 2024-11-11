CREATE TABLE `users` (
    `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
    `name` TEXT NOT NULL,
    `password` TEXT NOT NULL,
    `email` VARCHAR(255) UNIQUE NOT NULL,
    `cpf` VARCHAR(14) UNIQUE NOT NULL,
    `birthdate` DATE,
    `phone` VARCHAR(15) UNIQUE
);