package com.aliapp.rent.controller;


import com.aliapp.rent.entity.po.Picture;
import com.aliapp.rent.entity.vo.PictureVo;
import com.aliapp.rent.service.PictureService;
import com.aliapp.rent.util.ResponseEntity;
import com.aliapp.rent.util.UploadUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.BeanUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.Serializable;

/**
 * (Picture)表控制层
 *
 * @author makejava
 * @since 2021-04-07 11:20:37
 */
@RestController
@RequestMapping("picture")
public class PictureController {
    /**
     * 服务对象
     */
    @Resource
    private PictureService pictureService;

    /**
     * 查询所有数据
     *
     * @return 所有数据
     */
    @GetMapping
    public ResponseEntity selectAll(PictureVo vo) {
        Picture picture = new Picture();
        BeanUtils.copyProperties(vo, picture);
        return ResponseEntity.success(this.pictureService.list(new QueryWrapper<>(picture)));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public ResponseEntity selectOne(@PathVariable Serializable id) {
        return ResponseEntity.success(this.pictureService.getById(id));
    }

    /**
     * 新增数据
     *
     * @return 新增结果
     */
    @PostMapping
    public ResponseEntity insert(MultipartFile file, PictureVo vo) {
        String upload = UploadUtil.upload(file);

        if (StringUtils.hasText(upload)) {
            Picture picture = new Picture();
            BeanUtils.copyProperties(vo, picture);
            picture.setPicUrl(upload);
            this.pictureService.save(picture);
            BeanUtils.copyProperties(picture, vo);
            return ResponseEntity.success(vo);
//            商品文件上传接口
           /* AlipayMerchantItemFileUploadResponse response = AlipayOrder.setPicture(vo
           .getFilePath());
            if (response!=null){
                vo.setMaterialId(response.getMaterialId());
                vo.setMaterialKey(response.getMaterialKey());
                this.pictureService.save(picture);
                BeanUtils.copyProperties(picture,vo);
                return ResponseEntity.success(vo);
            }
            return ResponseEntity.fail("图片格式有误！商品图片宽度必须大于 750 px，宽高比建议 4:3 - 1:1 之间！");
        */
        }
        return ResponseEntity.fail();
    }

    /**
     * 修改数据
     *
     * @param picture 实体对象
     * @return 修改结果
     */
    @PutMapping
    public ResponseEntity update(@RequestBody Picture picture) {
        return ResponseEntity.success(this.pictureService.updateById(picture));
    }

    /**
     * 删除数据
     *
     * @return 删除结果
     */
    @DeleteMapping("{id}")
    public ResponseEntity delete(@PathVariable Serializable id) {
        return ResponseEntity.success(this.pictureService.removeById(id));
    }
}
