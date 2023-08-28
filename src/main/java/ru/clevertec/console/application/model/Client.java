package ru.clevertec.console.application.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Client {
    private final int ID;
    private String lastName;
    private String firstName;
}
