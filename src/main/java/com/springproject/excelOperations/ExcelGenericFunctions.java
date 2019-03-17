package com.springproject.excelOperations;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;

public class ExcelGenericFunctions {

    // Generic Excel Read Functions - start

    // Input colName and searching rowObj
    // outptu: colIndex number of matching colName
    public static int getCellIndexFromColName(XSSFRow row, String colName){
        int cellIndex = -1;
        try{
            for(int i = 0; i<row.getLastCellNum(); i++){
                if(row.getCell(i).getStringCellValue().trim().equals(colName)){
                    cellIndex = i;
                    break;
                }
            }

        }catch(Exception e){
            if(cellIndex==-1){
                return cellIndex;   // -1 means col name not found in given row
            }
        }
        return cellIndex;
    }

    // input; DataSheet object, RowNumber to search and ColIndex
    // ouput; Return Cell value of particular column index and row number cell value.
    public static String getCellValue(XSSFSheet DataSheet, int rowNum, int ColIndex){
        String cellValue = "";
        CellType type = DataSheet.getRow(rowNum).getCell(ColIndex).getCellTypeEnum();
        if(type.equals("NUMERIC")) {
            cellValue = NumberToTextConverter.toText(DataSheet.getRow(rowNum).getCell(ColIndex).getNumericCellValue());
        }else{
            cellValue = DataSheet.getRow(rowNum).getCell(ColIndex).getStringCellValue();
        }
        return cellValue;
    }

    // get column name based on column Index in particular sheet
    public String getColName(XSSFSheet DataSheet, int ColIndex){
        String ColName = "";
        ColName = DataSheet.getRow(0).getCell(ColIndex).getStringCellValue();
        System.out.println("Column Name in Sheet : " + DataSheet + " at ColIndex : " + ColIndex + " is : " + ColName);
        return ColName;
    }


    // Generic Excel Read Functions - end

    // Generic Excel write Functions - start

    // set value in to Excel Cell under given row number
    public static void writeToExcelSheet(XSSFSheet DataSheet, int rowNum, int ColIndex, String dataValue){
        try{
            if(DataSheet!=null){
                if(rowNum!=0){
                    DataSheet.getRow(rowNum).getCell(ColIndex).setCellValue(dataValue);
                }
            }
        }catch(Exception e){
            System.out.println("Exception in Set cell value in Excel is : " + e.getMessage());
        }
    }


    // Generic Excel write Functions - end

}
