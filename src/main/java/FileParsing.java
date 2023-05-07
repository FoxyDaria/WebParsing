import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class FileParsing {

    private static String getJsonFile(File file) {
        StringBuilder builder = new StringBuilder();
        try {
            List<String> lines = Files.readAllLines(Paths.get(file.getAbsolutePath()));
            lines.forEach(builder::append);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return builder.toString();
    }

    public static void parseFiles(List<File> fileList, List<Station> stationsFromWeb) throws ParseException, IOException {
        for (File file : fileList) {
            if (file.getName().toLowerCase().endsWith(".json")) {
                JSONParser parser = new JSONParser();
                JSONArray jsonData = (JSONArray) parser.parse(getJsonFile(file));
                for (Object it : jsonData) {
                    JSONObject object = (JSONObject) it;
                    String depth = (String) object.get("depth");
                    String finalDepth = depth.replace("–", "-")
                            .replace(",",".")
                            .replace("?","");
                    String station_name = (String) object.get("station_name");
                    stationsFromWeb.stream()
                            .filter(s -> s.getName().equalsIgnoreCase(station_name) && s.getDepth().isEmpty())
                            .findFirst()
                            .ifPresent(s -> s.setDepth(finalDepth));
                }
            }
            if (file.getName().toLowerCase().endsWith(".csv")) {
                List<String> lines = Files.readAllLines(Paths.get(file.getAbsolutePath()));
                for (String line : lines) {
                    String[] fragments = line.split(",");
                    stationsFromWeb.stream().filter(s -> s.getName().equalsIgnoreCase(fragments[0])&& s.getDate().isEmpty())
                            .findFirst()
                            .ifPresent(s -> s.setDate(fragments[1]));
                }
            }
        }
    }
}