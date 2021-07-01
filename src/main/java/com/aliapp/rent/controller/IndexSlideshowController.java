package com.aliapp.rent.controller;


import com.aliapp.rent.entity.po.IndexSlideshow;
import com.aliapp.rent.entity.vo.IndexSlideshowVo;
import com.aliapp.rent.service.IndexSlideshowService;
import com.aliapp.rent.util.ResponseEntity;
import com.aliapp.rent.util.UploadUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

/**
 * (IndexSlideshow)表控制层
 *
 * @author makejava
 * @since 2021-04-06 13:22:45
 */
@RestController
@RequestMapping("indexSlideshow")
public class IndexSlideshowController {
    /**
     * 服务对象
     */
    @Resource
    private IndexSlideshowService indexSlideshowService;

    public IndexSlideshow copy(IndexSlideshowVo source) {
        IndexSlideshow target = new IndexSlideshow();
        BeanUtils.copyProperties(source, target);
        return target;
    }

    /**
     * 分页查询所有数据
     *
     * @return 所有数据
     */
    @GetMapping
    public ResponseEntity selectAll(IndexSlideshowVo vo) {
        IndexSlideshow indexSlideshow = this.copy(vo);
        return ResponseEntity.success(this.indexSlideshowService.list(new QueryWrapper<>(indexSlideshow)));
    }

    /**
     * 新增图片
     */
    @PostMapping("picture")
    public ResponseEntity picture(MultipartFile file) {
        IndexSlideshow slideshow = new IndexSlideshow();
        String upload = UploadUtil.upload(file);
        slideshow.setUrl(upload);
        this.indexSlideshowService.save(slideshow);
        return ResponseEntity.success();
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public ResponseEntity selectOne(@PathVariable Serializable id) {
        return ResponseEntity.success(this.indexSlideshowService.getById(id));
    }

    /**
     * 编辑分类名
     *
     * @return 新增结果
     */
    @PostMapping
    public ResponseEntity insert(IndexSlideshowVo vo) {
        IndexSlideshow indexSlideshow = new IndexSlideshow();
        BeanUtils.copyProperties(vo, indexSlideshow);
        this.indexSlideshowService.updateById(indexSlideshow);
        return ResponseEntity.success();
    }

    /**
     * 修改数据
     *
     * @param indexSlideshow 实体对象
     * @return 修改结果
     */
    @PutMapping
    public ResponseEntity update(@RequestBody IndexSlideshow indexSlideshow) {
        return ResponseEntity.success(this.indexSlideshowService.updateById(indexSlideshow));
    }

    /**
     * 删除数据
     *
     * @param idList 主键结合
     * @return 删除结果
     */
    @DeleteMapping
    public ResponseEntity delete(@RequestParam("idList") List<Long> idList) {
        return ResponseEntity.success(this.indexSlideshowService.removeByIds(idList));
    }
}
