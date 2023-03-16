package src;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class Configuration {

    private static Map nodeMap = new HashMap<>();

    public static void initCacheConfiguration() {

        try {
            DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document document = documentBuilder.parse(new File("/Users/arun-zt455/IdeaProjects/Hashing/SimpleHashing/src/server.xml"));

            NodeList nodeList = document.getElementsByTagName("server");
            for (int i=0; i<nodeList.getLength(); i++){
                Node node =  nodeList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE){
                    Element element = (Element) node;
                    String ip = element.getAttribute("ip");
                    int port = Integer.parseInt(element.getAttribute("port"));
                    nodeMap.put(i, new ServerNode(ip, port));
                }
            }
        }catch (Exception e){
            System.out.println("Exception : " + e);
        }

    }

    public static Map getNodeMap(){
        return nodeMap;
    }

}
