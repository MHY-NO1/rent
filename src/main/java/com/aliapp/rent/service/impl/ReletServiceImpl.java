package com.aliapp.rent.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.aliapp.rent.dao.ReletDao;
import com.aliapp.rent.entity.po.Relet;
import com.aliapp.rent.service.ReletService;
import org.springframework.stereotype.Service;

/**
 * (Relet)表服务实现类
 *
 * @author makejava
 * @since 2021-04-28 15:59:52
 */
@Service("reletService")
public class ReletServiceImpl extends ServiceImpl<ReletDao, Relet> implements ReletService {

}
