package org.example.command;

import org.example.entity.Order;
import org.example.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

@Component
public class FindOrdersCommand implements ICommand{

    private final Scanner scanner;
    private final OrderService orderService;

    @Autowired
    public FindOrdersCommand(Scanner scanner, OrderService orderService) {
        this.scanner = scanner;
        this.orderService = orderService;
    }

    @Override
    public void execute() {
        System.out.println("Введите фильтр по сумме (оставьте пустым, если не нужен):");
        String totalAmountInput = scanner.nextLine().trim();
        Integer totalAmountFilter = totalAmountInput.isEmpty() ? null : Integer.parseInt(totalAmountInput);

        System.out.println("Введите фильтр по статусу (оставьте пустым, если не нужен):");
        String statusFilter = scanner.nextLine().trim();
        if (statusFilter.isEmpty()) {
            statusFilter = null;
        }

        System.out.println("Введите фильтр по дате (формат: DD MM YYYY, оставьте пустым, если не нужен):");
        String dateString = scanner.nextLine().trim();
        LocalDateTime dateFilter = null;
        if (!dateString.isEmpty()) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MM yyyy");
            dateFilter = LocalDateTime.parse(dateString, formatter);
        }

        List<Order> filteredOrders = orderService.getFilteredOrders(totalAmountFilter, statusFilter, dateFilter);
        if (filteredOrders.isEmpty()) System.out.println("Не нашлось заказов с указанными фильрами");
        else {
            filteredOrders.stream().forEach(element -> {
                System.out.print(element.getId());
                System.out.print(" | ");
                System.out.print(element.getTotalAmount());
                System.out.print(" | ");
                System.out.print(element.getStatus());
                System.out.print(" | ");
                System.out.print(element.getOrderDate());
                System.out.println();
            });
        }
    }

    @Override
    public String getDescription() {
        return "Найти заказы";
    }
}
