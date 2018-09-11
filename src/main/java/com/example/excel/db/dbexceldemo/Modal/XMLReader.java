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
import java.util.ArrayList;

@Service
public class XMLReader {
    @Autowired
    SqlValues sqlValues = new SqlValues();

    public SqlValues display(String xmlFileName){
        String tableName = null;
        StringBuilder tableColumns = new StringBuilder();
        ArrayList<Object> excelColumnArray = new ArrayList<Object>();

        try{
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(xmlFileName);
            XPath xPath = XPathFactory.newInstance().newXPath();

            /*Gets Table Name*/
            NodeList tableNodeList = (NodeList)xPath.compile("//Table").evaluate(document, XPathConstants.NODESET);
            for (int i=0; i<tableNodeList.getLength(); i++){
                tableName = xPath.compile("./@tableName").evaluate(tableNodeList.item(i));
            }
            /**/

            /*Gets Database Columns*/
            tableColumns.append("(");
            NodeList columnNodeList = (NodeList)xPath.compile("//Column").evaluate(document, XPathConstants.NODESET);
            for (int i=0;i<columnNodeList.getLength();i++){
                if (i > 0 ){
                    tableColumns.append(",");
                }
                String columnName = xPath.compile("./@columnName").evaluate(columnNodeList.item(i));
                tableColumns.append(columnName);

                excelColumnArray.add(xPath.compile("./ExcelValue").evaluate(columnNodeList.item(i)));
            }
            tableColumns.append(")");

            sqlValues.setSqlColumns(tableColumns);
            sqlValues.setSqlTableName(tableName);
            sqlValues.setSqlExcelColumnArray(excelColumnArray);
            /**/

        } catch (Exception e){
            e.printStackTrace();
        }

        return sqlValues;
    }
}
