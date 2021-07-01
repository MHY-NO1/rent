package com.aliapp.rent.controller;


import com.aliapp.rent.entity.po.Fine;
import com.aliapp.rent.entity.vo.FineVo;
import com.aliapp.rent.service.FineService;
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
 * (Fine)表控制层
 *
 * @author makejava
 * @since 2021-06-17 15:13:35
 */
@RestController
@RequestMapping("fine")
public class FineController {
    /**
     * 服务对象
     */
    @Resource
    private FineService fineService;

    /**
     * 分页查询所有数据
     *
     * @param page 分页对象
     * @param fine 查询实体
     * @return 所有数据
     */
    @GetMapping
    public ResponseEntity selectAll(Page<Fine> page, Fine fine) {
        return success(this.fineService.page(page, new QueryWrapper<>(fine)));
    }

    @GetMapping("list")
    public ResponseEntity list() {
        return success(this.fineService.list());
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public ResponseEntity selectOne(@PathVariable Serializable id) {
        return success(this.fineService.getById(id));
    }

    /**
     * 新增数据
     *
     * @param fine 实体对象
     * @return 新增结果
     */
    @PostMapping
    public ResponseEntity insert(Fine fine) {
        return success(this.fineService.save(fine));
    }

    /**
     * 修改数据
     *
     * @return 修改结果
     */
    @PostMapping("edit")
    public ResponseEntity update(FineVo vo) {
        Fine fine = new Fine();
        BeanUtils.copyProperties(vo, fine);
        return success(this.fineService.updateById(fine));
    }

    /**
     * 删除数据
     *
     * @return 删除结果
     */
    @DeleteMapping
    public ResponseEntity delete(@RequestParam("idList") List<Long> idList) {
        return success(this.fineService.removeByIds(idList));
    }
}
