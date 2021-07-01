package com.aliapp.rent.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.aliapp.rent.dao.FineDao;
import com.aliapp.rent.entity.po.Fine;
import com.aliapp.rent.service.FineService;
import org.springframework.stereotype.Service;

/**
 * (Fine)表服务实现类
 *
 * @author makejava
 * @since 2021-06-17 15:13:35
 */
@Service("fineService")
public class FineServiceImpl extends ServiceImpl<FineDao, Fine> implements FineService {

}
