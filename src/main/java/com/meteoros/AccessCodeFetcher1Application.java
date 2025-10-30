package com.meteoros;


import java.util.List;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AccessCodeFetcher1Application implements CommandLineRunner {

    @Autowired
    private AuthService authService;

    @Autowired
    private DeviceService deviceService;
    @Autowired
    private ExcelService excelService;


    public static void main(String[] args) {
        SpringApplication.run(AccessCodeFetcher1Application.class, args);
    }

    @Override
    public void run(String... args) {

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter Base URL (http://127.0.0.1:8080): ");
        String baseUrl = sc.nextLine().trim();

        System.out.print("Enter Username (tenant@***.org) : ");
        String username = sc.nextLine().trim();

        System.out.print("Enter Password: ");
        String password = sc.nextLine().trim();

        String token = authService.authenticate(baseUrl, username, password);

        if (token == null) {
            System.out.println("Authentication Failed");
            return;
        }

        System.out.println("\nLogin Successful!");

        System.out.print("\nEnter Device Type (e.g. AEMA): ");
        String type = sc.nextLine().trim();

        List<DeviceBasic> devices = deviceService.getDeviceBasics(baseUrl, token, type);

//        System.out.println("\n Devices Found:\n");
//
//        devices.forEach(d ->
//            System.out.println(d.getDeviceName() + " | " + d.getDeviceId() + " | " + d.getAccessToken())
//        );
        excelService.writeDeviceAccessExcel(devices);
        sc.close();
    }
}
