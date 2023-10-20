import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class JsoupTester {

    public static void main(String[] args) {

        try {
            String url = "https://www.vg.no/";
            Document document = Jsoup.connect(url).get();

            // Prøver å finne publiseringstidspunkt for artikler
            //Elements timestamps = document.select("data-published");
            //Elements timestamps = document.select("data-published-iso");
            //Elements timestamps = document.select("firstPublished");

            if (timestamps.size() > 0) {
                int i = 1;
                for (Element timestamp : timestamps) {
                    System.out.println("Artikkel " + i + ": Publiseringstidspunkt " + timestamp.text());
                    i++;
                }
            } else {
                System.out.println("Ingen publiseringstidspunkt funnet");
            }
            
        } catch (Exception e) {
            System.out.println(e);    
        }
   }
   
}