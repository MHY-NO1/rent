package com.aliapp.rent.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.aliapp.rent.dao.ServePhoneDao;
import com.aliapp.rent.entity.po.ServePhone;
import com.aliapp.rent.service.ServePhoneService;
import org.springframework.stereotype.Service;

/**
 * (ServePhone)表服务实现类
 *
 * @author makejava
 * @since 2021-05-18 14:23:46
 */
@Service("servePhoneService")
public class ServePhoneServiceImpl extends ServiceImpl<ServePhoneDao, ServePhone> implements ServePhoneService {

}
