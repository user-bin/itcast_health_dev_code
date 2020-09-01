package com.itheima.dao;

import com.itheima.pojo.Setmeal; /**
 * @author 黑马程序员
 * @Company http://www.ithiema.com
 * @Version 1.0
 */
public interface SetmealDao {
    void add(Setmeal setmeal);

    void setRelation(Integer setmealId, Integer checkgroupId);
}
