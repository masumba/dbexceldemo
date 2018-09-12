package com.example.excel.db.dbexceldemo.Controller;

import com.example.excel.db.dbexceldemo.Modal.Database;
import netscape.javascript.JSObject;
import org.apache.poi.ss.formula.functions.T;
import org.hibernate.*;
import org.hibernate.internal.SessionImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.util.*;

@RestController
public class DatabaseController {

    //{"Database":"information_schema"}

    @Autowired
    private SessionFactory sessionFactory;

    @RequestMapping("/databases")
    public List<String> getDatabases(){
        List<String> databaseNames = new ArrayList<>();
        String sqlStatement = "SHOW DATABASES";

        Session session;
        try {
            session = sessionFactory.getCurrentSession();
        } catch (HibernateException e) {
            session = sessionFactory.openSession();
        }

        SQLQuery query = session.createSQLQuery(sqlStatement);
        query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
        List list = query.list();
        System.out.println(list);
        for (int i = 0; i < list.size(); i++) {
            //System.out.println(list.get(i).toString());
            String str = list.get(i).toString();

            System.out.println(str);
            String stripped = str.replace("{Database=","");
            stripped = stripped.replace("}","");
            System.out.println(stripped);
            databaseNames.add(stripped);

        }

        return databaseNames;
    }

    @GetMapping("/tables-for/{databaseName}")
    public List<String> getTablesForDatabase(@PathVariable(value = "databaseName") String name){
        System.out.println("DatabaseController.getTablesForDatabase["+name+"]");
        List<String> tablesNames = new ArrayList<>();

        String sqlStatement = "SELECT TABLE_NAME\n" +
                "FROM INFORMATION_SCHEMA.TABLES\n" +
                "WHERE TABLE_TYPE = 'BASE TABLE' AND TABLE_SCHEMA='" + name +"'";

        Session session;
        try {
            session = sessionFactory.getCurrentSession();
        } catch (HibernateException e) {
            session = sessionFactory.openSession();
        }

        SQLQuery query = session.createSQLQuery(sqlStatement);
        query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
        List list = query.list();
        System.out.println(list);

        for (int i = 0; i < list.size(); i++) {
            //System.out.println(list.get(i).toString());
            String str = list.get(i).toString();

            System.out.println(str);
            String stripped = str.replace("{TABLE_NAME=","");
            stripped = stripped.replace("}","");
            System.out.println(stripped);
            tablesNames.add(stripped);

        }

        /*
        SELECT TABLE_NAME
FROM INFORMATION_SCHEMA.TABLES
WHERE TABLE_TYPE = 'BASE TABLE' AND TABLE_SCHEMA='ads-agro-pay';
         */
        return tablesNames;
    }

    @RequestMapping("/database")
    public List<String> doHome(){
        List<String> tableNames = new ArrayList<>();

        Session session;
        try {
            session = sessionFactory.getCurrentSession();
        } catch (HibernateException e) {
            session = sessionFactory.openSession();
        }

        try{
            SessionImpl sessionImpl = (SessionImpl) session;
            Connection connection = sessionImpl.connection();

            DatabaseMetaData databaseMetaData = connection.getMetaData();
            System.out.println(connection.getSchema());
            System.out.println(connection.getCatalog());

            ResultSet resultSet = databaseMetaData.getTables(null, null, null, new String[] {"TABLE"});
            while(resultSet.next()) {
                System.out.println(resultSet.getString(1));
                System.out.println(resultSet.getString(3));
                String tableName = resultSet.getString(3);
                System.out.println(tableName);
                tableNames.add(tableName);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        /*
        // Way3 - using Session
SessionImpl sessionImpl = (SessionImpl) sessionFactory.openSession();
try {
    Connection connection = sessionImpl.connection();
    DatabaseMetaData databaseMetaData = connection.getMetaData();
    ResultSet resultSet = databaseMetaData.getTables(null, null, null, new String[] {"TABLE"});
    while(resultSet.next()) {
        String tableName = resultSet.getString(3);
        System.out.println(tableName);
    }
} catch (Exception e) {
    e.printStackTrace();
}
         */
        return tableNames;
    }


}
