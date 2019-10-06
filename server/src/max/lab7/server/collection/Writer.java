package max.lab7.server.collection;


import max.lab5.humans.Card;

import java.util.Set;

public interface Writer {
    boolean write(Set<Card> collection);
    void reset();
    void close();
}
