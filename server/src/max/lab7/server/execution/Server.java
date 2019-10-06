package max.lab7.server.execution;

import max.lab7.server.collection.databasecollection.Cards;
import max.lab7.server.collection.CollectionManager;
import max.lab7.server.execution.handlers.CommandKeyHandler;
import max.lab7.server.execution.handlers.UnknownKeyHandler;
import max.lab7.server.sql.JDBCWorker;
import max.lab7.server.users.Connection;
import max.lab7.server.users.Type;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.*;

public class Server implements Runnable {

    private Selector selector;
    private CollectionManager manager;
    private static int id = 0;
    private static Map<Integer, SelectionKey> connections = new HashMap<>();
    private Map<String, KeyHandler> handlers = new HashMap<>();
    private JDBCWorker database = JDBCWorker.instance();

    public Server(int port) throws IOException {
        ServerSocketChannel channel = ServerSocketChannel.open();
        channel.bind(new InetSocketAddress("localhost", port));
        channel.configureBlocking(false);
        selector = Selector.open();
        channel.register(selector, SelectionKey.OP_ACCEPT);
        manager = new Cards();
        handlers.put(Type.COMMANDS.name(), new CommandKeyHandler(manager));
        handlers.put(Type.UNKNOWN.name(), new UnknownKeyHandler());

    }

    @Override
    public void run() {
        while (true) {
            try {

                if (selector.select() == 0) continue;

                Iterator<SelectionKey> iter = selector.selectedKeys().iterator();

                while (iter.hasNext()) {
                    SelectionKey key = iter.next();
                    iter.remove();
                    try {
                        if (key.attachment() == null) {
                            handleNewConnection(key);
                        } else {
                            handleCommand(key);
                        }
                    } catch (IOException e) {
                        key.cancel();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void handleNewConnection(SelectionKey key) throws IOException {
        SocketChannel channel = ((ServerSocketChannel) key.channel()).accept();
        channel.configureBlocking(false);
        int curId = id++;
        Connection connection = new Connection(curId);
        connections.put(curId, channel.register(selector, SelectionKey.OP_READ, connection));
    }

    private void handleCommand(SelectionKey key) throws IOException {
        KeyHandler handler = handlers.get(((Connection)key.attachment()).type().name());
        try {
            handler.handle(key);
        } catch (IOException e) {
            throw e;
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public static Map<Integer, SelectionKey> getConnections() {
        return connections;
    }

    public Selector getSelector() {
        return selector;
    }
}
