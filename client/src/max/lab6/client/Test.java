package max.lab6.client;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Objects;
import java.util.Scanner;
import java.util.concurrent.*;

public class Test {
    public static void main(String[] args) throws IOException {
        while (true) {
            try {
                int port = -1;
                System.out.println("Введите хост сервера");
                Scanner scanner = new Scanner(System.in);
                String host = scanner.nextLine();
                try {
                    System.out.println("Введите порт сервера");
                    port = Integer.parseInt(scanner.nextLine());

                } catch (java.lang.NumberFormatException e) {
                    System.out.println("Номер порта не является числом");
                    continue;
                } catch (IllegalArgumentException e){
                    System.out.println("Порт должен быть числом от 1 до 65536");
                }
                Client client = new Client(host, port);
                try {
                    throw Executors.newFixedThreadPool(1).submit(client).get(5, TimeUnit.SECONDS);
                } catch (TimeoutException e){
                    break;
                }

            } catch (java.net.SocketException e) {
                System.out.println("Нечитабельный хост");
            } catch (InterruptedException | ExecutionException e){
                e.printStackTrace();
            } catch (UnknownHostException e){
                System.out.println("Неверный хост");
            }
        }
    }
}
