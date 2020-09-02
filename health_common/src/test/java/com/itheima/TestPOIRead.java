package com.itheima;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;

/**
 * @author 黑马程序员
 * @Company http://www.ithiema.com
 * @Version 1.0
 */
public class TestPOIRead {

    public static void main(String[] args) throws IOException {
        //创建工作薄 对象[excel 文件]
        XSSFWorkbook workbook = new XSSFWorkbook("C:\\Users\\sun\\Desktop\\test_poi.xlsx");
        //获取工作表对象
        XSSFSheet sheet = workbook.getSheetAt(0);
        //获取行对象
//        XSSFRow row = sheet.getRow(1);
        //获取单元格
//        XSSFCell cell = row.getCell(1);
//        System.out.println(cell);
        // 获取最后一行的行号
        int lastRowNum = sheet.getLastRowNum();
        for (int i = 0; i <= lastRowNum ; i++) {
            XSSFRow row = sheet.getRow(i);
//            System.out.println(row);
            // 一行有几个单元格
            short lastCellNum = row.getLastCellNum();
//            System.out.println(lastCellNum);
            for (int j = 0; j < lastCellNum; j++) {
                XSSFCell cell = row.getCell(j);
                System.out.print(cell + "\t\t");
            }
            System.out.println();
        }

    }

}
