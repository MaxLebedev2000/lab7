package max.lab7.server.collection.databasecollection;

import max.lab5.humans.Card;
import max.lab7.server.collection.CollectionManager;
import max.lab7.server.sql.JDBCWorker;
import max.lab7.server.sql.tables.CardsTable;

import java.util.Set;

public class Cards implements CollectionManager {

    private CardsTable table;
    private Set<Card> collection;

    public Cards(){
        this.table = JDBCWorker.instance().getCards();
        this.read();
    }

    @Override
    public boolean read() {
        this.collection = table.set();
        if (collection == null) return false;
        return true;
    }

    @Override
    public boolean write() {
        this.table.addAll(collection);
        return true;
    }

    @Override
    public void close() {

    }

    @Override
    public Set<Card> getCollection() {
        return collection;
    }
}
