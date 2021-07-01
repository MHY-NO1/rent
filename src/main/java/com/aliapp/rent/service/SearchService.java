package com.aliapp.rent.service;

import com.aliapp.rent.entity.po.Search;
import com.aliapp.rent.entity.vo.SearchVo;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * (Search)表服务接口
 *
 * @author makejava
 * @since 2021-04-14 13:34:27
 */
public interface SearchService extends IService<Search> {
    List<SearchVo> queryHistory(Wrapper<Search> wrapper);
}
