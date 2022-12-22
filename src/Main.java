import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {

        File file = new File("basket.txt");

        String[] products = new String[]{"Молоко", "Хлеб", "Гречневая крупа", "Мясо"};
        int[] prices = new int[]{50, 14, 80, 290};
        Basket basket = new Basket(products, prices);

        Scanner scanner = new Scanner(System.in);
        System.out.println("Список возможных товаров для покупки");
        for (int i = 0; i < basket.getProducts().length; i++) {
            String product = basket.getProducts()[i];
            int price = basket.getPrices()[i];
            int position = i + 1;
            System.out.println(position + ". " + product + " " + price + " руб/шт");
        }
        if (!file.createNewFile()) {
            basket = Basket.loadFromTxtFile(file);
            basket.printCart();
        }

        while (true) {
            System.out.println("Выберите товар и количество или введите `end`");
            String input = scanner.nextLine();
            if (input.equals("end")) {
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
                basket.addToCart(productNum, amount);
            } catch (ArrayIndexOutOfBoundsException exception) {
                System.out.println("Товар с таким номером не найден!");
                continue;
            }
            basket.printCart();
            basket.saveTxt(file);
        }
    }
}