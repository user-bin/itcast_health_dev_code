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
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

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

    @Autowired
    JedisPool jedisPool;

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
        //添加套餐完成后， 移除redis中的图片名称
        Jedis jedis = jedisPool.getResource();
        jedis.srem(RedisConst.SETMEAL_PIC_RESOURCES, setmeal.getImg());
    }

    @Override
    public PageResult findPage(QueryPageBean queryPageBean) {
        //开始分页
        PageHelper.startPage(queryPageBean.getCurrentPage(), queryPageBean.getPageSize());
        //条件查询
        Page<Setmeal> page = setmealDao.findByCondition(queryPageBean.getQueryString());

        return new PageResult(page.getTotal(), page);
    }

    public void setRelation(Integer setmealId, Integer[] checkgroupIds){
        if(checkgroupIds != null && checkgroupIds.length > 0){
            for (Integer checkgroupId : checkgroupIds) {
                setmealDao.setRelation(setmealId, checkgroupId);
            }
        }
    }
}
