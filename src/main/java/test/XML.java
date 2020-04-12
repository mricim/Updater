package main.java.test;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

import static main.java.test.Main.*;

public class XML {
    public static XMLtoUploader readXML(File xml) {
        String url;
        String installer;
        String md5;
        String path;
        String fileName;
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document documento = builder.parse(xml);
            documento.getDocumentElement().normalize();
            //NodeList unalista =  documento.getElementsByTagName("name");
            //System.out.println("XX "+unalista.item(0).getTextContent());
            url = documento.getElementsByTagName("url").item(0).getTextContent();
            versionOld = documento.getElementsByTagName("versionOld").item(0).getTextContent();
            versionNew = documento.getElementsByTagName("versionNew").item(0).getTextContent();
            installer = documento.getElementsByTagName("installer").item(0).getTextContent();
            md5 = documento.getElementsByTagName("md5").item(0).getTextContent();
            path = documento.getElementsByTagName("path").item(0).getTextContent();
            fileName = documento.getElementsByTagName("fileName").item(0).getTextContent();

            return new XMLtoUploader(url, versionOld, versionNew, Boolean.parseBoolean(installer),md5,path, fileName);
        } catch (ParserConfigurationException | IOException | SAXException e) {
            e.printStackTrace();
        }
        return null;
    }
}
