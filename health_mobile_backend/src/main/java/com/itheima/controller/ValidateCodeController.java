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
     * 生成验证码
     * 发送验证到 给用户，且存储到redis
     * @param telephone
     * @return
     */
    @RequestMapping("/send4Order")
    public Result send4Order(String telephone){
        log.debug("send4Order：" + telephone);
        //生成验证码
        Integer validateCode = ValidateCodeUtils.generateValidateCode(6);
        log.debug("validateCode:" + validateCode);
        //发送验证码且存储到redis
        validateCodeService.sendValidateCodeShortMessage(telephone, RedisMessageConstant.SENDTYPE_ORDER, String.valueOf(validateCode));
        log.debug(MessageConst.SEND_VALIDATECODE_SUCCESS);
        return new Result(true, MessageConst.SEND_VALIDATECODE_SUCCESS);
    }
}
