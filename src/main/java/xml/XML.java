package main.java.xml;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.w3c.dom.Document;

public class XML {
    public static Document getListUpdates(String hostDowloads, String fileList) throws ParserConfigurationException, IOException, SAXException {

// or if you prefer DOM:
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document documento = db.parse(new URL(hostDowloads + fileList).openStream());
        documento.getDocumentElement().normalize();
        return documento;


        //forVersions = doc.getElementsByTagName("url").item(0).getTextContent();

    }
}
