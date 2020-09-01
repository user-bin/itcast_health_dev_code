package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConst;
import com.itheima.constant.RedisConst;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.entity.Result;
import com.itheima.pojo.Setmeal;
import com.itheima.service.SetmealService;
import com.qiniu.util.Auth;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

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

    @RequestMapping("/findPage")
    public Result findPage(@RequestBody QueryPageBean queryPageBean){
        log.debug("queryPageBean:" + queryPageBean);
        PageResult pageResult = setmealService.findPage(queryPageBean);
        log.debug("PageResult:" + pageResult);
        return new Result(true,MessageConst.QUERY_SETMEAL_SUCCESS, pageResult);
    }

//    注入连接池对象
    @Autowired
    JedisPool jedisPool;

    @RequestMapping("/saveImgName")
    public Result saveImgName(String imgName){
        log.debug("imgName：" + imgName);
        //获取 redis的连接对象
        Jedis jedis = jedisPool.getResource();
        //操作redis数据库
        jedis.sadd(RedisConst.SETMEAL_PIC_RESOURCES, imgName);
        log.debug("添加图片名称到redis成功：" + imgName);
        return new Result(true,MessageConst.ADD_IMGNAME_REDIS);
    }
}
