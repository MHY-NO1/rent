package com.aliapp.rent.controller;


import com.aliapp.rent.entity.po.BizApply;
import com.aliapp.rent.entity.po.UserInfo;
import com.aliapp.rent.entity.vo.BizApplyVo;
import com.aliapp.rent.service.BizApplyService;
import com.aliapp.rent.service.UserInfoService;
import com.aliapp.rent.util.ResponseEntity;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

/**
 * (BizApply)表控制层
 *
 * @author makejava
 * @since 2021-05-19 11:05:22
 */
@RestController
@RequestMapping("bizApply")
public class BizApplyController {
    /**
     * 服务对象
     */
    @Resource
    private BizApplyService bizApplyService;

    @Resource
    private UserInfoService userInfoService;

    /**
     * 分页查询所有数据
     *
     * @param page     分页对象
     * @param bizApply 查询实体
     * @return 所有数据
     */
    @GetMapping
    public ResponseEntity selectAll(Page<BizApply> page, BizApply bizApply) {
        return ResponseEntity.success(this.bizApplyService.page(page,
                new QueryWrapper<>(bizApply)));
    }

    @GetMapping("list")
    public ResponseEntity list(BizApplyVo vo) {
        BizApply bizApply = new BizApply();
        BeanUtils.copyProperties(vo, bizApply);
        return ResponseEntity.success(this.bizApplyService.list(new QueryWrapper<>(bizApply)));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public ResponseEntity selectOne(@PathVariable Serializable id) {
        return ResponseEntity.success(this.bizApplyService.getById(id));
    }

    /**
     * 新增数据
     *
     * @return 新增结果
     */
    @PostMapping
    public ResponseEntity insert(@RequestBody BizApplyVo vo) {
        BizApply bizApply = new BizApply();
        BeanUtils.copyProperties(vo, bizApply);
        return ResponseEntity.success(this.bizApplyService.save(bizApply));
    }

    /**
     * 修改数据
     *
     * @return 修改结果
     */
    @PutMapping
    public ResponseEntity update(BizApplyVo vo) {
        BizApply bizApply = new BizApply();
        BeanUtils.copyProperties(vo, bizApply);
        if (vo.getState() == 1) {
            UserInfo userInfo = new UserInfo();
            userInfo.setId(vo.getUid());
            userInfo.setState(2);
            userInfoService.updateById(userInfo);
        }
        return ResponseEntity.success(this.bizApplyService.updateById(bizApply));
    }

    /**
     * 删除数据
     *
     * @param idList 主键结合
     * @return 删除结果
     */
    @DeleteMapping
    public ResponseEntity delete(@RequestParam("idList") List<Long> idList) {
        return ResponseEntity.success(this.bizApplyService.removeByIds(idList));
    }
}
