package com.itheima.dao;

import com.github.pagehelper.Page;
import com.itheima.pojo.Setmeal;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author 黑马程序员
 * @Company http://www.ithiema.com
 * @Version 1.0
 */
public interface SetmealDao {
    void add(Setmeal setmeal);

    void setRelation(@Param("setmeal_id") Integer setmealId,@Param("checkgroup_id") Integer checkgroupId);

    Page<Setmeal> findByCondition(String queryString);

    List<Setmeal> findAll();

    Setmeal findDetailsById(int id);

    Setmeal findById(Integer id);
}
