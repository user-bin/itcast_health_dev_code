package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConst;
import com.itheima.constant.RedisMessageConstant;
import com.itheima.entity.Result;
import com.itheima.pojo.Member;
import com.itheima.service.MemberService;
import com.itheima.service.ValidateCodeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.Map;

/**
 * @author 黑马程序员
 * @Company http://www.ithiema.com
 * @Version 1.0
 */
@RestController
@RequestMapping("/member")
@Slf4j
public class MemberController {

    @Reference
    MemberService memberService;

    @Reference
    ValidateCodeService validateCodeService;

    /**
     * 1. 获取参数中的手机号码和验证码
     * 2. 调用service校验验证码
     * 3. 判断是否是会员，如果不是会员， 自动注册
     *
     *
     * @param map
     * @return
     */
    @RequestMapping("/login")
    public Result login(@RequestBody Map<String, String> map){
        //1. 获取参数
        String telephone = map.get("telephone");
        String validateCode = map.get("validateCode");
        //2. 校验验证码
        boolean flag = validateCodeService.checkValidateCode(telephone, RedisMessageConstant.SENDTYPE_LOGIN, validateCode);
        if(!flag){
            return new Result(false, MessageConst.VALIDATECODE_ERROR);
        }
        //3. 自动注册会员
        Member member = memberService.findByTelephone(telephone);
        if(member == null){
            //说明不是会员， 自动注册
            member = new Member();
            member.setPhoneNumber(telephone);
            member.setRegTime(new Date());
            memberService.add(member);
        }
        log.debug(MessageConst.LOGIN_SUCCESS);
        return new Result(true,MessageConst.LOGIN_SUCCESS);
    }
}
