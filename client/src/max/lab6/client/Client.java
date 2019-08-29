package max.lab6.client;

import Humans.Card;
import org.json.CDL;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
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

        try {
            connect(host, port);
        } catch (IOException e){
            e.printStackTrace();
        }
        while (true)  {
            Scanner scanner = new Scanner(System.in);
            if (scanner.hasNext()) {
                String command = scanner.nextLine();
                if (command.equals("import")) {
                    Path path = Paths.get("D:\\ТЕСТЫ ПО ПРОГЕ\\lab6\\client\\collection.csv");
                    try {
                        command += " " + Files.lines(path).collect(Collectors.joining("\n"));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                try {
                    socket.getOutputStream().write(command.getBytes());
                    System.out.println("Отправлено!");
                    byte[] data = new byte[8192];
                    int count = socket.getInputStream().read(data);
                    System.out.println(new String(data, 0, count));
                } catch (IOException e) {
                    if (e instanceof SocketException){
                        try {
                            System.out.println("Потеряно соединение с сервером");
                            Thread.sleep(1000);
                            socket.close();
                            connect(host, port);
                            continue;
                        } catch (IOException | InterruptedException e1){
                            continue;
                        }
                    }
                    e.printStackTrace();
                }

            }
        }

    }

    public void connect(String address, int port) throws IOException{
        socket = new Socket(address, port);
    }
}
