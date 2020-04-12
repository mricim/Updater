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
        String iniciar=PATH+"/";
        String app=iniciar+"core.exe";
        consolaPRINT(iniciar);
        consolaPRINT(app);
        Runtime.getRuntime().exec(app, null, new File(iniciar));
        System.exit(0);
    }

    static void installer(File file) throws IOException {
        consolaPRINT("INSTALADOR");
        if (Utils.isWindows()) {
            String path = file.getAbsolutePath();
            consolaPRINT(path);
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
                path = PATH + nameFile;
                break;
            case "":
                path = PATH + nameFile;
                break;
            case "tmp":
                path = pathTemp;
                break;
            default:
                path = path + nameFile;
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
//                consolaPRINT(CheckSumMD5.getMD5Checksum(fileNew));
//                consolaPRINT(xmLtoUploader.getMd5());
            if (!toUpload.isInstaller()) {
                File old = new File(path);
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
