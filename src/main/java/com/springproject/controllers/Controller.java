package com.springproject.controllers;

import com.springproject.excelOperations.ReadAndWriteExcel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@org.springframework.stereotype.Controller
public class Controller {
    @Autowired
    ReadAndWriteExcel rwExcel;

    @RequestMapping(value="/", method= RequestMethod.GET)
    public String homePage(){
        return "index";
    }

    @RequestMapping(value="/", method=RequestMethod.POST)
    public String onSubmit(@RequestParam String testcase1){
        System.out.println("User selected value is : " + testcase1);

        rwExcel.ReadAndWriteExcel(testcase1);
        return "post";
    }


}
