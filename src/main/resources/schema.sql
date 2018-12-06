CREATE TABLE IF NOT EXISTS organization (
  id              INTEGER PRIMARY KEY AUTO_INCREMENT,
  name            VARCHAR (50)  NOT NULL,
  full_name       VARCHAR (100) NOT NULL,
  inn             VARCHAR (12)  NOT NULL,
  kpp             VARCHAR (9)  NOT NULL,
  address         VARCHAR (200) NOT NULL,
  phone           VARCHAR (20),
  is_active       BOOLEAN DEFAULT FALSE
);

CREATE TABLE IF NOT EXISTS office (
  id        INTEGER PRIMARY KEY AUTO_INCREMENT,
  org_id    INTEGER NOT NULL,
  name      VARCHAR (50)  NOT NULL,
  address   VARCHAR (200) NOT NULL,
  phone     VARCHAR (20),
  is_active BOOLEAN DEFAULT FALSE
);

ALTER TABLE office ADD FOREIGN KEY (org_id) REFERENCES organization(id);
CREATE INDEX IX_office_org_id ON office (org_id);

CREATE TABLE IF NOT EXISTS employer (
  id                INTEGER PRIMARY KEY AUTO_INCREMENT,
  office_id         INTEGER NOT NULL,
  first_name        VARCHAR (50) NOT NULL,
  last_name         VARCHAR (50),
  middle_name       VARCHAR (50),
  second_name       VARCHAR (50),
  position          VARCHAR (50) NOT NULL,
  phone             VARCHAR (50),
  document_id       INTEGER NOT NULL,
  citizenship_id    INTEGER NOT NULL,
  is_identified     BOOLEAN DEFAULT FALSE
);

ALTER TABLE employer ADD FOREIGN KEY (office_id) REFERENCES office(id);
CREATE INDEX IX_employer_office_id ON employer (office_id);

CREATE TABLE IF NOT EXISTS document (
  id           INTEGER PRIMARY KEY AUTO_INCREMENT,
  type_id      INTEGER NOT NULL,
  number       VARCHAR (20) NOT NULL,
  date         DATE NOT NULL
);

ALTER TABLE employer ADD FOREIGN KEY (document_id) REFERENCES document(id);
CREATE INDEX IX_document_id ON employer (document_id);

CREATE TABLE IF NOT EXISTS document_type (
  id    INTEGER PRIMARY KEY AUTO_INCREMENT,
  code  VARCHAR (2) NOT NULL,
  name  VARCHAR (100) NOT NULL
);

ALTER TABLE document ADD FOREIGN KEY (type_id) REFERENCES document_type(id);
CREATE INDEX IX_document_type_id ON document (type_id);

CREATE TABLE IF NOT EXISTS country (
  id    INTEGER PRIMARY KEY AUTO_INCREMENT,
  code  VARCHAR (3) NOT NULL,
  name  VARCHAR (100) NOT NULL
);

ALTER TABLE employer ADD FOREIGN KEY (citizenship_id) REFERENCES country(id);
CREATE INDEX IX_employer_citizenship_id ON employer (citizenship_id);
