package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConst;
import com.itheima.entity.Result;
import com.itheima.exception.BusinessRuntimeException;
import com.itheima.pojo.OrderSetting;
import com.itheima.service.OrderSettingService;
import com.itheima.utils.POIUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author 黑马程序员
 * @Company http://www.ithiema.com
 * @Version 1.0
 */
@RestController
@RequestMapping("/ordersetting")
@Slf4j
public class OrderSettingController {

    @Reference
    OrderSettingService orderSettingService;

    @RequestMapping("/upload")
    public Result upload(MultipartFile excelFile){
        log.debug(excelFile.toString());
        //获取excel中的数据
        try {
            List<String[]> strings = POIUtils.readExcel(excelFile);
            //把List<String[]> 类型 转换为  List<OrderSetting>
            //创建集合对象
            List<OrderSetting> orderSettingList = new ArrayList<>();
            
            //循环获取到的数据
            for (String[] excelStrs : strings) {
                //一个string数组对应一个 OrderSetting对
                OrderSetting orderSetting = new OrderSetting();
                orderSetting.setNumber(Integer.parseInt(excelStrs[1]));
                String dateStr = excelStrs[0];
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
                Date orderDate = sdf.parse(dateStr);
                orderSetting.setOrderDate(orderDate);
                //把预约设置对象添加到 集合中
                orderSettingList.add(orderSetting);
            }
            //调用service ，添加 orderSetting集合到数据库
            orderSettingService.addOrderSettingList(orderSettingList);

            return new Result(true, MessageConst.IMPORT_ORDERSETTING_SUCCESS);
        } catch (BusinessRuntimeException e) {
            e.printStackTrace();
            throw  e;
        }catch (Exception e) {
            e.printStackTrace();
            throw  new BusinessRuntimeException(MessageConst.IMPORT_ORDERSETTING_FAIL);
        }
    }
}
