package com.aliapp.rent.util;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.request.*;
import com.alipay.api.response.*;

public class AlipayOrder {
    private static AlipayClient alipayClient;

    static {
        alipayClient = AlipayUtils.getAlipayClient();
    }

    //保留订单中心
   /* //订单中心

    //商品文件上传
    public static AlipayMerchantItemFileUploadResponse setPicture(String picUrl) {
        AlipayMerchantItemFileUploadRequest request = new AlipayMerchantItemFileUploadRequest();
        request.setScene("SYNC_ORDER");
        FileItem FileContent = new FileItem(picUrl);
        request.setFileContent(FileContent);
        AlipayMerchantItemFileUploadResponse response = null;
        try {
            response = alipayClient.certificateExecute(request);
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        if (response.isSuccess()) {
            System.out.println("上传图片成功");
            return response;
        } else {
            System.out.println("上传图片失败");
        }
        return null;
    }

    //创建商品接口
    public static AntMerchantExpandItemOpenCreateResponse setProduct(
            String standardCategoryId,Integer pid,String materialKey,
                                  String name, String price, String originalPrice) {
        AntMerchantExpandItemOpenCreateRequest request = new AntMerchantExpandItemOpenCreateRequest();
        request.setBizContent("{" +
                "\"scene\":\"APP_ORDER\"," +
                "\"target_id\":\"2021002133636540\"," +
                "\"target_type\":\"8\"," +
                "\"standard_category_id\":\"" + standardCategoryId + "\"," +
                "\"name\":\"" + name + "\"," +
                "\"type\":\"STANDARD_GOODS\"," +
                "      \"sku_list\":[{" +
                "        \"price\":" + price + "," +
                "\"original_price\":" + originalPrice + "," +
                "        }]," +
                "\"ext_info\":[{" +
                "\"ext_key\":\"DETAIL_URL\"," +
                "\"ext_value\":\""+"pages/productDetail/productDetail?pid=" + pid+"\"" +
                "}]," +
                "      \"material_list\":[{" +
                "        \"type\":\"ITEM_PIC\"," +
                "\"content\":\""+materialKey +"\"" +
                "        }]," +
                "  }");
        AntMerchantExpandItemOpenCreateResponse response = null;
        try {
            response = alipayClient.certificateExecute(request);
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        if (response.isSuccess()) {
            System.out.println("上传商品信息成功");
            return response;
        } else {
            System.out.println("上传商品信息调用失败");
        }
        return null;
    }

    //同步非交易订单信息
    public static boolean setOrderSync(String formId, String orderModifiedTime,
                                    String buyerId, String trackingNo,
                                    String logisticCode, String orderCreTime,
                                    String outBizNo, Integer oid,
                                    String merchantOrderStatus, String name,
                                    String imageMaterialId, Integer pid) {
        AlipayMerchantOrderSyncRequest request = new AlipayMerchantOrderSyncRequest();
        request.setBizContent("{" +
                "\"out_biz_no\":\"" + outBizNo + "\"," +
                "\"buyer_id\":\"" + buyerId + "\"," +

                "\"order_type\":\"SERVICE_ORDER\"," +

                "\"order_auth_code\":\"" + formId + "\"," +

                "      \"logistics_info_list\":[{" +
                "        \"tracking_no\":\"" + trackingNo + "\"," +
                "\"logistics_code\":\"" + logisticCode + "\"" +
                "        }]," +

                "\"order_create_time\":\"" + orderCreTime + "\"," +
                "\"order_modified_time\":\"" + orderModifiedTime + "\"," +
                "\"send_msg\":\"Y\"," +
                "      \"ext_info\":[{" +
                "        \"ext_key\":\"tiny_app_id\"," +
                "\"ext_value\":\"2021002133636540\"" +
                "}," +
                "{" +
                "        \"ext_key\":\"merchant_order_status\"," +
                "\"ext_value\":\"" + merchantOrderStatus + "\"" +//MERCHANT_WAITING_FOR_DELIVERY待发货
                "}," +
                "{" +
                "        \"ext_key\":\"merchant_order_link_page\"," +
                "\"ext_value\":\"" + "pages/pay-fail/pay-fail?oid=" + oid+ "\"" +
                "        }]" +
                "      \"item_order_list\":[{" +
                "\"item_name\":\"" + name + "\"," +
                "          \"ext_info\":[{" +
                "            \"ext_key\":\"image_material_id\"," +
                "\"ext_value\":\"" + imageMaterialId + "\"" +
                "},{" +
                "            \"ext_key\":\"merchant_item_link_page\"," +
                "\"ext_value\":\"" + "pages/productDetail/productDetail?pid=" + pid + "\"" +
                "            }]" +
                "        }]," +
                "  }");
        AlipayMerchantOrderSyncResponse response = null;
        try {
            response = alipayClient.certificateExecute(request);
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        if (response.isSuccess()) {
            System.out.println("调用成功");
            return true;
        } else {
            System.out.println("调用失败");
        }
        return false;
    }*/


