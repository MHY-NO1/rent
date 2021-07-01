package com.aliapp.rent.controller;


import com.aliapp.rent.entity.po.ProductExpire;
import com.aliapp.rent.entity.vo.ProductExpireVo;
import com.aliapp.rent.service.ProductExpireService;
import com.aliapp.rent.util.ResponseEntity;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

import static com.aliapp.rent.util.ResponseEntity.success;

/**
 * (ProductExpire)表控制层
 *
 * @author makejava
 * @since 2021-06-17 14:43:36
 */
@RestController
@RequestMapping("productExpire")
public class ProductExpireController {
    /**
     * 服务对象
     */
    @Resource
    private ProductExpireService productExpireService;

    /**
     * 分页查询所有数据
     *
     * @param page          分页对象
     * @param productExpire 查询实体
     * @return 所有数据
     */
    @GetMapping
    public ResponseEntity selectAll(Page<ProductExpire> page, ProductExpire productExpire) {
        return success(this.productExpireService.page(page, new QueryWrapper<>(productExpire)));
    }

    @GetMapping("one")
    public ResponseEntity one() {
        return success(this.productExpireService.list());
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public ResponseEntity selectOne(@PathVariable Serializable id) {
        return success(this.productExpireService.getById(id));
    }

    /**
     * 新增数据
     *
     * @param productExpire 实体对象
     * @return 新增结果
     */
    @PostMapping
    public ResponseEntity insert(ProductExpire productExpire) {
        return success(this.productExpireService.save(productExpire));
    }

    /**
     * 修改数据
     *
     * @return 修改结果
     */
    @PostMapping("edit")
    public ResponseEntity update(ProductExpireVo vo) {
        ProductExpire productExpire = new ProductExpire();
        BeanUtils.copyProperties(vo, productExpire);
        return success(this.productExpireService.updateById(productExpire));
    }

    /**
     * 删除数据
     *
     * @param idList 主键结合
     * @return 删除结果
     */
    @DeleteMapping
    public ResponseEntity delete(@RequestParam("idList") List<Long> idList) {
        return success(this.productExpireService.removeByIds(idList));
    }
}
