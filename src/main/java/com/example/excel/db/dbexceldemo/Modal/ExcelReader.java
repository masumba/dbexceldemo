package com.example.excel.db.dbexceldemo.Modal;

import com.example.excel.db.dbexceldemo.Repository.ExcelDbAppRepository;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class ExcelReader {

    @Autowired
    ExcelDbAppRepository appRepo;
    public static void main(String args[]) throws IOException {
        /**/
        FileInputStream file = new FileInputStream(new File("upload-dir/data - Copy.xlsx"));
        XSSFWorkbook workbook = new XSSFWorkbook(file);
        XSSFSheet sheet = workbook.getSheetAt(0);

        Row row;

        for (int i=1; i<=sheet.getLastRowNum(); i++){
            row = (Row) sheet.getRow(i);

            String id;
            if (row.getCell(0) == null){
                id="0";
            } else {
                id=row.getCell(0).toString();
            }

            String name;
            if (row.getCell(1) == null){
                name="null";
            } else {
                name = row.getCell(1).toString();
            }

            String address;
            if (row.getCell(2) == null){
                address = "null";
            } else{
                address=row.getCell(2).toString();
            }

            ExcelDbApp std = new ExcelDbApp();
            std.setName(name);
            std.setAddress(address);
            /*appRepo.save(std);*/

            System.out.println(id+"***"+name+"***"+address);

        }

        file.close();
    }
}
