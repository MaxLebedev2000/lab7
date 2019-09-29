package max.lab7.server.execution;

import java.io.IOException;
import java.nio.channels.SelectionKey;

public interface KeyHandler {
    void handle(SelectionKey key) throws Exception;
}
