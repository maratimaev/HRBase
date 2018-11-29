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
CREATE INDEX IX_organization_id ON office (org_id);

ALTER TABLE organization ADD FOREIGN KEY (head_office_id) REFERENCES office(id);
CREATE INDEX IX_headOffice_id ON organization (head_office_id);

CREATE TABLE IF NOT EXISTS user (
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

ALTER TABLE user ADD FOREIGN KEY (office_id) REFERENCES office(id);
CREATE INDEX IX_office_id ON user (office_id);

CREATE TABLE IF NOT EXISTS doc (
  code  INTEGER PRIMARY KEY NOT NULL,
  name  VARCHAR (100) NOT NULL
);

CREATE TABLE IF NOT EXISTS user_doc (
  user_code INTEGER NOT NULL,
  doc_code INTEGER NOT NULL,
  FOREIGN KEY (user_code) REFERENCES doc(code)      ON DELETE RESTRICT ON UPDATE CASCADE,
  FOREIGN KEY (doc_code)  REFERENCES user(doc_code) ON DELETE RESTRICT ON UPDATE CASCADE,
  PRIMARY KEY (user_code, doc_code)
);

CREATE INDEX IX_user_doc_user_code_id ON user_doc (user_code);
CREATE INDEX IX_user_doc_doc_code     ON user_doc (doc_code);

CREATE TABLE IF NOT EXISTS country (
  code  INTEGER PRIMARY KEY NOT NULL,
  name  VARCHAR (100) NOT NULL
);

CREATE TABLE IF NOT EXISTS user_country (
  user_code     INTEGER NOT NULL,
  country_code  INTEGER NOT NULL,
  FOREIGN KEY (user_code)     REFERENCES country(code)          ON DELETE RESTRICT ON UPDATE CASCADE,
  FOREIGN KEY (country_code)  REFERENCES user(citizenship_code) ON DELETE RESTRICT ON UPDATE CASCADE,
  PRIMARY KEY (user_code, country_code)
);

CREATE INDEX IX_user_country_user_code    ON user_country (user_code);
CREATE INDEX IX_user_country_country_code ON user_country (country_code);