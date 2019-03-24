package Utilities;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.xml.crypto.Data;
import java.io.FileInputStream;

public class ExcelReusableFunctions {

    // common Excel variable:
    public String ExcelFilePath = "./src/TestData/SampleExcelFile.xlsx";
    public String Sheet_Name = "SampleSheet";

    XSSFWorkbook Dataworkboook;
    XSSFSheet DataSheet;
    FileInputStream inputworkbook;


    // Write into Excel Datasheet column at given row and col number
    public void writeToExcel(int rownum, int colnum, String dataValue){
        try{
            if(DataSheet!= null){
                if(rownum!=0){
                    Cell cell = null;
                    cell.setCellValue(dataValue);
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }       // end of function = writeToExcel

    // get column name based on col index for first row
    public String getColumnName(int colindex, XSSFSheet DataSheet){
        String colname = "";
        colname = DataSheet.getRow(0).getCell(colindex).getStringCellValue();
        return colname;
    }   // end of function =

    // get column Cell Index based on Column name
    public int getCellIndexBasedOnColumnName(Row row, String columnName){
        int colNumber = -1;      // -1 means, column name does not exist
        try{
            for(int i=0; i<row.getLastCellNum(); i++){
                if(row.getCell(i).getStringCellValue().trim().equals(columnName)){
                    colNumber = i;
                    break;
                }
            }

        }catch(Exception e){
            if(colNumber==-1){
                return colNumber;
            }
        }

        return colNumber;
    }       // end of function = getCellIndexBasedOnColumnName

    // get excel Cell value either numeric or string
    public String getValueOfExcelCell(XSSFSheet DataSheet, int row, int col){
        String actualValue = "";
        CellType type = DataSheet.getRow(row). getCell(col).getCellTypeEnum();

        if(type.equals("INTEGER")){
            actualValue = NumberToTextConverter.toText(DataSheet.getRow(row).getCell(col).getNumericCellValue());
        }else if(type.equals("STRING")){
            actualValue= DataSheet.getRow(row).getCell(col).getStringCellValue();
        }

        return actualValue;
    }   // end of function  = getValueOfExcelCell

    // validate col value length
    public void validateColumnValueLength(String column_length, String dataValue){
        int dataLength = dataValue.length();
        if(dataLength != Integer.parseInt(column_length)){
            System.out.println("Length does not match");
        }
    }   // end of function = validateColumnValueLength


    // get cell type
    public CellType getCellType(int row, int colIndex){

        CellType cellType = DataSheet.getRow(row).getCell(colIndex).getCellTypeEnum();
        return cellType;
    }       // end of function = getCellType

    // generate prefix with datavalue based on given length and prefix value
    public String generatePrefixValue(int length, String dataValue, String prefixValue){

        String modifiedValue = "";
        String formatString = "";
        int data_length = dataValue.length();
        int difference_length = length-data_length;

        if(length==data_length){
            modifiedValue = dataValue;
        }else {
            for(int i=0; i<difference_length; i++){
                formatString = formatString + prefixValue;
            }
            modifiedValue = formatString + dataValue;
        }

        return modifiedValue;
    }       // end of function = generatePrefixValue


    // get desired cell value with alignment of prefix or postfix
    public static String cellValueAlignment(int length, String type, String dataValue){
        // LEFTSPACE, RIGHTSPACE, LEFTZERO, RIGHTZERO
        if(dataValue.length()==length){
            return dataValue;
        }
        int differenceValue = length-dataValue.length();
        if(differenceValue<0){
            return "ERROR";
        }

        String returnValue = dataValue;

        if(type.equalsIgnoreCase("LEFTSPACE")){
            for(int i=0;i<differenceValue;i++){
                returnValue = " " + returnValue;
            }
            return returnValue;
        }

        if(type.equalsIgnoreCase("RIGHTSPACE")){
            for(int i=0;i<differenceValue;i++){
                returnValue = returnValue + " ";
            }
            return returnValue;
        }

        if(type.equalsIgnoreCase("LEFTZERO")){
            for(int i=0;i<differenceValue;i++){
                returnValue = "0" + returnValue;
            }
            return returnValue;
        }

        if(type.equalsIgnoreCase("RIGHTZERO")){
            for(int i=0;i<differenceValue;i++){
                returnValue = returnValue + "0";
            }
            return returnValue;
        }
        return "ERROR";

    }



    // Release Excel object
    public void ReleaseExcelObjectVariable(){
        DataSheet = null;
        Dataworkboook = null;
        inputworkbook = null;
    }

}
