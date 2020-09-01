package com.itheima.service;

import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.pojo.CheckGroup;

import java.util.List;

/**
 * 检查组服务接口
 */
public interface CheckGroupService {
	/**
	 * 添加检查组
	 * @param checkGroup 检查组基本信息
	 * @param checkItemIds 检查项ID列表
	 */
	public void add(CheckGroup checkGroup, Integer[] checkItemIds);

	PageResult findPage(QueryPageBean queryPageBean);

	CheckGroup findById(Integer id);

	List<Integer> findCheckItemIdsById(Integer id);

	/**
	 * 编辑检查组
	 * @param checkGroup 检查基本数据
	 * @param checkitemIds 检查项选择列表
	 */
	public void edit(CheckGroup checkGroup,Integer[] checkitemIds);

    List<CheckGroup> findAll();
}