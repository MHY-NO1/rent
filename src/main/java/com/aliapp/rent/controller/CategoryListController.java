package com.aliapp.rent.controller;


import com.aliapp.rent.entity.po.Category;
import com.aliapp.rent.entity.po.CategoryList;
import com.aliapp.rent.entity.vo.CategoryListVo;
import com.aliapp.rent.entity.vo.CategoryVo;
import com.aliapp.rent.entity.vo.TreeVo;
import com.aliapp.rent.service.CategoryListService;
import com.aliapp.rent.service.CategoryService;
import com.aliapp.rent.util.ResponseEntity;
import com.aliapp.rent.util.UploadUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.BeanUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * (CategoryList)表控制层
 *
 * @author makejava
 * @since 2021-04-06 14:00:26
 */
@RestController
@RequestMapping("categoryList")
public class CategoryListController {
    /**
     * 服务对象
     */
    @Resource
    private CategoryListService categoryListService;

    @Resource
    private CategoryService categoryService;

    /**
     * 分页查询所有数据
     *
     * @return 所有数据
     */
    @GetMapping
    public ResponseEntity selectAll(CategoryListVo vo) {
        CategoryList categoryList = new CategoryList();
        BeanUtils.copyProperties(vo, categoryList);
        return ResponseEntity.success(this.categoryListService.list(new QueryWrapper<>(categoryList)));
    }

    @GetMapping("tree")
    public ResponseEntity getTree() {
        List<Category> list = categoryService.list();
        QueryWrapper<CategoryList> qw = new QueryWrapper<>();
        qw.eq("state", 1);
        List<CategoryList> list1 = categoryListService.list(qw);
        ArrayList<TreeVo> tree = new ArrayList<>();
        for (Category category : list) {
            tree.add(new TreeVo(category.getId(), 0, category.getTitle(), "true"));
        }
        for (CategoryList categoryList : list1) {
            tree.add(new TreeVo(Integer.valueOf("99999" + categoryList.getId()),
                    categoryList.getCid(),
                    categoryList.getName(), "false"));
        }

        return ResponseEntity.success(tree);
    }

    @GetMapping("list")
    public ResponseEntity getAllCategory() {
        List<CategoryVo> result = new ArrayList<>();
        List<Category> list = this.categoryService.list();
        for (Category category : list) {
            List<CategoryListVo> subs = new ArrayList<>();
            for (CategoryList categoryList :
                    this.categoryListService.list(new QueryWrapper<CategoryList>().eq(
                            "cid", category.getId()).eq("state", 1))) {
                subs.add(new CategoryListVo(categoryList.getName(),
                        categoryList.getId()));
            }
            result.add(new CategoryVo(subs, category.getId(), category.getTitle()));
        }
        return ResponseEntity.success(result);
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public ResponseEntity selectOne(@PathVariable Serializable id) {
        return ResponseEntity.success(this.categoryListService.getById(id));
    }

    /**
     * 新增数据
     *
     * @return 新增结果
     */
    @PostMapping
    public ResponseEntity insert(CategoryListVo vo) {
        CategoryList categoryList = new CategoryList();
        BeanUtils.copyProperties(vo, categoryList);
        categoryList.setState(1);
        this.categoryListService.save(categoryList);
        return ResponseEntity.success(99999 + "" + categoryList.getId());
    }

    /**
     * 编辑图片
     */
    @PostMapping("picture")
    public ResponseEntity picture(MultipartFile file,
                                  @RequestParam("clid") Integer clid) {
        CategoryList categoryList = new CategoryList();
        categoryList.setId(clid);
        String upload = UploadUtil.upload(file);
        categoryList.setPicUrl(upload);
        this.categoryListService.updateById(categoryList);
        return ResponseEntity.success();
    }

    /**
     * 客户新增分类
     */
    @PostMapping("userAdd")
    public ResponseEntity userAdd(MultipartFile file, CategoryListVo vo) {
        String upload = UploadUtil.upload(file);

        if (StringUtils.hasText(upload)) {
            CategoryList categoryList = new CategoryList();
            BeanUtils.copyProperties(vo, categoryList);
            categoryList.setPicUrl(upload);
            categoryListService.save(categoryList);

            BeanUtils.copyProperties(categoryList, vo);
            return ResponseEntity.success(vo);
        }

        return ResponseEntity.fail("上传图片失败！");
    }

    /**
     * 改变二级分类父分类
     */
    @PostMapping("change")
    public ResponseEntity change(CategoryListVo vo) {
        CategoryList categoryList = new CategoryList();
        BeanUtils.copyProperties(vo, categoryList);
        return ResponseEntity.success(this.categoryListService.updateById(categoryList));
    }

    /**
     * 修改数据
     *
     * @return 修改结果
     */
    @PutMapping
    public ResponseEntity update(CategoryListVo vo) {
        CategoryList categoryList = new CategoryList();
        BeanUtils.copyProperties(vo, categoryList);
        return ResponseEntity.success(this.categoryListService.updateById(categoryList));
    }

    /**
     * 删除数据
     *
     * @param idList 主键结合
     * @return 删除结果
     */
    @DeleteMapping
    public ResponseEntity delete(@RequestParam("idList") List<Long> idList) {
        return ResponseEntity.success(this.categoryListService.removeByIds(idList));
    }
}
