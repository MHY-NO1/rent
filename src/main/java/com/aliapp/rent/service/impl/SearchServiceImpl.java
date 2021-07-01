package com.aliapp.rent.service.impl;

import com.aliapp.rent.entity.vo.SearchVo;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.aliapp.rent.dao.SearchDao;
import com.aliapp.rent.entity.po.Search;
import com.aliapp.rent.service.SearchService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * (Search)表服务实现类
 *
 * @author makejava
 * @since 2021-04-14 13:34:27
 */
@Service("searchService")
public class SearchServiceImpl extends ServiceImpl<SearchDao, Search> implements SearchService {

    @Resource
    SearchDao searchDao;

    @Override
    public List<SearchVo> queryHistory(Wrapper<Search> wrapper) {
        return searchDao.queryHistory(wrapper);
    }
}
