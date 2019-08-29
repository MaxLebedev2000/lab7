package max.lab6.server.commands;

import Humans.Card;
import MainPackage.Main;
import MainPackage.Pair;
import max.lab6.server.execution.Server;
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

public class ComandFactory {
    /**
     * Удаляет элемент из коллекции
     */
    private static Comandable removeCmd = (jsonElement, manager, id) -> {
        manager.updateCollection();
        Card card = new Card(new JSONObject(jsonElement));
        manager.getCollection().remove(card);
        manager.write(); // Сохраняет коллекцию
        return "Элемент удалён!";
    };
    /**
     * Очищает коллекцию
     */
    private static Comandable clearCmd = ((jsonElement, manager, id) -> {
        manager.updateCollection();
        manager.getCollection().clear();
        manager.write(); // Сохраняет коллекцию
        return "Коллекция очищена!";
    });
    /**
     * Выводит на экран иныормацию о коллекции
     */
    private static Comandable infoCmd = ((jsonElement, manager, id) -> {
        manager.updateCollection();
        return "Тип коллекции: " + manager.getCollection().getClass().getSimpleName() + "\n" +
                "Дата инициализации коллекции: " + new Date().toString() + "\n" +
                "Тип элементов коллекции: " + "Humans.Card" + "\n" +
                "Количество элементов коллекции: " + manager.getCollection().size() + "\n" + "Готово!" + "\n";
    });
    /**
     * Добавляет элемент в коллекцию
     */
    private static Comandable addCmd = (jsonElement, manager, id) -> {
        manager.updateCollection();
        Card card = new Card(new JSONObject(jsonElement));
        manager.getCollection().add(card);
        manager.write(); // Сохраняет коллекцию
        return "Элемент добавлен!";
    };
    /**
     * Добавляет элемент в коллекцию, если он минимальный
     */
    private static Comandable addIfMinCmd = (jsonElement, manager, id) -> {
        manager.updateCollection();
        Card card = new Card(new JSONObject(jsonElement));
        if (Collections.min(manager.getCollection()).compareTo(card) > 0) {
            manager.getCollection().add(card);
            manager.write(); // Сохраняет коллекцию
        }
        return "Элемент добавлен,тк он минимальный!";

    };
    /**
     * Выводит на экран элементы коллекции
     */
    private static Comandable showCmd = ((jsonElement, manager, id) -> {
        manager.updateCollection();
        if (manager.getCollection().size() == 0) return "Коллекция пуста";
        return manager.getCollection().stream().map(Card::toString).collect(Collectors.joining("\n+++++++++++++++++++++++++++++++++++++++++++++\n"));
    });
    /**
     * Сохраняет коллекцию в файл
     */
    private static Comandable saveCmd = ((jsonElement, manager, id) -> {
        manager.updateCollection();
        manager.write();
        return "Коллекция сохранена";
    });
    /**
     * Завершает работу программы
     */
    private static Comandable exitCmd = ((jsonElement, manager, id) -> {
        Server.getConnections().remove(id).cancel();
        return "Завершение работы программы!";
    });

    /**
     * Переносит данные нв сервер
     */
    private static Comandable importCmd = ((jsonElement, manager, id) -> {
        System.out.println(manager.getCollection().size());
        manager.updateCollection();
        System.out.println(manager.getCollection().size());
        try {
            if (jsonElement.length() != 0) {
                JSONArray array = CDL.toJSONArray(jsonElement);
                array.forEach(p -> {
                    Card card = new Card((JSONObject) p);
                    manager.getCollection().add(card);
                });
                System.out.println(manager.getCollection().size());
                manager.write(); // Сохраняет коллекцию
                System.out.println(manager.getCollection().size());
            }

            return "Данные загружены на сервер!";
        } catch (Exception e) {
            return e.getMessage();

        }

    });

    /**
     * Загружает данные
     */
    private static Comandable loadCmd = ((jsonElement, manager, id) -> {
        manager.updateCollection();
        return "Данные загружены";
    });


