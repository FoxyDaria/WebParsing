import org.jsoup.nodes.Document;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws Exception {
        WebParsing webParsing = new WebParsing();
        Document document = webParsing.getHtml("https://skillbox-java.github.io");
        webParsing.addLines(document);
        webParsing.addStationsToLines(document);
        List <Line> lines = webParsing.getMetroLines();  // получает список линий с сайта
        List<Station> stations = webParsing.getMetroStations(); // получает список станций с сайта

        FileSearcher fileSearcher = new FileSearcher();
        ArrayList<File> fileList = new ArrayList<File>();
        fileSearcher.searchFiles(new File("data"), fileList);  //ищет файлы в папке и добавляет их в список

        FileParsing jsonParsing = new FileParsing();
        jsonParsing.parseFiles(fileList,stations); //к каждой станции с сайта добавляет глубину и дату

       JsonCreator jsonCreator = new JsonCreator();
       jsonCreator.createFirstJson("1st_JSON.json",lines,stations);
       jsonCreator.createSecondJson("stations.json",stations,lines);
    }
}
