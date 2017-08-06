DROP TABLE IF EXISTS spittle;

CREATE TABLE spittle (
  id         INTEGER IDENTITY PRIMARY KEY,
  spitterId  INTEGER       NOT NULL,
  message    VARCHAR(2000) NOT NULL,
  postedTime DATETIME      NOT NULL,
);
