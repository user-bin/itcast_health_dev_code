package com.itheima.job;

import com.itheima.constant.RedisConst;
import com.qiniu.common.QiniuException;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.util.Auth;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Set;

/**
 * @author 黑马程序员
 * @Company http://www.ithiema.com
 * @Version 1.0
 */
public class ClearImageJob {

    @Autowired
    JedisPool jedisPool;

    public void clear(){
        System.out.println("执行了" + new Date());
    }

    /**
     * 步骤
     * 1.获取redis中所有的图片名称（垃圾图片名称）
     * 2.删除前一天 上传的垃圾图片 (通过日期字符串判断 时间)【 七牛云】
     * 3.删除redis中的垃圾图片名称
     */
    public void clearImageJob(){
        //获取连接对象
        Jedis jedis = jedisPool.getResource();
        //获取redis  set集合中的图片名称
        Set<String> imgNames = jedis.smembers(RedisConst.SETMEAL_PIC_RESOURCES);
        //昨天的日期字符串： 20200831
        //获取日历对象，指定当前日期
        Calendar calendar = Calendar.getInstance();
        //前移一天
        calendar.add(Calendar.DAY_OF_MONTH , -1);
        //转换为date类型
        Date date = calendar.getTime();
        //转换为字符串
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        //获取 昨天的日期字符串
        String dateStr = sdf.format(date);
        //遍历set集合
        for (String imgName : imgNames) {
            if(imgName.startsWith(dateStr)){
                //删除七牛上的图片
                delQiniuImg(imgName);
                //删除redis的图片名称
                jedis.srem(RedisConst.SETMEAL_PIC_RESOURCES, imgName);
            }
        }

    }

    public void delQiniuImg(String imgName){
        //构造一个带指定 Region 对象的配置类
        Configuration cfg = new Configuration(Region.region0());
        //...其他参数参考类注释

        //...生成上传凭证，然后准备上传
        String accessKey = "0LgMC2u8lKrLeLzMEDG08olOu4XQsk2MHWHhcPHB";
        String secretKey = "V1uIRA21evh3AdoJr3ZPyUfQ78tp-q--fQ4-5pVm";
        //存储空间的名称
        String bucket = "hm31";

        Auth auth = Auth.create(accessKey, secretKey);
        BucketManager bucketManager = new BucketManager(auth, cfg);
        try {
            bucketManager.delete(bucket, imgName);
        } catch (QiniuException ex) {
            //如果遇到异常，说明删除失败
            System.err.println(ex.code());
            System.err.println(ex.response.toString());
        }
    }
}
