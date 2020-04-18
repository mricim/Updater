package main.java.test.jsoup;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.LinkedHashMap;

public class ListWeb {
    //https://jsoup.org/cookbook/extracting-data/example-list-links
    public static Document parseFile(String url, String filename) throws IOException {
        String urlList = url + filename;
        System.out.println(urlList);
        return Jsoup.connect(urlList).get();
    }
    public static LinkedHashMap<String, Rutas> getListVersionsWeb(Document docs, String tags) {
        LinkedHashMap<String, Rutas> lista = new LinkedHashMap<>();
        Elements links;
        if (tags == null) {
            links = docs.select("a[href]");
        } else {
            links = docs.select(tags + " a[href]");
        }
        for (Element link : links) {
            //lista.put(link.text(), new ListaDeRutas(link.attr("href"), link.attr("md5"), link.attr("path"), link.attr("file")));
        }
        return lista;
    }

    public static LinkedHashMap<String, Rutas> getListVersionsWeb(String url, String fileList, String tags) {
        try {
            LinkedHashMap<String, Rutas> lista = new LinkedHashMap<>();
//https://jsoup.org/cookbook/extracting-data/example-list-links
            String urlList = url + fileList;
            System.out.println(url + " " + fileList);
            Document docs = Jsoup.connect(urlList).get();
            Elements links;
            if (tags == null) {
                links = docs.select("a[href]");
            } else {
                links = docs.select(tags + " a[href]");
            }
            for (Element link : links) {
                //lista.put(link.text(), new ListaDeRutas(link.attr("href"), link.attr("md5"), link.attr("path"), link.attr("file")));
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