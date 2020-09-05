package com.itheima.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.constant.RedisConst;
import com.itheima.dao.SetmealDao;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.pojo.Setmeal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.JedisPool;

import java.util.List;

/**
 * @author 黑马程序员
 * @Company http://www.ithiema.com
 * @Version 1.0
 */
@Service
public class SetmealServiceImpl implements SetmealService {
    @Autowired
    SetmealDao setmealDao;

    @Autowired
    JedisPool jedisPool;

    @Transactional
    @Override
    public void add(Setmeal setmeal, Integer[] checkgroupIds) {
        //1. 添加套餐（回显主键）
        setmealDao.add(setmeal);
        //2. 维护套餐和检查组的关系
        setRelation(setmeal, checkgroupIds);

        jedisPool.getResource().srem(RedisConst.SETMEAL_PIC_RESOURCES, setmeal.getImg());

    }

    /**
     * 设置检查组和套餐的关系
     * @param setmeal
     * @param checkgroupIds
     */
    private void setRelation(Setmeal setmeal,Integer[] checkgroupIds){
        if(setmeal.getId()!= null && checkgroupIds != null && checkgroupIds.length > 0){
            for (Integer checkgroupId : checkgroupIds) {
                setmealDao.setRelation(setmeal.getId(), checkgroupId);
            }
        }
    }

    @Override
    public PageResult findPage(QueryPageBean queryPageBean) {
        PageHelper.startPage(queryPageBean.getCurrentPage(),queryPageBean.getPageSize());
        Page<Setmeal> pageSetmeal = setmealDao.findByCondition(queryPageBean.getQueryString());
        return new PageResult(pageSetmeal.getTotal(),pageSetmeal.getResult());
    }

    @Override
    public List<Setmeal> findAll() {
        return setmealDao.findAll();
    }

    @Override
    public Setmeal findDetailById(Integer id) {
        return setmealDao.findDetailsById(id);
    }

    @Override
    public Setmeal findById(Integer id) {
        return setmealDao.findById(id);
    }

}
