package ru.clevertec.console.application.model;


import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Bank{
    private final int ID;
    private String bankName;
}
