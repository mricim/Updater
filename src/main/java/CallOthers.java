package main.java;

import com.sun.javafx.util.Utils;
import main.java.os.Os;

import java.io.File;
import java.io.IOException;

import static main.java.Main.PATH;
import static main.java.Main.consolaPRINT;
import static main.java.os.Os.operativeSystem;

public class CallOthers {

    private static final String OS_VERSION  = operativeSystem();
    private static final String UNKNOWN_OS = "UNKNOWN OS: "+OS_VERSION;


    static void generateFiles() throws IOException {
        String iniciar = PATH + "/";
        String app = "";
        switch (OS_VERSION){
            case "windows":
                System.out.println("OS Version: "+OS_VERSION);
                app = iniciar + "generator.exe";
                break;
            case "mac":
                System.out.println("OS Version: "+OS_VERSION);
                app = iniciar + "generator.app";//TODO no estoy seguro de la extensión de ejecutable de MAC OS
                break;
            case "linux":
                System.out.println("OS Version: "+OS_VERSION);
                app = iniciar + "generator.sh";
                break;
            default:
                System.err.println(UNKNOWN_OS);
                break;
        }
        consolaPRINT(iniciar);
        consolaPRINT(app);
        Runtime.getRuntime().exec(app, null, new File(iniciar));
        consolaPRINT("call to files generator");
    }
    static void inciarApp() throws IOException {
        String iniciar = PATH + "/";
        String app = "";
        switch (OS_VERSION){
            case "windows":
                System.out.println("OS Version: "+OS_VERSION);
                app = iniciar + "core.exe";
                break;
            case "mac":
                System.out.println("OS Version: "+OS_VERSION);
                app = iniciar + "core.app";//TODO no estoy seguro de la extensión de ejecutable de MAC OS
                break;
            case "linux":
                System.out.println("OS Version: "+OS_VERSION);
                app = iniciar + "core.sh";
                break;
            default:
                System.err.println(UNKNOWN_OS);
                break;
        }
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
//            consolaPRINT(path.substring(0, path.lastIndexOf("\\") + 1));
            Runtime.getRuntime().exec(path, null, new File(file.getParent()));
        } else if (Utils.isMac()) {
//            consolaPRINT(path.substring(0, path.lastIndexOf("\\") + 1));
            Runtime.getRuntime().exec(path, null, new File(file.getParent()));
        } else if (Utils.isUnix()) {
//            consolaPRINT(path.substring(0, path.lastIndexOf("\\") + 1));
            Runtime.getRuntime().exec(path, null, new File(file.getParent()));
        } else {
            //TODO ERROR SO ?
            System.err.println("UNKNOWN OS");
        }
    }
}
