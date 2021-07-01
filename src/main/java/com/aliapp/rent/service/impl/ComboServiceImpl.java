package com.aliapp.rent.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.aliapp.rent.dao.ComboDao;
import com.aliapp.rent.entity.po.Combo;
import com.aliapp.rent.service.ComboService;
import org.springframework.stereotype.Service;

/**
 * (Combo)表服务实现类
 *
 * @author makejava
 * @since 2021-04-07 11:21:04
 */
@Service("comboService")
public class ComboServiceImpl extends ServiceImpl<ComboDao, Combo> implements ComboService {

}
