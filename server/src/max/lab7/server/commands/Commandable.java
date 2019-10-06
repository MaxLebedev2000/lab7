package max.lab7.server.commands;


import max.lab7.server.collection.CollectionManager;

/**
 * Коммандный интерфейс
 */
public interface Commandable {
    /**
     * Запускатет комманду
     * @param jsonElement данные комманды
     * @param manager менеджер коллекции
     */
     String run(String jsonElement, CollectionManager manager, int id);

}
