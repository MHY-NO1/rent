package com.aliapp.rent.controller;

import com.aliapp.rent.util.ResponseEntity;
import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

@RestController
@RequestMapping("upload")
public class UploadController {
    private static final String IMAGE_PATH="http://qqxafovhz.hd-bkt.clouddn.com/";

    @PostMapping
    public ResponseEntity upload(MultipartFile file) {
        //构造一个带指定 Region 对象的配置类
        Configuration cfg = new Configuration(Region.region0());
//...其他参数参考类注释
        UploadManager uploadManager = new UploadManager(cfg);
//...生成上传凭证，然后准备上传
        String accessKey = "7Itc0uyREhTO21f_kxKQj6oNSJsbX6yce5txE47s";
        String secretKey = "LtAfk4ff8Qua-PB2O6XiYzKPU77eHyv-AH7PvBMC";
        String bucket = "ming-aliapp";
//默认不指定key的情况下，以文件内容的hash值作为文件名
        String key = null;
        try {
//            byte[] uploadBytes = "hello qiniu cloud".getBytes("utf-8");
//            ByteArrayInputStream byteInputStream = new ByteArrayInputStream(uploadBytes);
            InputStream inputStream = file.getInputStream();
            Auth auth = Auth.create(accessKey, secretKey);
            String upToken = auth.uploadToken(bucket);
            try {
                Response response = uploadManager.put(inputStream, key, upToken, null, null);
                //解析上传成功的结果
                DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
//                System.out.println(putRet.key);
//                System.out.println(putRet.hash);
                return ResponseEntity.success(IMAGE_PATH+putRet.hash);
            } catch (QiniuException ex) {
                Response r = ex.response;
                System.err.println(r.toString());
                try {
                    System.err.println(r.bodyString());
                } catch (QiniuException ex2) {
                    //ignore
                }
            }
        } catch (UnsupportedEncodingException ex) {
            //ignore
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResponseEntity.fail();
    }
}
