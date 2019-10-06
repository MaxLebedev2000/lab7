package max.lab7.server.commands;


import max.lab5.humans.Card;
import max.lab7.server.Pair;
import max.lab7.server.execution.Server;
import max.lab7.server.users.Connection;
import org.json.CDL;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class CommandFactory {
    /**
     * Удаляет элемент из коллекции
     */
    private static Commandable removeCmd = (jsonElement, manager, id) -> {
        String owner = ((Connection)Server.getConnections().get(id).attachment()).getLogin();
        Card card = new Card(new JSONObject(jsonElement).put("owner", owner));
        manager.getCollection().remove(card);
        manager.write(); // Сохраняет коллекцию
        return "Элемент удалён!";
    };
    /**
     * Очищает коллекцию
     */
    private static Commandable clearCmd = ((jsonElement, manager, id) -> {
        manager.getCollection().clear();
        manager.write(); // Сохраняет коллекцию
        return "Коллекция очищена!";
    });
    /**
     * Выводит на экран иныормацию о коллекции
     */
    private static Commandable infoCmd = ((jsonElement, manager, id) -> {
        return "Тип коллекции: " + manager.getCollection().getClass().getSimpleName() + "\n" +
                "Дата инициализации коллекции: " + new Date().toString() + "\n" +
                "Тип элементов коллекции: " + Card.class.getSimpleName() + "\n" +
                "Количество элементов коллекции: " + manager.getCollection().size() + "\n" + "Готово!" + "\n";
    });
    /**
     * Добавляет элемент в коллекцию
     */
    private static Commandable addCmd = (jsonElement, manager, id) -> {
        String owner = ((Connection)Server.getConnections().get(id).attachment()).getLogin();
        Card card = new Card(new JSONObject(jsonElement).put("owner", owner));
        manager.getCollection().add(card);
        manager.write(); // Сохраняет коллекцию
        return "Элемент добавлен!";
    };
    /**
     * Добавляет элемент в коллекцию, если он минимальный
     */
    private static Commandable addIfMinCmd = (jsonElement, manager, id) -> {
        String owner = ((Connection)Server.getConnections().get(id).attachment()).getLogin();
        Card card = new Card(new JSONObject(jsonElement).put("owner", owner));
        if (Collections.min(manager.getCollection()).compareTo(card) > 0) {
            manager.getCollection().add(card);
            manager.write(); // Сохраняет коллекцию
        }
        return "Элемент добавлен,тк он минимальный!";

    };
    /**
     * Выводит на экран элементы коллекции
     */
    private static Commandable showCmd = ((jsonElement, manager, id) -> {
        if (manager.getCollection().size() == 0) return "Коллекция пуста";
        return manager.getCollection().stream().map(Card::toString).collect(Collectors.joining("\n+++++++++++++++++++++++++++++++++++++++++++++\n"));
    });
    /**
     * Сохраняет коллекцию в файл
     */
    private static Commandable saveCmd = ((jsonElement, manager, id) -> {
        manager.write();
        return "Коллекция сохранена";
    });
    /**
     * Завершает работу программы
     */
    private static Commandable exitCmd = ((jsonElement, manager, id) -> {
        Server.getConnections().remove(id).cancel();
        return "Завершение работы программы!";
    });

    /**
     * Переносит данные нв сервер
     */
    private static Commandable importCmd = ((jsonElement, manager, id) -> {
        String owner = ((Connection)Server.getConnections().get(id).attachment()).getLogin();
        try {
            if (jsonElement.length() != 0) {
                JSONArray array = CDL.toJSONArray(jsonElement);

                array.forEach(p -> {
                    JSONObject o = (JSONObject) p;
                    o.put("owner", owner);
                    Card card = new Card(o);
                    manager.getCollection().add(card);
                });
                manager.write(); // Сохраняет коллекцию
            }

            return "Данные загружены на сервер!";
        } catch (Exception e) {
            return e.getMessage();
        }

    });

    /**
     * Загружает данные
     */
    private static Commandable loadCmd = ((jsonElement, manager, id) -> {
        manager.read();
        return "Данные загружены";
    });


    /**
     * Выводит на экран справку по программе
     */
    private static Commandable helpCmd = ((jsonElement, manager, id) -> "remove {element}: удалить элемент из коллекции по его значению\n" +
            "clear: очистить коллекцию\n" +
            "info: вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)\n" +
            "add {element}: добавить новый элемент в коллекцию\n" +
            "show: вывести в стандартный поток вывода все элементы коллекции в строковом представлении\n" +
            "add_if_min {element}: добавить новый элемент в коллекцию, если его значение меньше, чем у наименьшего элемента этой коллекции\n" +
            "save: сохранить коллекцию в файл\n" +
            "help: справка\n" +
            "exit: выход из программы\n" +
            "import: загрузка данныз на сервер\n" +
            "load: загрузка\n" +
            "Пример json элемента: \n" +
            "{\"cardHeight\":50,\"nosesize\":2.5,\"name\":\"Красавчик\"," +
            "\"cardWidth\":2,\"photo\":{\"hair\":\"Red\",\"eyes\":\"Amber\"}," +
            "\"headsize\":30.5,\"status\":\"Jailbird\",\"height\":72.5}");

    /**
     * Создаёт комманду
     *
     * @param userInput Строка с коммандой и данными в формате json
     * @return Пара: ключ - сама команда, значение - данные комманды
     */
    public static Pair<Commandable, String> createComand(String userInput) {
        String jsonRegex = "\\{\"cardHeight\":(\\d+.?\\d),\"nosesize\":(\\d+.?\\d),\"name\":\"(.*?)\",\"cardWidth\"" +
                ":(\\d+.?\\d?),\"photo\":\\{\"hair\":\"(Blond|DarkBrown|Red|Rusyi|Brunette|Grey)\",\"" +
                "eyes\":\"(Blue|Gray|Swamp|Green|Amber|Brown|Yellow|Black)\"},\"headsize\":(\\d+.?\\d?)," +
                "\"status\":\"(ChiefPoliceOfficer|OfficerAssistant|Jailbird|Suspect)\",\"height\":(\\d+.?\\d?)}";
        String dataCommandRegex = "(remove|add_if_min|add) \\{" + jsonRegex + "}";
        String nodataCommandRegex = "show|info|exit|help|clear|save|load";

        if (userInput.split(" ")[0].equals("import")) {
            String[] array = userInput.split(" ", 2);
            if (array.length == 2) {
                String userFile = array[1];
                return new Pair<>(importCmd, userFile);
            } else {
                return new Pair<>(null, "Был отправлен пустой файл");
            }
        }

        if (userInput.split(" ", 2)[0].equals("remove")) {
            List<String> matches = findMatches(jsonRegex, userInput);
            if (matches.size() == 0) return new Pair<>(null, "Элемент для удаления не найден");
            String jsonElement = matches.get(0);
            return new Pair<>(removeCmd, jsonElement);
        }

        if (userInput.matches(dataCommandRegex)) {
            String jsonElement = findMatches(jsonRegex, userInput).get(0);
            String cmd = findMatches("(remove|add_if_min|add)", userInput).get(0);
            switch (cmd) {
                case "remove":
                    return new Pair<>(removeCmd, jsonElement);
                case "add_if_min":
                    return new Pair<>(addIfMinCmd, jsonElement);
                case "add":
                    return new Pair<>(addCmd, jsonElement);
                default:
                    return new Pair<>(null, jsonElement);
            }
        } else if (userInput.matches(nodataCommandRegex)) {
            switch (userInput) {
                case "show":
                    return new Pair<>(showCmd, null);
                case "info":
                    return new Pair<>(infoCmd, null);
                /*case "start": return new Pair<>(startCmd, null);*/
                case "exit":
                    return new Pair<>(exitCmd, null);
                case "help":
                    return new Pair<>(helpCmd, null);
                case "clear":
                    return new Pair<>(clearCmd, null);
                case "save":
                    return new Pair<>(saveCmd, null);
                case "load":
                    return new Pair<>(loadCmd, null);
                default:
                    return new Pair<>(null, null);
            }
        } else {
            return new Pair<>(null, null);
        }

    }

    /**
     * Ищет совпадения в строке
     *
     * @param patterStr Регулярное выражения для поиска
     * @param text      Строка, в которой нужно найти
     * @return Список совпадений
     */
    private static ArrayList<String> findMatches(String patterStr, String text) {
        Pattern pattern = Pattern.compile(patterStr);
        Matcher matcher = pattern.matcher(text);
        ArrayList<String> collection = new ArrayList<>();
        while (matcher.find()) {
            collection.add(text.substring(matcher.start(), matcher.end()));
        }
        return collection;
    }
}
