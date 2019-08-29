package max.lab6.server.collection;

import Humans.Card;

import java.util.Set;

public interface Reader {
    boolean read();
    Set<Card> getCollection();

}
