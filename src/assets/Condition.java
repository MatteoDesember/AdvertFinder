package assets;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import java.io.*;
import java.util.Arrays;
import java.util.List;

public class Condition {

    private static String FILENAME = "data/Conditions.json";
    public static List<Condition> conditionList = null;
    private Boolean send_mail = true;
    private String mail = null;
    private String name = "Condition";
    private Double priceFilter = 335000.0;
    private Double areaFilter = 35.0;
    private String locationNoFilter = "BIAŁOŁĘKA, TARGÓWEK, PRAGA, URSUS, REMBERTÓW, TARCHOMIN, WOLA, BEMOWO, WŁOCHY, BRÓDNO, VERDIEGO, WILANÓW";
    private String locationYesFilter = null;

    public static void readFromFile() {
        try {
            conditionList = new Gson().fromJson(new JsonReader(new FileReader(FILENAME)), new TypeToken<List<Condition>>() {
            }.getType());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void saveToFile() {
        MyFile.write(FILENAME, new GsonBuilder().setPrettyPrinting().create().toJson(conditionList));
    }

    public static boolean stringInStrings(String firstString, String secondString) {
        firstString = firstString.toUpperCase();
        secondString = secondString.toUpperCase();
        return Arrays.stream(firstString.split(" ")).parallel().anyMatch(secondString::contains);
    }

    public Double getPriceFilter() {
        return priceFilter;
    }

    public Double getAreaFilter() {
        return areaFilter;
    }

    public String getLocationNoFilter() {
        return locationNoFilter;
    }

    public String getLocationYesFilter() {
        return locationYesFilter;
    }
}
