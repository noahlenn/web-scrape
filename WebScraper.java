import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class WebScraper {

    public static void main(String[] args) {

        try {
            String url = "https://www.vg.no/";
            Document document = Jsoup.connect(url).get();
            
            // Hent overskrifter fra siden
            //Elements headers = document.select("h1, h2, h3, h4, h5, h6");
            Elements headers = document.select("h2");
            
            // Print ut all informasjon med headers
            for (Element header : headers) {
                System.out.println(header.text());
            }
        } catch (Exception e) {
            System.out.println(e);    
        }
   }
   
}