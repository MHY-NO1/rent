package com.aliapp.rent.service;

import com.aliapp.rent.entity.po.Collected;
import com.aliapp.rent.entity.vo.CollectedVo;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * (Collected)表服务接口
 *
 * @author makejava
 * @since 2021-04-13 11:39:26
 */
public interface CollectedService extends IService<Collected> {
    IPage<CollectedVo> queryCollected(Page<CollectedVo> page, Wrapper<Collected> wrapper);
}
