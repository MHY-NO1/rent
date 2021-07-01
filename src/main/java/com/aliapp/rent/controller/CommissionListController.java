package com.aliapp.rent.controller;


import com.aliapp.rent.entity.po.CommissionList;
import com.aliapp.rent.entity.vo.CommissionListVo;
import com.aliapp.rent.service.CommissionListService;
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
 * (CommissionList)表控制层
 *
 * @author makejava
 * @since 2021-06-28 17:34:50
 */
@RestController
@RequestMapping("commissionList")
public class CommissionListController {
    /**
     * 服务对象
     */
    @Resource
    private CommissionListService commissionListService;

    /**
     * 分页查询所有数据
     *
     * @param page           分页对象
     * @param commissionList 查询实体
     * @return 所有数据
     */
    @GetMapping
    public ResponseEntity selectAll(Page<CommissionList> page, CommissionList commissionList) {
        return success(this.commissionListService.page(page, new QueryWrapper<>(commissionList)));
    }

    /**
     * 获取全部数据
     * @param vo
     * @return
     */
    @GetMapping("list")
    public ResponseEntity list(CommissionListVo vo) {
        CommissionList commissionList = new CommissionList();
        BeanUtils.copyProperties(vo, commissionList);
        return success(this.commissionListService.list(new QueryWrapper<>(commissionList)));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public ResponseEntity selectOne(@PathVariable Serializable id) {
        return success(this.commissionListService.getById(id));
    }

    /**
     * 新增数据
     *
     * @param commissionList 实体对象
     * @return 新增结果
     */
    @PostMapping
    public ResponseEntity insert(@RequestBody CommissionList commissionList) {
        return success(this.commissionListService.save(commissionList));
    }

    /**
     * 修改数据
     *
     * @param commissionList 实体对象
     * @return 修改结果
     */
    @PutMapping
    public ResponseEntity update(@RequestBody CommissionList commissionList) {
        return success(this.commissionListService.updateById(commissionList));
    }

    /**
     * 删除数据
     *
     * @param idList 主键结合
     * @return 删除结果
     */
    @DeleteMapping
    public ResponseEntity delete(@RequestParam("idList") List<Long> idList) {
        return success(this.commissionListService.removeByIds(idList));
    }
}
