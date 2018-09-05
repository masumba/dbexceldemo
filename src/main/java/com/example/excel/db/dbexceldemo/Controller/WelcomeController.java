package com.example.excel.db.dbexceldemo.Controller;


import com.example.excel.db.dbexceldemo.Modal.ExcelDbApp;
import com.example.excel.db.dbexceldemo.Repository.ExcelDbAppRepository;
import com.example.excel.db.dbexceldemo.Service.StorageService;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class WelcomeController {

    @Autowired
    ExcelDbAppRepository appRepo;

    @Autowired
    StorageService storageService;

    List<String> uploadfiles = new ArrayList<String>();



    @RequestMapping("/")
    public ModelAndView doHome(){
        ModelAndView homepage = new ModelAndView("index");
        homepage.addObject("lists",appRepo.findAll());
        return homepage;
    }

    @PostMapping("/")
    public String handleFileUpload(@RequestParam("file")MultipartFile file, Model model){
        try {
            storageService.store(file);
            uploadfiles.add(file.getOriginalFilename());

            model.addAttribute("message","Your Upload Was Successfull of "+file.getOriginalFilename());
            /*Reading of excel File and writing to DB Start*/
            String DirFileLocation = "upload-dir/"+file.getOriginalFilename();

            System.out.println(DirFileLocation);
            FileInputStream excelfile = new FileInputStream(new File(DirFileLocation));
            XSSFWorkbook workbook = new XSSFWorkbook(excelfile);
            XSSFSheet sheet = workbook.getSheetAt(0);

            Row row;

            for (int i=1; i<=sheet.getLastRowNum(); i++){
                row = (Row)sheet.getRow(i);

                String name;
                if (row.getCell(1) == null){
                    name = "null";
                } else {
                    name = row.getCell(1).toString();
                }

                String address;
                if (row.getCell(2) == null){
                    address = "null";
                } else {
                    address = row.getCell(2).toString();
                }

                ExcelDbApp std = new ExcelDbApp();
                std.setName(name);
                std.setAddress(address);
                appRepo.save(std);
            }
            excelfile.close();
            /*Reading of excel File and writing to DB End*/

        } catch (Exception e){
            model.addAttribute("message","Upload Failed of "+file.getOriginalFilename());
            e.printStackTrace();
        }
        return "index";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ModelAndView doSave(@RequestParam(value = "id",defaultValue = "") String id, @RequestParam("name") String name, @RequestParam("address") String address){
        ModelAndView mv = new ModelAndView("redirect:/");
        ExcelDbApp users;
        if (!id.isEmpty()){
            users = (ExcelDbApp)appRepo.findOne(Integer.parseInt(id));
        } else {
            users = new ExcelDbApp();
        }

        users.setName(name);
        users.setAddress(address);
        appRepo.save(users);
        return mv;
    }

    @RequestMapping(value = "/view/{id}", method = RequestMethod.GET)
    public ModelAndView doView(@PathVariable("id") int id){
        ModelAndView mv = new ModelAndView("view");
        mv.addObject("lists",appRepo.findOne(id));
        return mv;
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public ModelAndView doDelete(@PathVariable("id") int id){
        ModelAndView mv = new ModelAndView("redirect:/");
        appRepo.delete(id);
        return mv;
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public ModelAndView doEdit(@PathVariable("id") int id){
        ModelAndView mv = new ModelAndView("edit");
        mv.addObject("lists",appRepo.findOne(id));
        return mv;
    }


    /*Excel Reader Start*/

    public void ExcelFileReader(String DirFileLocation) throws IOException {

        FileInputStream file = new FileInputStream(new File(DirFileLocation));
        XSSFWorkbook workbook = new XSSFWorkbook(file);
        XSSFSheet sheet = workbook.getSheetAt(0);

        Row row;

        for (int i=1; i<=sheet.getLastRowNum(); i++){
            row = (Row)sheet.getRow(i);

            String name;
            if (row.getCell(1) == null){
                name = "null";
            } else {
                name = row.getCell(1).toString();
            }

            String address;
            if (row.getCell(2) == null){
                address = "null";
            } else {
                address = row.getCell(2).toString();
            }

            String excelid = null;
            String excelname = name;
            String excelAddress = address;
            doSave(excelid,excelname,excelAddress);
        }
        file.close();

    }
    /*Excel Reader End*/
}
