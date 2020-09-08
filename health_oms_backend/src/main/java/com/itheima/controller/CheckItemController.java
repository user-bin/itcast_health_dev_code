package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConst;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.entity.Result;
import com.itheima.pojo.CheckItem;
import com.itheima.service.CheckItemService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author 黑马程序员
 * @Company http://www.ithiema.com
 * @Version 1.0
 */
@RestController
@RequestMapping("/checkitem")
@Slf4j
public class CheckItemController {

    @Reference
    private CheckItemService checkItemService;

    @RequestMapping("/add")
    @PreAuthorize("hasAuthority('CHECKITEM_ADD')")
    public Result add(@RequestBody CheckItem checkItem){
        log.debug("controller:{"+checkItem+"}");
        checkItemService.add(checkItem);
        log.debug("检查项添加成功！！");
        return new Result(true, MessageConst.ADD_CHECKITEM_SUCCESS);
    }

    /**
     * 分页查询
     *  1.调用Service获取分页结果数据
     * @param queryPageBean
     * @return
     */
    @PreAuthorize("hasAuthority('CHECKITEM_QUERY')")
    @RequestMapping("/findPage")
    public Result findPage(@RequestBody QueryPageBean queryPageBean){
        log.debug("controller{"+queryPageBean+"}");
        PageResult pageResult = checkItemService.findPage(queryPageBean);
        log.debug("检查项分页查询成功!!");
        return new Result(true, MessageConst.QUERY_CHECKITEM_SUCCESS, pageResult);
    }

    @PreAuthorize("hasAuthority('CHECKITEM_DELETE')")
    @RequestMapping("/delById")
    public Result delById(Integer id){
        log.debug("CheckItemController:delete: " + id);
        checkItemService.delById(id);
        log.debug("检查项删除成功!!");
        return new Result(true, MessageConst.DELETE_CHECKITEM_SUCCESS);
    }

    @RequestMapping("/findById")
    @PreAuthorize("hasAuthority('CHECKITEM_QUERY')")
    public Result findById(Integer id){
        log.debug("CheckItemController：findById:" + id);
        CheckItem checkItem = checkItemService.findById(id);
        log.debug("根据id查询检查项成功!!");
        return new Result(true, MessageConst.QUERY_CHECKITEM_SUCCESS, checkItem);
    }

    @RequestMapping("/edit")
    @PreAuthorize("hasAuthority('CHECKITEM_EDIT')")
    public Result edit(@RequestBody CheckItem checkItem){
        log.debug("CheckItemController:edit: " + checkItem);
        checkItemService.edit(checkItem);
        log.debug("检查项修改成功!!");
        return new Result(true, MessageConst.EDIT_CHECKITEM_SUCCESS);
    }

    @RequestMapping("/findAll")
    @PreAuthorize("hasAuthority('CHECKITEM_QUERY')")
    public Result findAll(){
        log.debug("findAll");
        List<CheckItem> checkItemList = checkItemService.findAll();
        log.debug("checkItemList:" + checkItemList);
        return new Result(true,MessageConst.QUERY_CHECKITEM_SUCCESS, checkItemList);
    }
}