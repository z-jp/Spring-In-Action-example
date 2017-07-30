DROP TABLE IF EXISTS `Spitter`;
DROP TABLE IF EXISTS `Spittle`;
CREATE TABLE Spitter
(
  id         INT(12) PRIMARY KEY,
  username   VARCHAR(20) NOT NULL UNIQUE,
  password   VARCHAR(20) NOT NULL,
  first_name VARCHAR(30) NOT NULL,
  last_name  VARCHAR(30) NOT NULL,
  email      VARCHAR(30) NOT NULL
);
CREATE TABLE Spittle
(
  id         INT                                 NULL,
  message    VARCHAR(140)                        NOT NULL,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  latitude   DOUBLE                              NULL,
  longitude  DOUBLE                              NULL
);