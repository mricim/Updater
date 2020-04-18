package main.java.versions;

import main.java.Main;
import main.java.xml.XML;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public class Version {
    private static final int ceroOld = Integer.parseInt(Main.versionOldSplit[0]);
    private static final int unoOld = Integer.parseInt(Main.versionOldSplit[1]);
    private static final int dosOld = Integer.parseInt(Main.versionOldSplit[2]);

    public static Rutas chekUpdateMajor(Document document) throws IOException, SAXException, ParserConfigurationException {
        Rutas listaDowloadsApp = null;
        NodeList nList = document.getElementsByTagName("forVersions");//xml
        for (int temp = 0; temp < nList.getLength(); temp++) {
            Node node = nList.item(temp);//list
            listaDowloadsApp = sacarCosas(Main.hostDowloads, null, node, "installer");//switch
        }

        Document document2 = XML.getListUpdates(listaDowloadsApp.getHref(), listaDowloadsApp.getFile());
        NodeList nList2 = document2.getElementsByTagName("list");
        Rutas listaInstallers = sacarCosas(listaDowloadsApp.getHref(), nList2, null, "os");

        Document document3 = XML.getListUpdates(listaInstallers.getHref(), listaInstallers.getFile());
        NodeList nList3 = document3.getElementsByTagName("file");
        Rutas temp=sacarCosas(listaInstallers.getHref(), nList3, null, "InstallerExe");
        return temp;
    }
    public static Rutas chekUpdateMinor(Document document) throws IOException, SAXException, ParserConfigurationException {
        Rutas listaDowloadsApp = null;
        NodeList nList = document.getElementsByTagName("forVersions");
        for (int temp = 0; temp < nList.getLength(); temp++) {
            Node node = nList.item(temp);//list
            listaDowloadsApp = sacarCosas(Main.hostDowloads, null, node, "updater");
        }
        Document document3 = XML.getListUpdates(listaDowloadsApp.getHref(), listaDowloadsApp.getFile());//INSTALLERS list
        NodeList nList3 = document3.getElementsByTagName("file");
        return sacarCosas(listaDowloadsApp.getHref(), nList3, null, "updaterCore");
    }

    private static Rutas sacarCosas(String ruta, NodeList nodeList, Node node, String busqueda) {
        if (nodeList == null) {
            nodeList = node.getChildNodes();
        }
        for (int temp2 = 0; temp2 < nodeList.getLength(); temp2++) {
            Node node2 = nodeList.item(temp2);
            if (node2.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) node2;
                String system = null;
                String type = null;
                String version = null;
                String href = null;
                String name = null;
                String path = null;
                String md5 = null;
                String file = null;
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
                switch (busqueda) {
                    case "installer":
                        if (type.equals("installer")) {
                            return new Rutas(system, type, version, ruta + href, name, path, md5, file);
                        }
                        break;
                    case "updater":
                        if (type.equals("updater")) {
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
                            int ceroNew= Integer.parseInt(versionNewSplit[0]);
                            int unoNew= Integer.parseInt(versionNewSplit[1]);
                            int dosNew= Integer.parseInt(versionNewSplit[2]);
                            if (ceroNew>=ceroOld && unoNew>=unoOld&& dosNew>dosOld) {// ?.x.x.x > actual &&  x.?.x.x >= actual &&  x.x.?.x > actual
                                return new Rutas(system, "updaterCore", version, ruta + href, name, path, md5, file);
                            } else if (ceroNew<=ceroOld&&unoNew<=unoOld&&dosNew<dosOld) {
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
