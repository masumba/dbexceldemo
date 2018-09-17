package com.example.excel.db.dbexceldemo.Controller;

import com.google.gson.Gson;
import org.hibernate.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@RestController
public class DatabaseController {

    @Autowired
    private SessionFactory sessionFactory;

    @RequestMapping(value = "/databases", method = RequestMethod.POST)
    @CrossOrigin
    public ModelAndView getDatabases(@RequestParam(value = "dblist",defaultValue = "") String dblist){
        ModelAndView databaseList = new ModelAndView("databaseOptions");
        ArrayList<Object> databaseNames = new ArrayList<Object>();
        String sqlStatement = "SHOW DATABASES";
        Session session;
        try {
            session = sessionFactory.getCurrentSession();
        } catch (HibernateException e){
            session = sessionFactory.openSession();
        }

        SQLQuery query = session.createSQLQuery(sqlStatement);
        query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
        List list = query.list();

        databaseList.addObject("dbLists",list);

        return databaseList;
    }

    @RequestMapping(value = "/tables-for", method = RequestMethod.POST)
    @CrossOrigin
    public ModelAndView getTables(@RequestParam(value = "tblName",defaultValue = "") String tblName){
        ModelAndView dbTables = new ModelAndView("tableOptions");
        ArrayList<Object> tableNames = new ArrayList<Object>();
        String sqlStatement = "SELECT TABLE_NAME FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_TYPE = 'BASE TABLE' AND TABLE_SCHEMA='"+tblName+"'";

        Session session;
        try {
            session = sessionFactory.getCurrentSession();
        } catch (HibernateException e){
            session = sessionFactory.openSession();
        }

        SQLQuery query = session.createSQLQuery(sqlStatement);
        query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
        List list = query.list();

        dbTables.addObject("dbTblLists",list);
        dbTables.addObject("dbTblListsName",tblName);


        return dbTables;
    }

    @RequestMapping(value = "/table-columns-for", method = RequestMethod.POST)
    @CrossOrigin
    public ModelAndView getColumns(@RequestParam(value = "clmnName",defaultValue = "") String clmnName){
        ModelAndView tblColumns = new ModelAndView("columnOptions");
        ArrayList<Object> columnNames = new ArrayList<Object>();
        String sqlStatement = "SHOW COLUMNS FROM "+clmnName;

        Session session;
        try {
            session = sessionFactory.getCurrentSession();
        } catch (HibernateException e){
            session = sessionFactory.openSession();
        }

        SQLQuery query = session.createSQLQuery(sqlStatement);
        query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
        List list = query.list();

        tblColumns.addObject("tblClmnLists",list);

        return tblColumns;
    }

}
