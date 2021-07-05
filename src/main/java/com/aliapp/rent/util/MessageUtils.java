package com.aliapp.rent.util;

import com.aliyun.dysmsapi20170525.Client;
import com.aliyun.dysmsapi20170525.models.SendSmsRequest;
import com.aliyun.teaopenapi.models.Config;

/**
 * @author sda
 */
public class MessageUtils {
    private static final String ACCESS_KEY_ID = "您的密钥";
    //你的accessKeyId,参考本文档步骤2
    private static final String ACCESS_KEY_SECRET = "您的密钥";
    //你的accessKeySecret，参考本文档步骤2

    /**
     * 使用AK&SK初始化账号Client
     *
     * @return Client
     * @throws Exception
     */
    public static Client createClient() {
        Config config = new Config()
                // 您的AccessKey ID
                .setAccessKeyId(ACCESS_KEY_ID)
                // 您的AccessKey Secret
                .setAccessKeySecret(ACCESS_KEY_SECRET);
        // 访问的域名
        config.endpoint = "dysmsapi.aliyuncs.com";
        try {
            return new Client(config);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void sendMessage(String phone, String pname, String type) {
        Client client = MessageUtils.createClient();
        String signName = "黄金甲租赁";
        String templateCode;
        if ("发货".equals(type)) {
            //发货通知
            templateCode = "SMS_218167354";
        } else if ("提醒发货".equals(type)) {
            //提醒发货
            templateCode = "SMS_218167347";
        } else if ("提醒到期归还".equals(type)) {
            //提醒到期归还
            templateCode = "SMS_218162432";
        } else {
            //收货通知
            templateCode = "SMS_218157402";
        }

        SendSmsRequest sendSmsRequest = new SendSmsRequest()
                .setPhoneNumbers(phone)
                .setSignName(signName)
                .setTemplateCode(templateCode)
                .setTemplateParam("{\"name\":\"" + pname + "\"}");
        // 复制代码运行请自行打印 API 的返回值

        try {
            client.sendSms(sendSmsRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
