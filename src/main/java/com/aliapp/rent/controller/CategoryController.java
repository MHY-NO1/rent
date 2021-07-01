package com.aliapp.rent.controller;


import com.aliapp.rent.entity.po.Category;
import com.aliapp.rent.entity.po.IndexSlideshow;
import com.aliapp.rent.entity.vo.CategoryVo;
import com.aliapp.rent.service.CategoryService;
import com.aliapp.rent.service.IndexSlideshowService;
import com.aliapp.rent.util.ResponseEntity;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.Serializable;

/**
 * (Category)表控制层
 *
 * @author makejava
 * @since 2021-04-06 10:05:58
 */
@RestController
@RequestMapping("category")
public class CategoryController {
    /**
     * 服务对象
     */
    @Resource
    private CategoryService categoryService;

    @Resource
    private IndexSlideshowService indexSlideshowService;

    public Category copy(CategoryVo source) {
        Category target = new Category();
        BeanUtils.copyProperties(source, target);
        return target;
    }

    /**
     * 分页查询所有数据
     *
     * @return 所有数据
     */
    @GetMapping
    public ResponseEntity selectAll(CategoryVo vo) {
        Category category = this.copy(vo);
        return ResponseEntity.success(this.categoryService.list(new QueryWrapper<>(category)));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public ResponseEntity selectOne(@PathVariable Serializable id) {
        return ResponseEntity.success(this.categoryService.getById(id));
    }

    /**
     * 新增数据
     *
     * @return 新增结果
     */
    @PostMapping
    public ResponseEntity insert(CategoryVo vo) {
        Category category = new Category();
        BeanUtils.copyProperties(vo, category);
        return ResponseEntity.success(this.categoryService.save(category));
    }

    /**
     * 修改数据
     *
     * @return 修改结果
     */
    @PutMapping
    public ResponseEntity update(CategoryVo vo) {
        Category category = new Category();
        BeanUtils.copyProperties(vo, category);
        return ResponseEntity.success(this.categoryService.updateById(category));
    }

    /**
     * 删除数据
     *
     * @return 删除结果
     */
    @DeleteMapping
    public ResponseEntity delete(@RequestParam("id") Integer id) {
        //删除一级分类下的广告
        QueryWrapper<IndexSlideshow> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("cid", id);
        indexSlideshowService.remove(queryWrapper);
        return ResponseEntity.success(this.categoryService.removeById(id));
    }
}
