package com.aliapp.rent.controller;


import com.aliapp.rent.entity.po.Express;
import com.aliapp.rent.entity.vo.ExpressVo;
import com.aliapp.rent.service.ExpressService;
import com.aliapp.rent.util.ResponseEntity;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

/**
 * (Express)表控制层
 *
 * @author makejava
 * @since 2021-05-08 13:06:45
 */
@RestController
@RequestMapping("express")
public class ExpressController {
    /**
     * 服务对象
     */
    @Resource
    private ExpressService expressService;

    /**
     * 分页查询所有数据
     *
     * @return 所有数据
     */
    @GetMapping
    public ResponseEntity selectAll(ExpressVo vo) {
        Express express = new Express();
        BeanUtils.copyProperties(vo,express);
        return ResponseEntity.success(this.expressService.list(new QueryWrapper<>(express)));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public ResponseEntity selectOne(@PathVariable Serializable id) {
        return ResponseEntity.success(this.expressService.getById(id));
    }

    /**
     * 新增数据
     *
     * @param express 实体对象
     * @return 新增结果
     */
    @PostMapping
    public ResponseEntity insert(@RequestBody Express express) {
        return ResponseEntity.success(this.expressService.save(express));
    }

    /**
     * 修改数据
     *
     * @param express 实体对象
     * @return 修改结果
     */
    @PutMapping
    public ResponseEntity update(@RequestBody Express express) {
        return ResponseEntity.success(this.expressService.updateById(express));
    }

    /**
     * 删除数据
     *
     * @param idList 主键结合
     * @return 删除结果
     */
    @DeleteMapping
    public ResponseEntity delete(@RequestParam("idList") List<Long> idList) {
        return ResponseEntity.success(this.expressService.removeByIds(idList));
    }
}
