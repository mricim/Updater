package main.java.os;

/**
 * helper class to check the operating system this Java VM runs in
 * <p>
 * please keep the notes below as a pseudo-license
 * <p>
 * http://stackoverflow.com/questions/228477/how-do-i-programmatically-determine-operating-system-in-java
 * compare to http://svn.terracotta.org/svn/tc/dso/tags/2.6.4/code/base/common/src/com/tc/util/runtime/Os.java
 * http://www.docjar.com/html/api/org/apache/commons/lang/SystemUtils.java.html
 */

import com.sun.javafx.util.Utils;

import java.io.File;
import java.util.Locale;

import static main.java.Main.OS;

public final class OsCheck {
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

    /**
     * types of Operating Systems
     */
    public enum OSType {
        Windows, MacOS, Linux, Other
    }

    // cached result of OS detection
    protected static OSType detectedOS;

    /**
     * detect the operating system from the os.name System property and cache
     * the result
     *
     * @returns - the operating system detected
     */
    public static OSType getOperatingSystemType() {
        if (detectedOS == null) {
            String OS = System.getProperty("os.name", "generic").toLowerCase(Locale.ENGLISH);
            if ((OS.indexOf("mac") >= 0) || (OS.indexOf("darwin") >= 0)) {
                detectedOS = OSType.MacOS;
            } else if (OS.indexOf("win") >= 0) {
                detectedOS = OSType.Windows;
            } else if (OS.indexOf("nux") >= 0) {
                detectedOS = OSType.Linux;
            } else {
                detectedOS = OSType.Other;
            }
        }
        return detectedOS;
    }

    public static String changeRuteURL(String rute) {
        try {
            return rute.replaceAll("\\\\", "/");
        } catch (Exception e) {
            //e.printStackTrace();
        }
        return rute;
    }

    public static String changeRute(String rute) {
        try {
            if (OS.equals("windows")) {
                return rute.replaceAll("/", "\\");
            } else if (OS.equals("linux")) {
                return rute.replaceAll("\\\\", "/");
            } else {
                return rute;
            }
        } catch (Exception e) {
            //e.printStackTrace();
        }
        return rute;
    }

    public static String returnSlash(String delante, String detras) {
        StringBuilder frase = new StringBuilder(delante);
        if (OS.equals("windows")) {
            frase.append("\\");
        } else if (OS.equals("linux")) {
            frase.append("/");
        } else {
            frase.append("/");
        }
        frase.append(detras);
        return frase.toString();
    }
}
