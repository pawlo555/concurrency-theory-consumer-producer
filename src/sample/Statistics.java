package sample;

import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import java.util.TreeMap;

public class Statistics {
    private static final Map<Integer, Integer> consumersMap = new TreeMap<>();
    private static final Map<Integer, Integer> producersMap = new TreeMap<>();

    public static void main(String[] args) throws IOException {
        String file = getDataFromFile("data.txt");
        String[] actions = file.split(" ");
        for (String action: actions) {
            updateMaps(action);
        }
        showMap(consumersMap);
        showMap(producersMap);
    }

    public static String getDataFromFile(String filename) throws IOException {
        FileReader reader = new FileReader(filename);
        StringBuilder builder = new StringBuilder();
        int nextC = reader.read();

        while ( nextC != -1) {
            char c = (char) nextC;
            builder.append(c);
            nextC = reader.read();
        }
        return builder.toString();
    }

    private static void updateMaps(String action) {
        if (action.charAt(0) == 'C') {
            updateMap(action.substring(2), consumersMap);
        }
        else {
            updateMap(action.substring(2), producersMap);
        }
    }

    private static void updateMap(String name, Map<Integer, Integer> map) {
        Integer intName = Integer.valueOf(name);
        int previousMapValue = map.getOrDefault(intName, 0);
        map.put(intName, previousMapValue+1);
    }

    private static void showMap(Map<Integer, Integer> map) {
        System.out.println(Arrays.toString(map.values().stream().sorted().toArray()));
    }
}
