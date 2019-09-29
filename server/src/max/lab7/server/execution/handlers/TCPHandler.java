package max.lab7.server.execution.handlers;

import max.lab7.server.execution.KeyHandler;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;

public abstract class TCPHandler {

    protected void send(SelectionKey key, String string) throws IOException{
        SocketChannel channel = (SocketChannel) key.channel();
        ByteBuffer buffer = ByteBuffer.allocate(8192);
        buffer.clear();
        buffer.put(string.getBytes());
        buffer.flip();
        channel.write(buffer);
    }

    protected String receive(SelectionKey key) throws IOException {
        SocketChannel channel = (SocketChannel) key.channel();
        ByteBuffer buffer = ByteBuffer.allocate(8192);
        channel.read(buffer);
        buffer.flip();
        return new String(buffer.array(), 0, buffer.limit());
    }

}
