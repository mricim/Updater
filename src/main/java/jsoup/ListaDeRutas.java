package main.java.jsoup;

public class ListaDeRutas {
    private final String href;
    private final String md5;
    private final String path;
    private final String file;

    public ListaDeRutas(String href, String md5, String path, String file) {
        this.href=href;
        this.md5=md5;
        this.path = path;
        this.file=file;
    }

    public String getHref() {
        return href;
    }

    public String getMd5() {
        return md5;
    }

    public String getPath() {
        return path;
    }

    public String getFile() {
        return file;
    }
}
