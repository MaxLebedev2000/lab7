package max.lab6.server.collection;

import max.lab5.humans.Card;

import java.util.Set;

public interface Reader {
    boolean read();
    Set<Card> getCollection();

}
