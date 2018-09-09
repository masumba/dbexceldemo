package com.example.excel.db.dbexceldemo.Controller;


import com.example.excel.db.dbexceldemo.Modal.ExcelReader;
import com.example.excel.db.dbexceldemo.Modal.MyClass;
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
    MyClass myClass;

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

        return homepage;
    }

    @RequestMapping("/records_list")
    public ModelAndView recordList(){
        ModelAndView recordslist = new ModelAndView("ajax_records");
        recordslist.addObject("listrecords",excelReaderRepository.findAll());
        return recordslist;
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ModelAndView doFileUpload(@RequestParam("file")MultipartFile file){
        ModelAndView mv = new ModelAndView("redirect:/");

        try{
            storageService.store(file);
            uploadfiles.add(file.getOriginalFilename());

            String DirFileLocation = "upload-dir/"+file.getOriginalFilename();

            FileInputStream excelFile = new FileInputStream(new File(DirFileLocation));
            XSSFWorkbook workbook = new XSSFWorkbook(excelFile);
            XSSFSheet sheet = workbook.getSheetAt(0);

            Row row;

            row = (Row)sheet.getRow(0);

            /*Pick Relevant Columns*/
            Iterator<Row> iterator = sheet.iterator();

            int target_Name_CellNum = 0;
            int target_Address_CellNum = 0;

            int intcount = 0;
            while (iterator.hasNext() && intcount < 1){
                Row currentRow = iterator.next();
                Iterator<Cell> cellIterator = currentRow.iterator();

                while(cellIterator.hasNext()){
                    Cell currentCell = cellIterator.next();

                    currentCell.getRow();

                    myClass = xmlReader.display("ExcelFileReadConfig.xml");

                    System.out.println(currentCell.getStringCellValue());
                    String targetCellName = currentCell.getStringCellValue();
                    int targetCellNum = currentCell.getColumnIndex();

                    String name_xml_feild =  myClass.getXml_name_feild();
                    String address_xml_feild =  myClass.getXml_address_feild();

                    if (targetCellName.equals(name_xml_feild)) {
                        System.out.println("NameFeild = "+targetCellName+" #"+targetCellNum+" = "+name_xml_feild);
                        target_Name_CellNum = targetCellNum;
                    } else if (targetCellName.equals(address_xml_feild)){
                        System.out.println("AddressFeild = "+targetCellName+" #"+targetCellNum+" = "+address_xml_feild);
                        target_Address_CellNum = targetCellNum;
                    }
                }

                intcount++;
            }
            /**/

            System.out.println("Name "+target_Name_CellNum);
            System.out.println("Address "+target_Address_CellNum);
            /*Pull From Relevant Columns*/
            Row row1;

            for (int i=1; i<=sheet.getLastRowNum();i++){
                row1 = (Row)sheet.getRow(i);



                String farmer_name;
                if (row1.getCell(target_Name_CellNum) == null){
                    farmer_name = null;
                } else {
                    farmer_name = row1.getCell(target_Name_CellNum).toString();
                }

                String farmer_address;
                if (row1.getCell(target_Address_CellNum) == null){
                    farmer_address = null;
                } else {
                    farmer_address = row1.getCell(target_Address_CellNum).toString();
                }

                ExcelReader eReader = new ExcelReader();
                eReader.setName(farmer_name);
                eReader.setAddress(farmer_address);
                excelReaderRepository.save(eReader);
            }
            excelFile.close();

        } catch (Exception e){
            e.printStackTrace();
        }

        return mv;
    }


}
