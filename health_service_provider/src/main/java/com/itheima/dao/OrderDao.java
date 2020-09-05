package com.itheima.dao;

import com.itheima.pojo.Order; /**
 * @author 黑马程序员
 * @Company http://www.ithiema.com
 * @Version 1.0
 */
public interface OrderDao {
    long findByCondition(Order condition);

    void addOrder(Order order);
}
