package ru.clevertec.console.application.services;

import ru.clevertec.console.application.model.Client;

public interface ClientManagable {
    void createClient(String lastName, String firstName);

    Client readClient(int clientId);

    default void updateClient(int clientId, String newLastName, String newFirstName) {
    }

    void deleteClient(int clientId);
}
