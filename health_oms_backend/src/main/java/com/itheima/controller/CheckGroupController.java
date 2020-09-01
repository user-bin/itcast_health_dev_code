package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConst;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.entity.Result;
import com.itheima.pojo.CheckGroup;
import com.itheima.service.CheckGroupService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

/**
 * @author 黑马程序员
 * @Company http://www.ithiema.com
 * @Version 1.0
 */
@RestController
@RequestMapping("/checkgroup")
@Slf4j
public class CheckGroupController {

    @Reference
    CheckGroupService checkGroupService;

    //新增
    @RequestMapping("/add")
    public Result add(@RequestBody CheckGroup checkGroup, Integer[] checkitemIds){
        log.debug("CheckGroupController:add : checkGroup:" +checkGroup);
        log.debug("CheckGroupController:add : checkitemIds: " +checkitemIds);
        checkGroupService.add(checkGroup,checkitemIds);
        log.debug("添加检查项成功");
        //新增成功
        return new Result(true, MessageConst.ADD_CHECKGROUP_SUCCESS);
    }

    @RequestMapping("/findPage")
    public Result findPage(@RequestBody QueryPageBean queryPageBean){
        log.debug("CheckGroupController{"+queryPageBean+"}");
        PageResult pageResult = checkGroupService.findPage(queryPageBean);
        log.debug("检查组分页查询成功!!");
        return new Result(true, MessageConst.QUERY_CHECKGROUP_SUCCESS, pageResult);
    }

    @RequestMapping("/findById")
    public Result findById(Integer id){
        log.debug("CheckGroupController:findById: " + id);
        CheckGroup checkGroup = checkGroupService.findById(id);
        log.debug("检查组查询成功!!");
        return new Result(true, MessageConst.QUERY_CHECKGROUP_SUCCESS,checkGroup);
    }
    @RequestMapping("/findCheckItemIdsById")
    public Result findCheckItemIdsById(Integer id){
        log.debug("CheckGroupController:findCheckItemIdsById: " + id);
        List<Integer> checkItemIds = checkGroupService.findCheckItemIdsById(id);
        log.debug("根据检查组id查询检查项id成功!!");
        return new Result(true, MessageConst.QUERY_CHECKGROUP_SUCCESS,checkItemIds);
    }


    @RequestMapping("/edit")
    public Result edit(Integer[] checkitemIds ,@RequestBody CheckGroup checkGroup){
        log.debug("CheckGroupController:edit: " + Arrays.toString(checkitemIds));
        log.debug("CheckGroupController:edit: " + checkGroup);
        checkGroupService.edit(checkGroup, checkitemIds);
        log.debug("检查组修改成功！！！");
        return new Result(true,MessageConst.EDIT_CHECKGROUP_SUCCESS);
    }

    @RequestMapping("/findAll")
    public Result findAll(){
        log.debug("findAll");
        List<CheckGroup> checkGroupList = checkGroupService.findAll();
        log.debug("checkGroupList:" + checkGroupList);
        return new Result(true,MessageConst.QUERY_CHECKGROUP_SUCCESS, checkGroupList);
    }
}
