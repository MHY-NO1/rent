package com.aliapp.rent.controller;


import com.aliapp.rent.entity.po.PreAuth;
import com.aliapp.rent.entity.po.Product;
import com.aliapp.rent.entity.po.TenancyCombo;
import com.aliapp.rent.entity.vo.PreAuthVo;
import com.aliapp.rent.service.PreAuthService;
import com.aliapp.rent.service.ProductService;
import com.aliapp.rent.service.TenancyComboService;
import com.aliapp.rent.util.AlipayUtils;
import com.aliapp.rent.util.ResponseEntity;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * (PreAuth)表控制层
 *
 * @author makejava
 * @since 2021-04-22 11:30:25
 */
@RestController
@RequestMapping("preAuth")
public class PreAuthController {
    /**
     * 服务对象
     */
    @Resource
    private PreAuthService preAuthService;

    @Resource
    private ProductService productService;

    @Resource
    private TenancyComboService tenancyComboService;

    /**
     * 分页查询所有数据
     *
     * @param page    分页对象
     * @param preAuth 查询实体
     * @return 所有数据
     */
    @GetMapping
    public ResponseEntity selectAll(Page<PreAuth> page, PreAuth preAuth) {
        return ResponseEntity.success(this.preAuthService.page(page, new QueryWrapper<>(preAuth)));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public ResponseEntity selectOne(@PathVariable Serializable id) {
        return ResponseEntity.success(this.preAuthService.getById(id));
    }

    /**
     * 新增数据
     *
     * @return 新增结果
     */
    @PostMapping
    public ResponseEntity insert(@RequestBody PreAuthVo vo) {
        QueryWrapper<TenancyCombo> qw = new QueryWrapper<>();
        qw.eq("tid", vo.getTid());
        qw.eq("cid", vo.getCid());
        qw.eq("pid", vo.getPid());

        TenancyCombo tenancyCombo = tenancyComboService.getOne(qw);
        Product product = productService.getById(vo.getPid());

        if (tenancyCombo.getInventory() < vo.getNum()) {
            return ResponseEntity.fail("库存不足！");
        }

        vo.setPayerUserId("2088141082766380");
        vo.setAmount(tenancyCombo.getRent() * vo.getNum());


        //生成预授权订单
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String orederNo = dateFormat.format(new Date()) + (int) Math.random() * 10000;
        vo.setOrderNo(orederNo);
        String reqNo = orederNo + 1;
        vo.setReqNo(reqNo);
        String orderTitle = product.getName() + "预授权";
        vo.setTitle(orderTitle);
        String notifyUrl = "https://120.26.0.67:80/preAuth/notify";

        String orderStr = AlipayUtils.fundAuthOrderAppFreeze(orderTitle, vo.getAmount() + ""
                , notifyUrl, "RENT_PHONE", orederNo, reqNo);

        //新增
        PreAuth preAuth = new PreAuth();
        BeanUtils.copyProperties(vo, preAuth);
        this.preAuthService.save(preAuth);

        return ResponseEntity.success(orderStr);
    }

    /**
     *  预授权冻结异步通知
     */
    @PostMapping("notify")
    public ResponseEntity notify(HttpServletRequest request) {
        Map map = AlipayUtils.asyncCertificate(request);
        map.get("status");
        map.get("payer_user_id");
        map.get("auth_no");
        map.get("credit_amount");
        map.get("fund_amount");
        map.get("amount");
        System.out.println("freeze");
        return ResponseEntity.success();
    }


    /**
     * 修改数据
     *
     * @param preAuth 实体对象
     * @return 修改结果
     */
    @PutMapping
    public ResponseEntity update(@RequestBody PreAuth preAuth) {
        return ResponseEntity.success(this.preAuthService.updateById(preAuth));
    }

    /**
     * 删除数据
     *
     * @param idList 主键结合
     * @return 删除结果
     */
    @DeleteMapping
    public ResponseEntity delete(@RequestParam("idList") List<Long> idList) {
        return ResponseEntity.success(this.preAuthService.removeByIds(idList));
    }
}
