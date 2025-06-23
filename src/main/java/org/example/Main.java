package org.example;

import org.example.command.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext("org.example");


        Scanner scanner = context.getBean(Scanner.class);
        Map<Integer, ICommand> commands = new HashMap<>();

        // Регистрация команд
        commands.put(1, context.getBean(AddClientCommand.class));
        commands.put(2, context.getBean(RemoveClientCommand.class));
        commands.put(3, context.getBean(EditProfileCommand.class));
        commands.put(4, context.getBean(AddOrderCommand.class));
//        commands.put(5, context.getBean(EditCouponsCommand.class));
//        commands.put(6, context.getBean(FindOrdersCommand.class));
//        commands.put(7, context.getBean(ExitCommand.class));

        while (true) {
            printMenu(commands);
            int choice = scanner.nextInt();
            scanner.nextLine(); // Очистка буфера

            ICommand command = commands.get(choice);
            if (command != null) {
                command.execute();
            } else {
                System.out.println("Неверная команда!");
            }
        }
    }

    private static void printMenu(Map<Integer, ICommand> commands) {
        System.out.println("\nДоступные команды:");

        // Сортируем команды по ключу и выводим
        commands.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .forEach(entry -> System.out.printf("%d. %s%n",
                        entry.getKey(),
                        entry.getValue().getDescription()));

        System.out.println("Выберите команду: ");
    }
}