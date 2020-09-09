package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConst;
import com.itheima.entity.Result;
import com.itheima.exception.BusinessRuntimeException;
import com.itheima.service.MemberService;
import com.itheima.service.ReportService;
import com.itheima.service.SetmealService;
import com.itheima.utils.POIUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 黑马程序员
 * @Company http://www.ithiema.com
 * @Version 1.0
 */
@RestController
@RequestMapping("/report")
@Slf4j
public class ReportController {

    @Reference
    MemberService memberService;

    @Reference
    SetmealService setmealService;

    @Reference
    ReportService reportService;

    /**
     * 1. 获取运营数据
     * 2. 获取excel模板
     * 3. 写入到模板中
     * 4. 下载excel到本地
     *
     * @return
     */
    @RequestMapping("/exportBusinessReport")
    public Result exportBusinessReport(HttpServletResponse response){
        log.debug("exportBusinessReport");
        //1. 获取运营数据
        Map<String, Object> reportData = reportService.getBusinessReportData();
        String reportDate = String.valueOf(reportData.get("reportDate"));
        String todayNewMember = String.valueOf(reportData.get("todayNewMember"));
        String totalMember = String.valueOf(reportData.get("totalMember"));
        String thisWeekNewMember = String.valueOf(reportData.get("thisWeekNewMember"));
        String thisMonthNewMember = String.valueOf(reportData.get("thisMonthNewMember"));
        String todayOrderNumber = String.valueOf(reportData.get("todayOrderNumber"));
        String todayVisitsNumber = String.valueOf(reportData.get("todayVisitsNumber"));
        String thisWeekOrderNumber = String.valueOf(reportData.get("thisWeekOrderNumber"));
        String thisMonthOrderNumber = String.valueOf(reportData.get("thisMonthOrderNumber"));
        String thisWeekVisitsNumber = String.valueOf(reportData.get("thisWeekVisitsNumber"));
        String thisMonthVisitsNumber = String.valueOf(reportData.get("thisMonthVisitsNumber"));

        List<Map<String,Object>> hotSetmeal = (List<Map<String, Object>>) reportData.get("hotSetmeal");

        //获取excel模板
        //获取文件流对象
        //创建工作薄
        try {
            InputStream inputStream = this.getClass().getResourceAsStream("/template/report_template.xlsx");
            XSSFWorkbook workbook = new XSSFWorkbook(inputStream);


            //把数据写入到模板
            //获取工作表
            XSSFSheet sheet = workbook.getSheetAt(0);

            //写入统计时间
            POIUtils.writeCell(sheet, 2,5, reportDate);
            //写入今日新增会员数
            POIUtils.writeCell(sheet, 4,5, todayNewMember);
            //写入总会员数
            POIUtils.writeCell(sheet, 4,7, totalMember);
            //写入本周新增会员数
            POIUtils.writeCell(sheet, 5,5, thisWeekNewMember);
            //写入本月新增会员数
            POIUtils.writeCell(sheet, 5,7, thisMonthNewMember);
            //写入今日预约数
            POIUtils.writeCell(sheet, 7,5, todayOrderNumber);
            //写入今日到诊数
            POIUtils.writeCell(sheet, 7,7, todayVisitsNumber);
            //写入本周预约数
            POIUtils.writeCell(sheet, 8,5, thisWeekOrderNumber);
            //写入本周到诊数
            POIUtils.writeCell(sheet, 8,7, thisWeekVisitsNumber);
            //写入本月预约数
            POIUtils.writeCell(sheet, 9,5, thisMonthOrderNumber);
            //写入本月到诊数
            POIUtils.writeCell(sheet, 9,7, thisMonthVisitsNumber);

            int rownum = 12;
            //写入热门套餐
            for (Map<String, Object> setmeal : hotSetmeal) {
                POIUtils.writeCell(sheet, rownum,4, String.valueOf(setmeal.get("name")));
                POIUtils.writeCell(sheet, rownum,5, String.valueOf(setmeal.get("setmeal_count")));
                POIUtils.writeCell(sheet, rownum,6, String.valueOf(setmeal.get("proportion")));
                rownum ++ ;
             }


            //4. 下载excel到本地
            // 通过输出流进行文件下载
            ServletOutputStream out = response.getOutputStream();
            //指定附件的格式为excel
            response.setContentType("application/vnd.ms-excel");
            //attachment 以附件下载
            //下载弹出框架中的默认的文件名
            response.setHeader("content-Disposition","attachment;filename="+reportDate+"_report.xlsx");
            //输出流下载
            workbook.write(out);
            out.flush();
            out.close();
            workbook.close();


        } catch (IOException e) {
            throw new BusinessRuntimeException(MessageConst.GET_BUSINESS_REPORT_FAIL);
        }

        return null;
    }


    @RequestMapping("/getBusinessReportData")
    public Result getBusinessReportData(){
        log.debug("getBusinessReportData");
        Map<String,Object> map = reportService.getBusinessReportData();
        log.debug("reportData:" + map);
        return new Result(true,MessageConst.GET_BUSINESS_REPORT_SUCCESS, map);
    }


    /**
     *
     * 前端需要的
     *  {
 *          setmealNames: ['入职无忧体检套餐', '粉红真爱']
 *          setmealCount: [
 *              {name: '入职无忧体检套餐' value: 14},
 *              {name: '粉红真爱', value: 20}
 *          ]
     *
     * }
     * @return
     */
    @RequestMapping("/getSetmealReport")
    public Result getSetmealReport(){
        log.debug("getSetmealReport");
        List<Map<String, Object>> setmealCount =  setmealService.getSetmealReport();
        //所有的套餐名称
        List<Object> setmealNames = new ArrayList<>();
        for (Map<String, Object> map : setmealCount) {
            Object setmalName = map.get("name");
            setmealNames.add(setmalName);
        }
        //创建 返回值对象
        Map<String,Object> resultMap = new HashMap<>();
        resultMap.put("setmealNames", setmealNames);
        resultMap.put("setmealCount", setmealCount);
        log.debug("Result: " + resultMap);
        return new Result(true,MessageConst.GET_SETMEAL_COUNT_REPORT_SUCCESS, resultMap);
    }

    /**
     * {
     *     months: []
     *      memberCount:[]
     * }
     *
     *
     * @return
     */
    @RequestMapping("/getMemberReport")
    public Result getMemberReport(){
        try {
            Map<String,Object> map = memberService.getMemberReport();
            return new Result(true, MessageConst.GET_MEMBER_NUMBER_REPORT_SUCCESS,map );
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConst.GET_MEMBER_NUMBER_REPORT_FAIL );
        }
    }
}
