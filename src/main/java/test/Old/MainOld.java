package main.java.test.Old;

import main.java.test.CheckSumMD5;

import java.io.File;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.TreeMap;

import static main.java.test.XML.readXML;


public class MainOld {
    private static final String PATH = System.getProperty("user.dir").replace("Updater", "");

    public static String nameApp;
    public static String versionOld;
    public static String url;
    public static String fileList;

    public static String versionNew;
    public static LinksDeLaWebParaDownloadFile linksDeLaWebParaDownloadFile;
    public static String nameFileFinal;

    public static void main(String[] args) {
        /*
        //leer variables del XML
        File xml = new File(PATH + "/toUpdater.xml");
        readXML(xml);

        //Buscamos en la web las veriones
        TreeMap<String, LinksDeLaWebParaDownloadFile> lista = ListWeb.getListVersionsWeb();
        if (lista != null) {
            versionNew = lista.lastKey();
            linksDeLaWebParaDownloadFile = lista.get(versionNew);
            nameFileFinal = linksDeLaWebParaDownloadFile.getHref().replace("_" + versionNew, "");

            String filePathNameSaveTemp = PATH + "\\" + linksDeLaWebParaDownloadFile;
            String filePathNameSaveFinal = PATH + "\\" + nameFileFinal;

            try {
                Files.copy(new URL(url + linksDeLaWebParaDownloadFile.getHref()).openStream(), Paths.get(filePathNameSaveTemp));
                System.out.println("Descarga completada");
                File fileNew = new File(filePathNameSaveTemp);
                try {
                    CheckSumMD5.isMD5Checksum(fileNew, linksDeLaWebParaDownloadFile.getMd5());
                    System.out.println(CheckSumMD5.getMD5Checksum(fileNew));
                    System.out.println(linksDeLaWebParaDownloadFile.getMd5());
                    File old=new File(filePathNameSaveFinal);
                    if (old.delete() || ! old.exists()) {
                        System.out.println("original borrado o no existe");
                        if (fileNew.renameTo(new File(filePathNameSaveFinal))) {
                            System.out.println("TODO OK");
                        } else {
                            System.out.println("TODO NO OK");
                            //TODO ERROR NO SE PUDO MOVER
                        }
                    } else {
                        //TODO NO SE PUDO BORRAR
                    }



                } catch (Exception e) {
                    //TODO EL CHECKSUM NO ES CORRECTO
                }
                //TODO removeFile(xml); xml.delete()
            } catch (Exception e) {
                System.out.println("NO SE PUDO DESCARGAR");
            }
            // specify StandardCopyOption.REPLACE_EXISTING as 3rd argument to enable overwriting



        } else {
            //TODO MENSAJE DE QUE FUE MAL (No tenemos lista)
        }

         */
    }
    /*
            private static void print(String msg, Object... args) {
                System.out.println(String.format(msg, args));
            }

            private static String trim(String s, int width) {
                if (s.length() > width)
                    return s.substring(0, width - 1) + ".";
                else
                    return s;


            }

         */
}