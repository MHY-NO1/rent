package com.aliapp.rent.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.aliapp.rent.entity.po.Vip;

import java.util.Date;

/**
 * (Vip)表服务接口
 *
 * @author makejava
 * @since 2021-05-12 15:29:52
 */
public interface VipService extends IService<Vip> {
    int daysBetween(Date smdate, Date bdate);
}
