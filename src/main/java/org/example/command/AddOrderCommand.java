package org.example.command;

import org.example.entity.Client;
import org.example.entity.Order;
import org.example.service.ClientService;
import org.example.service.OrderService;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

@Component
public class AddOrderCommand implements ICommand{

    private final Scanner scanner;
    private final OrderService orderService;
    private final ClientService clientService;

    public AddOrderCommand(Scanner scanner, OrderService orderService, ClientService clientService) {
        this.scanner = scanner;
        this.orderService = orderService;
        this.clientService = clientService;
    }

    @Override
    public void execute() {
        System.out.println("Выберите ID клиента для добавления заказа:");
        List<Client> clients = clientService.getAllClients();
        for (Client client : clients) {
            System.out.println(client.getId() + " " + client.getName());
        }
        Long id = scanner.nextLong();

        System.out.println("Введите итоговое количество:");
        int totalAmount = scanner.nextInt();
        System.out.println("Введите статус:");
        String status = scanner.nextLine();

        orderService.saveOrder(id, LocalDateTime.now(), totalAmount, status);
    }

    @Override
    public String getDescription() {
        return "Добавить заказ";
    }
}
