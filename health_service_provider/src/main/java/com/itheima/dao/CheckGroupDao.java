package com.itheima.dao;

import com.github.pagehelper.Page;
import com.itheima.pojo.CheckGroup;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 持久层Dao接口
 */
public interface CheckGroupDao {
	/**
	 * 添加检查组
	 * @param checkGroup
	 */
	void add(CheckGroup checkGroup);

	/**
	 * 添加检查组的检查项
	 */
	void set(Integer checkgroupId,Integer checkitemId);
	/**
	 * 基于条件分页获取检查组列表
	 * @param queryString
	 * @return
	 */
	Page<CheckGroup> findByCondition(@Param("queryString") String queryString);

	/**
	 * 根据id查询
	 * @param id
	 * @return
	 */
	CheckGroup findById(Integer id);
	/**
	 * 根据检查组id查询相关的检查项id
	 * @param id
	 * @return
	 */
	List<Integer> findCheckItemIdsById(Integer id);

	void edit(CheckGroup checkGroup);

	void delRelation(Integer id);

    List<CheckGroup> findAll();
}