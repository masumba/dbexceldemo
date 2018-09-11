package com.example.excel.db.dbexceldemo.Controller;


import com.example.excel.db.dbexceldemo.Modal.ExcelReader;
import com.example.excel.db.dbexceldemo.Modal.SqlValues;
import com.example.excel.db.dbexceldemo.Modal.XMLReader;
import com.example.excel.db.dbexceldemo.Repository.ExcelReaderRepository;
import com.example.excel.db.dbexceldemo.Services.StorageService;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.FileInputStream;
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
    ExcelReaderRepository excelReaderRepository;

    @Autowired
    StorageService storageService;

    List<String> uploadfiles = new ArrayList<String>();

    @RequestMapping("/")
    public ModelAndView doHome(){
        ModelAndView homepage = new ModelAndView("index");
        homepage.addObject("lists",excelReaderRepository.findAll());
        /**/
        sqlValues = xmlReader.display("ExcelFileReadConfig.xml");
        //System.out.println(sqlValues.getSqlTableName()+"/"+sqlValues.getSqlColumns()+"\n"+sqlValues.getSqlExcelColumnArray());
        /**/
        return homepage;
    }

    @RequestMapping("/records_list")
    public ModelAndView recordList(){
        ModelAndView recordslist = new ModelAndView("ajax_records");
        recordslist.addObject("listrecords",excelReaderRepository.findAll());
        return recordslist;
    }

    @RequestMapping(value = "/",method = RequestMethod.POST)
    public ModelAndView doFileUpload(@RequestParam("file")MultipartFile file){
        ModelAndView mv = new ModelAndView("redirect:/");

        ArrayList<Object> targetCellNums = new ArrayList<Object>();
        ArrayList<Object> targetCellNames = new ArrayList<Object>();
        ArrayList<Object> targetSqlScript = new ArrayList<Object>();
        StringBuilder sqlExcelValues = new StringBuilder();

        try {
            storageService.store(file);
            uploadfiles.add(file.getOriginalFilename());

            sqlValues = xmlReader.display("ExcelFileReadConfig.xml");

            String DirFilelocation = "upload-dir/"+file.getOriginalFilename();

            FileInputStream excelFile = new FileInputStream(new File(DirFilelocation));
            XSSFWorkbook xssfWorkbook = new XSSFWorkbook(excelFile);
            XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(0);

            /**/
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
                        }
                    }
                    //System.out.println("Target Cell Column Number "+targetCellNums);

                }
                intcounter++;
            }
            /**/
            /**/
            Row row1;

            for (int i=1; i<=xssfSheet.getLastRowNum();i++){

                row1 = (Row)xssfSheet.getRow(i);

                Iterator iterator1 = targetCellNums.iterator();
                //System.out.println("Arr No"+targetCellNums+" IT:"+i);

                StringBuilder strbuild = new StringBuilder();
                strbuild.append("(");
                int strcount = 0;
                while (iterator1.hasNext()){

                    if (strcount>0){
                        strbuild.append(",");
                    }

                    Integer testNum = (Integer) iterator1.next();
                    //System.out.println("NUM"+testNum);

                    Integer rowNumValue = (Integer)testNum;
                    if (row1.getCell(rowNumValue) == null){
                        //System.out.println(row1.getCell(rowNumValue).toString());
                        strbuild.append("null");
                    } else {
                        //System.out.println(row1.getCell(rowNumValue).toString());
                        strbuild.append(row1.getCell(rowNumValue).toString());
                    }
                    strcount++;
                }
                strbuild.append(")");

                targetSqlScript.add(strbuild);

                //System.out.println(strbuild);
                /***/

            }


            //System.out.println("INSERT INTO "+sqlValues.getSqlTableName()+" "+sqlValues.getSqlColumns()+" VALUES ");
            //System.out.println(targetSqlScript);

            System.out.println("INSERT SQL SCRIPTS");

            int targetNumCount = 1;
            for (Object obj: targetSqlScript){
                System.out.println(targetNumCount+": "+"INSERT INTO "+sqlValues.getSqlTableName()+" "+sqlValues.getSqlColumns()+" VALUES "+obj+";");

                targetNumCount++;
                /**/
                //I would like to be able to execute the raw queries generated within this loop here if possible.
                //The sout statement in this loop generates insert scripts
                //to get a look at how the scripts appear upload the fisp excel file or any excel file
                //in order to get out put if you are using an alternate excel file make required changes to xml file
                /**/
            }

            /**/
        } catch (Exception e){
            e.printStackTrace();
        }

        return mv;
    }


}
