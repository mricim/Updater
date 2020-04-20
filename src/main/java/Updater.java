package main.java;

import main.java.test.CheckSumMD5;

import java.io.File;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

import static main.java.Main.*;

public class Updater {

    static File dowloadFiles(Rutas toUpload) throws Exception {
        String nameFile = toUpload.getName();
        pathTemp = PATH + "/temp/" + nameFile;
        String path = PATH +toUpload.getPath()+"/"+nameFile;

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
                File old = new File(path);
                consolaPRINT("\n"+fileNew.getAbsolutePath());
                consolaPRINT(path+"\n");
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
