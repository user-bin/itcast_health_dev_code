package com.itheima.dao;

import com.itheima.pojo.Order;

import java.util.List;
import java.util.Map;

public interface OrderDao {
    List<Order> findByCondition(Order order);

    void add(Order order);

    Map<String,Object> findById(Integer id);



    long findTodayOrderNumber(String today);

    long findTodayVisitsNumber(String today);

    /**
     * 查询日期区间的预约人数
     * @param firstDay
     * @param lastDay
     * @return
     */
    long findOrderCountByBetweenDate(String firstDay, String lastDay);

    /**
     * 查询指定日期之后的到诊人数
     * @param startDate
     * @return
     */
    long findVisitsCountByAfterDate(String startDate);
}