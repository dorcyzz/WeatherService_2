ALTER TABLE TemperatureHistory
DROP
FOREIGN KEY FK_m5qt12jfk71x85mh7ap168scv;

ALTER TABLE TemperatureHistory
DROP
FOREIGN KEY FK_na6p28l66hd1lj0v1ofhc8v8w;

ALTER TABLE WeatherHistory
DROP
FOREIGN KEY FK_2bpuebs9b4mbmyynvyhjaey9q;

ALTER TABLE WeatherHistory
DROP
FOREIGN KEY FK_68g2qh94qy2n6nl3xao65uc0v;

DROP TABLE IF EXISTS City;

DROP TABLE IF EXISTS TemperatureHistory;

DROP TABLE IF EXISTS WeatherHistory;

CREATE TABLE City (
  name       VARCHAR(255) NOT NULL,
  population INTEGER      NOT NULL,
  province   VARCHAR(255),
  region     VARCHAR(255),
  PRIMARY KEY (name)
);

CREATE TABLE TemperatureHistory (
  id          INTEGER      NOT NULL AUTO_INCREMENT,
  date        DATETIME,
  name        VARCHAR(255),
  temperature INTEGER      NOT NULL,
  cityName    VARCHAR(255) NOT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE WeatherHistory (
  id       INTEGER      NOT NULL AUTO_INCREMENT,
  date     DATETIME,
  name     VARCHAR(255),
  weather  VARCHAR(255),
  cityName VARCHAR(255) NOT NULL,
  PRIMARY KEY (id)
);

ALTER TABLE TemperatureHistory
ADD CONSTRAINT FK_m5qt12jfk71x85mh7ap168scv
FOREIGN KEY (cityName)
REFERENCES City (name);

ALTER TABLE TemperatureHistory
ADD CONSTRAINT FK_na6p28l66hd1lj0v1ofhc8v8w
FOREIGN KEY (name)
REFERENCES City (name);

ALTER TABLE WeatherHistory
ADD CONSTRAINT FK_2bpuebs9b4mbmyynvyhjaey9q
FOREIGN KEY (cityName)
REFERENCES City (name);

ALTER TABLE WeatherHistory
ADD CONSTRAINT FK_68g2qh94qy2n6nl3xao65uc0v
FOREIGN KEY (name)
REFERENCES City (name);