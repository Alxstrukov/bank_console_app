package ru.clevertec.console.application.utils;

public class SQLquery {
    public static final String ADD_MONEY_ENUM_DB = "ADD MONEY";
    public static final String RECEIVE_MONEY_ENUM_DB = "RECEIVE MONEY";
    public static final String TRANSFER_MONEY_ENUM_DB = "TRANSFER MONEY";
    public static final int CLEVER_BANK_ID = 100;
    public static final String CLEVER_BANK_NAME = "Clever-Bank";
    public static final String SELECT_BANK_ACCOUNTS = "SELECT clients.id as client_id,\n" +
            "       clients.last_name,\n" +
            "       clients.first_name,\n" +
            "       banks.id   as bank_id,\n" +
            "       banks.name_bank,\n" +
            "       bank_accounts.id,\n" +
            "       bank_accounts.account_number,\n" +
            "       balance, bank_accounts.date\n" +
            "FROM bank_accounts\n" +
            "         LEFT JOIN banks ON bank_accounts.bank_id = banks.id\n" +
            "         LEFT JOIN clients ON bank_accounts.client_id = clients.id\n" +
            "WHERE account_number = ";

    public static final String SELECT_ALL_BANK_ACCOUNTS_BY_BANK_ID = "SELECT *\n" +
            "FROM bank_accounts\n" +
            "WHERE bank_id = ";

    public static final String INSERT_BANK = "INSERT INTO banks (name_bank) VALUES (?)";
    public static final String UPDATE_BANK_BY_ID = "UPDATE banks SET name_bank = (?) WHERE id = (?)";
    public static final String DELETE_BANK_BY_ID = "DELETE FROM banks WHERE id = (?)";
    public static final String SELECT_BANKS = "SELECT * FROM banks WHERE id = ";

    public static final String INSERT_CLIENT = "INSERT INTO clients (last_name, first_name) VALUES (?,?)";
    public static final String UPDATE_CLIENT_BY_ID = "UPDATE clients SET last_name = (?), first_name = (?) WHERE id = (?)";
    public static final String DELETE_CLIENT_BY_ID = "DELETE FROM clients WHERE id = (?)";
    public static final String SELECT_ALL_CLIENTS = "SELECT * FROM clients";

    public static final String INSERT_BANK_ACCOUNT = "INSERT INTO bank_accounts (bank_id, client_id) VALUES (?,?)";
    public static final String INSERT_BANK_ACCOUNT_AND_BALANCE = "INSERT INTO bank_accounts (bank_id, client_id, balance)\n" +
            "VALUES (?, ?, ?);";
    public static final String DELETE_BANK_ACCOUNT = "DELETE FROM bank_accounts\n" +
            "WHERE account_number = (?)";
    public static final String RECEIVE_MONEY = "UPDATE bank_accounts\n" +
            "SET balance = balance - (?)\n" +
            "WHERE account_number = (?);";
    public static final String ADD_MONEY = "UPDATE bank_accounts\n" +
            "SET balance = balance + (?)\n" +
            "WHERE account_number = (?);";
    public static final String TRANSFER_MONEY = "UPDATE bank_accounts\n" +
            "SET balance = balance - (?)\n" +
            "WHERE account_number = (?);\n" +
            "UPDATE bank_accounts\n" +
            "SET balance = balance + (?)\n" +
            "WHERE account_number = (?);";

    public static final String INSERT_TRANSACTION_ADD_MONEY = "INSERT INTO transactions" +
            "(operation, recipient_account, amount, recipient_bank)\n" +
            "VALUES ('ADD MONEY', (?), (?), (?))";
    public static final String INSERT_TRANSACTION_ADD_PERCENT = "INSERT INTO transactions" +
            "(operation, recipient_account, amount, recipient_bank)\n" +
            "VALUES ('ADD PERCENT', (?), (?), (?))";
    public static final String INSERT_TRANSACTION_RECEIVE_MONEY = "INSERT INTO transactions" +
            "(operation, sender_account, amount, sender_bank)\n" +
            "VALUES ('RECEIVE MONEY', (?), (?), (?))";
    public static final String INSERT_TRANSACTION_TRANSFER_MONEY = "INSERT INTO transactions" +
            "(sender_account, recipient_account, sender_bank, recipient_bank, amount, operation)\n" +
            "VALUES ((?), (?), (?), (?), (?), 'TRANSFER MONEY');";

