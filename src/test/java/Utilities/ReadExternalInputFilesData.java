package Utilities;

import javax.xml.parsers.*;
import javax.xml.xpath.*;
import org.w3c.dom.*;
import java.io.File;

public class ReadExternalInputFilesData {

    public static void main(String[] args){

        ReadExternalInputFilesData readXMLFileData = new ReadExternalInputFilesData();
        ReadXMLFileData();

    }

    public static void ReadXMLFileData(){
        try{
            File xmlFile = new File("C:\\GitHubProjects\\springbootproject\\Configurations\\Env_Variables.xml");
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(xmlFile);

            document.getDocumentElement().normalize();

            System.out.println("Root Element : " + document.getDocumentElement().getNodeName());
            NodeList nodeList = document.getElementsByTagName("Environment");
            System.out.println("################################");

            for(int i=0; i<nodeList.getLength(); i++){
                Node node = nodeList.item(i);

                System.out.println("\nCurrent Element : " + node.getNodeName());

                if(node.getNodeType()==Node.ELEMENT_NODE){
                    Element element = (Element)node;

                    System.out.println("Environment ID : " + element.getAttribute("id"));
                    System.out.println("Name is : " + element.getElementsByTagName("name").item(0).getTextContent());
                    System.out.println("URL is : " + element.getElementsByTagName("URL").item(0).getTextContent());
                }
            }


        } catch(Exception e){
            System.out.println("Exception in reading XML file : " + e.getMessage());
        }
    }


}
