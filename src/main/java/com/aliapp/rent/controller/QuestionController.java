package com.aliapp.rent.controller;


import com.aliapp.rent.entity.po.Question;
import com.aliapp.rent.entity.vo.QuestionVo;
import com.aliapp.rent.service.QuestionService;
import com.aliapp.rent.util.ResponseEntity;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

/**
 * (Question)表控制层
 *
 * @author makejava
 * @since 2021-05-18 09:49:05
 */
@RestController
@RequestMapping("question")
public class QuestionController {
    /**
     * 服务对象
     */
    @Resource
    private QuestionService questionService;

    /**
     * 分页查询所有数据
     *
     * @param page     分页对象
     * @param question 查询实体
     * @return 所有数据
     */
    @GetMapping
    public ResponseEntity selectAll(Page<Question> page, Question question) {
        return ResponseEntity.success(this.questionService.page(page,
                new QueryWrapper<>(question)));
    }

    @GetMapping("list")
    public ResponseEntity list(QuestionVo vo) {
        Question question = new Question();
        BeanUtils.copyProperties(vo, question);
        return ResponseEntity.success(this.questionService.list(new QueryWrapper<>(question)));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public ResponseEntity selectOne(@PathVariable Serializable id) {
        return ResponseEntity.success(this.questionService.getById(id));
    }

    /**
     * 新增数据
     *
     * @return 新增结果
     */
    @PostMapping
    public ResponseEntity insert(QuestionVo vo) {
        Question question = new Question();
        BeanUtils.copyProperties(vo, question);
        return ResponseEntity.success(this.questionService.saveOrUpdate(question));
    }

    /**
     * 修改数据
     *
     * @param question 实体对象
     * @return 修改结果
     */
    @PutMapping
    public ResponseEntity update(@RequestBody Question question) {
        return ResponseEntity.success(this.questionService.updateById(question));
    }

    /**
     * 删除数据
     *
     * @param idList 主键结合
     * @return 删除结果
     */
    @DeleteMapping
    public ResponseEntity delete(@RequestParam("idList") List<Long> idList) {
        return ResponseEntity.success(this.questionService.removeByIds(idList));
    }
}
