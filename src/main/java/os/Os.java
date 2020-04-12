package main.java.os;

import com.sun.javafx.util.Utils;

public class Os {
    public static String operativeSystem() {
        if (Utils.isWindows()) {
            return "windows";
        } else if (Utils.isMac()) {
            return "mac";
        } else if (Utils.isUnix()) {
            return "linux";
        } else {
            return "null";
        }
    }
}
