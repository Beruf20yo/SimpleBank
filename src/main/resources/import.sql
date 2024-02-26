INSERT INTO users (email, firstname, lastname, middle_name, password, phone, role, username) VALUES ('inna9544@yandex.ru', 'Инна', 'Смольянинова', 'Порфнрьевна', 'qwerty', '9178272152', 'ROLE_USER', 'inna9544');
INSERT INTO users (email, firstname, lastname, middle_name, password, phone, role, username) VALUES ('evgeniy1991@hotmail.com', 'Евгений', 'Куанышбаев', 'Никитьевич', 'qwerty', '9427135068', 'ROLE_USER', 'evgeniy1991');
INSERT INTO users (email, firstname, lastname, middle_name, password, phone, role, username) VALUES ('viktor.priluckiy@ya.ru', 'Виктор', 'Прилуцкий', 'Феоктистович', 'qwerty', '9993813999', 'ROLE_USER', 'priluckiy');
INSERT INTO users (email, firstname, lastname, middle_name, password, phone, role, username) VALUES ('aleksandr1995@outlook.com', 'Александр', 'Косма', 'Иванович', 'qwerty', '9548812811', 'ROLE_USER', 'aleksandr1995');
INSERT INTO users (email, firstname, lastname, middle_name, password, phone, role, username) VALUES ('anastasiya09031982@rambler.ru', 'Анастасия', 'Рабиновича', 'Ираклиевна', 'qwerty', '9427135068', 'ROLE_ADMIN', 'anastasiya09031982');

INSERT INTO account_type (name) VALUES ('Дебетовый');
INSERT INTO account_type (name) VALUES ('Кредитный');

INSERT INTO appointment (confirmed, date, description, location, user_id) VALUES (true, '2022-12-12', 'Принести с собой паспорт', 'Пушкина 21', 1);
INSERT INTO appointment (confirmed, date, description, location, user_id) VALUES (true, '2022-12-13', 'Принести с собой паспорт', 'Пушкина 21', 2);
INSERT INTO appointment (confirmed, date, description, location, user_id) VALUES (false, '2022-12-14', 'Принести с собой паспорт', 'Пушкина 21', 3);
INSERT INTO appointment (confirmed, date, description, location, user_id) VALUES (true, '2022-12-15', 'Принести с собой паспорт', 'Пушкина 21', 4);
INSERT INTO appointment (confirmed, date, description, location, user_id) VALUES (false, '2022-12-16', 'Принести с собой паспорт', 'Пушкина 21', 5);

INSERT INTO account (account_balance, account_details, date_opened, name, account_type_id, user_id) VALUES (10, '', '2022-12-12', '4000 0012 3456 7899', 1, 1);
INSERT INTO account (account_balance, account_details, date_opened, name, account_type_id, user_id) VALUES (0, '', '2022-12-12', '4000 0012 3456 7900', 2, 1);
INSERT INTO account (account_balance, account_details, date_opened, name, account_type_id, user_id) VALUES (0, '', '2022-12-12', '4000 0012 3456 7901', 1, 2);
INSERT INTO account (account_balance, account_details, date_opened, name, account_type_id, user_id) VALUES (0, '', '2022-12-12', '4000 0012 3456 7902', 2, 3);
INSERT INTO account (account_balance, account_details, date_opened, name, account_type_id, user_id) VALUES (0, '', '2022-12-12', '4000 0012 3456 7903', 1, 4);
INSERT INTO account (account_balance, account_details, date_opened, name, account_type_id, user_id) VALUES (0, '', '2022-12-12', '4000 0012 3456 7904', 2, 4);
INSERT INTO account (account_balance, account_details, date_opened, name, account_type_id, user_id) VALUES (0, '', '2022-12-12', '4000 0012 3456 7905', 2, 5);

INSERT INTO bill (date_created, details, price, service, status, credit_account_id, creator_id, recipient_id) VALUES ('2022-12-12', '', 1000, 'Подключение ТВ', 'Не оплачено', 3, 2, 1);