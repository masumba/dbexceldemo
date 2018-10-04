package com.example.excel.db.dbexceldemo.Controller;

import com.example.excel.db.dbexceldemo.Configurations.SqlValues;
import com.example.excel.db.dbexceldemo.Repository.ExcelReaderRepository;
import com.example.excel.db.dbexceldemo.Services.StorageService;
import com.example.excel.db.dbexceldemo.Services.XMLReader;
import com.example.excel.db.dbexceldemo.Services.XMLWriter;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.hibernate.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.FileInputStream;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Controller
public class WelcomeController {

    @Autowired
    SqlValues sqlValues;

    @Autowired
    XMLReader xmlReader;

    @Autowired
    XMLWriter xmlWriter;

    @Autowired
    ExcelReaderRepository excelReaderRepository;

    @Autowired
    StorageService storageService;

    List<String> uploodfiles = new ArrayList<String>();

    @Autowired
    private SessionFactory sessionFactory;

    private int results;

    /**/
    @RequestMapping("/")
    public ModelAndView doHome(){
        ModelAndView homepage = new ModelAndView("index");
        homepage.addObject("lists",excelReaderRepository.findAll());
        return homepage;
    }

    @RequestMapping("/records_list")
    public ModelAndView recordList(){
        ModelAndView recordslist = new ModelAndView("ajax_records");
        recordslist.addObject("listrecords",excelReaderRepository.findAll());
        return recordslist;
    }

    @RequestMapping(value = "/",method = RequestMethod.POST)
    public ModelAndView doFormCollect(@RequestParam("clmnName") String clmnName,@RequestParam("ExcelValue") List<String> ExcelValue,@RequestParam(value = "my-checkbox", defaultValue = "off") List<String> optionCheckbox,@RequestParam("columnName") List<String> xmlColumnName,@RequestParam("file")MultipartFile file){
        ModelAndView mv = new ModelAndView("redirect:/");

        System.out.println(clmnName);
        System.out.println(ExcelValue);
        System.out.println(optionCheckbox);
        System.out.println(xmlColumnName);

        sqlValues = xmlWriter.xmlCreate(clmnName,ExcelValue,optionCheckbox,xmlColumnName);

        String xmlLocation = sqlValues.getSqlXmlLocation();

        ArrayList<Object> targetCellNums = new ArrayList<Object>();
        ArrayList<Object> targetCellNames = new ArrayList<Object>();
        ArrayList<Object> targetSqlScript = new ArrayList<Object>();
        StringBuilder sqlExcelValues = new StringBuilder();

        Session session;
        try {
            session = sessionFactory.getCurrentSession();
        } catch (HibernateException e){
            session = sessionFactory.openSession();
        }

        try {
            storageService.store(file);
            uploodfiles.add(file.getOriginalFilename());
            sqlValues = xmlReader.display(xmlLocation);
            String DirFilelocation = "upload-dir/"+file.getOriginalFilename();
            FileInputStream excelFile = new FileInputStream(new File(DirFilelocation));
            XSSFWorkbook xssfWorkbook = new XSSFWorkbook(excelFile);
            XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(0);

            Row row;
            row = (Row)xssfSheet.getRow(0);
            Iterator<Row> iterator = xssfSheet.iterator();

            int intcounter = 0;
            while (iterator.hasNext() && intcounter<1){
                Row currentRow = iterator.next();
                Iterator<Cell> cellIterator = currentRow.iterator();

                while (cellIterator.hasNext()){
                    Cell currentCell = cellIterator.next();
                    currentCell.getRow();

                    for (Object obj:sqlValues.getSqlExcelColumnArray()){
                        if (obj.getClass() == String.class){
                            if (obj.equals(currentCell.getStringCellValue())){
                                targetCellNums.add(currentCell.getColumnIndex());
                            }
                        } else if (obj.getClass() == Integer.class){
                            if (obj.equals(currentCell.getNumericCellValue())){
                                targetCellNums.add(currentCell.getColumnIndex());
                            }
                        }
                    }
                }
                intcounter++;
            }
            /**/
            Row row2;
            for (int i=1;i<=1;i++){
                row2 = (Row)xssfSheet.getRow(0);
                Iterator iterator2 = targetCellNums.iterator();
                int int_counter = 0;
                while (iterator2.hasNext()){
                    Integer columnNumValue = (Integer)iterator2.next();

                    if (row2.getCell(columnNumValue) == null){
                        targetCellNames.add("Null");
                    } else {
                        targetCellNames.add(row2.getCell(columnNumValue).toString());
                    }
                    int_counter++;

                }
            }



            //System.out.println("testing = "+targetCellNames);
            sqlValues.setSqlExcelFileColumnOrder(targetCellNames);

            sqlValues = xmlReader.DB_Columns(xmlLocation);


            /**/

            Row row1;
            for (int i=1; i<=xssfSheet.getLastRowNum();i++){
                row1 = (Row)xssfSheet.getRow(i);
                Iterator iterator1 = targetCellNums.iterator();
                StringBuilder strBuild = new StringBuilder();
                strBuild.append("(\"");
                int strcount = 0;
                while (iterator1.hasNext()){
                    if (strcount>0){
                        strBuild.append("\",\"");
                    }
                    Integer testNum = (Integer)iterator1.next();

                    Integer rowNumValue = (Integer)testNum;
                    if (row1.getCell(rowNumValue) == null){
                        strBuild.append("null");
                    } else {
                        strBuild.append(row1.getCell(rowNumValue).toString());
                    }
                    strcount++;
                }
                strBuild.append("\")");
                targetSqlScript.add(strBuild);
            }

            int targetNumCount = 1;
            for (Object obj: targetSqlScript){
                String sql = "INSERT INTO "+sqlValues.getSqlTableName()+" "+sqlValues.getSqlColumns()+" VALUES "+obj;


                System.out.println("---Test---SQL :"+sql);
                targetNumCount++;

                try {
                    SQLQuery query = session.createSQLQuery(sql);
                    query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);

                    results = query.executeUpdate();
                } catch (Throwable ex){
                    throw new ExceptionInInitializerError(ex);
                }
            }

        } catch (Exception e){
            e.printStackTrace();
        }

