INSERT INTO country (code, name) VALUES ('643', 'Российская Федерация');
INSERT INTO country (code, name) VALUES ('620', 'Португальская Республика');
INSERT INTO country (code, name) VALUES ('882', 'Независимое Государство Самоа');
INSERT INTO country (code, name) VALUES ('250', 'Французская Республика');
INSERT INTO country (code, name) VALUES ('040', 'Австрийская Республика');

INSERT INTO document_type (code, name) VALUES ('21', 'Паспорт гражданина РФ');
INSERT INTO document_type (code, name) VALUES ('03', 'Свидетельство о рождении');
INSERT INTO document_type (code, name) VALUES ('07', 'Военный билет');
INSERT INTO document_type (code, name) VALUES ('24', 'Удостоверение личности военнослужащего Российской Федерации');
INSERT INTO document_type (code, name) VALUES ('91', 'Иные документы');

INSERT INTO document (type_id, number, date) VALUES (1, '123456789', '2001-02-02');
INSERT INTO document (type_id, number, date) VALUES (2, '987654321', '1996-05-21');
INSERT INTO document (type_id, number, date) VALUES (3, '1231231', '2002-04-18');
INSERT INTO document (type_id, number, date) VALUES (1, '453456423', '1999-01-27');
INSERT INTO document (type_id, number, date) VALUES (5, '90765752356', '2001-11-16');

INSERT INTO organization (name, full_name, inn, kpp, address, phone, is_active) VALUES (
    'ООО «Пример»',
    'Общество с ограниченной ответственностью «Пример»',
    '1234567891',
    '123456789',
    '124365 г.Москва, ул. Ленина, д.1, корп.1, офис 1',
    '+7 499 333 4455',
    FALSE
);
INSERT INTO organization (name, full_name, inn, kpp, address, phone, is_active) VALUES (
    'ООО «Дельта»',
    'Общество с ограниченной ответственностью «Дельта»',
    '6634545891',
    '883459989',
    '344365 г.Москва, ул. Цюрупы, д.5, офис 66',
    '+7 499 123 1233',
    TRUE
);
INSERT INTO office (org_id, name, address, phone, is_active) VALUES (
    1,
    'Светлый',
    '124365 г.Москва, ул. Ленина, д.1, корп.1, офис 1',
    '+7 495 233 4456',
    TRUE
);
INSERT INTO office (org_id, name, address, phone, is_active) VALUES (
    1,
    'Темный',
    '124365 г.Москва, ул. Октября, д.56, корп.7, офис 113',
    '+7 495 566 6789',
    FALSE
);
INSERT INTO office (org_id, name, address, phone, is_active) VALUES (
    2,
    'Круглый',
    '124365 г.Москва, ул. Победы, д.45, офис 784',
    '+7 495 245 3556',
    TRUE
);
INSERT INTO office (org_id, name, address, phone, is_active) VALUES (
    2,
    'Овальный',
    '124365 г.Москва, ул. Чернышевского, д.9, офис 21',
    '+7 499 245 7876',
    TRUE
);
INSERT INTO employer (office_id, first_name, last_name, position, phone, document_id, citizenship_id, is_identified) VALUES (
    1, 'Иван', 'Иванов', 'стажер', '+7 917 345 4554', '1', '1', TRUE
);
INSERT INTO employer (office_id, first_name, last_name, position, phone, document_id, citizenship_id, is_identified) VALUES (
    1, 'Петр', 'Сидоров', 'инженер', '+7 917 453 4554', '2', '3', TRUE
);
INSERT INTO employer (office_id, first_name, last_name, position, phone, document_id, citizenship_id, is_identified) VALUES (
    2, 'Константин', 'Котов', 'специалист', '+7 917 432 6678', '3', '1', FALSE
);
INSERT INTO employer (office_id, first_name, last_name, position, phone, document_id, citizenship_id, is_identified) VALUES (
    3, 'Максим', 'Петров', 'руководитель', '+7 917 535 4534', '4', '5', TRUE
);
INSERT INTO employer (office_id, first_name, last_name, position, phone, document_id, citizenship_id, is_identified) VALUES (
    3, 'Вячеслав', 'Архипов', 'водитель', '+7 917 321 2331', '5', '2', FALSE
);