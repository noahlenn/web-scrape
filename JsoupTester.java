import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.DataNode;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;
import java.util.Comparator;


public class JsoupTester {

    public static void main(String[] args) {
        try {
            String url = "https://www.vg.no/";
            Document document = Jsoup.connect(url).get();

            // Finner elementer med tagname: "script"
            Elements scriptElements = document.getElementsByTag("script");

            // Opprett Jackson mapper for JSON parsing
            ObjectMapper objectMapper = new ObjectMapper();

            // Opprett tom liste for å legge inn artiklene senere
            List<Article> articles = new ArrayList<>();

            // Går gjennom elementer med tagname: "script"
            // og trekker ut JSON-data
            for (Element element : scriptElements) {
                for (DataNode node : element.dataNodes()) {

                    String jsonContent = node.getWholeData();

                    // Sjekk om innholdet ser ut som JSON-data
                    if (jsonContent.startsWith("{") || jsonContent.startsWith("[")) {
                        try {
                            // Forsøker å parse JSON-data fra jsonContent ved hjelp av objectMapper
                            JsonNode jsonNode = objectMapper.readTree(jsonContent);

                            // Sjekk om JSON-data inneholder "articleId"
                            if (jsonNode.has("articleId")) {
                                JsonNode changesNode = jsonNode.get("changes");
                                if (changesNode != null && changesNode.has("firstPublished")) {
                                    // Tid i ISO 8601
                                    String timestamp = changesNode.get("firstPublished").asText();

                                    // Sjekk om JSON-data inneholder "teaserText"
                                    // Her kan vi finne tittelen
                                    if (jsonNode.has("teaserText")) {
                                        String title = jsonNode.get("teaserText").asText();
                                        //title = title.replace("\n", "");

                                        // legg artiklene i en lista
                                        articles.add(new Article(timestamp, title));
                                    }
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }


            // Sorter artikler fra eldst til nyest
            articles.sort(Comparator.comparing(Article::getTimestamp));

            /*
            // psuedokode nye artikler etter første henting av artikler
            while(true){
                try {
                    //finn nye artikler
                    articles.add(new Article(timestamp, title));
            //sleep 15sek
                }
            }
            */

            // Print sortert liste
            for (Article article : articles) {
                System.out.println(article.getTimestamp());
                System.out.println(article.getTitle());
                System.out.println("");
            }

            

            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


class Article {
    private String timestamp;
    private String title;

    public Article(String timestamp, String title) {
        this.timestamp = timestamp;
        this.title = title;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public String getTitle() {
        return title;
    }
}
