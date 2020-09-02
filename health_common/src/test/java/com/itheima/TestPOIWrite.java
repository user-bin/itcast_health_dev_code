package com.itheima;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * @author 黑马程序员
 * @Company http://www.ithiema.com
 * @Version 1.0
 */
public class TestPOIWrite {

    public static void main(String[] args) throws IOException {
        //创建工作薄
        XSSFWorkbook workbook = new XSSFWorkbook();
        //创建工作表对象
        XSSFSheet sheet = workbook.createSheet("test");
        //创建行
        XSSFRow row = sheet.createRow(6);
        //创建单元格
        XSSFCell cell = row.createCell(3);
        //给单元格赋值
        cell.setCellValue("小明");

        //创建单元格
        cell = row.createCell(4);
        //给单元格赋值
        cell.setCellValue("小丽");

        //创建单元格
        cell = row.createCell(5);
        //给单元格赋值
        cell.setCellValue("小花");

        //创建行
        XSSFRow row2 = sheet.createRow(7);
        //创建单元格
        XSSFCell cell2 = row2.createCell(3);
        //给单元格赋值
        cell2.setCellValue("小明2");

        //创建单元格
        cell2 = row2.createCell(4);
        //给单元格赋值
        cell2.setCellValue("小丽2");

        //创建单元格
        cell2 = row2.createCell(5);
        //给单元格赋值
        cell2.setCellValue("小花2");


        //创建输出流对象
        OutputStream outputStream = new FileOutputStream("C:\\Users\\sun\\Desktop\\test.xlsx");
        //写入excel
        workbook.write(outputStream);
        outputStream.flush();
        outputStream.close();
        workbook.close();
    }
}
