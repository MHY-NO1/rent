package com.aliapp.rent.controller;


import com.aliapp.rent.entity.po.RechargeRecord;
import com.aliapp.rent.entity.vo.RechargeRecordVo;
import com.aliapp.rent.service.RechargeRecordService;
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
 * (RechargeRecord)表控制层
 *
 * @author makejava
 * @since 2021-06-26 14:32:48
 */
@RestController
@RequestMapping("rechargeRecord")
public class RechargeRecordController {
    /**
     * 服务对象
     */
    @Resource
    private RechargeRecordService rechargeRecordService;

    /**
     * 分页查询所有数据
     *
     * @param page           分页对象
     * @param rechargeRecord 查询实体
     * @return 所有数据
     */
    @GetMapping
    public ResponseEntity selectAll(Page<RechargeRecord> page, RechargeRecord rechargeRecord) {
        return success(this.rechargeRecordService.page(page, new QueryWrapper<>(rechargeRecord)));
    }

    /**
     * 获取全部数据（不分页）
     */
    @GetMapping("list")
    public ResponseEntity list(RechargeRecordVo vo) {
        RechargeRecord rechargeRecord = new RechargeRecord();
        BeanUtils.copyProperties(vo, rechargeRecord);
        return success(this.rechargeRecordService.list(new QueryWrapper<>(rechargeRecord)));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public ResponseEntity selectOne(@PathVariable Serializable id) {
        return success(this.rechargeRecordService.getById(id));
    }

    /**
     * 新增数据
     *
     * @return 新增结果
     */
    @PostMapping
    public ResponseEntity insert(@RequestBody RechargeRecordVo vo) {
        RechargeRecord rechargeRecord = new RechargeRecord();
        BeanUtils.copyProperties(vo, rechargeRecord);
        return success(this.rechargeRecordService.save(rechargeRecord));
    }

    /**
     * 修改数据
     *
     * @param rechargeRecord 实体对象
     * @return 修改结果
     */
    @PutMapping
    public ResponseEntity update(@RequestBody RechargeRecord rechargeRecord) {
        return success(this.rechargeRecordService.updateById(rechargeRecord));
    }

    /**
     * 删除数据
     *
     * @param idList 主键结合
     * @return 删除结果
     */
    @DeleteMapping
    public ResponseEntity delete(@RequestParam("idList") List<Long> idList) {
        return success(this.rechargeRecordService.removeByIds(idList));
    }

    /**
     * 统计会员费收入
     */
    @GetMapping("count")
    public List countVipFee() {
        List<RechargeRecordVo> rechargeRecordVoList =
                rechargeRecordService.queryAmountByVid(new QueryWrapper<RechargeRecord>().groupBy("vid"));
        return rechargeRecordVoList;
    }
}
