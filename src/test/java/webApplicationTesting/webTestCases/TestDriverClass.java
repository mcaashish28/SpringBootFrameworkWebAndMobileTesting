package webApplicationTesting.webTestCases;

import com.springproject.excelOperations.ExcelGenericFunctions;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.TestNG;
import org.testng.annotations.Test;

import java.io.FileInputStream;
import java.io.FileOutputStream;

public class TestDriverClass {
    // Excel file path and sheet name

//    public static Logger log = LogManager.getLogger(TestDriverClass.class);

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

    private TestNG testScript;

    @Test
    public void ReadExcelDriverAndRunScript(){

        try{
            String ExcelSheetPath = "C:\\GitHubProjects\\springbootproject\\src\\TestData\\ExcelData\\TestData.xlsx";
            String ExcelSheetName = "TC_Details";
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

                        TargetColIndex = ExcelGenericFunctions.getCellIndexFromColName(rowObj, "Execute_Flag");
                        Execute_Flag = ExcelGenericFunctions.getCellValue(ExcelSheet, rownum, TargetColIndex);
                        System.out.println("Execute_Flag value is : " + Execute_Flag);

                        TargetColIndex = ExcelGenericFunctions.getCellIndexFromColName(rowObj, "AutomationScriptName");
                        AutomationScriptName = ExcelGenericFunctions.getCellValue(ExcelSheet, rownum, TargetColIndex);
                        System.out.println("AutomationScriptName is : " + AutomationScriptName);
                        testScript = new TestNG();
                        if (Execute_Flag.equals("Yes")){
                            if(AutomationScriptName.equals("GoogleLaunchURLTest")){
                                testScript.setTestClasses(new Class[] { GoogleLaunchURLTest.class });
                                System.out.println("Running GoogleLaunchURLTest Script");
                              //  log.info("Running GoogleLaunchURLTest Script.");
                            }else if(AutomationScriptName.equals("ManulifeLaunchURLTest")){
                                testScript.setTestClasses(new Class[] { ManulifeLaunchURLTest.class });
                                System.out.println("Running ManulifeLaunchURLTest Script");
                              //  log.info("Running ManulifeLaunchURLTest Script.");
                            }
                            testScript.run();
                        }

                        break;  // exit while loop

                    }   // enf of while loop

                }   // end of for loop
                fis.close();



            }   // end of try block

        }catch(Exception e){
            System.out.println("Exception in Running Script is : " + e.getMessage());
        }

    }   // end of function



}