    public static final String SELECT_LATEST_TRANSACTION_ADD = "SELECT transactions.id, operation, amount, " +
            "recipient_account,sender_account, rb.name_bank as r_bank, sb.name_bank as s_bank, time, date\n" +
            "FROM transactions\n" +
            "LEFT JOIN banks rb on transactions.recipient_bank = rb.id\n" +
            "LEFT JOIN banks sb on transactions.sender_bank =sb.id\n" +
            "WHERE recipient_account = %d AND operation = 'ADD MONEY' ORDER BY transactions.id DESC LIMIT 1";
    public static final String SELECT_LATEST_TRANSACTION_RECEIVE = "SELECT transactions.id,\n" +
            "       operation,\n" +
            "       amount,\n" +
            "       recipient_account,\n" +
            "       sender_account,\n" +
            "       rb.name_bank as r_bank,\n" +
            "       sb.name_bank as s_bank,\n" +
            "       time,\n" +
            "       date\n" +
            "FROM transactions\n" +
            "         LEFT JOIN banks rb on transactions.recipient_bank = rb.id\n" +
            "         LEFT JOIN banks sb on transactions.sender_bank = sb.id\n" +
            "WHERE sender_account = %d\n" +
            "  AND operation = 'RECEIVE MONEY'\n" +
            "  ORDER BY transactions.id DESC LIMIT 1";
    public static final String SELECT_LATEST_TRANSACTION_TRANSFER = "SELECT transactions.id,\n" +
            "       sender_account,\n" +
            "       recipient_account,\n" +
            "       amount,\n" +
            "       sb.name_bank as s_bank,\n" +
            "       rb.name_bank as r_bank,\n" +
            "       time,\n" +
            "       date,\n" +
            "       operation\n" +
            "FROM transactions\n" +
            "         LEFT JOIN banks rb on transactions.recipient_bank = rb.id\n" +
            "         LEFT JOIN banks sb on transactions.sender_bank = sb.id\n" +
            "WHERE sender_account = %d\n" +
            "  AND operation = 'TRANSFER MONEY'\n" +
            "  ORDER BY transactions.id\n" +
            "  DESC LIMIT 1";
    public static final String IS_CLIENT_OF_BANK = "SELECT client_id " +
            "FROM bank_accounts WHERE bank_id = " + CLEVER_BANK_ID + " AND client_id = ";
    public static final String IS_BANK_ACCOUNT_OF_OTHER_BANK = "SELECT account_number FROM bank_accounts WHERE account_number = ";
    public static final String IS_BANK_ACCOUNT_OF_CLEVER_BANK = "SELECT account_number" +
            " FROM bank_accounts WHERE bank_id = " + CLEVER_BANK_ID + " AND account_number = ";
    public static final String SELECT_NEW_CLIENT_ID = "SELECT id FROM clients " +
            "WHERE last_name = '%s' AND first_name = '%s' ORDER BY clients.id DESC LIMIT 1;";
    public static final String SELECT_NEW_BANK_ACCOUNT_ID = "SELECT account_number FROM bank_accounts " +
            "WHERE client_id = %s ORDER BY bank_accounts.account_number DESC LIMIT 1;";
    public static final String SELECT_USER_ALL_BANK_ACCOUNTS = "SELECT * FROM bank_accounts\n" +
            "LEFT JOIN banks  on bank_accounts.bank_id = banks.id\n" +
            "WHERE client_id = ";
    public static final String GET_ALL_ADD_MONEY= "SELECT sum(amount)\n" +
            "FROM transactions\n" +
            "WHERE recipient_account = %d\n" +
            "  AND (operation = 'TRANSFER MONEY' or operation = 'ADD MONEY' or operation = 'ADD PERCENT') AND (date >= '%s' AND date <= '%s');";
    public static final String GET_ALL_RECEIVE_MONEY= "SELECT sum(amount)\n" +
            "FROM transactions\n" +
            "WHERE sender_account = %d\n" +
            "  AND (operation = 'TRANSFER MONEY' or operation = 'RECEIVE MONEY') AND (date >= '%s' AND date <= '%s');";

