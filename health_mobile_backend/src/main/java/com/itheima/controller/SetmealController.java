package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConst;
import com.itheima.entity.Result;
import com.itheima.pojo.Setmeal;
import com.itheima.service.SetmealService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author 黑马程序员
 * @Company http://www.ithiema.com
 * @Version 1.0
 */
@RestController
@RequestMapping("/setmeal")
@Slf4j
public class SetmealController {

    @Reference
    SetmealService setmealService;

    @RequestMapping("/getSetmeal")
    public Result getSetmeal(){
        log.debug("SetmealController： getSetmeal");
        List<Setmeal> setmealList = setmealService.findAll();
        log.debug("查询所有套餐成功！！");
        log.debug(setmealList.toString());
        return new Result(true, MessageConst.QUERY_SETMEALLIST_SUCCESS, setmealList);
    }
}