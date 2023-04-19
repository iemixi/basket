package ru.netology;

import java.io.File;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        File file = new File("basket.bin");

        Basket basket;
        if (file.exists()) {
            basket = Basket.loadFromBin(file);
            basket.printCart();
        } else {
            basket = new Basket(new long[]{1, 2, 3}, new String[]{"Рис", "Хлеб", "Молоко"});
        }

        basket.printStock();

        while (true) {
            System.out.println("Выберите товар и количество или введите end: ");
            String input = scanner.nextLine();

            if (input.equals("end")) {
                break;
            }

            String[] data = input.split(" ");

            if (data.length != 2) {
                System.out.println("Неверный ввод данных.");
            } else {
                try {
                    int productNumber = Integer.parseInt(data[0]);
                    int amount = Integer.parseInt(data[1]);

                    if (!basket.addToCart(productNumber, amount)) {
                        System.out.println("Неверно указан товар или его количество");
                    } else {
                        basket.saveBin(file);
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Неверный формат данных.");
                }
            }
        }
    }
}
