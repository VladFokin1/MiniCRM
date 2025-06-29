package org.example.service;

import org.example.entity.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientService {

    private final TransactionHelper transactionHelper;

    @Autowired
    public ClientService(TransactionHelper transactionHelper) {
        this.transactionHelper = transactionHelper;
    }

    public void saveClient(Client client) {
        transactionHelper.executeInTransaction(session -> {
            session.persist(client);
        });
    }

    public void deleteClient(Long id) {
        transactionHelper.executeInTransaction(session -> {
            Client client = session.get(Client.class, id);
            session.remove(client);
        });
    }

    public List<Client> getAllClients() {
        return transactionHelper.executeInTransaction(session -> {
            return session.createQuery("SELECT c FROM Client c left join fetch c.orderList", Client.class).list();
        });
    }
}