        return mv;
    }

    @RequestMapping(value = "/test",method = RequestMethod.POST)
    public ModelAndView doFileUpload(@RequestParam("file")MultipartFile file){
        ModelAndView mv = new ModelAndView("redirect:/");

        ArrayList<Object> targetCellNumsArray = new ArrayList<Object>();
        ArrayList<Object> targetCellNamesArray = new ArrayList<Object>();
        ArrayList<Object> targetSqlScripts = new ArrayList<Object>();
        StringBuilder sqlExcelValues = new StringBuilder();

        Session session;

        try {
            session = sessionFactory.getCurrentSession();
        } catch (HibernateException e){
            session = sessionFactory.openSession();
        }

        try {
            storageService.store(file);
            uploodfiles.add(file.getOriginalFilename());
            sqlValues = xmlReader.display("ExcelFileReadConfig.xml");
            String DirFilelocation = "upload-dir/"+file.getOriginalFilename();
            FileInputStream excelFile = new FileInputStream(new File(DirFilelocation));
            XSSFWorkbook xssfWorkbook = new XSSFWorkbook(excelFile);
            XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(0);

            /**/
            Row row;
            row = (Row)xssfSheet.getRow(0);
            Iterator<Row> iterator = xssfSheet.iterator();
            int int_counter = 0;

            while (iterator.hasNext() && int_counter<1){
                Row currentRow = iterator.next();
                Iterator<Cell> cellIterator = currentRow.iterator();

                while (cellIterator.hasNext()){
                    Cell currentCell = cellIterator.next();

                    currentCell.getRow();

                    for (Object obj:sqlValues.getSqlExcelColumnArray()){
                        if (obj.getClass()==String.class){
                            if (obj.equals(currentCell.getStringCellValue())){
                                targetCellNamesArray.add(currentCell.getColumnIndex());
                            }
                        }
                    }
                }
                int_counter++;
            }
            /**/

            /**/
            Row row1;
            for (int i=1;i<xssfSheet.getLastRowNum();i++){
                row1 = (Row)xssfSheet.getRow(i);
                Iterator iterator1 = targetCellNumsArray.iterator();
                StringBuilder strBuilder = new StringBuilder();

                strBuilder.append("\"");
                int str_count = 0;
                while (iterator1.hasNext()){
                    /*Will Have to look further here Possible Bug*/
                    if (str_count>0){
                        strBuilder.append("\",\"");
                    }

                    Integer testNum = (Integer)iterator1.next();
                    Integer rowNumValue = (Integer)testNum;
                    if (row1.getCell(rowNumValue)==null){
                        strBuilder.append("null");
                    } else{
                        strBuilder.append(row1.getCell(rowNumValue).toString());
                    }
                    str_count++;
                }
                strBuilder.append("\")");
                targetSqlScripts.add(strBuilder);
            }
            int targetNumCount = 1;
            for (Object obj:targetSqlScripts){
                String sql = "INSERT INTO "+sqlValues.getSqlTableName()+" "+sqlValues.getSqlColumns()+" VALUES "+obj;

                targetNumCount++;
                try{
                    SQLQuery query = session.createSQLQuery(sql);
                    query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
                    results = query.executeUpdate();
                } catch (Throwable ex){
                    throw new ExceptionInInitializerError(ex);
                }
            }
            /**/

        } catch (Exception e){
            e.printStackTrace();
        }

        return mv;
    }
}
