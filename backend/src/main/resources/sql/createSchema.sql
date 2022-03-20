CREATE TABLE IF NOT EXISTS horse
(
    id            BIGINT AUTO_INCREMENT PRIMARY KEY,
    name          VARCHAR(255) NOT NULL,
    description   VARCHAR(255) NULL,
    birthdate     DATE NOT NULL,
    gender        ENUM('MALE','FEMALE') NOT NULL,
    ownerId       BIGINT NULL,
    damId         BIGINT NULL,
    sireId        BIGINT NULL
);