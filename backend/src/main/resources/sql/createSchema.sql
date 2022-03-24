CREATE TABLE IF NOT EXISTS horse
(
    id            BIGINT AUTO_INCREMENT PRIMARY KEY,
    name          VARCHAR(100) NOT NULL,
    description   VARCHAR(500) NULL,
    birthdate     DATE NOT NULL,
    gender        ENUM('MALE','FEMALE') NOT NULL,
    ownerId       BIGINT NULL,
    damId         BIGINT NULL,
    sireId        BIGINT NULL
);

CREATE TABLE IF NOT EXISTS owner
(
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    firstname   VARCHAR(100) NOT NULL,
    surname     VARCHAR(100) NOT NULL,
    email       VARCHAR(100) NULL
);