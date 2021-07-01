package com.aliapp.rent.controller;


import com.aliapp.rent.entity.po.BizApply;
import com.aliapp.rent.entity.po.RechargeRecord;
import com.aliapp.rent.entity.po.UserInfo;
import com.aliapp.rent.entity.po.Vip;
import com.aliapp.rent.entity.vo.UserInfoVo;
import com.aliapp.rent.service.*;
import com.aliapp.rent.util.AlipayUtils;
import com.aliapp.rent.util.ResponseEntity;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.BeanUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * (UserInfo)表控制层
 *
 * @author makejava
 * @since 2021-04-15 10:37:47
 */
@RestController
@RequestMapping("userInfo")
public class UserInfoController {
    /**
     * 服务对象
     */
    @Resource
    private UserInfoService userInfoService;

    @Resource
    private ProductService productService;

    @Resource
    private VipService vipService;

    @Resource
    private BizApplyService bizApplyService;

    @Resource
    private RechargeRecordService rechargeRecordService;

    /**
     * 分页查询所有数据
     *
     * @param page     分页对象
     * @param userInfo 查询实体
     * @return 所有数据
     */
    @GetMapping
    public ResponseEntity selectAll(Page<UserInfo> page, UserInfo userInfo) {
        return ResponseEntity.success(this.userInfoService.page(page,
                new QueryWrapper<>(userInfo)));
    }

