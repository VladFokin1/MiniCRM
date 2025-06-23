package org.example.service;

import org.example.entity.Client;
import org.example.entity.Order;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class OrderService {

    private final TransactionHelper transactionHelper;

    public OrderService(TransactionHelper transactionHelper) {
        this.transactionHelper = transactionHelper;
    }

    public void saveOrder(Long clientID, LocalDateTime dateTime, int totalAmount, String status) {
        transactionHelper.executeInTransaction(session -> {
            Client client = session.get(Client.class, clientID);
            Order order = new Order(client, dateTime, totalAmount, status);
            session.persist(order);
        });
    }
}
