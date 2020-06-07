package main.java;

import com.sun.javafx.util.Utils;

import java.io.File;
import java.io.IOException;

import static main.java.Main.PATH;
import static main.java.Main.consolaPRINT;
import static main.java.os.OsCheck.operativeSystem;

public class CallOthers {

    private static final String OS_VERSION = operativeSystem();
    private static final String UNKNOWN_OS = "UNKNOWN OS: " + OS_VERSION;


    static void generateFiles() throws IOException {
        String iniciar = PATH + "/";
        String app = "";
        switch (OS_VERSION) {
            case "windows":
                System.out.println("OS Version: " + OS_VERSION);
                app = iniciar + "generator.exe";
                break;
            case "mac":
                System.out.println("OS Version: " + OS_VERSION);
                app = iniciar + "generator.app";//TODO no estoy seguro de la extensión de ejecutable de MAC OS
                break;
            case "linux":
                System.out.println("OS Version: " + OS_VERSION);
                app = iniciar + "generator.sh";
                break;
            default:
                System.err.println(UNKNOWN_OS);
                break;
        }
        consolaPRINT(iniciar, 1000);
        consolaPRINT(app, 3000);
        Runtime.getRuntime().exec(app, null, new File(iniciar));
        consolaPRINT("call to files generator", 3000);
    }

    static void inciarApp() throws IOException {
        String iniciar = PATH + "/";
        File app = null;
        switch (OS_VERSION) {
            case "windows":
                System.out.println("OS Version: " + OS_VERSION);
                app = new File(iniciar + "core.exe");
                break;
            case "mac":
                System.out.println("OS Version: " + OS_VERSION);
                app = new File(iniciar + "core.app");//TODO no estoy seguro de la extensión de ejecutable de MAC OS
                break;
            case "linux":
                System.out.println("OS Version: " + OS_VERSION);
                app = new File(iniciar + "core.sh");
                break;
            default:
                System.err.println(UNKNOWN_OS);
                break;
        }
        consolaPRINT(iniciar, 3000);
        consolaPRINT(app.getAbsolutePath(), 3000);
        Runtime.getRuntime().exec(app.getAbsolutePath(), null, new File(iniciar));
        consolaPRINT("INICIO", 3000);
    }

    static void installer(File file) throws IOException {
        consolaPRINT("INSTALADOR", 3000);
        String path = file.getAbsolutePath();
        consolaPRINT(path, 3000);
        if (Utils.isWindows()) {
//            consolaPRINT(path.substring(0, path.lastIndexOf("\\") + 1));
            Runtime.getRuntime().exec(path, null, new File(file.getParent()));
        } else if (Utils.isMac()) {
//            consolaPRINT(path.substring(0, path.lastIndexOf("\\") + 1));
            Runtime.getRuntime().exec(path, null, new File(file.getParent()));
        } else if (Utils.isUnix()) {
            Runtime rt = Runtime.getRuntime();
            rt.exec("tar zxvf " + path + " -C " + PATH.substring(0, PATH.lastIndexOf("/")));
            rt.exec("sleep 2");
            consolaPRINT("tar zxvf " + path + " -C " + PATH.substring(0, PATH.lastIndexOf("/")) + " && sleep 2 && cd " + PATH + " && sh run.sh", 1000);
            rt.exec("cd " + PATH);
            rt.exec("sh run.sh");
        } else {
            //TODO ERROR SO ?
            System.err.println("UNKNOWN OS");
        }
        System.exit(0);
    }
}
