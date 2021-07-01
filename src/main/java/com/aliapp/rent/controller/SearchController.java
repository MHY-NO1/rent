package com.aliapp.rent.controller;


import com.aliapp.rent.entity.po.Search;
import com.aliapp.rent.entity.vo.SearchVo;
import com.aliapp.rent.service.SearchService;
import com.aliapp.rent.util.ResponseEntity;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

/**
 * (Search)表控制层
 *
 * @author makejava
 * @since 2021-04-14 13:34:27
 */
@RestController
@RequestMapping("search")
public class SearchController {
    /**
     * 服务对象
     */
    @Resource
    private SearchService searchService;

    /**
     * 分页查询所有数据
     *
     * @return 所有数据
     */
    @GetMapping
    public ResponseEntity selectAll(SearchVo vo) {
        //判断是否有用户登录
        if (vo.getUid() == null) {
            return ResponseEntity.fail();
        }
        List<Search> list = this.searchService.list(new QueryWrapper<Search>().eq("uid",
                vo.getUid()));

        if (list.size() > 10) {
            this.searchService.removeById(list.get(0).getId());
            list.remove(0);
        }
        return ResponseEntity.success(list);
    }


    @GetMapping("hot")
    public ResponseEntity hot(SearchVo vo) {
        QueryWrapper<Search> qw = new QueryWrapper<>();
        qw.groupBy("name").orderByDesc("num").last("limit 0,6");
        return ResponseEntity.success(this.searchService.queryHistory(qw));
    }

    //根据输入框的值下拉列表
    @GetMapping("ajax")
    public ResponseEntity ajax(SearchVo vo) {
        String value = vo.getSearchValue();
        QueryWrapper<Search> qw = new QueryWrapper<>();
        qw.like("name", value);
        qw.select("distinct name");
        return ResponseEntity.success(this.searchService.list(qw));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public ResponseEntity selectOne(@PathVariable Serializable id) {
        return ResponseEntity.success(this.searchService.getById(id));
    }

    /**
     * 新增数据
     *
     * @return 新增结果
     */
    @PostMapping
    public ResponseEntity insert(@RequestBody SearchVo vo) {
        Search search = new Search();
        BeanUtils.copyProperties(vo, search);
        if (this.searchService.count(new QueryWrapper<>(search)) > 0) {
            return ResponseEntity.fail();
        }
        return ResponseEntity.success(this.searchService.save(search));
    }

    /**
     * 修改数据
     *
     * @param search 实体对象
     * @return 修改结果
     */
    @PutMapping
    public ResponseEntity update(@RequestBody Search search) {
        return ResponseEntity.success(this.searchService.updateById(search));
    }

    /**
     * 删除数据
     *
     * @return 删除结果
     */
    @DeleteMapping("{uid}")
    public ResponseEntity delete(@PathVariable int uid) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("uid", uid);
        return ResponseEntity.success(this.searchService.removeByMap(map));
    }
}
