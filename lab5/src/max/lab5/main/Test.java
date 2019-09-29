package max.lab5.main;

import max.lab5.humans.Card;
import org.json.JSONObject;

import java.time.ZonedDateTime;
import java.util.Scanner;

public class Test {

    public static void main(String[] args) {

        String regex = "";


    }

    static String regex = "\\{\"cardHeight\":(\\d+.?\\d),\"date\":\"(Mon|Tue|Wed|Thu|Fri|Sat|Sun)\\s(Jan|Feb|Mar|Apr|May|June|July|Aug|Sept|Oct|Nov|Dec)\\s[0-3]\\d\\s[0-2]\\d:[0-5]\\d:[0-5]\\d\\s[A-Za-z][A-Za-z0-9~/._+-]+\",\"nosesize\":(\\d+.?\\d),\"name\":\"(.*?)\",\"cardWidth\":(\\d+.?\\d?),\"photo\":\\{\"hair\":\"(Blond|DarkBrown|Red|Rusyi|Brunette|Grey)\",\"eyes\":\"(Blue|Gray|Swamp|Green|Amber|Brown|Yellow|Black)\"},\"headsize\":(\\d+.?\\d?),\"status\":\"(ChiefPoliceOfficer|OfficerAssistant|Jailbird|Suspect)\",\"height\":(\\d+.?\\d?)}";
    //"{"cardHeight":50,"date":"2019-09-25T15:55:37.461+03:00[Europe/Moscow]","nosesize":2.5,"name":"Красавчик","cardWidth":2,"photo":{"hair":"Red","eyes":"Amber"},"headsize":30.5,"status":"Jailbird","height":72.5}

    static String string = "{\"cardHeight\":50,\"date\":\"2019-09-25T16:01:26.216+03:00[Europe/Moscow]\",\"nosesize\":2.5,\"name\":\"Красавчик\",\"cardWidth\":2,\"photo\":{\"hair\":\"Red\",\"eyes\":\"Amber\"},\"headsize\":30.5,\"status\":\"Jailbird\",\"height\":72.5}";


    public static void input() {
        String regex = "\\{\"cardHeight\":(\\d+.?\\d),\"date\":\"(Mon|Tue|Wed|Thu|Fri|Sat|Sun)\\s(Jan|Feb|Mar|Apr|May|June|July|Aug|Sept|Oct|Nov|Dec)\\s[0-3]\\d\\s[0-2]\\d:[0-5]\\d:[0-5]\\d\\s[A-Za-z][A-Za-z0-9~/._+-]+\",\"nosesize\":(\\d+.?\\d),\"name\":\"(.*?)\",\"cardWidth\":(\\d+.?\\d?),\"photo\":\\{\"hair\":\"(Blond|DarkBrown|Red|Rusyi|Brunette|Grey)\",\"eyes\":\"(Blue|Gray|Swamp|Green|Amber|Brown|Yellow|Black)\"},\"headsize\":(\\d+.?\\d?),\"status\":\"(ChiefPoliceOfficer|OfficerAssistant|Jailbird|Suspect)\",\"height\":(\\d+.?\\d?)}";
        String input = "\"{\\\"cardHeight\\\":50,\\\"date\\\":\\\"2019-09-25T16:01:26.216+03:00[Europe/Moscow]\\\",\\\"nosesize\\\":2.5,\\\"name\\\":\\\"Красавчик\\\",\\\"cardWidth\\\":2,\\\"photo\\\":{\\\"hair\\\":\\\"Red\\\",\\\"eyes\\\":\\\"Amber\\\"},\\\"headsize\\\":30.5,\\\"status\\\":\\\"Jailbird\\\",\\\"height\\\":72.5}\");";


        if (input.matches(regex)) {
            System.out.println("good");
        } else {
            System.out.println("bad");
        }


    }
}
