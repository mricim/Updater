package main.java;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.net.URL;
import java.util.List;

import org.w3c.dom.Document;

public class XML {
    private static int ceroOld = Integer.parseInt(Main.versionOldSplit[0]);
    private static int unoOld = Integer.parseInt(Main.versionOldSplit[1]);
    private static int dosOld = Integer.parseInt(Main.versionOldSplit[2]);

    protected static Document getDocument(String hostDowloads, String fileList) throws ParserConfigurationException, IOException, SAXException {
        // DOM:
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document documento = db.parse(new URL(hostDowloads + fileList).openStream());
        documento.getDocumentElement().normalize();
        return documento;
    }
    protected static NodeList getList(Document document) throws ParserConfigurationException, IOException, SAXException {
        return document.getElementsByTagName("list");
    }


    public static Rutas chekUpdateMajor(NodeList nList,String type) throws IOException, SAXException, ParserConfigurationException {
        Rutas listaDowloadsApp =extractorXML(Main.hostDowloads, nList, null, type);
        Rutas listaInstallers = getRuta(listaDowloadsApp.getHref(), listaDowloadsApp.getFile(), "list", "os");
        return getRuta(listaInstallers.getHref(), listaInstallers.getFile(), "file", "InstallerExe");
    }

    public static Rutas chekUpdateMinor(NodeList nList,String type) throws IOException, SAXException, ParserConfigurationException {
        Rutas listaDowloadsApp =extractorXML(Main.hostDowloads, nList, null, type);
        return getRuta(listaDowloadsApp.getHref(), listaDowloadsApp.getFile(), "file", "updaterCore");
    }

    private static Rutas getRuta(String url, String nameFile, String fileORlist, String ifSearch) throws IOException, SAXException, ParserConfigurationException {
        Document document2 = getDocument(url, nameFile);
        NodeList nList2 = document2.getElementsByTagName(fileORlist);
        return extractorXML(url, nList2, null, ifSearch);
    }

    private static Rutas extractorXML(String ruta, NodeList nodeList, Node node, String tipoDeBusqueda) {
        if (nodeList == null) {
            nodeList = node.getChildNodes();
        }
        for (int temp2 = 0; temp2 < nodeList.getLength(); temp2++) {
            Node node2 = nodeList.item(temp2);
            if (node2.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) node2;
                String seeker = null;
                String system = null;
                String type = null;
                String version = null;
                String href = null;
                String name = null;
                String path = null;
                String md5 = null;
                String file = null;
                try {
                    seeker = eElement.getElementsByTagName("seeker").item(0).getTextContent();
                } catch (NullPointerException e) {
                }
                try {
                    system = eElement.getElementsByTagName("system").item(0).getTextContent();
                } catch (NullPointerException e) {
                }
                try {
                    type = eElement.getElementsByTagName("type").item(0).getTextContent();
                } catch (NullPointerException e) {
                }
                try {
                    version = eElement.getElementsByTagName("version").item(0).getTextContent();
                } catch (NullPointerException e) {
                }
                try {
                    href = eElement.getElementsByTagName("href").item(0).getTextContent();
                } catch (NullPointerException e) {
                }
                try {
                    name = eElement.getElementsByTagName("name").item(0).getTextContent();
                } catch (NullPointerException e) {
                }
                try {
                    path = eElement.getElementsByTagName("path").item(0).getTextContent();
                } catch (NullPointerException e) {
                }
                try {
                    md5 = eElement.getElementsByTagName("md5").item(0).getTextContent();
                } catch (NullPointerException e) {
                }
                try {
                    file = eElement.getElementsByTagName("file").item(0).getTextContent();
                } catch (NullPointerException e) {
                }
//                System.out.println(system);
//                System.out.println(type);
//                System.out.println(version);
//                System.out.println(href);
//                System.out.println(name);
//                System.out.println(path);
//                System.out.println(md5 + "\n");
                String[] versionNewSplit;
                switch (tipoDeBusqueda) {
                    case "installer":
                        if (seeker.equals("installer")) {
                            return new Rutas(system, type, version, ruta + href, name, path, md5, file);
                        }
                        break;
                    case "updater":
                        if (seeker.equals("updater")) {
                            return new Rutas(system, type, version, ruta + href, name, path, md5, file);
                        }
                        break;
                    case "os":
                        if (system.equals(Main.OS)) {
                            System.out.println(ruta + href);
                            return new Rutas(system, type, version, ruta + href, name, path, md5, file);
                        }
                        break;
                    case "InstallerExe":
                        versionNewSplit = version.split("\\.");
                        if (Integer.parseInt(versionNewSplit[3]) > 3) {//COMPROBAR QUE la version es release x.x.x.? > 3
                            System.out.println(versionNewSplit[0] + "." + versionNewSplit[1] + ">" + Main.versionOldSplit[0] + "." + Main.versionOldSplit[1]);
                            System.out.println(version + ">" + Main.versionOld);
                            int ceroNew = Integer.parseInt(versionNewSplit[0]);
                            int unoNew = Integer.parseInt(versionNewSplit[1]);
                            if (ceroNew >= ceroOld && unoNew > unoOld) {// ?.x.x.x >= actual &&  x.?.x.x > actual
                                return new Rutas(system, "installer", version, ruta + href, name, path, md5, file);
                            } else if (ceroNew <= ceroOld && unoNew < unoOld) {
                                return null;
                            }
                        }
                        break;
                    case "updaterCore":
                        versionNewSplit = version.split("\\.");
                        if (Integer.parseInt(versionNewSplit[3]) > 3) {//COMPROBAR QUE la version es release x.x.x.? > 3
                            System.out.println(versionNewSplit[0] + "." + versionNewSplit[1] + "." + versionNewSplit[2] + ">" + Main.versionOldSplit[0] + "." + Main.versionOldSplit[1] + "." + Main.versionOldSplit[2]);
                            System.out.println(version + ">" + Main.versionOld);
                            int ceroNew = Integer.parseInt(versionNewSplit[0]);
                            int unoNew = Integer.parseInt(versionNewSplit[1]);
                            int dosNew = Integer.parseInt(versionNewSplit[2]);
                            if (ceroNew == ceroOld && unoNew == unoOld && dosNew > dosOld) {// ?.x.x.x == actual &&  x.?.x.x == actual &&  x.x.?.x > actual
                                return new Rutas(system, "updaterCore", version, ruta + href, name, path, md5, file);
                            } else if (ceroNew <= ceroOld && unoNew <= unoOld && dosNew < dosOld) {
                                return null;
                            }
                        }
                        break;
                }
            }
        }
        return null;
    }
}