package com.aliapp.rent.controller;


import com.aliapp.rent.entity.po.Brand;
import com.aliapp.rent.entity.po.Product;
import com.aliapp.rent.entity.vo.BrandVo;
import com.aliapp.rent.service.BrandService;
import com.aliapp.rent.service.ProductService;
import com.aliapp.rent.util.ResponseEntity;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

/**
 * (Brand)表控制层
 *
 * @author makejava
 * @since 2021-04-14 16:40:50
 */
@RestController
@RequestMapping("brand")
public class BrandController {
    /**
     * 服务对象
     */
    @Resource
    private BrandService brandService;

    @Resource
    private ProductService productService;

    /**
     * 分页查询所有数据
     *
     * @param page 分页对象
     * @return 所有数据
     */
    @GetMapping
    public ResponseEntity selectAll(Page<Brand> page, BrandVo vo) {
        Brand brand = new Brand();
        BeanUtils.copyProperties(vo, brand);
        return ResponseEntity.success(this.brandService.page(page,
                new QueryWrapper<>(brand)));
    }

    @GetMapping("list")
    public ResponseEntity list(BrandVo vo) {
        Brand brand = new Brand();
        BeanUtils.copyProperties(vo, brand);

        if (vo.getPid() != null) {
            Product byId = productService.getById(vo.getPid());
            brand.setClid(byId.getClid());
        }

        return ResponseEntity.success(brandService.list(new QueryWrapper<>(brand)));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public ResponseEntity selectOne(@PathVariable Serializable id) {
        return ResponseEntity.success(this.brandService.getById(id));
    }

    /**
     * 新增数据
     *
     * @return 新增结果
     */
    @PostMapping
    public ResponseEntity insert(BrandVo vo) {
        Brand brand = new Brand();
        BeanUtils.copyProperties(vo, brand);
        return ResponseEntity.success(this.brandService.saveOrUpdate(brand));
    }

    /**
     * 修改数据
     *
     * @param brand 实体对象
     * @return 修改结果
     */
    @PutMapping
    public ResponseEntity update(@RequestBody Brand brand) {
        return ResponseEntity.success(this.brandService.updateById(brand));
    }

    /**
     * 删除数据
     *
     * @param idList 主键结合
     * @return 删除结果
     */
    @DeleteMapping
    public ResponseEntity delete(@RequestParam("idList") List<Long> idList) {
        return ResponseEntity.success(this.brandService.removeByIds(idList));
    }
}
