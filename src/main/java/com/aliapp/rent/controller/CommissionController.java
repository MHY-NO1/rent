package com.aliapp.rent.controller;


import com.aliapp.rent.entity.po.Commission;
import com.aliapp.rent.entity.vo.CommissionVo;
import com.aliapp.rent.service.CommissionService;
import com.aliapp.rent.util.ResponseEntity;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

/**
 * (Commission)表控制层
 *
 * @author makejava
 * @since 2021-05-15 13:19:43
 */
@RestController
@RequestMapping("commission")
public class CommissionController {
    /**
     * 服务对象
     */
    @Resource
    private CommissionService commissionService;

    /**
     * 分页查询所有数据
     *
     * @param page 分页对象
     * @return 所有数据
     */
    @GetMapping
    public ResponseEntity selectAll(Page<Commission> page, CommissionVo vo) {
        return ResponseEntity.success(this.commissionService.list());
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public ResponseEntity selectOne(@PathVariable Serializable id) {
        return ResponseEntity.success(this.commissionService.getById(id));
    }

    /**
     * 编辑
     */
    @PostMapping("edit")
    public ResponseEntity edit(CommissionVo vo) {
        Commission commission = new Commission();
        BeanUtils.copyProperties(vo, commission);
        return ResponseEntity.success(this.commissionService.updateById(commission));
    }

    /**
     * 新增数据
     *
     * @param commission 实体对象
     * @return 新增结果
     */
    @PostMapping
    public ResponseEntity insert(@RequestBody Commission commission) {
        return ResponseEntity.success(this.commissionService.save(commission));
    }

    /**
     * 修改数据
     *
     * @param commission 实体对象
     * @return 修改结果
     */
    @PutMapping
    public ResponseEntity update(@RequestBody Commission commission) {
        return ResponseEntity.success(this.commissionService.updateById(commission));
    }

    /**
     * 删除数据
     *
     * @param idList 主键结合
     * @return 删除结果
     */
    @DeleteMapping
    public ResponseEntity delete(@RequestParam("idList") List<Long> idList) {
        return ResponseEntity.success(this.commissionService.removeByIds(idList));
    }
}
