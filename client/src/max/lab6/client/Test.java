package max.lab6.client;

import java.io.IOException;

public class Test {
    public static void main(String[] args) throws IOException {
        Client client = new Client("localhost", 2001);
        new Thread(client).start();
    }
}
