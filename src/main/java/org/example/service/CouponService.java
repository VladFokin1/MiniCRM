package org.example.service;

import org.example.entity.Coupon;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CouponService {
    private final TransactionHelper transactionHelper;

    public CouponService(TransactionHelper transactionHelper) {
        this.transactionHelper = transactionHelper;
    }

    public List<Coupon> getAllCoupons() {
        return transactionHelper.executeInTransaction(session -> {
           return session.createQuery("SELECT c FROM Coupon c", Coupon.class).list();
        });
    }

    public Coupon getByCode(String code) {
        return transactionHelper.executeInTransaction(session -> {
            return session.createQuery("SELECT c FROM Coupon c WHERE c.code = :code", Coupon.class)
                    .setParameter("code", code)
                    .getSingleResult();
        });
    }
}
