package ru.clevertec.console.application.utils;

public class SqlQuery {
    public static final String ADD_MONEY_ENUM_DB = "'ADD MONEY'";
    public static final String RECEIVE_MONEY_ENUM_DB = "'RECEIVE MONEY'";
    public static final String TRANSFER_MONEY_ENUM_DB = "'TRANSFER MONEY'";
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
    public static final String IS_CLIENT_OF_BANK = "SELECT id FROM clients WHERE id = ";
    public static final String IS_BANK_ACCOUNT_OF_BANK = "SELECT account_number FROM bank_accounts WHERE account_number = ";
    public static final String SELECT_NEW_CLIENT_ID = "SELECT id FROM clients " +
            "WHERE last_name = '%s' AND first_name = '%s' ORDER BY clients.id DESC LIMIT 1;";
    public static final String SELECT_NEW_BANK_ACCOUNT_ID = "SELECT account_number FROM bank_accounts " +
            "WHERE client_id = %s ORDER BY bank_accounts.account_number DESC LIMIT 1;";
}