    //订单信息同步【信用反馈专用】
    public static boolean orderInfoSync(String tradeNo, String origReqNo,
                                        String outReqNo, String status) {
        AlipayTradeOrderinfoSyncRequest request = new AlipayTradeOrderinfoSyncRequest();
        request.setBizContent("{" +
                "\"trade_no\":\"" + tradeNo + "\"," +
                "\"orig_request_no\":\"" + origReqNo + "\"," +
                "\"out_request_no\":\"" + outReqNo + "\"," +
                "\"biz_type\":\"CREDIT_AUTH\"," +
                "\"order_biz_info\":\"\\\"{\\\\\\\"status\\\\\\\":\\\\\\\"" + status + "\\\\\\\"}\\\"\"" +
                "  }");
        AlipayTradeOrderinfoSyncResponse response = null;
        try {
            response = alipayClient.certificateExecute(request);
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        if (response.isSuccess()) {
            System.out.println("同步成功");
            return true;
        } else {
            System.out.println("同步失败");
        }
        return false;
    }

    //信用订单退款
    public static boolean refund(String tradeNo, String refundAmount, String refundReason,
                                 String outReqNo) {
        AlipayTradeRefundRequest request = new AlipayTradeRefundRequest();
        request.setBizContent("{" +
                "\"trade_no\":\"" + tradeNo + "\"," +
                "\"refund_amount\":" + refundAmount + "," +
                "\"refund_currency\":\"CNY\"," +
                "\"refund_reason\":\"" + refundReason + "\"," +
                "\"out_request_no\":\"" + outReqNo + "\"," +
                "      \"query_options\":[" +
                "        \"refund_detail_item_list\"" +
                "      ]" +
                "  }");
        AlipayTradeRefundResponse response = null;
        try {
            response = alipayClient.certificateExecute(request);
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        if (response.isSuccess()) {
            System.out.println("退款成功");
            return true;
        } else {
            System.out.println("退款失败");
        }
        return false;
    }

    //支付并完结
    public static String tradePay(String outTradeNo, String authNo, String name,
                                  String buyerId, String sellerId, String amount,
                                  String title, String authConfirmMode) {
        AlipayTradePayRequest request = new AlipayTradePayRequest();
        request.setBizContent("{" +
                "\"out_trade_no\":\"" + outTradeNo + "\"," +
                "\"auth_no\":\"" + authNo + " \"," +
                "\"product_code\":\"PRE_AUTH_ONLINE\"," +
                "\"subject\":\"" + name + "\"," +
                "\"buyer_id\":\"" + buyerId + "\"," +
                "\"seller_id\":\"" + sellerId + "\"," +
                "\"total_amount\":" + amount + "," +
                "\"trans_currency\":\"CNY\"," +
                "\"settle_currency\":\"CNY\"," +
                "\"body\":\"" + title + "\"," +
                "\"timeout_express\":\"1h\"," +
                "\"business_params\":{\"tinyAppId\":\"2021002133636540\"},"+
// "\"auth_no\":\"2016110310002001760201905725\"," + // 仅预授权转支付场景使用
                "\"auth_confirm_mode\":\"" + authConfirmMode + "\"," +
                "  }");
        AlipayTradePayResponse response = null;
        try {
            response = alipayClient.certificateExecute(request);
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        if (response.isSuccess()) {
            System.out.println("调用成功");
            return response.getTradeNo();
        } else {
            System.out.println("调用失败");
        }
        return null;
    }

    //取消支付
    public static void tradeClose(String tradeNo) {
        AlipayTradeCloseRequest request = new AlipayTradeCloseRequest();
        request.setBizContent("{" +
                "\"trade_no\":\"" + tradeNo + "\"," +
                "  }");
        AlipayTradeCloseResponse response = null;
        try {
            response = alipayClient.certificateExecute(request);
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        if (response.isSuccess()) {
            System.out.println("调用成功");
        } else {
            System.out.println("调用失败");
        }
    }

    //免支付/取消订单/解冻订单
    public static void orderUnfreeze(String authNo, String outReqNo, String amount,
                                     String remark, String flag) {
        AlipayFundAuthOrderUnfreezeRequest request = new AlipayFundAuthOrderUnfreezeRequest();
        request.setBizContent("{" +
                "\"auth_no\":\"" + authNo + "\"," +
                "\"out_request_no\":\"" + outReqNo + "\"," +
                "\"amount\":" + amount + "," +
                "\"remark\":\"" + remark + "\"," +//2014-05期解冻200.00元
                "\"extra_param\":\"{\\\"unfreezeBizInfo\\\": \\\"{\\\\\\\"bizComplete\\\\\\\":\\\\\\\"" + flag + "\\\\\\\"}\\\"}\"" +
                "  }");
        AlipayFundAuthOrderUnfreezeResponse response = null;
        try {
            response = alipayClient.certificateExecute(request);
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        if (response.isSuccess()) {
            System.out.println("调用成功");
        } else {
            System.out.println("调用失败");
        }
    }

}
