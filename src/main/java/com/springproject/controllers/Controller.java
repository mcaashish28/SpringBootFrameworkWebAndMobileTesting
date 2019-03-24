package com.springproject.controllers;

import com.springproject.excelOperations.ReadAndWriteExcel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.testng.TestNG;

import java.io.File;
import java.io.IOException;

@org.springframework.stereotype.Controller
public class Controller {
    @Autowired
    ReadAndWriteExcel rwExcel;



    @RequestMapping(value="/", method= RequestMethod.GET)
    public String homePage(){
        return "index";
    }

    @RequestMapping(value="/", method=RequestMethod.POST)
    public String onSubmit(@RequestParam String testcase1) throws IOException {
        System.out.println("User selected value is : " + testcase1);
        // write User request into Excel
        rwExcel.ReadAndWriteExcel(testcase1);

        // Run maven project from command line
        File dir = new File("C:\\GitHubProjects\\springbootproject");
        ProcessBuilder pb = new ProcessBuilder("cmd.exe", "/C", "Start","BatchRun.bat");
        pb.directory(dir);
        Process p = pb.start();



        TestNG runTestScript = new TestNG();
        //runTestScript.setTestClasses(new Class[] {TestTestDriverClass.class});
        //runTestScript.run();*/

        return "post";
    }

    public void runTestScript(){

    }


}
