package main.java;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.TreeMap;

public class ListWeb {
    static TreeMap<String, LinksDeLaWebParaDownloadFile> getListVersionsWeb() {
        try {
            TreeMap<String, LinksDeLaWebParaDownloadFile> lista = new TreeMap<>();
//https://jsoup.org/cookbook/extracting-data/example-list-links
            String urlList = Main.url + Main.fileList;
            System.out.println(Main.url);
            Document docs = Jsoup.connect(urlList).get();
            Elements links = docs.select("a[href]");
            for (Element link : links) {
                lista.put(link.text(), new LinksDeLaWebParaDownloadFile(link.attr("href"), link.attr("md5")));
            }
        /*
        for (Element link : docs.select("[src]")) {
            System.out.println("[src]");
        }
        for (Element link : docs.select("link[href]")) {
            System.out.println("link[href]");
        }
         */
            return lista;
        } catch (IOException e) {
            return null;
        }
        /*
        //Validate.isTrue(args.length == 1, "usage: supply url to fetch"); // POR SI LOS ARGS DEL MAIN NO ESTAN BIEN
        String url = "http://news.ycombinator.com";
        print("Fetching %s...", url);

        Document doc = Jsoup.connect(url).get();

        Elements links = doc.select("a[href]");
        Elements media = doc.select("[src]");
        Elements imports = doc.select("link[href]");

        print("\nMedia: (%d)", media.size());
        for (Element src : media) {
            if (src.normalName().equals("img"))
                print(" * %s: <%s> %sx%s (%s)",
                        src.tagName(), src.attr("abs:src"), src.attr("width"), src.attr("height"),
                        trim(src.attr("alt"), 20));
            else
                print(" * %s: <%s>", src.tagName(), src.attr("abs:src"));
        }

        print("\nImports: (%d)", imports.size());
        for (Element link : imports) {
            print(" * %s <%s> (%s)", link.tagName(),link.attr("abs:href"), link.attr("rel"));
        }

        print("\nLinks: (%d)", links.size());
        for (Element link : links) {
            print(" * a: <%s>  (%s)", link.attr("abs:href"), trim(link.text(), 35));
        }
*/
    }
}
