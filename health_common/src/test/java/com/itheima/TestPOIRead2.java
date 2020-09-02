package com.itheima;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;

/**
 * @author 黑马程序员
 * @Company http://www.ithiema.com
 * @Version 1.0
 */
public class TestPOIRead2 {

    public static void main(String[] args) throws IOException {
        //创建工作薄 对象[excel 文件]
        XSSFWorkbook workbook = new XSSFWorkbook("C:\\Users\\sun\\Desktop\\test_poi.xlsx");

        //获取工作表对象
        XSSFSheet sheet = workbook.getSheet("Sheet1");
        //循环行对象
        for (Row row : sheet) {
            for (Cell cell : row) {
                System.out.println(cell);
            }
        }


    }
}
