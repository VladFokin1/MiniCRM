package org.example.command;

import org.example.entity.Coupon;
import org.example.service.CouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

@Component
public class EditCouponsCommand implements ICommand{

    private final Scanner scanner;
    private final CouponService couponService;

    @Autowired
    public EditCouponsCommand(Scanner scanner, CouponService couponService) {
        this.scanner = scanner;
        this.couponService = couponService;
    }

    @Override
    public void execute() {
        System.out.println("Существующие купоны:");
        List<Coupon> coupons = couponService.getAllCoupons();
        System.out.println("ID | code | discount | expiration date");
        for (Coupon coupon : coupons) {
            System.out.println(coupon.getId() + " | " +coupon.getCode() + " | " +coupon.getDiscount() + " | " + coupon.getExpirationDate());
        }
        System.out.println("Введите ID купона для редактирования:");
        long id = scanner.nextLong();
        Coupon couponToEdit = coupons.stream().filter(elem -> elem.getId() == id).findFirst().get();

        System.out.println("Введите новый код (оставьте поле пустым для пропуска):");
        String newCode = scanner.nextLine();

        System.out.println("Введите новое значение скидки (введите 0 для пропуска):");
        float newDiscount = scanner.nextFloat();

        String customFormat = "DD MM YYYY";
        DateTimeFormatter customFormatter = DateTimeFormatter.ofPattern(customFormat);
        System.out.println("Введите новую дату окончания (оставьте поле пустым для пропуска):");
        System.out.println("Формат ввода: " + customFormat);
        String dateString = scanner.nextLine();
        LocalDateTime newDate = dateString.isEmpty() ? null : LocalDateTime.parse(dateString, customFormatter);

        if (!newCode.isEmpty())
            couponToEdit.setCode(newCode);
        if (newDiscount != 0)
            couponToEdit.setDiscount(newDiscount);
        if (newDate != null)
            couponToEdit.setExpirationDate(newDate);

    }

    @Override
    public String getDescription() {
        return "Редактировать купоны";
    }
}
