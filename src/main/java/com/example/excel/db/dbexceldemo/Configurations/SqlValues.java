package com.example.excel.db.dbexceldemo.Configurations;

import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;

@Configuration
public class SqlValues {

    private ArrayList<Object> sqlExcelColumnArray;

    private ArrayList<Object> sqlExcelFileColumnOrder;

    private ArrayList<Object> sqlColumnMap;

    private StringBuilder sqlColumns;

    private String sqlTableName;

    private String sqlXmlLocation;

    public SqlValues() {

        sqlColumns = new StringBuilder();
        sqlExcelColumnArray = new ArrayList<>();
        sqlColumnMap = new ArrayList<>();
        sqlExcelFileColumnOrder = new ArrayList<>();

    }

    public ArrayList<Object> getSqlExcelColumnArray() {
        return sqlExcelColumnArray;
    }

    public void setSqlExcelColumnArray(ArrayList<Object> sqlExcelColumnArray) {
        this.sqlExcelColumnArray = sqlExcelColumnArray;
    }

    public ArrayList<Object> getSqlExcelFileColumnOrder() {
        return sqlExcelFileColumnOrder;
    }

    public void setSqlExcelFileColumnOrder(ArrayList<Object> sqlExcelFileColumnOrder) {
        this.sqlExcelFileColumnOrder = sqlExcelFileColumnOrder;
    }

    public ArrayList<Object> getSqlColumnMap() {
        return sqlColumnMap;
    }

    public void setSqlColumnMap(ArrayList<Object> sqlColumnMap) {
        this.sqlColumnMap = sqlColumnMap;
    }

    public StringBuilder getSqlColumns() {
        return sqlColumns;
    }

    public void setSqlColumns(StringBuilder sqlColumns) {
        this.sqlColumns = sqlColumns;
    }

    public String getSqlTableName() {
        return sqlTableName;
    }

    public void setSqlTableName(String sqlTableName) {
        this.sqlTableName = sqlTableName;
    }

    public String getSqlXmlLocation() {
        return sqlXmlLocation;
    }

    public void setSqlXmlLocation(String sqlXmlLocation) {
        this.sqlXmlLocation = sqlXmlLocation;
    }

    @Override
    public String toString() {
        return "SqlValues{" +
                "sqlExcelColumnArray=" + sqlExcelColumnArray +
                ", sqlExcelFileColumnOrder=" + sqlExcelFileColumnOrder +
                ", sqlColumnMap=" + sqlColumnMap +
                ", sqlColumns=" + sqlColumns +
                ", sqlTableName='" + sqlTableName + '\'' +
                ", sqlXmlLocation='" + sqlXmlLocation + '\'' +
                '}';
    }
}
