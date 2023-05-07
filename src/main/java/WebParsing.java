import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class WebParsing {
    private static List<Line> metroLines;
    private static List<Station> metroStations;
    public static List<Line> getMetroLines() {
        return metroLines;
    }
    public static List<Station> getMetroStations() {
        return metroStations;
    }


    public static void addLines(Document document) {
        metroLines = new ArrayList<>();
        Elements lines = getLines(document);
        lines.forEach(l -> metroLines.add(new Line((l.attr("data-line")), l.text())));
    }
    public static Document getHtml(String path) throws IOException {
        return Jsoup.connect(path).get();
    }
    public static Elements getLines(Document doc) {
        return doc.select("span.js-metro-line");
    }
    public static Elements getStations(Document doc) {
        return doc.select("div.js-metro-stations");
    }
    public static void addStationsToLines(Document document){
        metroStations = new ArrayList<>();
        Elements stat = getStations(document);
        stat.forEach(a->a.select("p.single-station")
                .forEach(s->metroStations.add(new Station(s.select("span.name").text(),a.attr("data-line"),
                        s.select("span.t-icon-metroln").attr("title")))));

        metroLines.forEach(l->metroStations.stream().filter(s->l.getNumber().equals(s.getLine())).forEach(s->l.getStations().add(s)));
        }
   }

