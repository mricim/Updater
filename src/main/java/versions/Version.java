package main.java.versions;

import main.java.Main;
import main.java.jsoup.ListWeb;
import main.java.jsoup.ListaDeRutas;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

public class Version {
    private String url;
    private String versionOld;
    private String versionNew;
    private boolean installer;
    private String md5;
    private String path;
    private String fileName;

    public Version(String url, String versionOld, String versionNew, boolean installer, String md5, String path, String fileName) {
        this.url = url;
        this.versionOld = versionOld;
        this.versionNew = versionNew;
        this.installer = installer;
        this.md5 = md5;
        this.path = path;
        this.fileName = fileName;
    }

    public static Version chekUpdateMajor(Document doc) throws IOException {
        LinkedHashMap<String, ListaDeRutas> listaDowloadsApp = ListWeb.getListVersionsWeb(doc, "div[id=aplication] div[id=installer]");
        LinkedHashMap<String, ListaDeRutas> dowloadPrincipal = null;
        String rute = null;
        for (ListaDeRutas value : listaDowloadsApp.values()) {//donde esta la lista?
            rute = Main.hostDowloads + value.getHref();//dowloads/list.html
            dowloadPrincipal = ListWeb.getListVersionsWeb(rute, value.getFile(), null);
            break;
        }

        String rute2 = null;
        for (Map.Entry<String, ListaDeRutas> entry : dowloadPrincipal.entrySet()) {//donde esta la lista?
            if (entry.getKey().equals(Main.OS)) {
                ListaDeRutas value = entry.getValue();
                rute2 = rute + value.getHref();//operative system/
                dowloadPrincipal = ListWeb.getListVersionsWeb(rute2, value.getFile(), null);
                break;
            }
        }
        //TODO SACAR EXCEPCION

        //Buscamos versiones
        for (Map.Entry<String, ListaDeRutas> stringListaDeRutasEntry : dowloadPrincipal.entrySet()) {
            String versionNew = stringListaDeRutasEntry.getKey();
            String[] versionNewSplit = versionNew.split("\\.");
            System.out.println(versionNew);
            if (Integer.parseInt(versionNewSplit[3]) > 3) {//COMPROBAR QUE la version es release x.x.x.? > 3
                System.out.println(versionNewSplit[0] + "." + versionNewSplit[1] + ">" + Main.versionOldSplit[0] + "." + Main.versionOldSplit[1]);
                System.out.println(versionNew + ">" + Main.versionOld);
                if (versionNewSplit[0].compareTo(Main.versionOldSplit[0]) >= 0 && versionNewSplit[1].compareTo(Main.versionOldSplit[1]) > 0) {// ?.x.x.x >= actual &&  x.?.x.x > actual
                    System.out.println("ENTRO 1 INSTALLER");
                    ListaDeRutas objeto = stringListaDeRutasEntry.getValue();
                    return new Version(rute2 + objeto.getHref(), Main.versionOld, versionNew,true,objeto.getMd5(), objeto.getPath(),objeto.getFile());
                } else if (versionNewSplit[0].compareTo(Main.versionOldSplit[0]) <= 0 && versionNewSplit[1].compareTo(Main.versionOldSplit[1]) < 0) {
                    break;
                }
            }
        }
        System.out.println("NO INSTALLER");
        return null;
    }

    public static Version chekUpdateMinor(Document doc) {
        LinkedHashMap<String, ListaDeRutas> listaDowloadsJar = ListWeb.getListVersionsWeb(doc, "div[id=aplication] div[id=jar]");
        LinkedHashMap<String, ListaDeRutas> dowloadPrincipal = null;

        String rute = null;
        for (ListaDeRutas value : listaDowloadsJar.values()) {//obtenemos de donde tenemos que mirar
            rute = Main.hostDowloads + value.getHref();//DE dowloads/list.html
            dowloadPrincipal = ListWeb.getListVersionsWeb(rute, value.getFile(), null);
            break;
        }
        //Buscamos versiones
        for (Map.Entry<String, ListaDeRutas> stringListaDeRutasEntry : dowloadPrincipal.entrySet()) {
            String versionNew = stringListaDeRutasEntry.getKey();
            String[] versionNewSplit = versionNew.split("\\.");
            if (Integer.parseInt(versionNewSplit[3]) > 3) {//COMPROBAR QUE la version es release x.x.x.? > 3
                System.out.println(versionNewSplit[0] + "." + versionNewSplit[1] + "." + versionNewSplit[2] + ">" + Main.versionOldSplit[0] + "." + Main.versionOldSplit[1] + "." + Main.versionOldSplit[2]);
                System.out.println(versionNew + ">" + Main.versionOld);
                if (versionNewSplit[0].compareTo(Main.versionOldSplit[0]) >= 0 && versionNewSplit[1].compareTo(Main.versionOldSplit[1]) >= 0 && versionNewSplit[2].compareTo(Main.versionOldSplit[2]) > 0) {// ?.x.x.x > actual &&  x.?.x.x >= actual &&  x.x.?.x > actual
                    System.out.println("ENTRO 1 JAR");
                    ListaDeRutas objeto = stringListaDeRutasEntry.getValue();
                    return new Version(rute + objeto.getHref(), Main.versionOld, versionNew,true,objeto.getMd5(), objeto.getPath(),objeto.getFile());
                } else if (versionNewSplit[0].compareTo(Main.versionOldSplit[0]) <= 0 && versionNewSplit[1].compareTo(Main.versionOldSplit[1]) <= 0 && versionNewSplit[2].compareTo(Main.versionOldSplit[2]) < 0) {
                    break;
                }
            }
        }
        System.out.println("NO JAR");
        return null;
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
        return "Version{" +
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
