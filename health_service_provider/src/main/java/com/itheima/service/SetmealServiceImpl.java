package com.itheima.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.dao.SetmealDao;
import com.itheima.pojo.Setmeal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author 黑马程序员
 * @Company http://www.ithiema.com
 * @Version 1.0
 */
@Service
@Transactional
public class SetmealServiceImpl implements SetmealService {


    @Autowired
    SetmealDao setmealDao;

    /**
     * 步骤：
     *  1. 添加套餐（主键回显）
     *  2. 维护套餐和检查组的关系
     *
     * @param setmeal
     * @param checkgroupIds
     */
    @Override
    public void add(Setmeal setmeal, Integer[] checkgroupIds) {
        setmealDao.add(setmeal);
        if(setmeal.getId() != null){
            setRelation(setmeal.getId(), checkgroupIds);
        }
    }

    public void setRelation(Integer setmealId, Integer[] checkgroupIds){
        if(checkgroupIds != null && checkgroupIds.length > 0){
            for (Integer checkgroupId : checkgroupIds) {
                setmealDao.setRelation(setmealId, checkgroupId);
            }
        }
    }
}
