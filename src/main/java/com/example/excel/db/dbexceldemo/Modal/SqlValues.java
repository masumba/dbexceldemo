package com.example.excel.db.dbexceldemo.Modal;

import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;

@Configuration
public class SqlValues {

    private ArrayList<Object> sqlValuesArray;
    private ArrayList<Object> sqlExcelColumnArray;
    private ArrayList<String> columns;

    private StringBuilder sqlValues;
    private StringBuilder sqlColumns;
    private String sqlTableName;
    private String sqlStatmentString;

    public SqlValues() {

        sqlExcelColumnArray = new ArrayList<>();
        sqlValuesArray = new ArrayList<>();
        sqlColumns = new StringBuilder();
        sqlValues = new StringBuilder();

    }

    public ArrayList<Object> getSqlValuesArray() {
        return sqlValuesArray;
    }

    public void setSqlValuesArray(ArrayList<Object> sqlValuesArray) {
        this.sqlValuesArray = sqlValuesArray;
    }

    public ArrayList<Object> getSqlExcelColumnArray() {
        return sqlExcelColumnArray;
    }

    public void setSqlExcelColumnArray(ArrayList<Object> sqlExcelColumnArray) {
        this.sqlExcelColumnArray = sqlExcelColumnArray;
    }

    public StringBuilder getSqlValues() {
        return sqlValues;
    }

    public void setSqlValues(StringBuilder sqlValues) {
        this.sqlValues = sqlValues;
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

    public String getSqlStatmentString() {
        return sqlStatmentString;
    }

    public void setSqlStatmentString(String sqlStatmentString) {
        this.sqlStatmentString = sqlStatmentString;
    }

    @Override
    public String toString() {
        return "SqlValues{" +
                "sqlValuesArray=" + sqlValuesArray +
                ", sqlExcelColumnArray=" + sqlExcelColumnArray +
                ", sqlValues=" + sqlValues +
                ", sqlColumns=" + sqlColumns +
                ", sqlTableName='" + sqlTableName + '\'' +
                ", sqlStatmentString='" + sqlStatmentString + '\'' +
                '}';
    }


    public String createJsonObject(){
        Object object;

        return "";
    }
}
