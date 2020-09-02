package com.itheima.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.dao.OrderSettingDao;
import com.itheima.exception.BusinessRuntimeException;
import com.itheima.pojo.OrderSetting;
import com.itheima.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author 黑马程序员
 * @Company http://www.ithiema.com
 * @Version 1.0
 */
@Service
@Transactional
public class OrderSettingServiceImpl implements OrderSettingService {

    @Autowired
    OrderSettingDao orderSettingDao;


    /**
     * 分析：
     *  1. 循环集合
     *  2. 先判断该日期是否 已经预约设置
     *      如果已经预约设置 ， 则执行修改操作
     *              判断 已预约是否大于可预约，如果大于，不能修改，相反可以修改
     *      如果没有预约设置， 则添加
     *
     * @param orderSettingList
     */
    @Override
    public void addOrderSettingList(List<OrderSetting> orderSettingList) {
        if(orderSettingList !=  null && orderSettingList.size() > 0){
            for (OrderSetting orderSetting : orderSettingList) {
                saveOrEdit(orderSetting);
            }
        }
    }

    /**
     *
     * @param month = 2020-09
     * @return
     */
    @Override
    public List<OrderSetting> findByMonth(String month) {
        String thisMonthFirstDay = month + "-01";
        String thisMonthLastDay =
                DateUtils.parseDate2Str(DateUtils.getThisMonthLastDayByFirstDay(thisMonthFirstDay));

        return orderSettingDao.findByBetweenDate(thisMonthFirstDay, thisMonthLastDay);
    }

    /**
     *
     * 先判断该日期是否 已经预约设置
     * 如果已经预约设置 ， 则执行修改操作
     *      判断 已预约是否大于可预约，如果大于，不能修改，相反可以修改
     * 如果没有预约设置， 则添加
     * @param orderSetting
     */
    public void saveOrEdit(OrderSetting orderSetting){
        //根据预约日期获取 orderSetting对象
        OrderSetting orderSettingDb = orderSettingDao.findByOrderDate(orderSetting.getOrderDate());
        //如果不等于null，说明已经预约设置， 则执行修改操作
        if(orderSettingDb != null){
            //如果已预约大于可预约， 不能修改，抛出异常
            if(orderSettingDb.getReservations() > orderSetting.getNumber()){
                throw new BusinessRuntimeException("已预约大于可预约人数，不可修改，批量导入失败!!");
            }else{
                orderSettingDao.edit(orderSetting);
            }
        }else{
            orderSettingDao.add(orderSetting);
        }
    }
}
