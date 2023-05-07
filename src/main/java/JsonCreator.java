import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class JsonCreator {

    public void createFirstJson(String filename, List<Line> linesFromWeb, List<Station> stationsFromWeb) throws Exception {
        JSONObject jsonObject = new JSONObject();
        JSONObject lines = new JSONObject();
        Map<String, List<Station>> st = stationsFromWeb.stream().collect(Collectors.groupingBy(Station::getLine));
        for (Map.Entry<String, List<Station>> entry : st.entrySet()) {
            JSONArray stationsOfLines = new JSONArray();
            entry.getValue().forEach(s -> stationsOfLines.add(s.getName()));
            lines.put(entry.getKey(), stationsOfLines);
        }
        jsonObject.put("stations", lines);

        JSONArray line3 = new JSONArray();
        for (Line l : linesFromWeb) {
            JSONObject lines2 = new JSONObject();
            lines2.put("number", l.getNumber());
            lines2.put("name", l.getName());
            line3.add(lines2);
        }
        jsonObject.put("lines", line3);
        Files.write(Paths.get(filename), jsonObject.toJSONString().getBytes());
    }

    public void createSecondJson (String filename,List<Station> stations,List<Line> linesFromWeb) throws IOException {
        JSONObject jsonObject = new JSONObject();
        JSONArray stArr = new JSONArray();
        for (Station s : stations){
            JSONObject st = new JSONObject();
            st.put("name",s.getName());
            st.put("line",linesFromWeb.stream().filter(l->l.getNumber().equals(s.getLine()))
                    .findAny().orElse(null).getName());
           if(!s.getDate().isEmpty()){ st.put("date",s.getDate());}
           if(!s.getDepth().isEmpty()) {st.put("depth",Double.parseDouble(s.getDepth()));}
            st.put("hasConnection",s.getHasConnection());
            stArr.add(st);
        }
        jsonObject.put("stations", stArr);
        Files.write(Paths.get(filename), jsonObject.toJSONString().getBytes());
    }
}
