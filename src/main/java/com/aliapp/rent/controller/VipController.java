package com.aliapp.rent.controller;


import com.aliapp.rent.entity.po.UserInfo;
import com.aliapp.rent.entity.po.Vip;
import com.aliapp.rent.entity.vo.VipVo;
import com.aliapp.rent.service.UserInfoService;
import com.aliapp.rent.service.VipService;
import com.aliapp.rent.util.AlipayUtils;
import com.aliapp.rent.util.ResponseEntity;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * (Vip)表控制层
 *
 * @author makejava
 * @since 2021-05-12 15:29:53
 */
@RestController
@RequestMapping("vip")
public class VipController {
    /**
     * 服务对象
     */
    @Resource
    private VipService vipService;

    @Resource
    private UserInfoService userInfoService;

    /**
     * 分页查询所有数据
     *
     * @param page 分页对象
     * @return 所有数据
     */
    @GetMapping
    public ResponseEntity selectAll(Page<Vip> page, VipVo vo) {
        Vip vip = new Vip();
        BeanUtils.copyProperties(vo, vip);
        return ResponseEntity.success(this.vipService.page(page,
                new QueryWrapper<>(vip)));
    }

    @GetMapping("list")
    public ResponseEntity list(VipVo vo) {
        Vip vip = new Vip();
        BeanUtils.copyProperties(vo, vip);
        QueryWrapper<Vip> qw = new QueryWrapper<>(vip);
        // qw.gt("level", 0);

        // 充值时，获取大于当前用户会员等级的会员信息
        if (vo.getUid() != null) {
            UserInfo userInfo = userInfoService.getById(vo.getUid());
            Vip vip2 = vipService.getById(userInfo.getVid());
            qw.gt("level", vip2.getLevel());
        }
        List<Vip> list = this.vipService.list(qw);
        if (list == null || list.isEmpty()) {
            return ResponseEntity.fail("当前会员已是最高等级！");
        }
        return ResponseEntity.success(list);
    }

    /**
     * 用户充值
     */
    @PostMapping("recharge")
    public ResponseEntity recharge(@RequestBody VipVo vo) {
        String tradeNo;
        UserInfo userInfo = userInfoService.getById(vo.getUid());
        //判断用户是否已经有充值中的会员
        Vip userVip = vipService.getById(userInfo.getVid());
        Vip vip = vipService.getById(vo.getId());
        //充值金额
        String totalAmount = vip.getPrice() * vo.getMonth() + "";
        if (userVip.getLevel() > 0) {
            //有充值中的会员
            if (userVip.getId().equals(vo.getId())) {
                //续费
                tradeNo = AlipayUtils.getTradeNo(UUID.randomUUID().toString().replace(
                        "-", ""), totalAmount, "续费" + vip.getName(), userInfo.getUserId());
            } else {
                //升级
                //计算会员剩余天数
                int days = vipService.daysBetween(new Date(), userInfo.getVipExpirationTime());
                //计算高级会员与低级会员每天的差价
                double v = (vip.getPrice() - userVip.getPrice()) / 30;
                //算出需要升级的价格
                Double price = v * days;
                String format = new DecimalFormat("#.00").format(price);
                vo.setMoney(Double.valueOf(format));
                tradeNo = AlipayUtils.getTradeNo(UUID.randomUUID().toString().replace(
                        "-", ""), format, "升级" + vip.getName(), userInfo.getUserId());
                vo.setTradeNo(tradeNo);
                return ResponseEntity.build(205, "会员需要先升级！", vo);
            }
        } else {
            tradeNo = AlipayUtils.getTradeNo(UUID.randomUUID().toString().replace(
                    "-", ""), totalAmount, "充值" + vip.getName(), userInfo.getUserId());
        }
        return ResponseEntity.success(tradeNo);
    }

    /**
     * 通过主键查询单条数据(会员信息)
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public ResponseEntity selectOne(@PathVariable Serializable id) {
        UserInfo userInfo = userInfoService.getById(id);
        return ResponseEntity.success(this.vipService.getById(userInfo.getVid()));
    }

    /**
     * 新增或编辑数据
     *
     * @return 新增结果
     */
    @PostMapping
    public ResponseEntity insert(VipVo vo) {
        Vip vip = new Vip();
        BeanUtils.copyProperties(vo, vip);
        return ResponseEntity.success(this.vipService.saveOrUpdate(vip));
    }

    /**
     * 修改数据
     *
     * @param vip 实体对象
     * @return 修改结果
     */
    @PutMapping
    public ResponseEntity update(@RequestBody Vip vip) {
        return ResponseEntity.success(this.vipService.updateById(vip));
    }

    /**
     * 删除数据
     *
     * @param idList 主键结合
     * @return 删除结果
     */
    @DeleteMapping
    public ResponseEntity delete(@RequestParam("idList") List<Long> idList) {
        return ResponseEntity.success(this.vipService.removeByIds(idList));
    }
}
