package org.example.service;

import org.example.entity.Coupon;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
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
            return session.createQuery("SELECT c FROM Coupon WHERE c.code = %s".formatted(code), Coupon.class).getSingleResult();
        });
    }
}
