package com.aliapp.rent.service.impl;

import com.aliapp.rent.entity.vo.CollectedVo;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.aliapp.rent.dao.CollectedDao;
import com.aliapp.rent.entity.po.Collected;
import com.aliapp.rent.service.CollectedService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * (Collected)表服务实现类
 *
 * @author makejava
 * @since 2021-04-13 11:39:26
 */
@Service("collectedService")
public class CollectedServiceImpl extends ServiceImpl<CollectedDao, Collected> implements CollectedService {

    @Resource
    CollectedDao collectedDao;

    @Override
    public IPage<CollectedVo> queryCollected(Page<CollectedVo> page, Wrapper<Collected> wrapper) {
        return collectedDao.queryCollected(page,wrapper);
    }
}
