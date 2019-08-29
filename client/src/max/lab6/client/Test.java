package max.lab6.client;

import java.io.IOException;
import java.util.Scanner;

public class Test {
    public static void main(String[] args) throws IOException {
        try {
            Scanner scanner = new Scanner(System.in);
            int port = Integer.parseInt(scanner.nextLine());
            Client client = new Client("localhost", port);
            new Thread(client).start();
        } catch (NumberFormatException e) {
            e.printStackTrace();
            System.out.println("Аргумент не является числом");
        }
    }
}
