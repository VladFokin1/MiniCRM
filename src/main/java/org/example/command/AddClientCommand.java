package org.example.command;

import org.example.entity.Client;
import org.example.entity.Coupon;
import org.example.entity.Profile;
import org.example.service.ClientService;
import org.example.service.CouponService;
import org.example.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

@Component
public class AddClientCommand implements ICommand{

    private final ClientService clientService;
    private final ProfileService profileService;
    private final Scanner scanner;
    private final CouponService couponService;

    @Autowired
    public AddClientCommand(ClientService clientService,
                            ProfileService profileService,
                            Scanner scanner,
                            CouponService couponService) {
        this.clientService = clientService;
        this.profileService = profileService;
        this.scanner = scanner;
        this.couponService = couponService;
    }

    @Override
    public void execute() {
        System.out.println("Введите имя клиента:");
        String name = scanner.nextLine();
        System.out.println("Введите email клиента:");
        String email = scanner.nextLine();
        System.out.println("Теперь заполним данные профиля.");
        System.out.println("Введите телефон:");
        String phone = scanner.nextLine();
        System.out.println("Введите адрес:");
        String address = scanner.nextLine();

        Client client = new Client(name, email, LocalDateTime.now());

        Profile profile = new Profile(address, phone, client);

        System.out.println("Доступные купоны:");
        List<Coupon> couponList = couponService.getAllCoupons();
        for (Coupon coupon : couponList) {
            System.out.println(coupon.getCode() + " " + coupon.getDiscount() + "% " + coupon.getExpirationDate()) ;
        }

        List<Coupon> addedCoupons;
        String couponCode = "";
        while (!couponCode.equals("0")) {
            System.out.println("Введите код купона для добавления (для выхода введите 0):");
            couponCode = scanner.nextLine();
            if (couponCode.equals("0")) break;
            String finalCouponCode = couponCode;
            Coupon coupon = couponList.stream()
                    .filter(element -> Objects.equals(element.getCode(), finalCouponCode))
                    .findFirst()
                    .get();
            client.getCouponList().add(coupon);
        }

        clientService.saveClient(client);
        profileService.saveProfile(profile);

    }

    @Override
    public String getDescription() {
        return "Добавить клиента";
    }
}
