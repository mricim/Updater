package main.java.test.Old;

public class LinksDeLaWebParaDownloadFile {
    private final String href;
    private final String md5;
    public LinksDeLaWebParaDownloadFile(String href, String md5) {
        this.href=href;
        this.md5=md5;
    }

    public String getHref() {
        return href;
    }

    public String getMd5() {
        return md5;
    }
}
