package com.aliapp.rent.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.aliapp.rent.dao.FaqDao;
import com.aliapp.rent.entity.po.Faq;
import com.aliapp.rent.service.FaqService;
import org.springframework.stereotype.Service;

/**
 * (Faq)表服务实现类
 *
 * @author makejava
 * @since 2021-05-22 11:54:56
 */
@Service("faqService")
public class FaqServiceImpl extends ServiceImpl<FaqDao, Faq> implements FaqService {

}
