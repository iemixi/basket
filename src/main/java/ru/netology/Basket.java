package ru.netology;

import java.io.*;
import java.util.Arrays;

public class Basket implements Serializable {
    private final long[] prices;
    private final String[] products;

    private final long[] productsAmount;

    public Basket(long[] prices, String[] products) {
        this.prices = prices;
        this.products = products;

        productsAmount = new long[products.length];
        Arrays.fill(productsAmount, 0);
    }

    public Basket(long[] prices, String[] products, long[] productsAmount) {
        this.prices = prices;
        this.products = products;
        this.productsAmount = productsAmount;
    }

    public void printStock() {
        System.out.println("Ассортимент:");
        for (int i = 0; i < products.length; ++i) {
            System.out.printf("%d. %s %dр./шт.\n", i + 1, products[i], prices[i]);
        }
    }

    public boolean addToCart(int productNum, int amount) {
        if (productNum < 1 || productNum > productsAmount.length) {
            return false;
        }

        productsAmount[productNum - 1] += amount;

        return true;
    }

    public void printCart() {
        System.out.println("Ваша корзина:");

        long sum = 0;
        int count = 1;
        for (int idx = 0; idx < productsAmount.length; ++idx) {
            if (productsAmount[idx] != 0) {
                long currentPrice = productsAmount[idx] * prices[idx];

                if (currentPrice != 0) {
                    System.out.printf("%d. %s %dр/шт. %dшт. %dр. в сумме \n",
                            count, products[idx], prices[idx], productsAmount[idx], currentPrice);
                    count++;
                }

                sum += currentPrice;
            }
        }

        if (sum > 0) {
            System.out.printf("Итого: %d руб.\n", sum);
        }
    }

    public void saveTxt(File textFile) {
        try (PrintWriter printWriter = new PrintWriter(textFile)) {
            for (long price : prices) {
                printWriter.print(price + " ");
            }
            printWriter.println();

            for (String product : products) {
                printWriter.print(product + " ");
            }
            printWriter.println();

            for (long amount : productsAmount) {
                printWriter.print(amount + " ");
            }
            printWriter.println();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static Basket loadFromTxtFile(File textFile) {
        try (BufferedReader reader = new BufferedReader(new FileReader(textFile))) {
            String[] pricesInString = reader.readLine().split(" ");
            String[] productNames = reader.readLine().split(" ");
            String[] productsAmountInString = reader.readLine().split(" ");

            long[] prices = new long[pricesInString.length];
            long[] productsAmount = new long[productsAmountInString.length];

            for (int i = 0; i < pricesInString.length; ++i) {
                prices[i] = Long.parseLong(pricesInString[i]);
            }

            for (int i = 0; i < productsAmountInString.length; ++i) {
                productsAmount[i] = Long.parseLong(productsAmountInString[i]);
            }

            return new Basket(prices, productNames, productsAmount);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveBin(File binFile) {
        try (ObjectOutputStream writer =
                     new ObjectOutputStream(new FileOutputStream(binFile))) {

            writer.writeObject(this);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Basket loadFromBin(File binFile) {
        try (ObjectInputStream reader =
                     new ObjectInputStream(new FileInputStream(binFile))) {

            return (Basket) reader.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
