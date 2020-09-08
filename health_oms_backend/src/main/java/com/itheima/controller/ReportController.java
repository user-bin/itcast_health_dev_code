package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConst;
import com.itheima.entity.Result;
import com.itheima.service.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @RequestMapping("/getMemberReport")
    public Result getMemberReport(){
        log.debug("会员数量统计!!");
        Map<String,Object> map = memberService.getMemberReport();
        log.debug("MemberReport:" + map);
        return new Result(true, MessageConst.GET_MEMBER_NUMBER_REPORT_SUCCESS,map);
    }
}
