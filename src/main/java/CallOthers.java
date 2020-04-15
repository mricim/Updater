package main.java;

import com.sun.javafx.util.Utils;

import java.io.File;
import java.io.IOException;

import static main.java.Main.PATH;
import static main.java.Main.consolaPRINT;

public class CallOthers {
    static void generateFiles() throws IOException {
        String iniciar = PATH + "/";
        String app = iniciar + "generator.exe";
        consolaPRINT(iniciar);
        consolaPRINT(app);
        Runtime.getRuntime().exec(app, null, new File(iniciar));
        consolaPRINT("call to files generator");
    }
    static void inciarApp() throws IOException {
        String iniciar = PATH + "/";
        String app = iniciar + "core.exe";
        consolaPRINT(iniciar);
        consolaPRINT(app);
        Runtime.getRuntime().exec(app, null, new File(iniciar));
        consolaPRINT("INICIO");
    }
    static void installer(File file) throws IOException {
        consolaPRINT("INSTALADOR");
        String path = file.getAbsolutePath();
        consolaPRINT(path);
        if (Utils.isWindows()) {
            //consolaPRINT(path.substring(0, path.lastIndexOf("\\") + 1));
            Runtime.getRuntime().exec(path, null, new File(file.getParent()));
        } else if (Utils.isMac()) {

        } else if (Utils.isUnix()) {

        } else {
            //TODO ERROR SO ?
        }
    }
}
