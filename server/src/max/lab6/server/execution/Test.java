package max.lab6.server.execution;

import java.io.IOException;
import java.util.Scanner;

public class Test {
    public static void main(String[] args) throws IOException {
        try {
            if (args.length==0){
                System.out.println("Необходимо указать порт агрументом");
                System.exit(2);
            }
            int port = Integer.parseInt(args[0]);
            Server server = new Server(port);
            new Thread(server).start();
        } catch (NumberFormatException e) {
            e.printStackTrace();
            System.out.println("Аргумент не является числом");
        }
    }
}
