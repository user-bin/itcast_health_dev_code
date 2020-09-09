package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConst;
import com.itheima.entity.Result;
import com.itheima.service.MemberService;
import com.itheima.service.SetmealService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 黑马程序员
 * @Company http://www.ithiema.com
 * @Version 1.0
 */
@RestController
@RequestMapping("/report")
@Slf4j
public class ReportController {

    @Reference
    MemberService memberService;

    @Reference
    SetmealService setmealService;

    /**
     *
     * 前端需要的
     *  {
 *          setmealNames: ['入职无忧体检套餐', '粉红真爱']
 *          setmealCount: [
 *              {name: '入职无忧体检套餐' value: 14},
 *              {name: '粉红真爱', value: 20}
 *          ]
     *
     * }
     * @return
     */
    @RequestMapping("/getSetmealReport")
    public Result getSetmealReport(){
        log.debug("getSetmealReport");
        List<Map<String, Object>> setmealCount =  setmealService.getSetmealReport();
        //所有的套餐名称
        List<Object> setmealNames = new ArrayList<>();
        for (Map<String, Object> map : setmealCount) {
            Object setmalName = map.get("name");
            setmealNames.add(setmalName);
        }
        //创建 返回值对象
        Map<String,Object> resultMap = new HashMap<>();
        resultMap.put("setmealNames", setmealNames);
        resultMap.put("setmealCount", setmealCount);
        log.debug("Result: " + resultMap);
        return new Result(true,MessageConst.GET_SETMEAL_COUNT_REPORT_SUCCESS, resultMap);
    }

    /**
     * {
     *     months: []
     *      memberCount:[]
     * }
     *
     *
     * @return
     */
    @RequestMapping("/getMemberReport")
    public Result getMemberReport(){
        try {
            Map<String,Object> map = memberService.getMemberReport();
            return new Result(true, MessageConst.GET_MEMBER_NUMBER_REPORT_SUCCESS,map );
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConst.GET_MEMBER_NUMBER_REPORT_FAIL );
        }
    }
}
