package com.aliapp.rent.controller;


import com.aliapp.rent.util.ResponseEntity;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.aliapp.rent.entity.po.Combo;
import com.aliapp.rent.service.ComboService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

/**
 * (Combo)表控制层
 *
 * @author makejava
 * @since 2021-04-07 11:21:04
 */
@RestController
@RequestMapping("combo")
public class ComboController {
    /**
     * 服务对象
     */
    @Resource
    private ComboService comboService;

    /**
     * 分页查询所有数据
     *
     * @param page  分页对象
     * @param combo 查询实体
     * @return 所有数据
     */
    @GetMapping
    public ResponseEntity selectAll(Page<Combo> page, Combo combo) {
        return ResponseEntity.success(this.comboService.page(page, new QueryWrapper<>(combo)));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public ResponseEntity selectOne(@PathVariable Serializable id) {
        return ResponseEntity.success(this.comboService.getById(id));
    }

    /**
     * 新增数据
     *
     * @param combo 实体对象
     * @return 新增结果
     */
    @PostMapping
    public ResponseEntity insert(@RequestBody Combo combo) {
        return ResponseEntity.success(this.comboService.save(combo));
    }

    /**
     * 修改数据
     *
     * @param combo 实体对象
     * @return 修改结果
     */
    @PutMapping
    public ResponseEntity update(@RequestBody Combo combo) {
        return ResponseEntity.success(this.comboService.updateById(combo));
    }

    /**
     * 删除数据
     *
     * @param idList 主键结合
     * @return 删除结果
     */
    @DeleteMapping
    public ResponseEntity delete(@RequestParam("idList") List<Long> idList) {
        return ResponseEntity.success(this.comboService.removeByIds(idList));
    }
}
