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

            Elements scriptElements = document.getElementsByTag("script");

            // Opprett Jackson mapper for JSON parsing
            ObjectMapper objectMapper = new ObjectMapper();

            // Går gjennom elementer med tagname: "script"
            for (Element element : scriptElements) {
                for (DataNode node : element.dataNodes()) {
                    String jsonContent = node.getWholeData();

                    // Prøv å parse JSON data
                    try {
                        JsonNode jsonNode = objectMapper.readTree(jsonContent);

                        if (jsonNode.has("articleId")) {
                            JsonNode changesNode = jsonNode.get("changes");
                            String timestamp = changesNode.get("firstPublished").asText();

                            // Ser om jeg får ut tidspunktene
                            System.out.println("Publiseringstidspunkt: " + timestamp);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
