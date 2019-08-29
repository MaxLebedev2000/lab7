package max.lab6.server.execution;

import java.io.IOException;

public class Test {
    public static void main(String[] args) throws IOException {
        Server server = new Server(2001);
        new Thread(server).start();
    }
}