    public static final String LOAD_AND_INIT_DB = "DROP TABLE if exists clients cascade ;\n" +
            "CREATE TABLE clients\n" +
            "(\n" +
            "    id         int generated always as identity (start 1 ) primary key,\n" +
            "    last_name  varchar not null,\n" +
            "    first_name varchar not null\n" +
            ");\n" +
            "DROP TABLE if exists banks cascade ;\n" +
            "CREATE TABLE banks\n" +
            "(\n" +
            "    id        int generated always as identity (start 100 ) primary key,\n" +
            "    name_bank varchar not null unique\n" +
            ");\n" +
            "DROP TABLE if exists bank_accounts cascade ;\n" +
            "CREATE TABLE bank_accounts\n" +
            "(\n" +
            "\tid serial primary key,\n" +
            "    account_number int generated always as identity (start 1000 ) unique,\n" +
            "    balance        double precision default 0     not null,\n" +
            "    bank_id        integer\n" +
            "        references banks\n" +
            "            on delete cascade,\n" +
            "    client_id      integer                        not null\n" +
            "        constraint bank_accounts_clients_id_fkey\n" +
            "            references clients\n" +
            "            on delete cascade,\n" +
            "    date           date             default now() not null\n" +
            ");\n" +
            "DROP TYPE if exists operation cascade;\n" +
            "CREATE TYPE operation AS ENUM ('ADD MONEY', 'RECEIVE MONEY', 'TRANSFER MONEY', 'ADD PERCENT');\n" +
            "DROP TABLE if exists transactions cascade ;\n" +
            "create table transactions\n" +
            "(\n" +
            "    id                integer generated always as identity (start 12345)\n" +
            "        constraint transaction_pkey\n" +
            "            primary key,\n" +
            "    operation         operation             not null,\n" +
            "    sender_account    integer\n" +
            "        constraint transaction_from_bank_account_number_fkey\n" +
            "            references bank_accounts (account_number),\n" +
            "    recipient_account integer\n" +
            "        constraint transaction_to_bank_account_number_fkey\n" +
            "            references bank_accounts (account_number),\n" +
            "    amount            double precision      not null\n" +
            "        constraint transaction_amount_check\n" +
            "            check (amount > (0)::double precision),\n" +
            "    sender_bank       integer\n" +
            "        constraint transaction_from_bank_fkey\n" +
            "            references banks,\n" +
            "    recipient_bank    integer\n" +
            "        constraint transaction_to_bank_fkey\n" +
            "            references banks,\n" +
            "    time              time(0) default now() not null,\n" +
            "    date              date    default now() not null\n" +
            ");\n" +
            "\n" +
            "INSERT INTO clients (last_name, first_name)\n" +
            "VALUES ('Strukov', 'Alexandr'),\n" +
            "       ('Lebedev', 'Ivan'),\n" +
            "       ('Ivanov', 'Petr'),\n" +
            "       ('Gromov', 'Mishail'),\n" +
            "       ('Vorobyova', 'Ekaterina'),\n" +
            "       ('Grishechkina', 'Liliya'),\n" +
            "       ('Vorobyova', 'Anna'),\n" +
            "       ('Strukova', 'Galina'),\n" +
            "       ('Azema', 'Svetlana'),\n" +
            "       ('Shibailo', 'Artem'),\n" +
            "       ('Busel', 'Klim'),\n" +
            "       ('Sidorov', 'Evgeniy'),\n" +
            "       ('Grib', 'Alla'),\n" +
            "       ('Dobrova', 'Darya'),\n" +
            "       ('Magonova', 'Valeria'),\n" +
            "       ('Kovalev', 'Maksim'),\n" +
            "       ('Latushev', 'Mihail'),\n" +
            "       ('Fedorcov', 'Andrey'),\n" +
            "       ('Grinevich', 'Pavel'),\n" +
            "       ('Kozlov', 'Igor'),\n" +
            "       ('Strukov', 'Evgeniy');\n" +
            "INSERT INTO banks (name_bank)\n" +
            "VALUES ('Clever-Bank'),\n" +
            "       ('MTBank'),\n" +
            "       ('Sber-Bank'),\n" +
            "       ('Alfa-Bank'),\n" +
            "       ('Prior-Bank'),\n" +
            "       ('Belarus-Bank');\n" +
            "INSERT INTO bank_accounts (balance, bank_id, client_id, date)\n" +
            "VALUES (7100, 100, 1, '2021-12-11'),\n" +
            "       (2500, 101, 1, '2016-10-12'),\n" +
            "       (150, 102, 1, '2021-05-12'),\n" +
            "       (1458, 103, 1, '2022-11-13'),\n" +
            "       (123.25, 104, 1, '2023-06-14'),\n" +
            "       (710.25, 100, 6, '2023-07-15'),\n" +
            "       (1200.55, 101, 7, '2021-03-16'),\n" +
            "       (1580, 102, 8, '2023-02-17'),\n" +
            "       (1000.10, 103, 9, '2020-01-18'),\n" +
            "       (140.25, 104, 10, '2020-01-19'),\n" +
            "       (3000, 105, 11, '2008-02-20'),\n" +
            "       (4105, 101, 12, '2019-03-21'),\n" +
            "       (1258, 102, 13, '2018-04-22'),\n" +
            "       (3241, 100, 14, '2017-06-23'),\n" +
            "       (524.22, 104, 15, '2022-08-30'),\n" +
            "       (758.33, 105, 16, '2018-09-24'),\n" +
            "       (4450, 101, 17, '2012-10-25'),\n" +
            "       (21508, 102, 18, '2011-12-26'),\n" +
            "       (22.5, 100, 19, '2010-11-28'),\n" +
            "       (2458.55, 104, 20, '2015-11-29'),\n" +
            "       (2000, 100, 21, '2013-04-01'),\n" +
            "       (2500, 101, 1, '2014-04-01'),\n" +
            "       (1978.22, 102, 2, '2013-04-05'),\n" +
            "       (1460, 103, 3, '2013-03-08'),\n" +
            "       (7100, 100, 4, '2022-03-08'),\n" +
            "       (1110, 105, 7, '2022-07-07'),\n" +
            "       (5000, 101, 6, '2022-06-09'),\n" +
            "       (6000, 102, 7, '2021-06-25'),\n" +
            "       (8542, 103, 8, '2020-06-25'),\n" +
            "       (2757.25, 104, 5, '2020-05-27'),\n" +
            "       (424.5, 105, 10, '2023-02-25'),\n" +
            "       (257.74, 101, 11, '2023-02-12'),\n" +
            "       (4212.22, 103, 12, '2023-01-17'),\n" +
            "       (2152, 102, 7, '2020-01-13'),\n" +
            "       (2111, 100, 14, '2017-03-24'),\n" +
            "       (3160, 100, 15, '2016-08-27'),\n" +
            "       (4720, 101, 5, '2023-08-20'),\n" +
            "       (5842, 103, 17, '2014-11-15'),\n" +
            "       (1245, 100, 18, '2023-11-03'),\n" +
            "       (5412.99, 102, 19, '2023-05-08'),\n" +
            "       (6.50, 104, 20, '2021-05-09');";
}
