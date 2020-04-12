package main.java.test;


import com.sun.javafx.util.Utils;

import java.io.File;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Main {
    private static final String PATH = System.getProperty("user.dir").replace("Updater", "");
    private static final String NAMEFILE = "toUpdater.xml";

    public static String versionOld;
    public static String versionNew;

    public static void main(String[] args) {
        //leer variables del XML
        try {
            Thread.sleep(18000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        File xml = new File(PATH + "/" + NAMEFILE);
        XMLtoUploader xmLtoUploader = XML.readXML(xml);
        //iniciamos el proceso
        //TODO LOAD BARRA DE CARGA FX
        try {
            //variables
            assert xmLtoUploader != null : "No se encontro el archivo " + NAMEFILE;
            String url = xmLtoUploader.getUrl();
            String nameFile = xmLtoUploader.getFileName();
            if (nameFile == null || nameFile.equals("")) {
                nameFile = url.substring(url.lastIndexOf("/") + 1);
            }
            String path;
            String pathTemp = System.getProperty("java.io.tmpdir") + nameFile;
            switch (xmLtoUploader.getPath()) {
                case ".":
                    path = PATH + nameFile;
                    break;
                case "":
                    path = PATH + nameFile;
                    break;
                case "tmp":
                    path = pathTemp;
                    break;
                default:
                    path = xmLtoUploader.getPath() + nameFile;
                    break;
            }
            //Descarga del fichero
            new File(pathTemp).delete();//por si existe otro fichero con el mismo nombre
            Files.copy(new URL(url).openStream(), Paths.get(pathTemp));
            System.out.println("Descarga completada");
            File fileNew = new File(pathTemp);
            try {
                //Comprobacion de que el md5 es correcto
                if (!CheckSumMD5.isMD5Checksum(fileNew, xmLtoUploader.getMd5())) {
                    throw new Exception("MD5 --> original=" + xmLtoUploader.getMd5() + " file=" + CheckSumMD5.getMD5Checksum(fileNew));
                }
//                System.out.println(CheckSumMD5.getMD5Checksum(fileNew));
//                System.out.println(xmLtoUploader.getMd5());
                if (!xmLtoUploader.isInstaller()) {
                    File old = new File(path);
                    if (old.delete() || !old.exists()) {
                        System.out.println("original borrado o no existe");
                        if (fileNew.renameTo(new File(path))) {
                            System.out.println("TODO OK");
                        } else {
                            System.out.println("TODO NO OK");
                            //TODO ERROR NO SE PUDO MOVER
                        }
                    } else {
                        //TODO NO SE PUDO BORRAR
                    }
                } else {
                    if (xmLtoUploader.isInstaller()) {
                        System.out.println("INSTALADOR");
                        if (Utils.isWindows()) {
                            System.out.println(path);
                            System.out.println(path.substring(0, path.lastIndexOf("\\") + 1));
                            Runtime.getRuntime().exec(path, null, new File(path.substring(0, path.lastIndexOf("\\") + 1)));
                            xml.delete();
                        } else if (Utils.isMac()) {

                        } else if (Utils.isUnix()) {

                        } else {
                            //TODO ERROR SO ?
                        }
                    } else {
                        System.out.println("UPDATE NORMAL");
                        if (Utils.isWindows()) {
                            System.out.println(path);
                            System.out.println(fileNew.exists());
                            Runtime.getRuntime().exec(path, null, new File(path.substring(0, path.lastIndexOf("\\") + 1)));
                            //Process process = new ProcessBuilder(path, "param1", "param2").start();
                        } else if (Utils.isMac()) {

                        } else if (Utils.isUnix()) {

                        } else {
                            //TODO ERROR SO ?
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                //TODO EL CHECKSUM NO ES CORRECTO
            }
            //TODO removeFile(xml); xml.delete()
        } catch (Exception e) {
            System.out.println("NO SE PUDO DESCARGAR");
            //TODO MENSAJE DE QUE FUE MAL (No tenemos lista)
        }
        // specify StandardCopyOption.REPLACE_EXISTING as 3rd argument to enable overwriting

    }
}
