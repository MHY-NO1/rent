package com.aliapp.rent.entity.e;

public enum MerchantOrderStatus {
    MERCHANT_WAIT_PAY,//待支付 （初始态）

    MERCHANT_PAID,//已付款 （初始态）

    MERCHANT_CONFIRMED,//商家已确认（中间态）

    MERCHENT_SERVICING,//订单服务中（中间态）

    MERCHANT_PACKING,//发货中（中间态）

    MERCHANT_DELIVERED,//已发货（中间态）

    MERCHANT_BORROWING,//借用中（中间态）

    MERCHANT_LOANING,//租赁中（中间态）

    MERCHANT_RETURNING,//归还中（中间态）

    MERCHANT_TO_BE_RECEIVED,//待收货（中间态）

    MERCHANT_WAIT_TO_BUYER_CONFIRM,//待用户确认（中间态）

    MERCHANT_WAITING_FOR_DELIVERY,//待发货 （中间态）

    MERCHANT_TO_BE_EXPIRED,//将到期（中间态）

    MERCHANT_SETTLING,//结算中（中间态）

    MERCHANT_SETTLED,//已结算（中间态）

    MERCHANT_EXPIRED,//已逾期（中间态）

    MERCHANT_RETURNED,//已归还（中间态）

    MERCHANT_FINISHED,//订单已完成（终态）

    MERCHANT_CLOSED,//订单关闭 （终态）

    MERCHANT_DELETE,//订单删除 （终态）

    MERCHANT_REFUNDED,//全额退款（终态）

}
