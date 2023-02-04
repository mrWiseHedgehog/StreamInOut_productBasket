import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.util.Arrays;
import java.util.Scanner;

public class Main {

    public static Config loadConfig() {
        return new Config();
    }

    public static void saveFromJson(Basket basket, Config config) {
        Gson gson = new GsonBuilder().create();

        try (FileWriter file = new FileWriter(config.getFileNameSave())) {
            file.write(gson.toJson(basket));
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Basket loadFromJson(String[] products, int[] price, Config config) {
        Basket basket = null;

        Gson gson = new GsonBuilder().create();
        File file = new File(config.getFileNameLoad());
        String productsJson;
        if (file.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                productsJson = reader.readLine();
                basket = gson.fromJson(productsJson, Basket.class);

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            String basketArray = Arrays.toString(new int[products.length]);
            productsJson = "{"
                    + "\"products\":" + Arrays.toString(products) + ","
                    + "\"price\":" + Arrays.toString(price) + ","
                    + "\"basket\":" + basketArray
                    + "}";

            basket = gson.fromJson(productsJson, Basket.class);
        }
        return basket;
    }


    public static void main(String[] args) throws IOException {

        Scanner scanner = new Scanner(System.in);
        Config config = loadConfig();
        String[] products = new String[]{"Молоко", "Хлеб", "Гречневая крупа", "Мясо"};
        int[] prices = new int[]{50, 14, 80, 340};
        ClientLog clientLog = new ClientLog();
        Basket basket = new Basket(products, prices);


        while (true) {

            System.out.println("Список возможных товаров для покупки");
            for (int i = 0; i < products.length; i++) {
                int position = i + 1;
                System.out.println(position + ": " + products[i] + " " + prices[i] + " руб/шт.");
            }

            System.out.println("Выберите товар и количество или введите `end`");
            String input = scanner.nextLine();

            // "end" logic
            if (input.equals("end")) {
                if (config.isLog()) {
                    clientLog.exportAsCSV(new File("/home/lex/" + config.getFileNameLog()));
                }
                if (config.isSave() && "txt".equals(config.getFormatSave())) {
                    basket.saveTxt(new File(config.getFileNameSave()));
                }
                if (config.isSave() && "json".equals(config.getFormatSave())) {
                    saveFromJson(basket, config);
                }
                System.out.println("Программа завершена!");
                break;
            }

            String[] parts = input.split(" ");
            if (parts.length != 2) {
                System.out.println("Ошибка ввода! Должно быть введено 2 числа!");
                continue;
            }
            int productNum = Integer.parseInt(parts[0]) - 1;
            int amount = Integer.parseInt(parts[1]);
            try {
                basket.addToBasket(productNum, amount);
                clientLog.log(productNum, amount);
            } catch (ArrayIndexOutOfBoundsException exception) {
                System.out.println("Товар с таким номером не найден!");
                continue;
            }
            basket.printBasket();
        }
    }
}