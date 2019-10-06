package max.lab7.server.collection.filecollection;


import max.lab5.humans.Card;
import max.lab7.server.collection.Reader;
import org.json.CDL;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Scanner;

/**
 * Класс читающий данные из файла
 */
public class CollectionReader implements Reader {
    /**
     * Путь к файлу
     */
    private String filePath;

    /**
     * Существует ли файл?
     */
    private boolean isFileExists;

    /**
     * Коллекция, в которую читаются данные из файла
     */
    private HashSet<Card> collection;

    /**
     * Конструктор
     * @param filePath Путь к файлу
     */
    public CollectionReader(String filePath){
        this.filePath = filePath;
        File file = new File(filePath);
        isFileExists = file.exists();
        collection = new HashSet<>();
        if (isFileExists){

        }
        else{
            System.err.println("Ошибка: файл не существует!");
        }
    }

    /**
     * Читает данные из файла в коллекцию
     * @return true, если данные прочитались успешно, иначе false
     */
    public boolean read(){
        if(isFileExists){
            Scanner reader = null;
            try {
                reader = new Scanner(new File(filePath));
            } catch (FileNotFoundException e) {
                System.err.println(e.getMessage());
            }
            StringBuilder builder = new StringBuilder();
            while(reader.hasNextLine()){
                builder.append(reader.nextLine()).append("\n");
            }
            String str = builder.toString();
            if(str.length() != 0) {
                JSONArray array = CDL.toJSONArray(str);
                array.forEach(p -> collection.add(new Card((JSONObject) p)));
            }
            else{
                System.out.println("Файл с коллекцией пуст!");
            }

            return true;
        }
        else{
            return false;
        }

    }

    /**
     * Геттер коллекции
     * @return Коллекция с данными
     */
    public HashSet<Card> getCollection() {
        return collection;
    }

}
