package main.java;

import com.sun.javafx.util.Utils;
import main.java.test.CheckSumMD5;
import main.java.versions.Version;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

import static main.java.Main.*;

public class Updater {
    static void inciarApp() throws IOException {
        String iniciar = PATH + "/";
        String app = iniciar + "core.exe";
        consolaPRINT(iniciar);
        consolaPRINT(app);
        try {
            Thread.sleep(18000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Runtime.getRuntime().exec(app, null, new File(iniciar));
        consolaPRINT("INICIO");
    }

    static void installer(File file) throws IOException {
        consolaPRINT("INSTALADOR");
        String path = file.getAbsolutePath();
        consolaPRINT(path);
        try {
            Thread.sleep(18000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (Utils.isWindows()) {
            //consolaPRINT(path.substring(0, path.lastIndexOf("\\") + 1));
            Runtime.getRuntime().exec(path, null, new File(file.getParent()));
        } else if (Utils.isMac()) {

        } else if (Utils.isUnix()) {

        } else {
            //TODO ERROR SO ?
        }
    }

    static File dowloadFiles(Version toUpload) throws Exception {
        String nameFile = toUpload.getFileName();
        pathTemp = System.getProperty("java.io.tmpdir") + nameFile;
        String path = toUpload.getPath();
        switch (path) {
            case ".":
                path = PATH + "\\bin\\" + nameFile;
                break;
            case "":
                path = PATH + "\\bin\\" + nameFile;
                break;
            case "tmp":
                path = pathTemp;
                break;
            default:
                path = path + "\\bin\\" + nameFile;
                break;
        }

        //Descarga del fichero
        new File(pathTemp).delete();//por si existe otro fichero con el mismo nombre
        Files.copy(new URL(toUpload.getUrl()).openStream(), Paths.get(pathTemp));
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
            if (!toUpload.isInstaller()) {
                consolaPRINT(path);
                File old = new File(path);
                Thread.sleep(6000);
                consolaPRINT("");
                consolaPRINT(fileNew.getAbsolutePath());
                consolaPRINT(path);
                consolaPRINT("");
                        Thread.sleep(6000);
                if (old.delete() || !old.exists()) {
                    consolaPRINT("original borrado o no existe");
                    if (fileNew.renameTo(new File(path))) {
                        consolaPRINT("Update hecho");
                        return null;
                    } else {
                        consolaPRINT("Update fallo");
                        //TODO ERROR NO SE PUDO MOVER
                    }
                } else {
                    consolaPRINT("ERROR BORRAR ----------");
                    //TODO NO SE PUDO BORRAR
                }
            } else {
                return fileNew;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        throw new Exception("algo raro: dowloadFiles()");
    }
}
