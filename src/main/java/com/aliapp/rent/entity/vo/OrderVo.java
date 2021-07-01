package com.aliapp.rent.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderVo implements Serializable {
    private static final long serialVersionUID = -6944829828804282149L;

    //订单ID
    private Integer id;
    //订单号
    private String tradeNo;
    //商品ID
    private Integer pid;
    //选择的租期套餐ID
    private Integer tcid;
    //用户ID
    private Integer uid;
    //购买数量
    private Integer num;
    //状态（0：未支付；1：已支付；2：取消支付）
    private Integer state;
    //订单总金额
    private Double totalAmount;
    //已支付金额
    private Double paidAmount;
    //创建订单时间
    private Date createTime;
    //备注
    private String memo;
    //收货人手机号
    private String phone;
    //收货人地址
    private String address;
    //预授权ID
    private Integer preid;
    //外部订单号
    private String outTradeNo;
    //收货人姓名
    private String name;
    //服务订单ID
    private Integer sid;
    //总租金
    private Double depositeAmount;
    //总押金
    private Double rentAmount;
    //是否申请退款
    private Integer applyRefund;
    //退款理由
    private String refundReason;
    //退款操作流水号（多次退款确保唯一）
    private String refundReqNo;
    //租品到期时间
    private Date expTime;
    //逾期赔偿金额
    private Double compensateAmount;
    //评论
    private String remark;
    //是否有评论
    private Integer hasRemark;
    //订单过期时间
    private Date closeTime;
    //转账时间 3天后
    private Date transferTime;
    //转账状态（0：未转账；1：已转账）
    private Integer transferState;
    //逾期金额转账状态（0：正常；1：有逾期未转账）
    private Integer compensateTransferState;

    //套餐
    private String cid;
    //租期
    private Integer tid;


    //授权码
    private String authCode;

    //图片地址
    private String picUrl;
    //标题
    private String title;

    //调起收银台字符串
    private String orderStr;

    //发布者ID
    private String publisherId;

    //formId
    private String formId;

    //续租金额
    private Double reletAmount;

    //订单数
    private Integer count;
    //成交金额
    private Double rentAmountSum;
    //抽成金额
    private Double money;
    //逾期数
    private Integer expireNum;
    //分类ID
    private Integer categoryId;
    //商品名称
    private String pname;
}
