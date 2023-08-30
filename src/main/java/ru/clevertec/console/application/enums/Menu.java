package ru.clevertec.console.application.enums;

public enum Menu {
    AUTHORIZED(1),
    MAIN,
    EXIT,
    VIEW_BALANCE,
    ADD_MONEY,
    RECEIVE_MONEY,
    TRANSFER_MONEY_BY_CLEVER_BANK,
    TRANSFER_MONEY_BY_OTHER_BANK;

    private int value;

    public int getValue(){
        return value;
    }

    Menu(int value){
        this.value = value;
    }

    Menu(){

    }
}
