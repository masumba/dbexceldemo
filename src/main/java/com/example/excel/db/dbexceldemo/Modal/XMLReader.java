package com.example.excel.db.dbexceldemo.Modal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

@Service
public class XMLReader {
    @Autowired
    MyClass myClass = new MyClass();

    public MyClass display(String xmlFileName){

        String docNameFeild = null;
        String docAddressFeild = null;
        try {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();

            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(xmlFileName);
            XPath xPath = XPathFactory.newInstance().newXPath();
            NodeList nodeList = (NodeList)xPath.compile("//ExcelUpload").evaluate(document, XPathConstants.NODESET);

            for (int i=0; i<nodeList.getLength(); i++){
                docNameFeild = xPath.compile("./name").evaluate(nodeList.item(i));
                docAddressFeild = xPath.compile("./address").evaluate(nodeList.item(i));
            }
        } catch (Exception e){
            e.printStackTrace();
        }

        String data[] = {docNameFeild, docAddressFeild};

        myClass.setXml_address_feild(docAddressFeild);
        myClass.setXml_name_feild(docNameFeild);

        return myClass;
    }
}
