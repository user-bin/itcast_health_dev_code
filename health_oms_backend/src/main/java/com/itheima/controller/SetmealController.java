package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConst;
import com.itheima.entity.Result;
import com.itheima.pojo.Setmeal;
import com.itheima.service.SetmealService;
import com.qiniu.util.Auth;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @RequestMapping("/getToken")
    public Result getToken(){
        log.debug("获取文件上传的token!!");
        //...生成上传凭证，然后准备上传
        String accessKey = "0LgMC2u8lKrLeLzMEDG08olOu4XQsk2MHWHhcPHB";
        String secretKey = "V1uIRA21evh3AdoJr3ZPyUfQ78tp-q--fQ4-5pVm";
        //存储空间的名称
        String bucket = "hm31";
        //创建 认证对象
        Auth auth = Auth.create(accessKey, secretKey);
        //生成token
        String token = auth.uploadToken(bucket);
        return new Result(true, MessageConst.GET_QINIU_TOKEN_SUCCESS,token);
    }

    @RequestMapping("/add")
    public Result add(@RequestBody  Setmeal setmeal, Integer[] checkgroupIds){
        log.debug("setmeal:" +setmeal);
        log.debug("checkgroupIds:" + checkgroupIds);
        setmealService.add(setmeal, checkgroupIds);
        log.debug(MessageConst.ADD_SETMEAL_SUCCESS);
        return new Result(true,MessageConst.ADD_SETMEAL_SUCCESS);
    }
}
