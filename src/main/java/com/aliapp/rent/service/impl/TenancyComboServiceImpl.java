package com.aliapp.rent.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.aliapp.rent.dao.TenancyComboDao;
import com.aliapp.rent.entity.po.TenancyCombo;
import com.aliapp.rent.service.TenancyComboService;
import org.springframework.stereotype.Service;

/**
 * (TenancyCombo)表服务实现类
 *
 * @author makejava
 * @since 2021-04-10 13:08:01
 */
@Service("tenancyComboService")
public class TenancyComboServiceImpl extends ServiceImpl<TenancyComboDao, TenancyCombo> implements TenancyComboService {

}
