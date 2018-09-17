package com.example.excel.db.dbexceldemo.Services;

import com.example.excel.db.dbexceldemo.Configurations.SqlValues;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.List;

@Service
public class XMLWriter {

    @Autowired
    SqlValues sqlValues = new SqlValues();

    public SqlValues xmlCreate(String targetDbTableName, List targetExcelColumnName,List targetXMLFieldState,List targetDbColumnName){

        String xmlFileLocation = "ConfigurationTest.xml";
        Element xmlColumn = null;

        try {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();

            Document document = documentBuilder.newDocument();
            Element rootElement = document.createElement("ExcelUploads");
            document.appendChild(rootElement);

            Element excelUploadElement = document.createElement("ExcelUpload");
            rootElement.appendChild(excelUploadElement);

            Element excelTable = document.createElement("Table");
            excelUploadElement.appendChild(excelTable);

            Attr attrtable = document.createAttribute("tableName");
            attrtable.setValue(targetDbTableName);
            excelTable.setAttributeNode(attrtable);

            /*DB will have to have default value for all feilds*/
            int check_box_state_position_point = 0;
            for(Object checkboxValuee : targetXMLFieldState) {
                //System.out.println("Field Action State = "+checkboxValuee);
                if (checkboxValuee.toString().equals("on")){
                    int position_point = 0;
                    for(Object xmlColumnNameValuee : targetDbColumnName) {

                        if (check_box_state_position_point == position_point){
                            xmlColumn = document.createElement("Column");
                            excelTable.appendChild(xmlColumn);
                            xmlColumn.setAttribute("columnName",xmlColumnNameValuee.toString());
                            /**/
                            int inner_position_point = 0;
                            for(Object ExcelValue : targetExcelColumnName) {

                                if (position_point == inner_position_point){
                                    Element xmlExcelValue = document.createElement("ExcelValue");
                                    xmlExcelValue.appendChild(document.createTextNode(ExcelValue.toString()));
                                    xmlColumn.appendChild(xmlExcelValue);
                                }

                                inner_position_point++;
                            }
                            /**/
                        }

                        position_point++;
                    }
                }
                check_box_state_position_point++;
            }
            /*int position_point = 0;
            for(Object xmlColumnNameValuee : targetDbColumnName) {
                xmlColumn = document.createElement("Column");
                excelTable.appendChild(xmlColumn);
                xmlColumn.setAttribute("columnName",xmlColumnNameValuee.toString());
                *//**//*
                int inner_position_point = 0;
                for(Object ExcelValue : targetExcelColumnName) {

                    if (position_point == inner_position_point){
                        Element xmlExcelValue = document.createElement("ExcelValue");
                        xmlExcelValue.appendChild(document.createTextNode(ExcelValue.toString()));
                        xmlColumn.appendChild(xmlExcelValue);
                    }

                    inner_position_point++;
                }
                *//**//*
                position_point++;
            }*/

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(document);
            StreamResult streamResult = new StreamResult(new File(xmlFileLocation));

            transformer.transform(source,streamResult);

            System.out.println("File Created And Saved");

            sqlValues.setSqlXmlLocation(xmlFileLocation);

        } catch (ParserConfigurationException pce){
            pce.printStackTrace();
        } catch (TransformerException tfe){
            tfe.printStackTrace();
        }

        return sqlValues;
    }
}
