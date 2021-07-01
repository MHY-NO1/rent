package com.aliapp.rent.controller;


import com.aliapp.rent.entity.po.Collected;
import com.aliapp.rent.entity.vo.CollectedVo;
import com.aliapp.rent.service.CollectedService;
import com.aliapp.rent.util.ResponseEntity;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.Serializable;

/**
 * (Collected)表控制层
 *
 * @author makejava
 * @since 2021-04-13 11:39:26
 */
@RestController
@RequestMapping("collected")
public class CollectedController {
    /**
     * 服务对象
     */
    @Resource
    private CollectedService collectedService;

    /**
     * 分页查询所有数据
     *
     * @param page 分页对象
     * @return 所有数据
     */
    @GetMapping
    public ResponseEntity selectAll(Page<CollectedVo> page, CollectedVo vo) {
        Collected collected = new Collected();
        BeanUtils.copyProperties(vo, collected);
        QueryWrapper<Collected> qw = new QueryWrapper<>(collected);
        qw.eq("c.uid", vo.getUid());
        qw.eq("c.state", vo.getState());
        //上架的商品
        qw.eq("p.state", 1);
        //目前只查商品，店铺还需核实
        return ResponseEntity.success(this.collectedService.queryCollected(page, qw));
    }

    @GetMapping("one")
    public ResponseEntity one(CollectedVo vo) {
        Collected collected = new Collected();
        BeanUtils.copyProperties(vo, collected);
        return ResponseEntity.success(this.collectedService.getOne(new QueryWrapper<>(collected)));
    }

    @GetMapping("delete")
    public ResponseEntity del(CollectedVo vo) {
        Collected collected = new Collected();
        BeanUtils.copyProperties(vo, collected);
        return ResponseEntity.success(this.collectedService.remove(new QueryWrapper<>(collected)));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public ResponseEntity selectOne(@PathVariable Serializable id) {
        return ResponseEntity.success(this.collectedService.getById(id));
    }

    /**
     * 新增数据
     *
     * @return 新增结果
     */
    @PostMapping
    public ResponseEntity insert(@RequestBody CollectedVo vo) {
        Collected collected = new Collected();
        BeanUtils.copyProperties(vo, collected);
        return ResponseEntity.success(this.collectedService.save(collected));
    }

    /**
     * 修改数据
     *
     * @param collected 实体对象
     * @return 修改结果
     */
    @PutMapping
    public ResponseEntity update(@RequestBody Collected collected) {
        return ResponseEntity.success(this.collectedService.updateById(collected));
    }

    /**
     * 删除数据
     *
     * @return 删除结果
     */
    @DeleteMapping
    public ResponseEntity delete(CollectedVo vo) {
        Collected collected = new Collected();
        BeanUtils.copyProperties(vo, collected);
        return ResponseEntity.success(this.collectedService.remove(new QueryWrapper<>(collected)));
    }
}
