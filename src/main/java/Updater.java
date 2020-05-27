package main.java;

import main.java.test.CheckSumMD5;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static main.java.Main.*;

public class Updater {

    protected static ArrayList<Rutas> newFiles = new ArrayList<>();
    protected static ArrayList<Rutas> oldFiles = new ArrayList<>();
    protected static Set<Rutas> borrarFiles = new HashSet<>();
    protected static Set<Rutas> descargarFiles = new HashSet<>();

    static File dowloadFiles(Rutas toUpload) throws Exception {
        String nameFile = toUpload.getName();
        pathTemp = PATH + "/temp/" + nameFile;
        String prePath = PATH + toUpload.getPath();
        String path = PATH + File.separator + toUpload.getPath() + File.separator + nameFile;
        if (OS.equals("windows")) {
            path = path.replaceAll("/", "\\");
        }

        //Descarga del fichero
        new File(pathTemp).delete();//por si existe otro fichero con el mismo nombre
        Files.copy(new URL(toUpload.getHref()).openStream(), Paths.get(pathTemp));
        consolaPRINT("Descarga completada");
        File fileNew = new File(pathTemp);
        try {
            //Comprobacion de que el md5 es correcto
            if (!CheckSumMD5.isMD5Checksum(fileNew, toUpload.getMd5())) {
                throw new Exception("MD5 --> original=" + toUpload.getMd5() + " file=" + CheckSumMD5.getMD5Checksum(fileNew));
            }
            consolaPRINT("MD5 OK");
//                consolaPRINT(CheckSumMD5.getMD5Checksum(fileNew));
//                consolaPRINT(xmLtoUploader.getMd5());
            if (!toUpload.getType().equals("installer")) {
                consolaPRINT(path);
                new File(prePath).mkdirs();
                File old = new File(path);
                consolaPRINT("\n" + fileNew.getAbsolutePath());
                consolaPRINT(path + "\n");
                if (old.delete() || !old.exists()) {
                    consolaPRINT("original borrado o no existe");

                    consolaPRINT(fileNew.getAbsolutePath() + " " + old.getAbsolutePath());
                    consolaPRINT(fileNew.getAbsolutePath());

                    //Files.move(Paths.get(fileNew.getAbsolutePath()), Paths.get(old.getAbsolutePath()), StandardCopyOption.REPLACE_EXISTING);
                    if (fileNew.renameTo(old)) {
                        consolaPRINT("move file: hecho");
                        return null;
                    } else {
                        consolaPRINT("move file: error");
                    }
                } else {
                    consolaPRINT("ERROR BORRAR ----------");
                }
            } else {
                return fileNew;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        throw new Exception("algo raro: dowloadFiles()");
    }

    public static void removeFiles(Rutas rutas) {
        String path = PATH + rutas.getPath() + File.separator + rutas.getName();
        File remove = new File(path);
        if (remove.delete()) {
            System.out.println("delete");
        }
    }

    public static Rutas chekUpdateMajor(NodeList nList, String type) throws IOException, SAXException, ParserConfigurationException {
        Rutas listaDowloadsApp = XML.extractorXML(hostDowloads, nList, null, type);
        Rutas listaInstallers = XML.getRuta(listaDowloadsApp.getHref(), listaDowloadsApp.getFile(), "list", "os");
        return XML.getRuta(listaInstallers.getHref(), listaInstallers.getFile(), "file", "InstallerExe");
    }

    public static Rutas chekUpdateMinor(NodeList nList, String type) throws IOException, SAXException, ParserConfigurationException {
        Rutas listaDowloadsApp = XML.extractorXML(hostDowloads, nList, null, type);
        return XML.getRuta(listaDowloadsApp.getHref(), listaDowloadsApp.getFile(), "file", "updaterCore");
    }

    public static void chekUpdateFiles(NodeList nList, String type) throws IOException, SAXException, ParserConfigurationException {
        Rutas listaDowloadsApp = XML.extractorXML(hostDowloads, nList, null, type);
        System.out.println(listaDowloadsApp.getHref());
        XML.getRuta(listaDowloadsApp.getHref(), listaDowloadsApp.getFile(), "file", "updaterFile");
        borrarFiles.addAll(oldFiles);
        descargarFiles.addAll(newFiles);

        descargarFiles.removeAll(oldFiles);
        borrarFiles.removeAll(newFiles);
    }

    public static void listOldFiles() throws Exception {
        List<File> files = new ArrayList<>();
        listf(PATH + "res", files);
        for (File file : files) {
            String name = file.getName();
            System.out.println(file.getName() + " " + file.getPath().replace(PATH, "").replace("\\" + name, "") + " " + CheckSumMD5.getMD5Checksum(file));
            oldFiles.add(new Rutas(null, null, null, null, name, file.getPath().replace(PATH, "").replace("\\" + name, ""), CheckSumMD5.getMD5Checksum(file), null));
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

