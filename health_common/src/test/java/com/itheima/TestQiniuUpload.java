package com.itheima;

import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;

/**
 * @author 黑马程序员
 * @Company http://www.ithiema.com
 * @Version 1.0
 */
public class TestQiniuUpload {

    public static void main(String[] args) {
        //构造一个带指定 Region 对象的配置类
        Configuration cfg = new Configuration(Region.region0());
        //...其他参数参考类注释

        UploadManager uploadManager = new UploadManager(cfg);
        //...生成上传凭证，然后准备上传
        String accessKey = "0LgMC2u8lKrLeLzMEDG08olOu4XQsk2MHWHhcPHB";
        String secretKey = "V1uIRA21evh3AdoJr3ZPyUfQ78tp-q--fQ4-5pVm";
        //存储空间的名称
        String bucket = "hm31";
        //如果是Windows情况下，格式是 D:\\qiniu\\test.png
        String localFilePath = "F:\\31期--项目一传智健康\\4.传智健康\\传智健康-项目资料\\10.图片资源\\03a36073-a140-4942-9b9b-712cecb144901.jpg";
//默认不指定key的情况下，以文件内容的hash值作为文件名
        String key = null;
        //创建认证对象
        Auth auth = Auth.create(accessKey, secretKey);
        //生成上传凭证
        String upToken = auth.uploadToken(bucket);

        try {
            // 上传文件
            Response response = uploadManager.put(localFilePath, key, upToken);
            //解析上传成功的结果
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            System.out.println(putRet.key);
            System.out.println(putRet.hash);
        } catch (QiniuException ex) {
            Response r = ex.response;
            System.err.println(r.toString());
            try {
                System.err.println(r.bodyString());
            } catch (QiniuException ex2) {
                //ignore
            }
        }

    }
}
