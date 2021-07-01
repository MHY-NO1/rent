package com.aliapp.rent.controller;


import com.aliapp.rent.entity.po.Faq;
import com.aliapp.rent.entity.vo.FaqVo;
import com.aliapp.rent.service.FaqService;
import com.aliapp.rent.util.ResponseEntity;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

/**
 * (Faq)表控制层
 *
 * @author makejava
 * @since 2021-05-22 11:54:56
 */
@RestController
@RequestMapping("faq")
public class FaqController {
    /**
     * 服务对象
     */
    @Resource
    private FaqService faqService;

    /**
     * 分页查询所有数据
     *
     * @param page 分页对象
     * @param faq  查询实体
     * @return 所有数据
     */
    @GetMapping
    public ResponseEntity selectAll(Page<Faq> page, Faq faq) {
        return ResponseEntity.success(this.faqService.page(page, new QueryWrapper<>(faq)));
    }

    @GetMapping("list")
    public ResponseEntity selectAll(FaqVo vo) {
        Faq faq = new Faq();
        BeanUtils.copyProperties(vo, faq);
        return ResponseEntity.success(this.faqService.list(new QueryWrapper<>(faq)));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public ResponseEntity selectOne(@PathVariable Serializable id) {
        return ResponseEntity.success(this.faqService.getById(id));
    }

    /**
     * 新增数据
     *
     * @return 新增结果
     */
    @PostMapping
    public ResponseEntity insert(FaqVo vo) {
        Faq faq = new Faq();
        BeanUtils.copyProperties(vo, faq);
        return ResponseEntity.success(this.faqService.saveOrUpdate(faq));
    }

    /**
     * 修改数据
     *
     * @param faq 实体对象
     * @return 修改结果
     */
    @PutMapping
    public ResponseEntity update(@RequestBody Faq faq) {
        return ResponseEntity.success(this.faqService.updateById(faq));
    }

    /**
     * 删除数据
     *
     * @param idList 主键结合
     * @return 删除结果
     */
    @DeleteMapping
    public ResponseEntity delete(@RequestParam("idList") List<Long> idList) {
        return ResponseEntity.success(this.faqService.removeByIds(idList));
    }
}
