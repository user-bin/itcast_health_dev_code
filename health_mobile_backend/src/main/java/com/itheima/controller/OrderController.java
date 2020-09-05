package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConst;
import com.itheima.constant.RedisMessageConstant;
import com.itheima.entity.Result;
import com.itheima.pojo.Order;
import com.itheima.service.OrderService;
import com.itheima.service.ValidateCodeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author 黑马程序员
 * @Company http://www.ithiema.com
 * @Version 1.0
 */
@RestController
@RequestMapping("/order")
@Slf4j
public class OrderController {

    @Reference
    OrderService orderService;

    @Reference
    ValidateCodeService validateCodeService;

    /**
     * 1. 校验验证码
     *  获取参数中的手机号，验证码
     *  调用验证码服务接口，校验
     * 2. 指定预约的方式(电话预约，微信预约)
     * 3. 添加预约信息到数据库
     * 4. 发送通知短信给用户
     * @param map
     * @return
     */
    @RequestMapping("/submit")
    public Result submit(@RequestBody Map<String,String> map){
        log.debug("orderInfo:" + map);
        String telephone = map.get("telephone");
        String validateCode = map.get("validateCode");
        //校验验证码
        if(!validateCodeService.checkValidateCode(telephone, RedisMessageConstant.SENDTYPE_ORDER, validateCode)){
            //如果校验失败
            return new Result(false, MessageConst.VALIDATECODE_ERROR);
        }
        //指定预约的方式(电话预约，微信预约)
        map.put("orderType", Order.ORDERTYPE_WEIXIN);
        //添加预约信息到数据库
        Result result = orderService.addOrder(map);

        if(result.isFlag()){
            validateCodeService.sendCommonShortMessage(telephone,"");
        }
        return result;
    }
}
