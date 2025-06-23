package org.example.command;

import org.example.service.ClientService;
import org.example.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class EditProfileCommand implements ICommand{

    private final ClientService clientService;
    private final ProfileService profileService;
    private final Scanner scanner;

    @Autowired
    public EditProfileCommand(ClientService clientService, ProfileService profileService, Scanner scanner) {
        this.clientService = clientService;
        this.profileService = profileService;
        this.scanner = scanner;
    }

    @Override
    public void execute() {
        System.out.println("Введите id профиля:");
        Long id = scanner.nextLong();
        System.out.println("Введите новый телефон профиля:");
        String phone = scanner.nextLine();
        System.out.println("Введите новый adress");
        String address = scanner.nextLine();
        profileService.updateProfile(id, phone, address);
    }

    @Override
    public String getDescription() {
        return "Редактировать профиль";
    }
}
