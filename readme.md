                                        ������: Bank Console Application
                                            
                                            �������� ����������
                    ������ ������ ������������ ����� ���������� ���������� �� Clever-Bank.
        �� ����� ������ ���������� ��������� ���������� �������� (������ 30 ���) �������� �� ����������� ���� ���������
    � ������. ����� ��������� ��������� �����, ���������� (���� ��� � ���� ����) ���������� ��������� �� ������ �������
    ������� Clever-Bank. �������� �������� �������� � ����� ������������ (src/main/resources/config.properties),
    �� ��������� ����������� "percentMoney=1". ��� �������� � ���������� �������� ��������� ������, ��� ���������
    ������������ ���������� ������������ ���� ����������� � ����������.
        � ���������� ������������ �������� ��������� �����������:
            * ���� � ���������� �� ������ ����������� ID ������ (������������� ������ ID � ���� ������, ����. "clients")
            * ����������� ������ ������� Clever-Bank � �������������� ���������� ��� ����������� �����)
            * �������� ���������� �� ���� ����� ���������� ������ (���������� � ����� ����� ��� ���� ��������)
            * ���������� �������� ������� ������ ������ ����������� �����
            * ������ �������� ������� � ������ ������ ����������� �����
            * ������� �������� ������� ������� ������� Clever-Bank (� ������ ������ ����� ������������ � ������ �����)
            * ������� �������� ������� ������� ������� ����� (� ������ ������ ����� ������������ � ������ �����).
            * ��������� ������� �� ����������� ����� �� ������������ ������
        
        ����� � ������� ���� ����������� ������ CRUD (create, read, update, delete) ��� Bank, BankAccount,
    Client � ������ ���������, ������ ������ ������������ �� ����� ������ ���������� ��� ���������� ������������
    �����. �������� ���������� �� ������� �������� ����:
        
            ����� ��������� Bank � ���� ������, ��������� � �������� ��������� ������ ������ String.
            ������ ������ � ���� ������, ����� ���� � ���� ������ ��������� ���� � ��� ������������� ���������� �����
            (��� ������ ���������� ������ ���������� �� 100, ��� ������� ���� ��������� � ������� SQL ���
            �������� ������� ������)
        public void createBank(String bankName) {
            try (PreparedStatement preparedStatement = DBService.createPreparedStatement(SQLquery.INSERT_BANK)) {
                preparedStatement.setString(1, bankName);
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
            e.printStackTrace();
                }
        }
    
            ����� ����������� � ���� ���� �� Id � ������������ ������ Bank, ��������� � �������� ��������� ����������
            ���� int. ������ ������ � ���� ������, ��������� ������ � �� �� ������ ������� ������ � Java
        public Bank readBank(int bankId) {
            try (ResultSet resultSet = DBService.getQueryResult(SQLquery.SELECT_BANKS + bankId)) {
                resultSet.next();
                if (resultSet.getInt("id") == bankId) {
                    String bankName = resultSet.getString(2);
                    return new Bank(bankId, bankName);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return null;
        }

        ����� ������ � ���� ������ ���� �� ID � ���������� ��� ��������, ��������� � �������� ���������
        ���������� ���� int, � ������ ������ String. �������� ������ � ���� ������, ����� ���� ���������� ���������
        �������� �����
        public void updateBank(int bank_id, String newBankName) {
            try (PreparedStatement preparedStatement = DBService.createPreparedStatement(SQLquery.UPDATE_BANK_BY_ID)) {
                preparedStatement.setString(1, newBankName);
                preparedStatement.setInt(2, bank_id);
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
    
        ����� ������ � ���� ������ ���� �� ID � ��������� ���. ��������� � �������� ��������� ���������� ���� int.
        ������ ������ � ���� ������, ��� ������ �������� ���������� �������� ����� id �������� ������ � ����������
        ���������� � �����
        public void deleteBank(int bankID) {
            try (PreparedStatement preparedStatement = DBService.createPreparedStatement(SQLquery.DELETE_BANK_BY_ID)) {
                preparedStatement.setInt(1, bankID);
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }        
        
        ��������� ������ �� ���������� CRUD �������� ����� ����� � ������� �� ���������� ����:
        src/main/java/ru/clevertec/console/application/services/
        
        ��� ������ � ����� ������ ��� ������ ��������� ����� DBService ��� ����� ����� � ������� �� ���������� ����:
        src/main/java/ru/clevertec/console/application/services/DBService.java
    
        ��� �������� � ��������� � SQL �������� ������������� ����� SQLquery ��� ����� ����� � ������� �� ���������� ����:
        src/main/java/ru/clevertec/console/application/utils/SQLquery.java
        
        ���������� ������ ���� ���������� ��������� � ����� menu:
        src/main/java/ru/clevertec/console/application/menu

        ������ �� ������ �� GitHub: https://github.com/Alxstrukov/bank_console_app/tree/feature 

        �������� ������ ��� ����������� ����������� ������� ����:
            ������: Strukov Alexandr
            ���������� ����� ������� ID: 1
            ����� ����������� �����: ��� ������� 1000


                                            ������ ����������
      1. ����� �������� ���������� ���������� � ����� ������������ (src/main/resources/config.properties)
       ������� ��������� ������ (dbUser- ������������, dbPass- ������, dbName- �������� ����� ���� ������)
      2. � ����� ������ "main()" � ������ startApp(DataBase.runType) ������� �������� ������� DataBase (Enum)
        * DataBase.NEW - ��� ������ ���������� ������������ ����� ������ � ���� ������. ���� �� ����� � ���� ������
         ��� ������� ������� (clients, banks, transactions, bank_accounts) ��� ����� ������������.
        * DataBase.OLD - ��� ������ ���������� ����� ���������� � ������������ ������ � ���� ������.
          ��������! - ���� ������ (������� "clients, banks, transactions, bank_accounts") ����������� �� ������ �������,
          ���������� ������� ��������� � ������ ���� ������ (EMPTY DATA  BASE) � �������� ���� ������.

                                        ������ ����������
    ��� ������ ���������� ����� ��������, � ������� ����� ���������� (�������� IDEA) ����� �������� ���� �����������
                                     
                                    ����������� �� ���� �����������

                        -------------------Clever-Bank--------------------
                        -----------------AUTHORISED MENU--------------------
                        1. Log in to Clever-Bank by ID
                        2. Become a new client at CleverBank
                        0. Exit the application

    �� ������ ����� ��������� ������ � ������� 1, 2 ��� 0.
    ��������!- ��� ������������ ����� ����� �������� ��������� "INCORRECT INPUT! Enter a number"

        1- ���� � ���������� �� ����������� ID ������� Clever-Bank.
        2- ����������� ������ ������� � Clever-Bank
        0- ����� �� ����������. ��������� �������� ���� ������.

        ��� ����� 1: (Log in to Clever-Bank by ID)
    ��� ������ ������ ���� "1" ������������ ���������� ����� ���������� ������ ���������� ����� ID ������� Clever-Bank

                        Enter your unique ID number

        ���� ������������ �������� �������� Clever-Bank � ��� ������ ��������� � ���� ������, ������������ ������� ������
    � ������� ����. � ������ ���������� ������� Clever-Bank � ����� ID, ������� ���� ������������, � ������� �����
    �������� ��������� "Is not a client of Clever-Bank:(" � ���������� ������� � ���� �����������.
       * ��� ��������� ������������ ���������� ������� ���������� ID ������� Strukov Alexandr, ����� ID = 1.
                    
                                        ��� �������� ����
                                    
                            --------Welcome, Strukov Alexandr--------
                        -------------------Clever-Bank--------------------
                        --------------------MAIN MENU---------------------
                        1. View bank accounts balance
                        2. Add money in bank account
                        3. Receive money in bank account
                        4. Transfer money to a Clever-Bank client
                        5. Transfer money to a client of another bank
                        6. Account statment
                        0. Exit from account

        ��� ����� 2: (Become a new client at CleverBank)
    ��� ������ ������ ���� "2" ������������ ���������� ����� ���������� ������ ��� ��� (�� ��������)

                        Enter your first name
    ����� �������
                        Enter your last name

    ����� ��������� ��������� ������ (���� �� �������� � ���� �� �����������, �������� ��� ������ ����������� ���������)
    � ���� ������ ��������� ����� ������, ��� ������������� ���������� ID (��� ����� ����� ��� ��������� ���� � ������),
    � ����� ��������� ����������� � ���� ����� ���������� ���� � Clever-Bank � �������� ������ 0 BYN. ���������� �������
    � ������� ����.

    ��������: 
                        --------Welcome, Sidorov Ivan--------
                        -------------------Clever-Bank--------------------
                        --------------------MAIN MENU---------------------
                        1. View bank accounts balance
                        2. Add money in bank account
                        3. Receive money in bank account
                        4. Transfer money to a Clever-Bank client
                        5. Transfer money to a client of another bank
                        6. Account statment
                        0. Exit from account

        ��� ����� 0: (Exit the application)
    ��� ������ ������ ���� "0" ���������� �������� ���� ������.
    ��������!- ��� ������������ ����� ����� �������� ��������� "INCORRECT INPUT! Enter a number"


                                ����������� �� �������� ����        

                                        ��� �������� ����
                                    
                        -------------------Clever-Bank--------------------
                        --------------------MAIN MENU---------------------
                        1. View bank accounts balance
                        2. Add money in bank account
                        3. Receive money in bank account
                        4. Transfer money to a Clever-Bank client
                        5. Transfer money to a client of another bank
                        0. Exit from account

    �� ������ ����� ��������� ������ � ������� 1, 2, 3, 4, 5, 6 ��� 0.
    ��������!- ��� ������������ ����� ����� �������� ��������� "INCORRECT INPUT! Enter a number"  
    
    ��� ����� 1: (View bank accounts balance)
    ��� ������ ������ ���� "1" � ������� ���������� ����� �������� ���������� � ���� ������ ������� � ���������,
    ��� ��� �������� � ������� ���� ���������� ������ "0"
    ��������!- ��� ������������ ����� ����� �������� ��������� "INCORRECT INPUT! Enter a number" � ������� � ������� ����

    ��������:
            ---------------------Clever-Bank----------------------
            ------------------VIEW BALANCE MENU-------------------
                             List bank accounts
            Bank:  Clever-Bank  Number:1000  Balance = 7100.00 BYN
            ======================================================
            Bank:       MTBank  Number:1021  Balance = 2500.00 BYN
            ======================================================
                
            Please, enter any value to return to the main menu
    
    ��� ����� 2: (Add money in bank account)
    ��� ������ ������ ���� "2" ������������ ����� ��������� ����� ���� ����. � ������� ����� �������� ���������� � ����
    ������ ������� � ��������� � ���, ��� ���������� ������ �������� ����� ����� ��� ����������, � ��� �� ������ ���������
    ��������. ��� �������� � ������� ���� ���������� ������ "0".
    ��������!- ��� ������������ ����� ����� �������� ��������� "INCORRECT INPUT! Enter a number" � ������� � ������� ����
    
    ��������:
                Client: Strukov Alexandr ID: 1    
                Bank:    Alfa-Bank  Number:1003  Balance = 1458.00 BYN
                ======================================================
                Bank:   Prior-Bank  Number:1004  Balance = 123.25 BYN
                ======================================================

                Please select your bank account number or enter '0' to return to the main menu
                For example: 1045

    ����� ������ � ����� ������ ����� ������������ ����� ���������� ������ ����� ��� ����������, � �������� ����� �����.
    ��������!- ��� ������������ ����� ����� �������� ��������� "INCORRECT INPUT! Enter a number" � ������� � ������� ����
             - ��� ����� ����� ���� ����� �������� ��������� "INCORRECT INPUT! Amount = 0" � ������� � ������� ����

    ��������:
                    Please, enter the amount
                    For example: 10,75

    ��� ������������ ��������� ������ � ������� ����� ������� ���, ��������� �� �������� ���������� �������� � ������ ����
    � ����� ������� .txt � ������� �������� ��� � ����������� ����������. ��� ������ ������������� ����������� � ����������
    � ����������� � ���� ������

    ��������: 
                                ����� � �������
             --------------------------------------------------------
            |                     Bank check                       |
            |Check:                                           12351|
            |03-09-2023                                    19:17:13|
            |Type transaction:                       ADD MONEY     |
            |Bank sender:                               -          |
            |Bank recipient:                            Prior-Bank |
            |Bank account sender:                              -   |
            |Bank account recipient:                           1004|
            |Amount:                                 10.00      BYN|
            --------------------------------------------------------
            The check is saved along the path: src/main/java/ru/clevertec/console/application/checks/check_12351.txt

            ************  Successfully add money *************
            
                                ������� �����
            --------------------------------------------------------
            |                 ���������� ���                       |
            |���:                                             12351|
            |03-09-2023                                    19:17:13|
            |��� ����������:                             ����������|
            |���� �����������:                          -          |
            |���� ����������:                           Prior-Bank |
            |���� �����������:                                 -   |
            |���� ����������:                                  1004|
            |�����:                                  10.00      BYN|
            --------------------------------------------------------

    ��� ����� 3: (Receive money in bank account)
    ��� ������ ������ ���� "3" ������������ ����� ������� ������ � ������ ������ �����. � ������� ����� �������� ���������� � ����
    ������ ������� � ��������� � ���, ��� ���������� ������ �������� ����� ����� ��� ��������, � ��� �� ������ ���������
    ��������. ��� �������� � ������� ���� ���������� ������ "0".
    ��������!- ��� ������������ ����� ����� �������� ��������� "INCORRECT INPUT! Enter a number" � ������� � ������� ����

    ��������:
                Client: Strukov Alexandr ID: 1    
                Bank:    Alfa-Bank  Number:1003  Balance = 1458.00 BYN
                ======================================================
                Bank:   Prior-Bank  Number:1004  Balance = 123.25 BYN
                ======================================================

                Please select your bank account number or enter '0' to return to the main menu
                For example: 1045

    ����� ������ � ����� ������ ����� ������������ ����� ���������� ������ ����� ��� ��������, � �������� ����� �����.
    ��������!- ��� ������������ ����� ����� �������� ��������� "INCORRECT INPUT! Enter a number" � ������� � ������� ����
             - ��� ����� ����� ���� ����� �������� ��������� "INCORRECT INPUT! Amount = 0" � ������� � ������� ����

    ��������:
                    Please, enter the amount
                    For example: 10,75

    ��� ������������ ��������� ������ � ������� ����� ������� ���, ��������� �� �������� ���������� �������� � ������ ����
    � ����� ������� .txt � ������� �������� ��� � ����������� ����������. ��� ������ ������������� ����������� � ����������
    � ����������� � ���� ������

    ��������: 
                                ����� � �������
             --------------------------------------------------------
            |                     Bank check                       |
            |Check:                                           12352|
            |03-09-2023                                    20:55:11|
            |Type transaction:                       RECEIVE MONEY |
            |Bank sender:                               Clever-Bank|
            |Bank recipient:                                   -   |
            |Bank account sender:                              1003|
            |Bank account recipient:                           -   |
            |Amount:                                 10.00      BYN|
            --------------------------------------------------------
            The check is saved along the path: src/main/java/ru/clevertec/console/application/checks/check_12352.txt

            ************  Successfully add money *************
            
                                ������� �����
            --------------------------------------------------------
            |                 ���������� ���                       |
            |���:                                             12352|
            |03-09-2023                                    20:55:11|
            |��� ����������:                               ��������|
            |���� �����������:                          Clever-Bank|
            |���� ����������:                                 -    |
            |���� �����������:                                 1003|
            |���� ����������:                                 -    |
            |�����:                                  10.00      BYN|
            --------------------------------------------------------

    ��� ����� 4: (Transfer money to a Clever-Bank client)
    ��� ������ ������ ���� "4" ������������ ����� ��������� ������ � ������ ������ �����, �� ����� ���� ����� Clever-Bank.
    � ������� ����� �������� ���������� � ���� ������ ������� � ��������� � ���, ��� ���������� ������ �������� �����
    ����� � �������� ��������� ��������� �������, � ��� �� ������ ��������� ��������. ��� �������� � ������� ����
    ���������� ������ "0".
    ��������!- ��� ������������ ����� ����� �������� ��������� "INCORRECT INPUT! Enter a number" � ������� � ������� ����

    ��������:
                Client: Strukov Alexandr ID: 1    
                Bank: Clever-Bank  Number:1003  Balance = 1458.00 BYN
                ======================================================
                Bank:   Prior-Bank  Number:1004  Balance = 123.25 BYN
                ======================================================

                Please select your bank account number or enter '0' to return to the main menu
                For example: 1045

    ����� ������ � ����� ������ ����� ������������ ����� ���������� ������ ����� ����� ���������� ������� Clever-Bank.
    (��� ������� ��������� �� ���� ������� �����, ����� �������� ���������, ��� ���� �� ��������� � ����� Clever-Bank)
    (��� ������� ��������� �� ���� �� ������������ � ���� ������, ����� �������� ���������, ��� ���� �� ������).
    ��������!- ��� ������������ ����� ����� �������� ��������� "INCORRECT INPUT! Enter a number" � ������� � ������� ����

    ��������:
                    Please, enter the Clever-Bank client bank account number
                    For example: 1000

    ����� ������ � ����� ������� ������ ������������ ����� ���������� ������ ����� ��� ��������, � �������� ����� �����.
    ��������!- ��� ������������ ����� ����� �������� ��������� "INCORRECT INPUT! Enter a number" � ������� � ������� ����
            - ��� ����� ����� ���� ����� �������� ��������� "INCORRECT INPUT! Amount = 0" � ������� � ������� ����

    ��������:
                    Please, enter the amount
                    For example: 10,75

    ��� ������������ ��������� ������ � ������� ����� ������� ���, ��������� �� �������� ���������� �������� � ������ ����
    � ����� ������� .txt � ������� �������� ��� � ����������� ����������. ��� ������ ������������� ����������� � ����������
    � ����������� � ���� ������

    ��������: 
                                ����� � �������
             --------------------------------------------------------
            |                     Bank check                       |
            |Check:                                           12353|
            |03-09-2023                                    19:58:58|
            |Type transaction:                       TRANSFER MONEY|
            |Bank sender:                               Clever-Bank|
            |Bank recipient:                            Clever-Bank|
            |Bank account sender:                              1000|
            |Bank account recipient:                           1042|
            |Amount:                                 10.00      BYN|
            --------------------------------------------------------
            The check is saved along the path: src/main/java/ru/clevertec/console/application/checks/check_12353.txt

            ************  Successfully add money *************
            
                                ������� �����
            --------------------------------------------------------
            |                 ���������� ���                       |
            |���:                                             12353|
            |03-09-2023                                    19:58:58|
            |��� ����������:                                �������|
            |���� �����������:                          Clever-Bank|
            |���� ����������:                           Clever-Bank|
            |���� �����������:                                 1000|
            |���� ����������:                                  1042|
            |�����:                                  10.00      BYN|
            --------------------------------------------------------

    ��� ����� 5: (Transfer money to a client of another bank)
    ��� ������ ������ ���� "5" ������������ ����� ��������� ������ � ������ ������ �����, �� ����� ���� ������� �����.
    �������� ���������� ���������� ������ �������� ���� "4", �� ����������� �������� ������ ����� ���������� � ���������
    � Clever-Bank.

    ��� ����� 6: (Account statemnt)
    ��� ������ ������ ���� "6" ������������ ����� �������� ������� �� ���������� ����������� ����� �� ������������
    ������ �������. ��� ����� ��� ����� ���������� ������� ���� ����� ����� Clever-Bank, ���� ������ ���������, ����
    ��������� ����������

    ��������:

                    Please, enter the Clever-Bank client bank account number
                    For example: 1000
    
    ��������!- ��� ������������ ����� ����� �������� ��������� "INCORRECT INPUT! Enter a number" � ������� � ������� ����
    
    ��������:    
                    Please, enter the start of the date
                    For example: 2023-11-10
    ��������!- ��� ������������ ����� ����� �������� ��������� "INCORRECT DATE! Enter a number" � ������� � ������� ����
    (����������� ��� ������ ����������� ���������)

    ��������:    
                    Please, enter the end of the date
                    For example: 2023-11-10
    ��������!- ��� ������������ ����� ����� �������� ��������� "INCORRECT DATE! Enter a number" � ������� � ������� ����
    (����������� ��� ������ ����������� ���������)

    ��� ������������ ��������� ������ � ������� ����� �������� �������, � ������ ���� � ����� ������� .txt � ������� 
    ��������� �������. 
    
                                            ����� � �������

                        -----------------Money Statement-----------------
                        -------------------Clever-Bank-------------------
                        Client:                | Strukov Alexandr
                        Bank account:          | 1004
                        Currency:              | BYN
                        Creation date:         | 14-06-2023
                        Period:                | 01-01-2011 -> 01-01-2024
                        Date and time creation | 03-09-2023 23:57:02
                        Balance                | 130.25 BYN  
                                    ADD MONEY  |    RECEIVE MONEY
                                    -----------------------------
                                     17    BYN | -125   BYN
                        The statement is saved along the path: 
                        src/main/java/ru/clevertec/console/application/statement_money/statement_04_09_2023.txt

                                            ������� ����� �������

                        ---------------------������� �� �����--------------------
                        -----------------------Clever-Bank-----------------------
                        ������:                      | Strukov Alexandr
                        ����:                        | 1004
                        ������:                      | BYN
                        ���� ��������:               | 14-06-2023
                        ������:                      | 01-01-2011 -> 01-01-2024
                        ���� � ����� ������������:   | 03-09-2023 23:57:02
                        �������:                     | 130.25 BYN  
                                           ������    |    ����
                                      ------------------------------
                                           17    BYN | -125   BYN
                        ---------------------------------------------------------

    