package org.example.service;

import org.example.entity.Client;
import org.example.entity.Order;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

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

    public List<Order> getFilteredOrders(Integer totalAmountFilter, String statusFilter, LocalDateTime dateFilter) {
        return transactionHelper.executeInTransaction(session -> {
            StringBuilder queryBuilder = new StringBuilder("FROM Order o WHERE 1=1");

            if (totalAmountFilter != null) {
                queryBuilder.append(" AND o.totalAmount = :totalAmount");
            }
            if (statusFilter != null && !statusFilter.isEmpty()) {
                queryBuilder.append(" AND o.status = :status");
            }
            if (dateFilter != null) {
                queryBuilder.append(" AND o.orderDate = :orderDate");
            }

            var query = session.createQuery(queryBuilder.toString(), Order.class);

            if (totalAmountFilter != null) {
                query.setParameter("totalAmount", totalAmountFilter);
            }
            if (statusFilter != null && !statusFilter.isEmpty()) {
                query.setParameter("status", statusFilter);
            }
            if (dateFilter != null) {
                query.setParameter("orderDate", dateFilter);
            }

            return query.getResultList();
        });
    }
}
