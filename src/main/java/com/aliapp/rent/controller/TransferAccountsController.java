package com.aliapp.rent.controller;


import com.aliapp.rent.entity.po.TransferAccounts;
import com.aliapp.rent.service.TransferAccountsService;
import com.aliapp.rent.util.ResponseEntity;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

import static com.aliapp.rent.util.ResponseEntity.success;

/**
 * (TransferAccounts)表控制层
 *
 * @author makejava
 * @since 2021-06-29 11:06:44
 */
@RestController
@RequestMapping("transferAccounts")
public class TransferAccountsController {
    /**
     * 服务对象
     */
    @Resource
    private TransferAccountsService transferAccountsService;

    /**
     * 分页查询所有数据
     *
     * @param page             分页对象
     * @param transferAccounts 查询实体
     * @return 所有数据
     */
    @GetMapping
    public ResponseEntity selectAll(Page<TransferAccounts> page, TransferAccounts transferAccounts) {
        return success(this.transferAccountsService.page(page, new QueryWrapper<>(transferAccounts)));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public ResponseEntity selectOne(@PathVariable Serializable id) {
        return success(this.transferAccountsService.getById(id));
    }

    /**
     * 新增数据
     *
     * @param transferAccounts 实体对象
     * @return 新增结果
     */
    @PostMapping
    public ResponseEntity insert(@RequestBody TransferAccounts transferAccounts) {
        return success(this.transferAccountsService.save(transferAccounts));
    }

    /**
     * 修改数据
     *
     * @param transferAccounts 实体对象
     * @return 修改结果
     */
    @PutMapping
    public ResponseEntity update(@RequestBody TransferAccounts transferAccounts) {
        return success(this.transferAccountsService.updateById(transferAccounts));
    }

    /**
     * 删除数据
     *
     * @param idList 主键结合
     * @return 删除结果
     */
    @DeleteMapping
    public ResponseEntity delete(@RequestParam("idList") List<Long> idList) {
        return success(this.transferAccountsService.removeByIds(idList));
    }
}
