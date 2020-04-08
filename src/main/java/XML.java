package main.java;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

import static main.java.Main.*;

public class XML {
    static void readXML(File xml) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document documento = builder.parse(xml);
            documento.getDocumentElement().normalize();
            //NodeList unalista =  documento.getElementsByTagName("name");
            //System.out.println("XX "+unalista.item(0).getTextContent());
            nameApp = documento.getElementsByTagName("name").item(0).getTextContent();
            versionOld = documento.getElementsByTagName("version").item(0).getTextContent();
            url = documento.getElementsByTagName("url").item(0).getTextContent();
            fileList = documento.getElementsByTagName("fileList").item(0).getTextContent();

        } catch (ParserConfigurationException | IOException | SAXException e) {
            e.printStackTrace();
        }
    }
}
