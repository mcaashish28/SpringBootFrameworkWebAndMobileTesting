package Utilities;

import javax.xml.parsers.*;
import javax.xml.xpath.*;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.w3c.dom.*;
import java.io.File;

public class ReadExternalInputFilesData {

    public static void main(String[] args){

        ReadExternalInputFilesData readXMLFileData = new ReadExternalInputFilesData();
        ReadXMLFileData();
        ReadPDFFileData();

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
    }       // end of function - ReadXMLFileData


    public static void ReadPDFFileData(){

        try{
            File pdfFile = new File("C:\\GitHubProjects\\springbootproject\\Configurations\\sampleToRead.pdf");
            PDDocument pdDocument = PDDocument.load(pdfFile);
            PDFTextStripper pdfTextStripper = new PDFTextStripper();

            String text = pdfTextStripper.getText(pdDocument);
            System.out.println("Print PDF Text : ");
            System.out.println("#####################################################");

            System.out.println(text);

            System.out.println("#####################################################");

            pdDocument.close();
         }catch(Exception e){
            System.out.println("Exception in reading PDF file : " + e.getMessage());
        }


    }       // end of function - ReadPDFFileData

}
