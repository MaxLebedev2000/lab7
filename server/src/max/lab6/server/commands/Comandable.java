package max.lab6.server.commands;


import FileWorks.collection.CollectionManager;

/**
 * Коммандный интерфейс
 */
public interface Comandable {
    /**
     * Запускатет комманду
     * @param jsonElement данные комманды
     * @param manager менеджер коллекции
     */
     String run(String jsonElement, CollectionManager manager, int id);

}
