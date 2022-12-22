import java.util.*;
import java.lang.String;


public class Main {


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String[] products = {"Молоко", "Хлеб", "Гречневая крупа"};
        int[] prices = {50, 14, 80};
        int[] counts = new int[products.length];
        int sumProducts = 0;


        System.out.println();
        System.out.println("Список возможных товаров для покупки");
        for (int i = 0; i < products.length; i++) {
            System.out.println((i + 1) + " " + products[i] + " " + prices[i] + " руб/шт");
        }

        while (true) {
            System.out.println("Выберите товар и количество или введите `end`");
            String input = scanner.nextLine();
            if ("end".equals(input)) {
                break;
            }
            String[] parts = input.split(" ");
            int productNumber = Integer.parseInt(parts[0]) - 1;
            int productCount = Integer.parseInt(parts[0]);
            counts[productNumber] += productCount;
        }

        for (int i = 0; i < counts.length; i++) {
            if (counts[i] > 0) {
                System.out.println(
                        products[i] + " " + prices[i] + " руб/шт" +
                                " " + counts[i] + " шт " + (prices[i] * counts[i]) + " руб в сумме"
                );
                sumProducts += prices[i] * counts[i];
            }
        }
        System.out.println("Итого " + sumProducts + " руб");
    }
}
