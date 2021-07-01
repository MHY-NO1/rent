package com.aliapp.rent.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.aliapp.rent.dao.PictureDao;
import com.aliapp.rent.entity.po.Picture;
import com.aliapp.rent.service.PictureService;
import org.springframework.stereotype.Service;

/**
 * (Picture)表服务实现类
 *
 * @author makejava
 * @since 2021-04-07 11:20:37
 */
@Service("pictureService")
public class PictureServiceImpl extends ServiceImpl<PictureDao, Picture> implements PictureService {

}
