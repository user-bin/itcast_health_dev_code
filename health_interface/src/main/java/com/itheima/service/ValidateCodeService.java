package com.itheima.service;

/**
 * @author 黑马程序员
 * @Company http://www.ithiema.com
 * @Version 1.0
 */
public interface ValidateCodeService {
    /**
     * 发送验证码短信且存储到redis
     * @param telephone  手机号码
     * @param type   发送短信的类型:预约，登录，获取密码
     * @param validateCode  要发送给用户的验证码
     */
    public void sendValidateCodeShortMessage(String telephone,  String type, String validateCode);

    /**
     * 校验验证码
     * @param telephone  手机号码
     * @param type  验证码的类型
     * @param validateCode  用户输入的验证码
     * @return
     */
    public boolean checkValidateCode(String telephone , String type , String validateCode);

    /**
     * 发送通用短信
     * @param telephone 手机号
     * @param param  通用短信中的参数
     */
    public void sendCommonShortMessage(String telephone, String... param);

}
