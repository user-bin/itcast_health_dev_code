package com.itheima.dao;

import com.github.pagehelper.Page;
import com.itheima.pojo.CheckItem;

import java.util.List;

/**
 * 持久层Dao接口
 */
public interface CheckItemDao {
    void add(CheckItem checkItem);

    Page<CheckItem> findByCondition(String queryString);

    long findCountById(Integer id);

    void delById(Integer id);

    /**
     * 基于ID，获取数据
     * @param id ID
     * @return 对象
     */
    public CheckItem findById(Integer id);

    void edit(CheckItem checkItem);

    List<CheckItem> findAll();

    /**
     * 根据检查组id查询 对应的检查项集合
     * @param checkgroupId
     * @return
     */
    List <CheckItem> findCheckItemsByCheckGroupId(Integer checkgroupId);
}