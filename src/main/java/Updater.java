package main.java;

import main.java.os.OsCheck;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static main.java.Hash.MD5;
import static main.java.Main.*;
import static main.java.os.OsCheck.*;

public class Updater {

    protected static ArrayList<Rutas> newFiles = new ArrayList<>();
    protected static ArrayList<Rutas> oldFiles = new ArrayList<>();
    protected static Set<Rutas> borrarFiles = new HashSet<>();
    protected static Set<Rutas> descargarFiles = new HashSet<>();

    static File dowloadFiles(Rutas toUpload) throws Exception {
        String nameFile = toUpload.getName();
        pathTemp = new File(OsCheck.returnSlash(PATH_TEMP.getAbsolutePath(), nameFile));
        //String prePath = PATH + toUpload.getPath();
        String path = changeRute(PATH + File.separator + toUpload.getPath() + File.separator + nameFile);

        //Descarga del fichero
        new File(pathTemp.getAbsolutePath()).delete();//por si existe otro fichero con el mismo nombre
        String url = changeRuteURL(toUpload.getHref());
        System.out.println(url);
        Files.copy(new URL(url).openStream(), Paths.get(pathTemp.getAbsolutePath()));
        consolaPRINT("Descarga completada", 50);
        File fileNew = new File(pathTemp.getAbsolutePath());
        try {
            //Comprobacion de que el md5 es correcto
            String extension = fileNew.getName().substring(fileNew.getName().lastIndexOf('.') + 1);
            if (!(extension.equals("properties") | extension.equals("xml"))) {
                if (!(Hash.toHex(MD5.checksum(fileNew)).equals(toUpload.getMd5()))) {
                    //throw new Exception("MD5 --> original=" + toUpload.getMd5() + " file=" + CheckSumMD5.getMD5Checksum(fileNew)+"\n"+fileNew.getAbsolutePath());
                    throw new Exception("MD5 --> inWEB=" + toUpload.getMd5() + " file=" + Hash.toHex(MD5.checksum(fileNew)) + "\n" + fileNew.getAbsolutePath());
                }
            }
            consolaPRINT("MD5 OK", 50);
//                consolaPRINT(CheckSumMD5.getMD5Checksum(fileNew));
//                consolaPRINT(xmLtoUploader.getMd5());
            if (!toUpload.getFileORlist().equals("installer")) {
                consolaPRINT(path, 50);
                //new File(prePath).mkdirs();
                File old = new File(changeRute(path));
                consolaPRINT(fileNew + " -> " + old, 100);
                if (old.delete() || !old.exists()) {

                    //consolaPRINT(fileNew.getAbsolutePath() + " -> " + old.getAbsolutePath());
                    //consolaPRINT(fileNew.getAbsolutePath());

                    //Files.move(Paths.get(fileNew.getAbsolutePath()), Paths.get(old.getAbsolutePath()), StandardCopyOption.REPLACE_EXISTING);
                    File dir = new File(old.getParent());
                    if (!dir.exists()) {
                        if (!dir.mkdirs()) {
                            throw new Exception("Not create dir");
                        }
                    }
                    if (fileNew.renameTo(old)) {
                        //consolaPRINT("move file: hecho");
                        return null;
                    } else {
                        consolaPRINT("move file: error", 30);
                    }
                } else {
                    consolaPRINT("ERROR BORRAR \n----------", 3000);
                }
            } else {
                return fileNew;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        throw new Exception("NOT DOWNLOAD: dowloadFiles()");
    }

    public static void removeFiles(Rutas rutas) {
        String path = PATH + rutas.getPath() + File.separator + rutas.getName();
        File remove = new File(OsCheck.changeRute(path));
        if (remove.delete()) {
            System.out.println("delete: " + remove.getAbsolutePath());
        } else {
            System.out.println("not delete: " + remove.getAbsolutePath());
        }
    }

    public static Rutas chekUpdateMajor(NodeList nList, String type) throws IOException, SAXException, ParserConfigurationException {
        Rutas listaDowloadsApp = XML.extractorXML(hostDowloads, nList, null, type);
        Rutas listaInstallers = XML.getRuta(listaDowloadsApp.getHref(), listaDowloadsApp.getNameFile(), "list", "os");
        return XML.getRuta(listaInstallers.getHref(), listaInstallers.getNameFile(), "file", "InstallerExe");
    }

    public static Rutas chekUpdateMinor(NodeList nList, String type) throws IOException, SAXException, ParserConfigurationException {
        Rutas listaDowloadsApp = XML.extractorXML(hostDowloads, nList, null, type);
        return XML.getRuta(listaDowloadsApp.getHref(), listaDowloadsApp.getNameFile(), "file", "updaterCore");
    }

    public static void chekUpdateFiles(NodeList nList, String type) throws IOException, SAXException, ParserConfigurationException {
        Rutas listaDowloadsApp = XML.extractorXML(hostDowloads, nList, null, type);
        System.out.println(listaDowloadsApp.getHref());
        XML.getRuta(listaDowloadsApp.getHref(), listaDowloadsApp.getNameFile(), "file", "updaterFile");
        borrarFiles.addAll(oldFiles);
        descargarFiles.addAll(newFiles);

        descargarFiles.removeAll(oldFiles);
        borrarFiles.removeAll(newFiles);

    }

    public static void listOldFiles() throws Exception {
        List<File> files = new ArrayList<>();
        listf(changeRute(PATH + "/res"), files);

        for (File file : files) {
            String name = file.getName();
            //System.out.println(file.getName() + " " + file.getPath().replace(PATH, "").replace("\\" + name, "") + " " + CheckSumMD5.getMD5Checksum(file));
            String rute = file.getPath().replace(PATH, "").replace(returnSlash("", name), "");
            if (rute.startsWith("/") | rute.startsWith("\\")) {
                rute = rute.substring(1);
            }
            //rute = rute.startsWith("/") ? rute.substring(1) : rute;
//            if (Utils.isWindows()) {
            //rute=OsCheck.returnSlash("",rute);
//            }
            oldFiles.add(new Rutas(null, null, null, null, name, rute, CheckSumMD5.getMD5Checksum(file), null));
            //System.out.println("rute= "+rute);
        }

    }

    public static void listf(String directoryName, List<File> files) {
        File directory = new File(directoryName);

        // Get all files from a directory.
        File[] fList = directory.listFiles();
        if (fList != null)
            for (File file : fList) {
                if (file.isFile()) {
                    files.add(file);
                } else if (file.isDirectory()) {
                    listf(file.getAbsolutePath(), files);
                }
            }
    }
}

