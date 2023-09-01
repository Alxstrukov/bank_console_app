DROP TABLE if exists clients cascade ;
CREATE TABLE clients
(
    id         int generated always as identity (start 1 ) primary key,
    last_name  varchar not null,
    first_name varchar not null
);
DROP TABLE if exists banks cascade ;
CREATE TABLE banks
(
    id        int generated always as identity (start 100 ) primary key,
    name_bank varchar not null unique
);
DROP TABLE if exists bank_accounts cascade ;
CREATE TABLE bank_accounts
(
    id serial primary key,
    account_number int generated always as identity (start 1000 ) unique,
    balance        double precision default 0     not null,
    bank_id        integer
        references banks
            on delete cascade,
    client_id      integer                        not null
        constraint bank_accounts_clients_id_fkey
            references clients
            on delete cascade,
    date           date             default now() not null
);
DROP TYPE if exists operation cascade;
CREATE TYPE operation AS ENUM ('ADD MONEY', 'RECEIVE MONEY', 'TRANSFER MONEY');
DROP TABLE if exists transactions cascade ;
create table transactions
(
    id                integer generated always as identity (start 12345)
        constraint transaction_pkey
            primary key,
    operation         operation             not null,
    sender_account    integer
        constraint transaction_from_bank_account_number_fkey
            references bank_accounts (account_number),
    recipient_account integer
        constraint transaction_to_bank_account_number_fkey
            references bank_accounts (account_number),
    amount            double precision      not null
        constraint transaction_amount_check
            check (amount > (0)::double precision),
    sender_bank       integer
        constraint transaction_from_bank_fkey
            references banks,
    recipient_bank    integer
        constraint transaction_to_bank_fkey
            references banks,
    time              time(0) default now() not null,
    date              date    default now() not null
);

INSERT INTO clients (last_name, first_name)
VALUES ('Strukov', 'Alexandr'),
       ('Lebedev', 'Ivan'),
       ('Ivanov', 'Petr'),
       ('Gromov', 'Mishail'),
       ('Vorobyova', 'Ekaterina'),
       ('Grishechkina', 'Liliya'),
       ('Vorobyova', 'Anna'),
       ('Strukova', 'Galina'),
       ('Azema', 'Svetlana'),
       ('Shibailo', 'Artem'),
       ('Busel', 'Klim'),
       ('Sidorov', 'Evgeniy'),
       ('Grib', 'Alla'),
       ('Dobrova', 'Darya'),
       ('Magonova', 'Valeria'),
       ('Kovalev', 'Maksim'),
       ('Latushev', 'Mihail'),
       ('Fedorcov', 'Andrey'),
       ('Grinevich', 'Pavel'),
       ('Kozlov', 'Igor'),
       ('Strukov', 'Evgeniy');
INSERT INTO banks (name_bank)
VALUES ('Clever-Bank'),
       ('MTBank'),
       ('Sber-Bank'),
       ('Alfa-Bank'),
       ('Prior-Bank'),
       ('Belarus-Bank');
INSERT INTO bank_accounts (balance, bank_id, client_id, date)
VALUES (7100, 100, 1, '2021-12-11'),
       (2500, 101, 2, '2016-10-12'),
       (150, 102, 3, '2021-05-12'),
       (1458, 103, 4, '2022-11-13'),
       (123.25, 104, 5, '2023-06-14'),
       (710.25, 100, 6, '2023-07-15'),
       (1200.55, 101, 7, '2021-03-16'),
       (1580, 102, 8, '2023-02-17'),
       (1000.10, 103, 9, '2020-01-18'),
       (140.25, 104, 10, '2020-01-19'),
       (3000, 105, 11, '2008-02-20'),
       (4105, 101, 12, '2019-03-21'),
       (1258, 102, 13, '2018-04-22'),
       (3241, 100, 14, '2017-06-23'),
       (524.22, 104, 15, '2022-08-30'),
       (758.33, 105, 16, '2018-09-24'),
       (4450, 101, 17, '2012-10-25'),
       (21508, 102, 18, '2011-12-26'),
       (22.5, 100, 19, '2010-11-28'),
       (2458.55, 104, 20, '2015-11-29'),
       (2000, 100, 21, '2013-04-01'),
       (2500, 101, 1, '2014-04-01'),
       (1978.22, 102, 2, '2013-04-05'),
       (1460, 103, 3, '2013-03-08'),
       (7100, 100, 4, '2022-03-08'),
       (1110, 105, 7, '2022-07-07'),
       (5000, 101, 6, '2022-06-09'),
       (6000, 102, 7, '2021-06-25'),
       (8542, 103, 8, '2020-06-25'),
       (2757.25, 104, 5, '2020-05-27'),
       (424.5, 105, 10, '2023-02-25'),
       (257.74, 101, 11, '2023-02-12'),
       (4212.22, 103, 12, '2023-01-17'),
       (2152, 102, 7, '2020-01-13'),
       (2111, 100, 14, '2017-03-24'),
       (3160, 100, 15, '2016-08-27'),
       (4720, 101, 5, '2023-08-20'),
       (5842, 103, 17, '2014-11-15'),
       (1245, 100, 18, '2023-11-03'),
       (5412.99, 102, 19, '2023-05-08'),
       (6.50, 104, 20, '2021-05-09');