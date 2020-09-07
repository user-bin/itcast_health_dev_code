package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConst;
import com.itheima.constant.RedisMessageConstant;
import com.itheima.entity.Result;
import com.itheima.service.ValidateCodeService;
import com.itheima.utils.ValidateCodeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 黑马程序员
 * @Company http://www.ithiema.com
 * @Version 1.0
 */
@RestController
@RequestMapping("/validateCode")
@Slf4j
public class ValidateCodeController {

    @Reference
    ValidateCodeService validateCodeService;

    /**
     *  步骤
     * 1. 生成验证码
     * 2. 发送短信给用户，存储到redis
     *
     * @param telephone
     * @return
     */
    @RequestMapping("/send4Login")
    public Result send4Login(String telephone){
        //生成验证码
        Integer validateCode = ValidateCodeUtils.generateValidateCode(6);
        log.debug("validateCode:" + validateCode);
        validateCodeService.sendValidateCodeShortMessage(telephone, RedisMessageConstant.SENDTYPE_LOGIN, String.valueOf(validateCode));
        log.debug("发送验证码成功！！");
        return new Result(true,MessageConst.SEND_VALIDATECODE_SUCCESS);
    }



    /**
     * 步骤
     *   1. 调用工具类生成验证码
     *   2. 把验证码存储到redis
     *   3. 发送验证码到用户手机
     *
     * @param telephone
     * @return
     */
    @RequestMapping("/send4Order")
    public Result send4Order(String telephone){
        String code = ValidateCodeUtils.generateValidateCode(4).toString();
        log.debug("验证码：" + code);
        //发送验证码到用户
        validateCodeService.sendValidateCodeShortMessage(telephone, RedisMessageConstant.SENDTYPE_ORDER, String.valueOf(code));
        log.debug("发送验证码成功！！");
        return new Result(true, MessageConst.SEND_VALIDATECODE_SUCCESS);
    }

}
