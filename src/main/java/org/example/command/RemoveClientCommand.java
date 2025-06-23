package org.example.command;

import org.example.service.ClientService;
import org.example.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class RemoveClientCommand implements ICommand{

    private final ClientService clientService;
    private final Scanner scanner;

    @Autowired
    public RemoveClientCommand(ClientService clientService, Scanner scanner) {
        this.clientService = clientService;
        this.scanner = scanner;
    }

    @Override
    public void execute() {
        System.out.println("Введите id клиента для удаления:");
        Long id = scanner.nextLong();
        clientService.deleteClient(id);
    }

    @Override
    public String getDescription() {
        return "Удалить клиента";
    }
}
