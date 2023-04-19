package ru.netology;

import java.io.File;

public class Main {
    public static void main(String[] args) {
        File file = new File("basket.txt");

        Basket basket = new Basket(new long[]{1, 2, 3}, new String[]{"Рис", "Вода", "Молоко"});

        basket.addToCart(1, 2);
        basket.addToCart(1, 2);
        basket.addToCart(0, 2);
        basket.addToCart(4, 2);
        basket.addToCart(3, 6);

        basket.printCart();

        basket.saveTxt(file);

        Basket loadedBasket = Basket.loadFromTxtFile(file);
        loadedBasket.printCart();
    }
}
