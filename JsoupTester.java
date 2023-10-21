import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.DataNode;
import org.jsoup.select.Elements;

public class JsoupTester {

    public static void main(String[] args) {
        try {
            String url = "https://www.vg.no/";
            Document document = Jsoup.connect(url).get();

            // Finner elementer med tagname: "script"
            Elements scriptElements = document.getElementsByTag("script");

            // Opprett Jackson mapper for JSON parsing
            ObjectMapper objectMapper = new ObjectMapper();

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

                                    // Ser om jeg får ut tidspunktene
                                    System.out.println("Publiseringstidspunkt: " + timestamp);
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
