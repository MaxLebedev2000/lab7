package max.lab7.server.execution.handlers;

import max.lab7.server.Pair;
import max.lab7.server.collection.CollectionManager;
import max.lab7.server.commands.CommandFactory;
import max.lab7.server.commands.Commandable;
import max.lab7.server.execution.KeyHandler;
import max.lab7.server.users.Connection;

import java.nio.channels.SelectionKey;

public class CommandKeyHandler extends TCPHandler implements KeyHandler {

    private CollectionManager manager;

    public CommandKeyHandler(CollectionManager manager){
        this.manager = manager;
    }


    @Override
    public void handle(SelectionKey key) throws Exception {

        String command = super.receive(key);
        String clearCommand = command.trim();
        Pair<Commandable, String> pair = CommandFactory.createComand(clearCommand);
        String respond;
        if (pair.getKey() != null) {
            respond = pair.getKey().run(pair.getValue(), manager, ((Connection) key.attachment()).id());
        } else {
            if (pair.getValue() == null) {
                respond = "Неверная команда!";
            } else {
                respond = pair.getValue();
            }
        }
        super.send(key, respond);

    }
}
