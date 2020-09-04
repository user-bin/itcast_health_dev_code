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
        log.debug("getSetmeal");
        List<Setmeal> setmealList = setmealService.findAll();
        log.debug("setmealList:" + setmealList);
        return new Result(true, MessageConst.GET_SETMEAL_LIST_SUCCESS, setmealList);
    }

    @RequestMapping("/findDetailsById")
    public Result findDetailsById(Integer id){
        log.debug("findDetailsById:" + id);
        Setmeal setmeal = setmealService.findDetailsById(id);
        log.debug("setmeal:" + setmeal);
        return new Result(true,MessageConst.QUERY_SETMEAL_SUCCESS, setmeal);
    }

}
