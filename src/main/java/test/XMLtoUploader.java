package main.java.test;

public class XMLtoUploader {
    private String url;
    private String versionOld;
    private String versionNew;
    private boolean installer;
    private String md5;
    private String path;
    private String fileName;

    public XMLtoUploader(String url, String versionOld, String versionNew, boolean installer, String md5, String path, String fileName) {
        this.url = url;
        this.versionOld = versionOld;
        this.versionNew = versionNew;
        this.installer = installer;
        this.md5 = md5;
        this.path = path;
        this.fileName = fileName;
    }

    public String getUrl() {
        return url;
    }

    public String getVersionOld() {
        return versionOld;
    }

    public String getVersionNew() {
        return versionNew;
    }

    public boolean isInstaller() {
        return installer;
    }

    public String getMd5() {
        return md5;
    }

    public String getPath() {
        return path;
    }

    public String getFileName() {
        return fileName;
    }

    @Override
    public String toString() {
        return "XMLtoUploader{" +
                "url='" + url + '\'' +
                ", versionOld='" + versionOld + '\'' +
                ", versionNew='" + versionNew + '\'' +
                ", installer=" + installer +
                ", md5='" + md5 + '\'' +
                ", path='" + path + '\'' +
                ", fileName='" + fileName + '\'' +
                '}';
    }
}
