-- Организация
--   id          первичный ключ
--   name        название организации
--   full_name   полное название организации
--   inn         ИНН
--   kpp         КПП
--   address     адрес
--   phone       телефон
--   is_active   признак активности
--   version   оптимистичная блокировка
CREATE TABLE IF NOT EXISTS organization (
  id              INTEGER PRIMARY KEY AUTO_INCREMENT,
  name            VARCHAR (50)  NOT NULL,
  full_name       VARCHAR (100) NOT NULL,
  inn             VARCHAR (12)  NOT NULL,
  kpp             VARCHAR (9)  NOT NULL,
  address         VARCHAR (200) NOT NULL,
  phone           VARCHAR (20),
  is_active       BOOLEAN DEFAULT FALSE,
  version         INTEGER
);

-- Оффис
--   id        первичный ключ
--   org_id    внешний ключ к организации
--   name      название
--   address   адрес
--   phone     телефон
--   is_active признак активности
--   version   оптимистичная блокировка
CREATE TABLE IF NOT EXISTS office (
  id        INTEGER PRIMARY KEY AUTO_INCREMENT,
  org_id    INTEGER NOT NULL,
  name      VARCHAR (50),
  address   VARCHAR (200),
  phone     VARCHAR (20),
  is_active BOOLEAN DEFAULT FALSE,
  version   INTEGER
);

ALTER TABLE office ADD FOREIGN KEY (org_id) REFERENCES organization(id);
CREATE INDEX IX_office_org_id ON office (org_id);

-- Сотрудник
--   id                первичный ключ
--   office_id         внешний ключ к офису
--   first_name        имя
--   last_name         фамилия
--   middle_name       отчество
--   second_name       второе имя
--   position          должность
--   phone             телефон
--   document_id       внешний ключ к документу
--   citizenship_id    внешний ключ к стране
--   is_identified     признак идентификации
--   version           оптимистичная блокировка
CREATE TABLE IF NOT EXISTS employer (
  id                INTEGER PRIMARY KEY AUTO_INCREMENT,
  office_id         INTEGER,
  first_name        VARCHAR (50) NOT NULL,
  last_name         VARCHAR (50),
  middle_name       VARCHAR (50),
  second_name       VARCHAR (50),
  position          VARCHAR (50) NOT NULL,
  phone             VARCHAR (50),
  document_id       INTEGER,
  citizenship_id    INTEGER,
  is_identified     BOOLEAN DEFAULT FALSE,
  version           INTEGER
);

ALTER TABLE employer ADD FOREIGN KEY (office_id) REFERENCES office(id);
CREATE INDEX IX_employer_office_id ON employer (office_id);

-- Документ
--   id       первичный ключ
--   type_id  внешний ключ к типу документа
--   number   номер документа
--   date     дата выдачи
CREATE TABLE IF NOT EXISTS document (
  id           INTEGER PRIMARY KEY AUTO_INCREMENT,
  type_id      INTEGER NOT NULL,
  number       VARCHAR (20),
  date         DATE
);

ALTER TABLE employer ADD FOREIGN KEY (document_id) REFERENCES document(id);
CREATE INDEX IX_document_id ON employer (document_id);

-- Тип документа
--   id    первичный ключ
--   code  код типа
--   name  название
CREATE TABLE IF NOT EXISTS document_type (
  id    INTEGER PRIMARY KEY AUTO_INCREMENT,
  code  VARCHAR (2) NOT NULL,
  name  VARCHAR (100) NOT NULL
);

ALTER TABLE document ADD FOREIGN KEY (type_id) REFERENCES document_type(id);
CREATE INDEX IX_document_type_id ON document (type_id);

-- Страна
--   id    первичный ключ
--   code  код страны
--   name  название
CREATE TABLE IF NOT EXISTS country (
  id    INTEGER PRIMARY KEY AUTO_INCREMENT,
  code  VARCHAR (3) NOT NULL UNIQUE,
  name  VARCHAR (100) NOT NULL
);

ALTER TABLE employer ADD FOREIGN KEY (citizenship_id) REFERENCES country(id);
CREATE INDEX IX_employer_citizenship_id ON employer (citizenship_id);