    @GetMapping("list")
    public ResponseEntity list(UserInfoVo vo) {
        UserInfo userInfo = new UserInfo();
        BeanUtils.copyProperties(vo, userInfo);
        return ResponseEntity.success(this.userInfoService.list(new QueryWrapper<>(userInfo)));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public ResponseEntity selectOne(@PathVariable Serializable id) {
        return ResponseEntity.success(this.userInfoService.getById(id));
    }

    //验证是否是自己的商品
    @PostMapping("vertify")
    public ResponseEntity vertify(@RequestBody UserInfoVo vo) {
        if (!productService.getById(vo.getPid()).getPublisherId().equals(vo.getId())) {
            return ResponseEntity.success();
        }
        return ResponseEntity.fail();
    }

    @GetMapping("getPhoneNum")
    public ResponseEntity getPhoneNum(UserInfoVo vo) {
        String encryptedData = vo.getEncryptedData();
        //解密
        String response = AlipayUtils.getPhone(encryptedData);
        //获取手机号
        String mobile = JSON.parseObject(response).getString("mobile");
        //判断是否成功获取手机号
        if (StringUtils.hasText(mobile)) {
            // 存储手机号
            UserInfo userInfo = userInfoService.getById(vo.getId());
            userInfo.setPhone(mobile);
            userInfoService.updateById(userInfo);
            return ResponseEntity.success();
        }
        return ResponseEntity.fail();
    }

    /**
     * 新增数据
     *
     * @return 新增结果
     */
    @PostMapping
    public ResponseEntity insert(@RequestBody UserInfoVo vo) {
        if (StringUtils.hasText(vo.getAuthCode())) {
            //获取用户支付宝userid
            String userId = AlipayUtils.getUserId(vo.getAuthCode());
            if (!StringUtils.hasText(userId)) {
                return ResponseEntity.fail("用户授权失败！");
            }
            //查询用户是否存在
            UserInfo userInfo = new UserInfo();
            userInfo.setUserId(userId);
            UserInfo one = this.userInfoService.getOne(new QueryWrapper<>(userInfo));
            //存在
            if (one != null) {
                //判断会员是否被封禁
                if (one.getState() == 0) {
                    return ResponseEntity.build(302, "用户已被封禁！", one.getId());
                }
                if (StringUtils.hasText(one.getPhone())) {
                    return ResponseEntity.build(201, "用户已有手机号！", one.getId());
                }
                return ResponseEntity.success(one.getId());
            }
            //查询普通会员VID
            Vip vip = new Vip();
            vip.setLevel(0);
            Vip one1 = vipService.getOne(new QueryWrapper<>(vip));
            //不存在就新增
            userInfo.setVid(one1.getId());
            this.userInfoService.save(userInfo);
            return ResponseEntity.success(userInfo.getId());
        }
        return ResponseEntity.fail();
    }

    /**
     * 修改数据
     *
     * @return 修改结果
     */
    @PutMapping
    public ResponseEntity update(UserInfoVo vo) {
        UserInfo userInfo = new UserInfo();
        BeanUtils.copyProperties(vo, userInfo);
        return ResponseEntity.success(this.userInfoService.updateById(userInfo));
    }

    /**
     * 更新用户昵称
     *
     * @param vo
     * @return
     */
    @PutMapping("name")
    public ResponseEntity name(@RequestBody UserInfoVo vo) {
        UserInfo userInfo = new UserInfo();
        BeanUtils.copyProperties(vo, userInfo);
        return ResponseEntity.success(this.userInfoService.updateById(userInfo));
    }

    /**
     * 解封用户
     */
    @PutMapping("unseal")
    public ResponseEntity unseal(UserInfoVo vo) {
        UserInfo userInfo = new UserInfo();
        BeanUtils.copyProperties(vo, userInfo);
        //判断是否为商家
        QueryWrapper<BizApply> qw = new QueryWrapper<>();
        qw.eq("uid", userInfo.getId()).eq("state", 1);
        BizApply bizApply = bizApplyService.getOne(qw);
        if (bizApply != null) {
            //是商家
            userInfo.setState(2);
        } else {
            //不是商家
            userInfo.setState(1);
        }
        return ResponseEntity.success(this.userInfoService.updateById(userInfo));
    }


    /**
     * 充值会员
     */
    @PutMapping("recharge")
    public ResponseEntity recharge(@RequestBody UserInfoVo vo) {
        //判断支付是否成功
        String body = AlipayUtils.tradeQuery(vo.getTradeNo());
        String tradeStatus = JSONObject.parseObject(body).getJSONObject(
                "alipay_trade_query_response").getString(
                "trade_status");
        //成功
        if ("TRADE_SUCCESS".equalsIgnoreCase(tradeStatus)) {
            UserInfo userInfo = userInfoService.getById(vo.getId());
            Double amount;
            Integer month;
            //是否是升级
            if (vo.getMonth() != null && vo.getMonth() > 0) {
                //判断是续费还是充值
                month = vo.getMonth();
                Vip vip = vipService.getById(vo.getVid());
                amount = vip.getPrice() * vo.getMonth();
                Calendar instance = Calendar.getInstance();
                if (userInfo.getVid().equals(vo.getVid())) {
                    //续费
                    //更新VIP过期时间
                    Date vipExpirationTime = userInfo.getVipExpirationTime();
                    instance.setTime(vipExpirationTime);
                } else {
                    //充值
                    instance.setTime(new Date());
                    userInfo.setVid(vo.getVid());
                }
                instance.add(Calendar.DATE, month * 30);
                userInfo.setVipExpirationTime(instance.getTime());
            } else {
                //升级
                userInfo.setVid(vo.getVid());
                month = 0;
                amount = vo.getAmount();
            }
            //更新用户信息
            userInfoService.updateById(userInfo);
            //新增会员充值记录
            RechargeRecord rechargeRecord = new RechargeRecord();
            rechargeRecord.setMonth(month);
            rechargeRecord.setUid(vo.getId());
            rechargeRecord.setVid(vo.getVid());
            rechargeRecord.setAmount(amount);
            rechargeRecordService.save(rechargeRecord);
            //计算出会员到期时间

            //改变会员已发布商品过期时间
            //查询已发布商品
            // QueryWrapper<Product> qw = new QueryWrapper<>();
            // qw.eq("publisher_id", userInfo.getId())
            //         .eq("state", 1);
            // List<Product> list = productService.list(qw);
            //获取会员信息
            // Vip vip = vipService.getById(userInfo.getVid());
            // Calendar instance = Calendar.getInstance();
            //改变过期时间
            // for (Product product : list) {
            //     instance.setTime(product.getCreateDate());
            //     instance.add(Calendar.DATE, vip.getDay());
            //     product.setExpTime(instance.getTime());
            // }
            //更新商品过期时间
            // productService.updateBatchById(list);
            return ResponseEntity.success();
        }
        return ResponseEntity.fail("充值失败！");
    }

    /**
     * 删除数据
     *
     * @param idList 主键结合
     * @return 删除结果
     */
    @DeleteMapping
    public ResponseEntity delete(@RequestParam("idList") List<Long> idList) {
        return ResponseEntity.success(this.userInfoService.removeByIds(idList));
    }
}
