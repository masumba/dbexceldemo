package com.example.excel.db.dbexceldemo.Modal;

import org.springframework.context.annotation.Configuration;

import javax.persistence.Entity;

@Configuration
public class MyClass {
    private String xml_name_feild;
    private String xml_address_feild;

    public MyClass() {
    }

    public String getXml_name_feild() {
        return xml_name_feild;
    }

    public void setXml_name_feild(String xml_name_feild) {
        this.xml_name_feild = xml_name_feild;
    }

    public String getXml_address_feild() {
        return xml_address_feild;
    }

    public void setXml_address_feild(String xml_address_feild) {
        this.xml_address_feild = xml_address_feild;
    }
}
