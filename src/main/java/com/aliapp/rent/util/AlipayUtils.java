package com.aliapp.rent.util;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.parser.Feature;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.CertAlipayRequest;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.*;
import com.alipay.api.internal.util.AlipayEncrypt;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.*;
import com.alipay.api.response.*;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class AlipayUtils {
    //    真实环境
    private static final String APP_ID = "2021002133636540";
    private static final String APP_PRIVATE_KEY =
            "MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQCKSqAlyxLihwVIgEitZ0jrwc0Pz7XzCoQfy" +
                    "/6MpRNWEskBpo0FL2A9v6Jdpn9zFmg9oKrlEhlVaEQg8YRiZDn9mBGjqRtzA0PM2rrtGuPcT7sTAgyExVGjryiaGND778tOnzkZtRayCeTSJeu6oZnkV8vEff+75MzWo5qD0iCYxkCsU32SUupS2rZPFgzdppbiOtmtQjNSbjuM40X3QqeBAL6ZPQXjn0BUzGd5oAfDU0EykeP/EgpacMEM9aH2DrlFChlGXMRtI7axIsNmO5fS8GhNhVejIbdNAd+VkGmRsDa/nQE5T4xS4NsTYumdVLYlVoO42EDgtB9eV0e6XytBAgMBAAECggEAAOWU/km/2Jo5jYvPbNhzn5vFIaoeKvJRgwdGolDbvD4o8YrL9VGmVSAuBF91CiEGEgg9s3iXk3c2oeQPELhHq/5VdvM8y1UlFmoAtlLrUV1vACOOQzooWmBjagRpLokSj3L24tTKzeLEihxW2AH+Rn3PQ3nxs507OtUaxmoRbgwwiBPpyHfPoeokbUrb30HzsIPRPGu7tcRMnadudf9mH9Qwu5cuvTT/C2TIkVRrNsC9V58l+ddbZpsTpf32SI3ljN+JOuVFXnJOmdoANxJ0hZRvcPbCTbboJ69cCwaC98P2XQL4MZcFZRTyPFvbH90vwxoW0GAVckkIJRiD+N2gAQKBgQD+CUxJOPj+18kJkW/u0o/Ey/xMeKhFOp6ZOiNXoHt5IJ/KwbqaPkoct1C36y2LsHZbjDond8dSqpYrLUXIYN7SFhNrJi6wW6UFvXZD4sMYVklByEssXFb/gqMvo0QSkCWKHgdG3fOmY2H3eBbrh0gY2lHVU/HfnKqEnn0pynDNwQKBgQCLXEjx3E5qI90QeDTGociL7sk+J1434Zf8tE1qKknRehcgjEQt+J3fUHxPttAfz21nilbGLCLRAhaq6ilb+3qIJnqIhRLQLDGPuhZ41/+rcAeDpQgDPGzUWNFMU4uXSnfsfwzew2/I9eqE2n0YJ+6409eOMI6LNUsAeGBml6C9gQKBgEt2X57wuaaaWzHobiO3kNWY8t2Uigfmne5Oguko1wToWA9W9SOUlVdqbSP1TJZOWRpg0XWf8ynvWqSA0lXv0MD/pc0SjpP85UyLN3J7DhFQsHpGEW6gD4ffOrKQAUyyXTRmLysyC4rFwvhMSCK3EQew8tY4FYWjeFPk8LFVXaUBAoGAM3ZDtZSeHFHcC33oN0V88jeeduZAw6/52SaXw9GT6tgD/E/vyW4almF5VlJy7pxBkU+jlZCs1rPHvRu1ZDMsX2SeMRW7CcaYErQsIjnZPuyiyblCiTdiFNQrjdz4Gkk0mc+c6BIRkItHLAIm2am9XOFVoztJGBz48laBesEZaYECgYBHM/zrZlHRdNxPj2dN/Jrk2xozD4dLioxHOrnUvOcVdBtOlvEIoA2ZHtYQTQxGUQDVgbxE0zntdjlHJ767KHrhxpnk3jhSA/1qDdiwh2DjarWyraQqXMG/GsOsW2zE4FsGzINMEL4K1UjeJMm0/G48rqyvYDoej7y4mVsrUeWKbA==";
    private static final String CHARSET = "utf-8";
    private static final String SIGN_TYPE = "RSA2";
    private static final String SERVER_URL = "https://openapi.alipay.com/gateway.do";
    private static final String FORMAT = "JSON";
    //本地的证书
    private static final String ALIPAY_PUBLIC_KEY_URL = "C:\\Users\\sda\\Desktop" +
            "\\cert\\alipayCertPublicKey_RSA2.crt";
    private static final String CERT_PATH = "C:\\Users\\sda\\Desktop\\cert" +
            "\\appCertPublicKey_2021002133636540.crt";
    private static final String ROOT_CERT_PATH = "C:\\Users\\sda\\Desktop\\cert" +
            "\\alipayRootCert.crt";
    //服务器上的证书
    // private static final String ALIPAY_PUBLIC_KEY_URL =
    //         "C:\\rent\\cert\\alipayCertPublicKey_RSA2.crt";
    // private static final String CERT_PATH =
    //         "C:\\rent\\cert\\appCertPublicKey_2021002133636540.crt";
    // private static final String ROOT_CERT_PATH = "C:\\rent\\cert\\alipayRootCert.crt";

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");

    public static AlipayClient getAlipayClient() {
        CertAlipayRequest certAlipayRequest = new CertAlipayRequest();
        certAlipayRequest.setServerUrl(SERVER_URL);
        certAlipayRequest.setAppId(APP_ID);
        certAlipayRequest.setPrivateKey(APP_PRIVATE_KEY);
        certAlipayRequest.setFormat(FORMAT);
        certAlipayRequest.setCharset(CHARSET);
        certAlipayRequest.setSignType(SIGN_TYPE);
        certAlipayRequest.setCertPath(CERT_PATH);
        certAlipayRequest.setAlipayPublicCertPath(ALIPAY_PUBLIC_KEY_URL);
        certAlipayRequest.setRootCertPath(ROOT_CERT_PATH);
        AlipayClient alipayClient = null;
        try {
            alipayClient = new DefaultAlipayClient(certAlipayRequest);
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        return alipayClient;
    }

    public static String getPhone(String response) {
//1. 获取验签和解密所需要的参数
        Map<String, String> openapiResult = JSON.parseObject(response,
                new TypeReference<Map<String, String>>() {
                }, Feature.OrderedField);
        String encryptType = "AES";
        String sign = openapiResult.get("sign");
        String content = openapiResult.get("response");

//如果密文的
        boolean isDataEncrypted = !content.startsWith("{");
        boolean signCheckPass = false;

//2. 验签
        String signContent = content;
        // String signVeriKey = "你的小程序对应的支付宝公钥（为扩展考虑建议用appId+signType做密钥存储隔离）";
        String decryptKey = "6wPxQHxfsWnBEmp/5CLUSw==";
//如果是加密的报文则需要在密文的前后添加双引号
        if (isDataEncrypted) {
            signContent = "\"" + signContent + "\"";
        }
        try {
            signCheckPass = AlipaySignature.rsaCertCheck(signContent, sign, ALIPAY_PUBLIC_KEY_URL, CHARSET, SIGN_TYPE);
            // signCheckPass = AlipaySignature.rsaCheck(signContent, sign, signVeriKey, charset, signType);
        } catch (AlipayApiException e) {
            //验签异常, 日志
        }
        if (!signCheckPass) {
            //验签不通过（异常或者报文被篡改），终止流程（不需要做解密）
            try {
                throw new Exception("验签失败");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

//3. 解密
        String plainData = null;
        if (isDataEncrypted) {
            try {
                plainData = AlipayEncrypt.decryptContent(content, encryptType, decryptKey, CHARSET);
            } catch (AlipayApiException e) {
                //解密异常, 记录日志
                try {
                    throw new Exception("解密异常");
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        } else {
            plainData = content;
        }
        return plainData;

//
// //1. 获取解密所需要的参数
//         Map<String, String> openapiResult = JSON.parseObject(response,
//                 new TypeReference<Map<String, String>>() {
//                 }, Feature.OrderedField);
//         String charset = "UTF-8";
//         String encryptType = "AES";
//         String content = openapiResult.get("response");
//         // 判断是否为加密内容
//         boolean isDataEncrypted = !content.startsWith("{");
//         String decryptKey = "6wPxQHxfsWnBEmp/5CLUSw==";//
//         // AES密钥，这里参数不能写成固定的，开发阶段需传入应用的AES密钥，实例化后应传入商家小程序的AES密钥
// // 2.解密
//         String plainData = null;
//         if (isDataEncrypted) {
//             try {
//                 System.out.println("AlipayEncrypt");
//                 plainData = AlipayEncrypt.decryptContent(content, encryptType,
//                         decryptKey, charset);
//                 System.out.println("AlipayEncrypt Trance done");
//             } catch (AlipayApiException e) {
//                 //解密异常, 记录日志
//                 try {
//                     throw new Exception("解密异常");
//                 } catch (Exception e1) {
//                     // TODO Auto-generated catch block
//                     e1.printStackTrace();
//                 }
//             }
//         } else {
//             plainData = content;
//         }
//         System.out.println(plainData);
//         return plainData;
    }

    public static String getTradeNo(String outTradeNo, String totalAmount, String pname
            , String buyerId) {

        AlipayClient alipayClient = getAlipayClient();

        AlipayTradeCreateRequest request = new AlipayTradeCreateRequest();
//SDK已经封装掉了公共参数，这里只需要传入业务参数。
        request.setBizContent("{" +
                "\"out_trade_no\":\"" + outTradeNo + "\"," +// 可以随机生成订单号：String
                // outTradeNo = UUID.randomUUID()
                // .toString().replace("-", "");
                "\"total_amount\":" + totalAmount + "," +
                "\"subject\":\"" + pname + "\"," +
                "\"buyer_id\":\"" + buyerId + "\"" + // 小程序支付场景中该参数必传
                "}");
        try {
            AlipayTradeCreateResponse response = alipayClient.certificateExecute(request);
            // 获取返回的tradeNO。
            return response.getTradeNo();
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        return null;
    }

    //支付查询
    public static String tradeQuery(String tradeNo) {
        AlipayClient alipayClient = getAlipayClient();
        AlipayTradeQueryModel model = new AlipayTradeQueryModel();
        //授权转支付时的outTradeNo
        model.setTradeNo(tradeNo);
        AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();
        request.setBizModel(model);
        try {
            AlipayTradeQueryResponse response = alipayClient.certificateExecute(request);
            return response.getBody();
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        return null;
    }

    //退款 tradeNo
    public static void tradeRefund(String tradeNo, String amount) {
        AlipayClient alipayClient = getAlipayClient();
        AlipayTradeRefundModel model = new AlipayTradeRefundModel();
        model.setTradeNo(tradeNo);
        model.setRefundAmount(amount);
        model.setRefundReason("金额退款测试");
        AlipayTradeRefundRequest request = new AlipayTradeRefundRequest();
        request.setBizModel(model);
        try {
            alipayClient.certificateExecute(request);
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
    }

    //获取用户userid接口
    public static String getUserId(String authCode) {

        AlipayClient alipayClient = getAlipayClient();

        AlipaySystemOauthTokenRequest request = new AlipaySystemOauthTokenRequest();
        request.setGrantType("authorization_code");
        request.setCode(authCode);
        AlipaySystemOauthTokenResponse response = null;
        try {
            response = alipayClient.certificateExecute(request);
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        if (response != null && response.isSuccess()) {
            System.out.println("getUserId调用成功");
        } else {
            System.out.println("getUserId调用失败");
        }
        return response.getUserId();
    }

    //资金冻结接口
    public static String fundAuthOrderAppFreeze(String orderTitle,
                                                String amount, String notifyUrl,
                                                String alipayCategory, String outOrderNo,
                                                String reqNo) {
        AlipayClient alipayClient = getAlipayClient();
        AlipayFundAuthOrderAppFreezeRequest request =
                new AlipayFundAuthOrderAppFreezeRequest();
        AlipayFundAuthOrderAppFreezeModel model = new AlipayFundAuthOrderAppFreezeModel();
        model.setOrderTitle(orderTitle);
        model.setOutOrderNo(outOrderNo); //替换为实际订单号
        model.setOutRequestNo(reqNo); //替换为实际请求单号，保证每次请求都是唯一的
        model.setPayeeUserId("2088141082766380"); //payee_user_id,Payee_logon_id不能同时为空
        model.setProductCode("PRE_AUTH_ONLINE"); //PRE_AUTH_ONLINE为固定值，不要替换
        model.setAmount(amount);//冻结金额
        //需要支持信用授权，该字段必传
        model.setExtraParam("{\"category\":\"" + alipayCategory + "\",\"serviceId" +
                "\":\"2021042100000000000085223800\"," +
                "\"outStoreCode\":\"code0011\"}");  //outStoreAlias将在用户端信用守护、支付信息、账单详情页展示
        //选填字段，指定支付渠道
        //model.setEnablePayChannels("[{\"payChannelType\":\"PCREDIT_PAY\"},
        // {\"payChannelType\":\"MONEY_FUND\"},
        // {\"payChannelType\":\"CREDITZHIMA\"}]");
        request.setBizModel(model);
        request.setNotifyUrl(notifyUrl); //异步通知地址，必填，该接口只通过该参数进行异步通知
        AlipayFundAuthOrderAppFreezeResponse response = null; //注意这里是sdkExecute，可以获取签名参数
        try {
            response = alipayClient.sdkExecute(request);
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        if (response.isSuccess()) {
            System.out.println("资金冻结调用成功");
            return response.getBody();
//            logger.info("response: {}" + response.getBody()); //签名后的参数，直接入参到
        } else {
            System.out.println("资金冻结调用失败");
        }
        return null;
    }


    //预授权信息同步，使用场景为信用授权或部分信用授权，目前不调用
    public static void tradeInfoSync() {
        AlipayClient alipayClient = getAlipayClient();
        AlipayTradeOrderinfoSyncRequest request = new AlipayTradeOrderinfoSyncRequest();
        AlipayTradeOrderinfoSyncModel model = new AlipayTradeOrderinfoSyncModel();
        model.setBizType("CREDIT_AUTH");
        model.setTradeNo("2018061021001004680073956707");
        model.setOutRequestNo("HZ01RF001");
        model.setOrderBizInfo("{\"status\":\"COMPLETE\"}");

        request.setBizModel(model);
        AlipayTradeOrderinfoSyncResponse response = null;
        try {
            response = alipayClient.certificateExecute(request);
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
    }

    //资金授权操作查询 冻结、解冻
    public static void fundAuthQuery(String authNo, String outReqNo) {
        AlipayClient alipayClient = getAlipayClient();
        AlipayFundAuthOperationDetailQueryRequest request =
                new AlipayFundAuthOperationDetailQueryRequest();
        AlipayFundAuthOperationDetailQueryModel model =
                new AlipayFundAuthOperationDetailQueryModel();
        model.setAuthNo(authNo); // 支付宝资金授权订单号，在授权冻结成功时返回参数中获得
        model.setOutRequestNo(outReqNo);
        //商户的授权资金操作流水号，与支付宝的授权资金操作流水号不能同时为空，
        // 该值为冻结或解冻时的outRequestNo
        request.setBizModel(model);
        try {
            alipayClient.certificateExecute(request);
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
    }

    //退款查询
    public static void refundQuery(String tradeNo, String outReqNo) {
        AlipayClient alipayClient = getAlipayClient();
        AlipayTradeFastpayRefundQueryModel model =
                new AlipayTradeFastpayRefundQueryModel();
        //退款时请求号
        model.setOutRequestNo(outReqNo);
        model.setTradeNo(tradeNo);
        AlipayTradeFastpayRefundQueryRequest request =
                new AlipayTradeFastpayRefundQueryRequest();
        request.setBizModel(model);
        AlipayTradeFastpayRefundQueryResponse response = null;
        try {
            response = alipayClient.certificateExecute(request);
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        if (response.isSuccess()) {
            System.out.println("退款查询调用成功");
        } else {
            System.out.println("退款查询调用失败");
        }
    }

    //交易查询
    //如果查询返回处理中（如：trade_ status=WAIT_BUYER_PAY或ORDER SUCCESS PAY INPROGRESS），
    // 商户可以调用预授权转支付，重新发起扣款（注意：商户订单号out_trade_no保持不变）
    public static String preAuthTradeQuery(String outTradeNo) {
        AlipayClient alipayClient = getAlipayClient();
        AlipayTradeQueryModel model = new AlipayTradeQueryModel();
        //授权转支付时的outTradeNo
        model.setOutTradeNo(outTradeNo);
        AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();
        request.setBizModel(model);
        try {
            AlipayTradeQueryResponse response = alipayClient.certificateExecute(request);
            return response.getBody();
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        return null;
    }

    //资金解冻接口
    public static String fundAuthOrderUnFreeze(String authNo, String amount,
                                               String notifyUrl) {
        Date date = new Date();

        AlipayClient alipayClient = getAlipayClient();
        AlipayFundAuthOrderUnfreezeRequest request =
                new AlipayFundAuthOrderUnfreezeRequest();
        AlipayFundAuthOrderUnfreezeModel model = new AlipayFundAuthOrderUnfreezeModel();
        model.setAuthNo(authNo);  // 支付宝资金授权订单号，在授权冻结成功时返回需要入库保存
        model.setOutRequestNo(dateFormat.format(date) + (int) Math.random() * 10000);
        //同一商户每次不同的资金操作请求，商户请求流水号不能重复,
        // 且与冻结流水号不同
        model.setAmount(amount);  // 本次操作解冻的金额，单位为：元（人民币），精确到小数点后两位
        model.setRemark("预授权解冻");  // 商户对本次解冻操作的附言描述，长度不超过100个字母或50个汉字
        //选填字段，信用授权订单，针对信用全免订单，传入该值完结信用订单，形成芝麻履约记录
        //暂不支持芝麻信用
        /*if (waiver) {
            model.setExtraParam("{\"unfreezeBizInfo\":\"{\\\"bizComplete\\\":\\\"true
            \\\"}\"}");
        }*/
        request.setBizModel(model);
        request.setNotifyUrl(notifyUrl); //异步通知地址，必填，该接口只通过该参数进行异步通知

        try {
            AlipayFundAuthOrderUnfreezeResponse response =
                    alipayClient.certificateExecute(request);
            return response.getBody();
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        return null;
    }

    //异步通知接口
    public static Map asyncCertificate(HttpServletRequest request) {
        Map<String, String> params = new HashMap<>();
        Map requestParams = request.getParameterMap();
        for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            //乱码解决，这段代码在出现乱码时使用。
            //valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
            params.put(name, valueStr);
        }
        boolean flag = false;
        //切记alipaypublickey是支付宝的公钥，请去open.alipay.com对应应用下查看。
        //boolean AlipaySignature.rsaCertCheckV1(Map<String, String> params, String
        // publicKeyCertPath, String
        // charset,String signType)
        try {
            flag = AlipaySignature.rsaCertCheckV1(params, ALIPAY_PUBLIC_KEY_URL, "utf-8"
                    , "RSA2");
            System.out.println("异步通知验签:");
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        if (flag) {
            // TODO 验签成功后
            System.out.println("success");
            return params;
        } else {
            System.out.println("fail");
        }
        return null;
    }

    //授权转支付接口
    public static void tradePay(String outTradeNo, String authNo, String totalAmount,
                                String sellerId, String buyerId,
                                String notifyUrl, String authConfirmMode) {
        AlipayClient alipayClient = getAlipayClient();

        AlipayTradePayRequest request = new AlipayTradePayRequest();

        AlipayTradePayModel model = new AlipayTradePayModel();
        model.setOutTradeNo(outTradeNo);  // 预授权转支付商户订单号，为新的商户交易流水号；如果重试发起扣款，商户订单号不要变；
        model.setProductCode("PRE_AUTH_ONLINE");  // 固定值PRE_AUTH_ONLINE
        model.setAuthNo(authNo);  // 填写预授权冻结交易号
        model.setSubject("预授权转支付测试");  // 解冻转支付标题，用于展示在支付宝账单中
        model.setTotalAmount(totalAmount);  // 结算支付金额
        // model.setSellerId(sellerId);  // 填写卖家支付宝账户pid
        model.setSellerId("2088141082766380"); // 固定写平台方userid
        model.setBuyerId(buyerId);  // 填写预授权用户uid，通过预授权冻结接口返回的payer_user_id字段获取
        model.setBody("预授权解冻转支付");  // 可填写备注信息
        model.setAuthConfirmMode(authConfirmMode); //必须使用COMPLETE,传入该值用户剩余金额会自动解冻
        request.setBizModel(model);
        request.setNotifyUrl(notifyUrl); //异步通知地址，必填，该接口只通过该参数进行异步通知

        AlipayTradePayResponse response = null;
        try {
            response = alipayClient.certificateExecute(request);
            System.out.println("授权转支付:");
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        if (response.isSuccess()) {
            System.out.println("授权转支付调用成功");
        } else {
            System.out.println("授权转支付调用失败");
        }
    }

    //授权转支付退款
    public static void refund(String outTradeNo, String amount) {
        AlipayClient alipayClient = getAlipayClient();

        Date date = new Date();
        String outReqNo = dateFormat.format(date) + (int) Math.random() * 10000;

        AlipayTradeRefundModel model = new AlipayTradeRefundModel();
        model.setOutTradeNo(outTradeNo); //与预授权转支付商户订单号相同，代表对该笔交易退款
        model.setRefundAmount(amount);
        model.setRefundReason("预授权退款测试");
        model.setOutRequestNo(outReqNo);//标识一次退款请求，同一笔交易多次退款需要保证唯一，如部分退款则此参数必传。
        AlipayTradeRefundRequest request = new AlipayTradeRefundRequest();
        request.setBizModel(model);
        try {
            alipayClient.certificateExecute(request);
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
    }

    //商家转账至卖家支付宝
    // public static void transfer(String outBizNo, String amount, String payeeUserId) {
    //     AlipayClient alipayClient = getAlipayClient();
    //     AlipayFundTransUniTransferRequest request =
    //             new AlipayFundTransUniTransferRequest();
    //     request.setBizContent("{" +
    //             "\"out_biz_no\":\"" + outBizNo + "\"," +
    //             "\"trans_amount\":" + amount + "," +
    //             "\"product_code\":\"TRANS_ACCOUNT_NO_PWD\"," +
    //             "\"order_title\":\"租金\"," +
    //             "\"payee_info\":{" +
    //             "\"identity\":\"" + payeeUserId + "\"," +
    //             "\"identity_type\":\"ALIPAY_USER_ID\"," +
    //             "}," +
    //             "}");
    //     AlipayFundTransUniTransferResponse response = null;
    //     try {
    //         response = alipayClient.certificateExecute(request);
    //     } catch (AlipayApiException e) {
    //         e.printStackTrace();
    //     }
    //     if (response.isSuccess()) {
    //         System.out.println("商家转账调用成功");
    //     } else {
    //         System.out.println("商家转账调用失败");
    //     }
    // }

    /**
     * 单笔转账
     *
     * @param outBizNo
     * @param amount
     * @param payeeUserId
     */
    public static boolean transfer(String outBizNo, String amount, String payeeUserId) {
        AlipayClient alipayClient = getAlipayClient();

        /** 实例化具体API对应的request类，类名称和接口名称对应,当前调用接口名称：alipay.fund.trans.uni.transfer(单笔转账接口) **/
        AlipayFundTransUniTransferRequest request = new AlipayFundTransUniTransferRequest();

        /** 设置业务参数，具体接口参数传值以文档说明为准：https://opendocs.alipay.com/apis/api_28/alipay.fund.trans.uni.transfer/ **/
        AlipayFundTransUniTransferModel model = new AlipayFundTransUniTransferModel();

        /** 商户端的唯一订单号，对于同一笔转账请求，商户需保证该订单号唯一 **/
        model.setOutBizNo(outBizNo);

        /** 转账金额，TRANS_ACCOUNT_NO_PWD产品取值最低0.1  **/
        model.setTransAmount(amount);

        /** 产品码，单笔无密转账到支付宝账户固定为：TRANS_ACCOUNT_NO_PWD **/
        model.setProductCode("TRANS_ACCOUNT_NO_PWD");

        /** 场景码，单笔无密转账到支付宝账户固定为：DIRECT_TRANSFER  **/
        model.setBizScene("DIRECT_TRANSFER");

        /** 转账业务的标题，用于在支付宝用户的账单里显示 **/
        model.setOrderTitle("租金");

        Participant participant = new Participant();

        /** 参与方的唯一标识,收款支付宝账号或者支付宝吧账号唯一会员ID **/
        participant.setIdentity(payeeUserId);

        /** 参与方的标识类型：ALIPAY_USER_ID 支付宝的会员ID  **/
        participant.setIdentityType("ALIPAY_USER_ID");

        /** 参与方真实姓名，如果非空，将校验收款支付宝账号姓名一致性。当identity_type=ALIPAY_LOGON_ID时，本字段必填 **/
        // participant.setName("张三");

        model.setPayeeInfo(participant);

        /** 业务备注  **/
        model.setRemark("单笔转账");

        request.setBizModel(model);

        AlipayFundTransUniTransferResponse response = null;

        try {

            response = alipayClient.certificateExecute(request);

        } catch (AlipayApiException e) {

            e.printStackTrace();
        }

        /** 获取接口调用结果，如果调用失败，可根据返回错误信息到该文档寻找排查方案：https://opensupport.alipay.com/support/helpcenter/107 **/
        // System.out.println(response.getBody());

        return "SUCCESS".equals(response.getStatus());
    }

    /**
     * 转账查询
     * status:
     * SUCCESS：转账成功；
     * WAIT_PAY：等待支付；
     * CLOSED：订单超时关闭；
     *
     * @param outBizNo
     */
    public static void transferQuery(String outBizNo) {
        AlipayClient alipayClient = getAlipayClient();
        AlipayFundTransCommonQueryRequest request =
                new AlipayFundTransCommonQueryRequest();
        request.setBizContent("{" +
                "\"product_code\":\"TRANS_ACCOUNT_NO_PWD\"," +
                "\"biz_scene\":\"DIRECT_TRANSFER\"," +
                "\"out_biz_no\":\"" + outBizNo + "\"," +
                "  }");
        AlipayFundTransCommonQueryResponse response = null;
        try {
            response = alipayClient.certificateExecute(request);
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        if (response.isSuccess()) {
            System.out.println("转账查询调用成功");
        } else {
            System.out.println("转账查询调用失败");
        }
    }

}
