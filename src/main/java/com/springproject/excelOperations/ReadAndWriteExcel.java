package com.springproject.excelOperations;

import org.apache.logging.log4j.core.tools.picocli.CommandLine;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.io.FileOutputStream;

@Component
public class ReadAndWriteExcel {


    // Apache poi object declaration
    XSSFWorkbook Excelworkbook;
    XSSFSheet ExcelSheet;
    XSSFRow rowObj;
    Cell cellObj = null;
    FileInputStream fis;
    FileOutputStream fout;


    // sheet Column name (parameter) declaration
    private String TestCaseID;
    private String TestCaseName;
    private String Execute_Flag;
    private String AutomationScriptName;
    private String Remarks;
    boolean writeToExcelFlag = false;

    private int StartIndexCol = 0;
    private int TargetColIndex;

    // main fucntion
/*
    public static void main(String[] args){
        ReadAndWriteExcel rw = new ReadAndWriteExcel();
        String ClientSelectedScriptID = "testcase2";
        rw.ReadAndWriteExcel(ExcelSheetPath, ExcelSheetName, ClientSelectedScriptID);
    }
*/

    public void ReadAndWriteExcel(String ClientSelectedScriptID){
        // Excel file path and sheet name
       String ExcelSheetPath = "./src/TestData/ExcelData/TestData.xlsx";
       String ExcelSheetName = "TC_Details";
        try{
            fis = new FileInputStream(ExcelSheetPath);
            Excelworkbook = new XSSFWorkbook(fis);
            ExcelSheet = Excelworkbook.getSheet(ExcelSheetName);
            if(ExcelSheet !=null){
                int RowCount = ExcelSheet.getPhysicalNumberOfRows();
                rowObj = ExcelSheet.getRow(0);

                System.out.println("Total number of rows in ExcelSheet : " + RowCount);
                System.out.println("Total number of Columns in rowObj : " + rowObj.getLastCellNum());

                for (int rownum = 1; rownum<RowCount; rownum++){
                    boolean excelParseFlag = true;
                    while(excelParseFlag==true){
                        // Read from Excel Sheet

                        TargetColIndex = ExcelGenericFunctions.getCellIndexFromColName(rowObj, "TestCaseID");
                        TestCaseID = ExcelGenericFunctions.getCellValue(ExcelSheet, rownum, TargetColIndex);
                        System.out.println("Test Case ID value is : " + TestCaseID);

                        TargetColIndex = ExcelGenericFunctions.getCellIndexFromColName(rowObj, "TestCaseName");
                        TestCaseName = ExcelGenericFunctions.getCellValue(ExcelSheet, rownum, TargetColIndex);
                        System.out.println("Test Case Name is : " + TestCaseName);

                        // Write into Excel Sheet
                        if(TestCaseName.equals(ClientSelectedScriptID)){
                            TargetColIndex = ExcelGenericFunctions.getCellIndexFromColName(rowObj, "Execute_Flag");
                            ExcelGenericFunctions.writeToExcelSheet(ExcelSheet, rownum, TargetColIndex, "Yes");

                            TargetColIndex = ExcelGenericFunctions.getCellIndexFromColName(rowObj, "Remarks");
                            ExcelGenericFunctions.writeToExcelSheet(ExcelSheet, rownum, TargetColIndex, "Execute this Script.");
                            writeToExcelFlag = true;
                        }else{
                            TargetColIndex = ExcelGenericFunctions.getCellIndexFromColName(rowObj, "Execute_Flag");
                            ExcelGenericFunctions.writeToExcelSheet(ExcelSheet, rownum, TargetColIndex, "No");

                            TargetColIndex = ExcelGenericFunctions.getCellIndexFromColName(rowObj, "Remarks");
                            ExcelGenericFunctions.writeToExcelSheet(ExcelSheet, rownum, TargetColIndex, "Do Not Execute this Script.");
                            writeToExcelFlag = true;
                        }


                        if(writeToExcelFlag==true){
                            fout = new FileOutputStream(ExcelSheetPath);
                            Excelworkbook.write(fout);
                            fout.close();
                            writeToExcelFlag = false;
                        }
                        break;  // exit while loop


                    }   // enf of while loop

                }   // end of for loop




            }   // end of try block

        }catch(Exception e){
            System.out.println("Exception in Read Write Operation of Excel : " + e.getMessage());
        }

    }   // end of function


}// end of class
