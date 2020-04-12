package main.java.propieties;


import java.io.*;
import java.util.Date;
import java.util.Properties;

import static main.java.Main.*;

/**
 * @author Crunchify.com
 */

public class GetPropertyValues {
    InputStream inputStream;

    public void getPropValues() throws IOException {

        Properties prop = new Properties();
        inputStream = new FileInputStream(new File(propFileName));

        prop.load(inputStream);


        // get the property value and print it out
        name = prop.getProperty("name");
        versionOld = prop.getProperty("version");
        host = prop.getProperty("host");
        project = prop.getProperty("project");
        fileList = prop.getProperty("fileList");

        System.out.println("name= \"" + name + "\"");
        System.out.println("version= \"" + versionOld + "\"");
        System.out.println("host= \"" + host + "\"");
        System.out.println("project= \"" + project + "\"");
        System.out.println("fileList= \"" + project + "\"");
        System.out.println("Program Ran on " + new Date(System.currentTimeMillis()));

        inputStream.close();

    }
}
