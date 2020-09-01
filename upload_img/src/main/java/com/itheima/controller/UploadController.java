package com.itheima.controller;

import com.itheima.entity.Result;
import com.qiniu.util.Auth;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 黑马程序员
 * @Company http://www.ithiema.com
 * @Version 1.0
 */
@RestController
public class UploadController {

    @RequestMapping("/getToken")
    public Result getToken(){
        //...生成上传凭证，然后准备上传
        String accessKey = "0LgMC2u8lKrLeLzMEDG08olOu4XQsk2MHWHhcPHB";
        String secretKey = "V1uIRA21evh3AdoJr3ZPyUfQ78tp-q--fQ4-5pVm";
        //存储空间的名称
        String bucket = "hm31";
        //创建 认证对象
        Auth auth = Auth.create(accessKey, secretKey);
        //生成token
        String token = auth.uploadToken(bucket);
        return new Result(true,"生成上传凭证成功",token);
    }
}
