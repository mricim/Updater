package main.java.test;

import main.java.Main;
import main.java.versions.Rutas;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import static main.java.Main.versionNewSplit;
import static main.java.test.Old.ListWeb.getListVersionsWeb;

public class VersionOld {
    private final String url;
    private final String versionOld;
    private final String versionNew;
    private final boolean installer;
    private final String md5;
    private final String path;
    private final String fileName;

    private static final int ceroOld= Integer.parseInt(Main.versionOldSplit[0]);
    private static final int unoOld= Integer.parseInt(Main.versionOldSplit[1]);
    private static final int dosOld= Integer.parseInt(Main.versionOldSplit[2]);
    public VersionOld(String url, String versionOld, String versionNew, boolean installer, String md5, String path, String fileName) {
        this.url = url;
        this.versionOld = versionOld;
        this.versionNew = versionNew;
        versionNewSplit=versionNew.split("\\.");
        this.installer = installer;
        this.md5 = md5;
        this.path = path;
        this.fileName = fileName;
    }

    public static VersionOld chekUpdateMajor(Document doc) throws IOException {
        /*
        LinkedHashMap<String, Rutas> listaDowloadsApp = getListVersionsWeb(doc, "div[id=aplication] div[id=installer]");
        LinkedHashMap<String, Rutas> dowloadPrincipal = null;
        String rute = null;
        for (Rutas value : listaDowloadsApp.values()) {//donde esta la lista?
            rute = Main.hostDowloads + value.getHref();//dowloads/list.html
            dowloadPrincipal = ListWeb.getListVersionsWeb(rute, value.getFile(), null);
            break;
        }

        String rute2 = null;
        for (Map.Entry<String, Rutas> entry : dowloadPrincipal.entrySet()) {//donde esta la lista?
            if (entry.getKey().equals(Main.OS)) {
                Rutas value = entry.getValue();
                rute2 = rute + value.getHref();//operative system/
                dowloadPrincipal = ListWeb.getListVersionsWeb(rute2, value.getFile(), null);
                break;
            }
        }
        //TODO SACAR EXCEPCION

        //Buscamos versiones
        for (Map.Entry<String, Rutas> stringListaDeRutasEntry : dowloadPrincipal.entrySet()) {
            String versionNew = stringListaDeRutasEntry.getKey();
            String[] versionNewSplit = versionNew.split("\\.");
            System.out.println(versionNew);
            if (Integer.parseInt(versionNewSplit[3]) > 3) {//COMPROBAR QUE la version es release x.x.x.? > 3
                System.out.println(versionNewSplit[0] + "." + versionNewSplit[1] + ">" + Main.versionOldSplit[0] + "." + Main.versionOldSplit[1]);
                System.out.println(versionNew + ">" + Main.versionOld);
                int ceroNew= Integer.parseInt(versionNewSplit[0]);
                int unoNew= Integer.parseInt(versionNewSplit[1]);
                if (ceroNew>=ceroOld && unoNew>unoOld) {// ?.x.x.x >= actual &&  x.?.x.x > actual
                    System.out.println("ENTRO 1 INSTALLER");
                    Rutas objeto = stringListaDeRutasEntry.getValue();
                    return new VersionOld(rute2 + objeto.getHref(), Main.versionOld, versionNew,true,objeto.getMd5(), objeto.getPath(),objeto.getFile());
                } else if (ceroNew<=ceroOld && unoNew<unoOld) {
                    break;
                }
            }
        }
        System.out.println("NO INSTALLER");

         */
        return null;
    }

    public static VersionOld chekUpdateMinor(Document doc) {
        /*
        LinkedHashMap<String, Rutas> listaDowloadsJar = ListWeb.getListVersionsWeb(doc, "div[id=aplication] div[id=jar]");
        LinkedHashMap<String, Rutas> dowloadPrincipal = null;

        String rute = null;
        for (Rutas value : listaDowloadsJar.values()) {//obtenemos de donde tenemos que mirar
            rute = Main.hostDowloads + value.getHref();//DE dowloads/list.html
            dowloadPrincipal = ListWeb.getListVersionsWeb(rute, value.getFile(), null);
            break;
        }
        //Buscamos versiones
        for (Map.Entry<String, Rutas> stringListaDeRutasEntry : dowloadPrincipal.entrySet()) {
            String versionNew = stringListaDeRutasEntry.getKey();
            String[] versionNewSplit = versionNew.split("\\.");
            if (Integer.parseInt(versionNewSplit[3]) > 3) {//COMPROBAR QUE la version es release x.x.x.? > 3
                System.out.println(versionNewSplit[0] + "." + versionNewSplit[1] + "." + versionNewSplit[2] + ">" + Main.versionOldSplit[0] + "." + Main.versionOldSplit[1] + "." + Main.versionOldSplit[2]);
                System.out.println(versionNew + ">" + Main.versionOld);
                int ceroNew= Integer.parseInt(versionNewSplit[0]);
                int unoNew= Integer.parseInt(versionNewSplit[1]);
                int dosNew= Integer.parseInt(versionNewSplit[2]);
                if (ceroNew>=ceroOld && unoNew>=unoOld&& dosNew>dosOld) {// ?.x.x.x > actual &&  x.?.x.x >= actual &&  x.x.?.x > actual
                    System.out.println("ENTRO 1 JAR");
                    Rutas objeto = stringListaDeRutasEntry.getValue();
                    return new VersionOld(rute + objeto.getHref(), Main.versionOld, versionNew,false,objeto.getMd5(), objeto.getPath(),objeto.getFile());
                } else if (ceroNew<=ceroOld&&unoNew<=unoOld&&dosNew<dosOld) {
                    break;
                }
            }
        }
        System.out.println("NO JAR");

         */
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
