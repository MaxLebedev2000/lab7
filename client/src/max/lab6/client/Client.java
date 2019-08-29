package max.lab6.client;

import Humans.Card;
import org.json.CDL;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.net.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Client implements Runnable {

    private Socket socket;
    private String host;
    private int port;

    public Client(String host, int port) throws IOException {
        this.host = host;
        this.port = port;
    }

    @Override
    public void run() {
        while (true) {
            try {
                connect(host, port);
                System.out.println("Соеденение с сервером установлено!");
                System.out.println("Введите help для получения помощи");

            } catch (ConnectException e) {
                System.out.println("Сервер неактивен!");
                System.out.println("Для повторного подключения введите команду connect");
                System.out.println("Если хотите завершить работу введите exit");
                Scanner scanner = new Scanner(System.in);
                if (scanner.hasNextLine()) {
                    String command = scanner.nextLine();
                    if (command.equals("connect")) {
                        continue;
                    } else if (command.equals("exit")) {
                        System.out.println("До свидания!");
                        System.exit(2);
                    } else {
                        System.out.println("Вы ввели неверную команду, но мы все равно попробуем вас подключить");
                        continue;
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

            while (true) {
                Scanner scanner = new Scanner(System.in);
                if (scanner.hasNextLine()) {
                    String command = scanner.nextLine();
                    if (command.equals("")) {
                        System.out.println("Вы ничего не ввели");
                        continue;
                    }
                    if (command.equals("import")) {
                        Path path = Paths.get("collection.csv");

                        try {
                            command += " " + Files.lines(path).collect(Collectors.joining("\n"));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    try {
                        if (socket == null) continue;
                        socket.getOutputStream().write(command.getBytes());
                        System.out.println("Отправлено!");
                        byte[] data = new byte[8192];
                        int count = socket.getInputStream().read(data);
                        System.out.println(new String(data, 0, count));

                        if (command.equals("exit")) {
                            System.exit(1);
                        }
                    } catch (IOException e) {
                        if (e instanceof SocketException) {
                            try {
                                System.out.println("Потеряно соединение с сервером!");
                                Thread.sleep(1000);
                                socket.close();
                                connect(host, port);
                                continue;
                            } catch (IOException | InterruptedException e1) {
                                continue;
                            }
                        }
                        e.printStackTrace();
                    }

                }
            }
        }

    }

    public void connect(String address, int port) throws IOException {
        socket = new Socket(address, port);
    }
}
