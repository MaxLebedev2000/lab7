package max.lab6.client;

import java.io.IOException;
import java.net.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;

public class Client implements Callable<UnknownHostException> {

    private Socket socket;
    private String host;
    private int port;

    public Client(String host, int port) throws IOException {
        this.host = host;
        this.port = port;
    }

    @Override
    public UnknownHostException call() throws IOException {
        while (true) {
            try {
                connect(host, port);
                System.out.println("Соеденение с сервером установлено!");

                signin();


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
            } catch (UnknownHostException e) {
                return e;
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
                        if (count == -1) {
                            System.out.println("неверная команда");
                            continue;
                        } else {
                            System.out.println(new String(data, 0, count));
                        }
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

    private void signin() {
        Scanner scanner = new Scanner(System.in);
        boolean done = false;
        while (!done) {

            System.out.println("Если вы хотите войти, введите команду в формате \"signin;login;password\",\n" +
                    "если хотите зарегестрироваться - \"signup;login;email\"");
            if (scanner.hasNext()) {
                String send = scanner.nextLine();
                send = send.replaceAll("\\s","");
                String[] array = send.split(";");
                if (array.length != 3) {
                    System.out.println("Введены неверные данные");
                    continue;
                }

                try {
                    if (socket == null) continue;
                    socket.getOutputStream().write(send.getBytes());
                    byte[] data = new byte[8192];
                    int count = socket.getInputStream().read(data);
                    if (count == -1) {
                        System.out.println("то-то пошло не так...");
                        continue;
                    } else {
                        String respond = new String(data, 0, count);
                        switch (respond) {
                            case "incorrect":
                                System.out.println("Введены неверные данные");
                                continue;

                            case "userExists":
                                System.out.println("Пользователь с таким логином уже существует");
                                break;

                            case "regdone":
                                System.out.println("Вы успешно зарегестрировались");
                                done = true;
                                break;

                            case "logdone":
                                System.out.println("Вы успешно вошли");
                                done = true;
                                break;

                            case "incorrectPassword":
                                System.out.println("Неверный пароль");
                                break;

                            case "noSuchUser":
                                System.out.println("Пользователь с данным логином не найден");
                                break;

                            default:
                                System.out.println("Unknown respond " + respond);
                                break;
                        }


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
