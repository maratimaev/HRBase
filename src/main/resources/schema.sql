CREATE TABLE IF NOT EXISTS organization (
  id              INTEGER PRIMARY KEY AUTO_INCREMENT,
  name            VARCHAR (50)  NOT NULL,
  full_name       VARCHAR (100) NOT NULL,
  inn             VARCHAR (50)  NOT NULL,
  kpp             VARCHAR (50)  NOT NULL,
  address         VARCHAR (200) NOT NULL,
  phone           VARCHAR (20),
  head_office_id  INTEGER,
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

ALTER TABLE organization ADD FOREIGN KEY (head_office_id) REFERENCES office(id);
CREATE INDEX IX_organization_head_office_id ON organization (head_office_id);

CREATE TABLE IF NOT EXISTS employer (
  id                INTEGER PRIMARY KEY AUTO_INCREMENT,
  office_id         INTEGER NOT NULL,
  first_name        VARCHAR (50) NOT NULL,
  last_name         VARCHAR (50),
  middle_name       VARCHAR (50),
  second_name       VARCHAR (50),
  position          VARCHAR (50) NOT NULL,
  phone             VARCHAR (50),
  date              DATE NOT NULL,
  doc_code          INTEGER NOT NULL,
  citizenship_code  INTEGER NOT NULL,
  is_identified     BOOLEAN DEFAULT FALSE
);

ALTER TABLE employer ADD FOREIGN KEY (office_id) REFERENCES office(id);
CREATE INDEX IX_employer_office_id ON employer (office_id);

CREATE TABLE IF NOT EXISTS doc (
  code  INTEGER PRIMARY KEY NOT NULL,
  name  VARCHAR (100) NOT NULL
);

ALTER TABLE employer ADD FOREIGN KEY (doc_code) REFERENCES doc(code);
CREATE INDEX IX_employer_doc_code ON employer (doc_code);

CREATE TABLE IF NOT EXISTS country (
  code  INTEGER PRIMARY KEY NOT NULL,
  name  VARCHAR (100) NOT NULL
);

ALTER TABLE employer ADD FOREIGN KEY (citizenship_code) REFERENCES country(code);
CREATE INDEX IX_employer_citizenship_code ON employer (citizenship_code);
