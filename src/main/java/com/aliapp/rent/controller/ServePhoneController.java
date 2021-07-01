package com.aliapp.rent.controller;


import com.aliapp.rent.entity.po.ServePhone;
import com.aliapp.rent.entity.vo.ServePhoneVo;
import com.aliapp.rent.service.ServePhoneService;
import com.aliapp.rent.util.ResponseEntity;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

/**
 * (ServePhone)表控制层
 *
 * @author makejava
 * @since 2021-05-18 14:23:46
 */
@RestController
@RequestMapping("servePhone")
public class ServePhoneController {
    /**
     * 服务对象
     */
    @Resource
    private ServePhoneService servePhoneService;

    /**
     * 分页查询所有数据
     *
     * @return 所有数据
     */
    @GetMapping
    public ResponseEntity selectAll(ServePhoneVo vo) {
        ServePhone servePhone = new ServePhone();
        BeanUtils.copyProperties(vo, servePhone);
        return ResponseEntity.success(this.servePhoneService.list(new QueryWrapper<>(servePhone)));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public ResponseEntity selectOne(@PathVariable Serializable id) {
        return ResponseEntity.success(this.servePhoneService.getById(id));
    }

    /**
     * 新增或更新数据
     */
    @PostMapping
    public ResponseEntity insert(ServePhoneVo vo) {
        ServePhone servePhone = new ServePhone();
        BeanUtils.copyProperties(vo, servePhone);
        return ResponseEntity.success(this.servePhoneService.saveOrUpdate(servePhone));
    }

    /**
     * 修改数据
     *
     * @param servePhone 实体对象
     * @return 修改结果
     */
    @PutMapping
    public ResponseEntity update(@RequestBody ServePhone servePhone) {
        return ResponseEntity.success(this.servePhoneService.updateById(servePhone));
    }

    /**
     * 删除数据
     *
     * @param idList 主键结合
     * @return 删除结果
     */
    @DeleteMapping
    public ResponseEntity delete(@RequestParam("idList") List<Long> idList) {
        return ResponseEntity.success(this.servePhoneService.removeByIds(idList));
    }
}
