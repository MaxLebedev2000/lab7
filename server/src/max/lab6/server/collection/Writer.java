package max.lab6.server.collection;

import Humans.Card;

import java.util.Set;

public interface Writer {
    boolean write(Set<Card> collection);
    void reset();
    void close();
}
