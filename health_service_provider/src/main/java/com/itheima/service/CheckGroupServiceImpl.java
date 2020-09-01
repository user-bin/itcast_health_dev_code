package com.itheima.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.dao.CheckGroupDao;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.pojo.CheckGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 检查组服务
 */
@Service
public class CheckGroupServiceImpl implements CheckGroupService {

	@Autowired
	private CheckGroupDao checkGroupDao;

	/**
     * 1. 添加检查组对象， 回显主键id
     * 2. 维护中间表的数据（检查组和检查项的关系）
     *
     * @param checkitemIds
     * @param checkGroup
     */
    @Override
    @Transactional
    public void add(CheckGroup checkGroup , Integer[] checkitemIds) {
        //1. 添加检查组对象， 回显主键id
        checkGroupDao.add(checkGroup);
        //2. 维护中间表的数据（检查组和检查项的关系）
        if(checkGroup.getId() != null){
            setRelation(checkGroup.getId(), checkitemIds);
        }
    }

    /**
     * 维护检查组和检查项的关系
     * @param checkgroupId
     * @param checkItemId
     */
    public void setRelation(Integer checkgroupId ,Integer[] checkItemId){
        if(checkgroupId != null && checkItemId != null && checkItemId.length > 0){
            for (Integer checkitemId : checkItemId) {
                checkGroupDao.set(checkgroupId ,checkitemId );
            }
        }
    }

    @Override
    public PageResult findPage(QueryPageBean queryPageBean) {
        PageHelper.startPage(queryPageBean.getCurrentPage(), queryPageBean.getPageSize());
        Page<CheckGroup> page = checkGroupDao.findByCondition(queryPageBean.getQueryString());
        return new PageResult(page.getTotal(), page);
    }

    @Override
    public CheckGroup findById(Integer id) {
        return checkGroupDao.findById(id);
    }
    @Override
    public List<Integer> findCheckItemIdsById(Integer id) {
        return checkGroupDao.findCheckItemIdsById(id);
    }

    /**
     * 1. 修改检查组
     * 2. 维护中间表的数据
     *      2.1 先删除该检查组原来的关系
     *      2.2 添加新的关系
     * @param checkitemIds
     * @param checkGroup
     */
    @Override
    @Transactional
    public void edit(CheckGroup checkGroup, Integer[] checkitemIds) {
        //1.修改检查组
        checkGroupDao.edit(checkGroup);
        //2.1 先删除该检查组原来的关系
        checkGroupDao.delRelation(checkGroup.getId());
        //2.2 添加新的关系
        if(checkGroup.getId() != null){
            setRelation(checkGroup.getId(), checkitemIds);
        }
    }

    @Override
    public List<CheckGroup> findAll() {
        return checkGroupDao.findAll();
    }
}