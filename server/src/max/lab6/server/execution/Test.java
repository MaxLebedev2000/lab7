package max.lab6.server.execution;

import java.io.IOException;
import java.net.BindException;
import java.util.Scanner;

public class Test {
    public static void main(String[] args) throws IOException {
        while (true) {
            try {
                System.out.println("Введите порт канала сервера");
                Scanner scanner = new Scanner(System.in);
                int port = Integer.parseInt(scanner.nextLine());
                Server server = new Server(port);
                new Thread(server).start();
                break;
            }  catch (NumberFormatException e) {
                e.printStackTrace();
                System.out.println("Номер порта не является числом");
            } catch (BindException e){
                System.out.println("Порт занят, попробуйте другой");
            }
        }
    }
}