    /**
     * Выводит на экран справку по программе
     */
    private static Comandable helpCmd = ((jsonElement, manager, id) -> {
        System.out.println("remove {element}: удалить элемент из коллекции по его значению\n" +
                "clear: очистить коллекцию\n" +
                "info: вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)\n" +
                "add {element}: добавить новый элемент в коллекцию\n" +
                "show: вывести в стандартный поток вывода все элементы коллекции в строковом представлении\n" +
                "add_if_min {element}: добавить новый элемент в коллекцию, если его значение меньше, чем у наименьшего элемента этой коллекции\n" +
                "save: сохранить коллекцию в файл\n" +
                "help: справка\n" +
                "start: запуск подпрограммы\n" +
                "exit: выход из программы\n" +
                "Пример json элемента: \n" +
                "{\"cardHeight\":50,\"date\":\"Fri Aug 16 01:33:12 MSK 2019\",\"nosesize\":2.5,\"name\":\"Красавчик\",\"cardWidth\":2,\"photo\":{\"hair\":\"Red\",\"eyes\":\"Amber\"},\"headsize\":30.5,\"status\":\"Jailbird\",\"height\":72.5}");
        return "remove {element}: удалить элемент из коллекции по его значению\n" +
                "clear: очистить коллекцию\n" +
                "info: вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)\n" +
                "add {element}: добавить новый элемент в коллекцию\n" +
                "show: вывести в стандартный поток вывода все элементы коллекции в строковом представлении\n" +
                "add_if_min {element}: добавить новый элемент в коллекцию, если его значение меньше, чем у наименьшего элемента этой коллекции\n" +
                "save: сохранить коллекцию в файл\n" +
                "help: справка\n" +
                "start: запуск подпрограммы\n" +
                "exit: выход из программы\n" +
                "import: загрузка данныз на сервер\n" +
                "load: загрузка\n" +
                "Пример json элемента: \n" +
                "{\"cardHeight\":50,\"date\":\"Fri Aug 16 01:33:12 MSK 2019\",\"nosesize\":2.5,\"name\":\"Красавчик\",\"cardWidth\":2,\"photo\":{\"hair\":\"Red\",\"eyes\":\"Amber\"},\"headsize\":30.5,\"status\":\"Jailbird\",\"height\":72.5}";
    });
    /**
     * Начинает работу подпрограммы
     */
/*    private static Comandable startCmd = ((jsonElement, manager) -> {
        manager.updateCollection();
        if(manager.getCollection().size() >= 2){
            System.out.println("Готово!");
            Main.start(manager);
            return "Готово!";
        }
        else{
            System.out.println("В коллекции слишком мало объектов для запуска программы!");
            System.out.println("Готово!");
            return "В коллекции слишком мало объектов для запуска программы!"+"\n"+"Готово!";
        }
    });*/

    /**
     * Создаёт комманду
     *
     * @param userInput Строка с коммандой и данными в формате json
     * @return Пара: ключ - сама команда, значение - данные комманды
     */
    public static Pair<Comandable, String> createComand(String userInput) {
        String jsonRegex = "\\{\"cardHeight\":(\\d+.?\\d),\"date\":\"(Mon|Tue|Wed|Thu|Fri|Sat|Sun)\\s(Jan|Feb|Mar|Apr|May|June|July|Aug|Sept|Oct|Nov|Dec)\\s[0-3]\\d\\s[0-2]\\d:[0-5]\\d:[0-5]\\d\\sMSK\\s\\d{4}\",\"nosesize\":(\\d+.?\\d),\"name\":\"(.*?)\",\"cardWidth\":(\\d+.?\\d?),\"photo\":\\{\"hair\":\"(Blond|DarkBrown|Red|Rusyi|Brunette|Grey)\",\"eyes\":\"(Blue|Gray|Swamp|Green|Amber|Brown|Yellow|Black)\"},\"headsize\":(\\d+.?\\d?),\"status\":\"(ChiefPoliceOfficer|OfficerAssistant|Jailbird|Suspect)\",\"height\":(\\d+.?\\d?)}";
        String dataCommandRegex = "(remove|add_if_min|add) \\{" + jsonRegex + "}";
        String nodataCommandRegex = "show|info|start|exit|help|clear|save";

        if (userInput.split(" ")[0].equals("import")) {
            String userFile = userInput.split(" ",2)[1];
            return new Pair<>(importCmd, userFile);
        }

        if (userInput.split(" ", 2)[0].equals("remove")){
            List<String> matches = findMatches(jsonRegex, userInput);
            if (matches.size() == 0) return new Pair<>(null, "lol");
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
