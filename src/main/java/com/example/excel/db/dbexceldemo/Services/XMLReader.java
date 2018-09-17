package com.example.excel.db.dbexceldemo.Services;

import com.example.excel.db.dbexceldemo.Configurations.SqlValues;
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

    public SqlValues display(String xmlFileLocationName){
        String tableName = null;
        StringBuilder tableColumns = new StringBuilder();
        ArrayList<Object> excelColumnArray = new ArrayList<Object>();
        ArrayList<Object> dbColumnMap = new ArrayList<Object>();

        try {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(xmlFileLocationName);
            XPath xPath = XPathFactory.newInstance().newXPath();

            /*Get The Table Name Specified In the XML File*/
            NodeList tableNodeList = (NodeList)xPath.compile("//Table").evaluate(document, XPathConstants.NODESET);
            for (int i = 0; i<tableNodeList.getLength(); i++){
                tableName = xPath.compile("./@tableName").evaluate(tableNodeList.item(i));

            }
            /**/

            /*Gets Database and Exce Document Column names Specified in the XML Document*/
            tableColumns.append("(");
            NodeList columnNodeList = (NodeList)xPath.compile("//Column").evaluate(document, XPathConstants.NODESET);
            for (int i=0;i<columnNodeList.getLength();i++){
                if (i>0){
                    tableColumns.append(",");
                }
                String dbColumnName = xPath.compile("./@columnName").evaluate(columnNodeList.item(i));
                tableColumns.append(dbColumnName);

                String excelColumnName = xPath.compile("./ExcelValue").evaluate(columnNodeList.item(i));
                excelColumnArray.add(excelColumnName);

                dbColumnMap.add(dbColumnName+" = "+ excelColumnName);
            }
            tableColumns.append(")");
            /**/

            sqlValues.setSqlColumns(tableColumns);
            sqlValues.setSqlTableName(tableName);
            sqlValues.setSqlColumnMap(dbColumnMap);
            sqlValues.setSqlExcelColumnArray(excelColumnArray);

        } catch (Exception e){
            e.printStackTrace();
        }

        return sqlValues;
    }

    public SqlValues DB_Columns(String xmlFileLocation){

        ArrayList<Object> newDB_Order = new ArrayList<Object>();
        ArrayList<Object> DB_List = new ArrayList<Object>();
        StringBuilder tableColumns = new StringBuilder();

        try {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(xmlFileLocation);
            XPath xPath = XPathFactory.newInstance().newXPath();

            NodeList columnNodeList = (NodeList)xPath.compile("//Column").evaluate(document, XPathConstants.NODESET);

            for (Object obj: sqlValues.getSqlExcelFileColumnOrder()){
                for (int i=0;i<columnNodeList.getLength();i++){
                    String dbColumn = xPath.compile("./@columnName").evaluate(columnNodeList.item(i));
                    String excelColumnName = xPath.compile("./ExcelValue").evaluate(columnNodeList.item(i));
                    if (obj.equals(excelColumnName)){
                        DB_List.add(dbColumn);
                    }
                }
            }

            int value_count = 0;
            tableColumns.append("(");
            for (Object obj: DB_List){
                if (value_count>0){
                    tableColumns.append(",");
                }
                tableColumns.append(obj);
                value_count++;
            }
            tableColumns.append(")");

            sqlValues.setSqlColumns(tableColumns);

        } catch (Exception e){
            e.printStackTrace();
        }

        return sqlValues;
    }
}
