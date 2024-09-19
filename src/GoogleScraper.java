import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class GoogleScraper {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter a word or phrase to search:");
        String query = scanner.nextLine();

        String searchUrl = "https://www.google.com/search?q=" + query.replace(" ", "+");

        try {
            Document doc = Jsoup.connect(searchUrl).userAgent("Mozilla/5.0").get();

            Elements links = doc.select("a[href]");

            FileWriter writer = new FileWriter("scrapeout.txt", true);

            writer.write("\nSearch Results for: " + query + "\n");

            for (Element link : links) {
                String url = link.attr("href");

                if (url.startsWith("/url?q=")) {
                    writer.write(url.substring(7) + "\n");  // Remove "/url?q=" from the URL
                }
            }

            writer.close();

            System.out.println("Results added to scrapeout.txt");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
