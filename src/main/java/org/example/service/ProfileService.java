package org.example.service;

import org.example.entity.Profile;
import org.springframework.stereotype.Service;

@Service
public class ProfileService {

    private final TransactionHelper transactionHelper;

    public ProfileService(TransactionHelper transactionHelper) {
        this.transactionHelper = transactionHelper;
    }

    public void saveProfile(Profile profile) {
        transactionHelper.executeInTransaction(session -> {
            session.persist(profile);
        });
    }

    public void updateProfile(Long id, String phone, String address) {
        transactionHelper.executeInTransaction(session -> {
            Profile profile = session.get(Profile.class, id);
            profile.setPhone(phone);
            profile.setAddress(address);
        });
    }
}
