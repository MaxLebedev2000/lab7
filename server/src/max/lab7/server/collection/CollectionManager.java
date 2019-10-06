package max.lab7.server.collection;

import max.lab5.humans.Card;

import java.util.Set;

public interface CollectionManager {
    boolean read();
    boolean write();
    void close();
    Set<Card> getCollection();
}
