package com.itheima.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * @author 黑马程序员
 * @Company http://www.ithiema.com
 * @Version 1.0
 */
@Service
@Slf4j
public class ValidateCodeServiceImpl implements ValidateCodeService {

    String accessKey = "LTAI4GAvxxqa5EvB7DeexnuZ";
    String secret = "8X3bwkA9JQ1w5tVhJhin99ZiioLCMx";
    String signName = "健康人生";
    String templateCode = "SMS_162221957";
    String commonTemplateCode = "SMS_165106805";

    @Override
    public void sendValidateCodeShortMessage(String telephone, String type, String validateCode) {
        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKey, secret);
        IAcsClient client = new DefaultAcsClient(profile);

        CommonRequest request = new CommonRequest();
        request.setSysMethod(MethodType.POST);
        request.setSysDomain("dysmsapi.aliyuncs.com");
        request.setSysVersion("2017-05-25");
        request.setSysAction("SendSms");
        request.putQueryParameter("RegionId", "cn-hangzhou");
        request.putQueryParameter("PhoneNumbers", telephone);
        request.putQueryParameter("SignName", signName);
        request.putQueryParameter("TemplateCode", templateCode);
        request.putQueryParameter("TemplateParam", "{\"code\":\""+validateCode+"\"}");
        try {
            CommonResponse response = client.getCommonResponse(request);
            log.debug(response.getData());
        } catch (ServerException e) {
            log.debug("发送验证码失败");
            e.printStackTrace();
        } catch (ClientException e) {
            log.debug("发送验证码失败");
            e.printStackTrace();
        }
        saveValidateCodeToRedis(telephone,type,validateCode);
    }


    @Autowired
    JedisPool jedisPool;
    /**
     * 存储验证码到redis
     */
    public void saveValidateCodeToRedis(String telephone, String type, String validateCode){
        //获取连接对象
        Jedis jedis = jedisPool.getResource();
        //存储验证码到redis 001-13400000000 : 123456
        jedis.setex(type +"-"+telephone,5 * 60, validateCode);
    }

    @Override
    public boolean checkValidateCode(String telephone, String type, String validateCode) {
        //获取 redis中的验证码
        Jedis jedis = jedisPool.getResource();
        String validateCodeInRedis = jedis.get(type + "-" + telephone);
        if(validateCode != null && validateCode.equals(validateCodeInRedis)){
            return true;
        }
        return false;
    }

    @Override
    public void sendCommonShortMessage(String telephone, String... param) {

    }
}
