package com.aliapp.rent.controller;


import com.aliapp.rent.entity.po.UserCategoryApply;
import com.aliapp.rent.entity.vo.UserCategoryApplyVo;
import com.aliapp.rent.service.UserCategoryApplyService;
import com.aliapp.rent.util.ResponseEntity;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

/**
 * (UserCategoryApply)表控制层
 *
 * @author makejava
 * @since 2021-04-20 14:22:51
 */
@RestController
@RequestMapping("userCategoryApply")
public class UserCategoryApplyController {
    /**
     * 服务对象
     */
    @Resource
    private UserCategoryApplyService userCategoryApplyService;

    /**
     * 分页查询所有数据
     *
     * @param page              分页对象
     * @param userCategoryApply 查询实体
     * @return 所有数据
     */
    @GetMapping
    public ResponseEntity selectAll(Page<UserCategoryApply> page,
                                    UserCategoryApply userCategoryApply) {
        return ResponseEntity.success(this.userCategoryApplyService.page(page,
                new QueryWrapper<>(userCategoryApply)));
    }

    @GetMapping("list")
    public ResponseEntity list() {
        return ResponseEntity.success(this.userCategoryApplyService.list());
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public ResponseEntity selectOne(@PathVariable Serializable id) {
        return ResponseEntity.success(this.userCategoryApplyService.getById(id));
    }

    /**
     * 新增数据
     *
     * @return 新增结果
     */
    @PostMapping
    public ResponseEntity insert(@RequestBody UserCategoryApplyVo vo) {
        /*新增子分类
        if (vo.getList() != null) {
            for (String s : vo.getList()) {
                QueryWrapper<UserCategoryApply> qw = new QueryWrapper<>();
                qw.eq("name", s);
                qw.eq("pid", vo.getPid());
                UserCategoryApply one = this.userCategoryApplyService.getOne(qw);
                if (one != null) {
                    one.setNum(one.getNum() + 1);
                    this.userCategoryApplyService.updateById(one);
                } else {
                    UserCategoryApply userCategoryApply = new UserCategoryApply();
                    userCategoryApply.setName(s);
                    userCategoryApply.setPid(vo.getPid());
                    this.userCategoryApplyService.save(userCategoryApply);
                }
            }
            return ResponseEntity.success();
        }
        查找或创建一级分类
        else {*/
        //有则数量加一，无则新增
        UserCategoryApply userCategoryApply = new UserCategoryApply();
        BeanUtils.copyProperties(vo, userCategoryApply);
        UserCategoryApply one =
                this.userCategoryApplyService.getOne(new QueryWrapper<>(userCategoryApply));
        if (one != null) {
            one.setNum(one.getNum() + 1);
            this.userCategoryApplyService.updateById(one);
            return ResponseEntity.success(one.getId());
        } else {
            this.userCategoryApplyService.save(userCategoryApply);
            return ResponseEntity.success(userCategoryApply.getId());
        }
        // }
    }

    /**
     * 修改数据
     *
     * @param userCategoryApply 实体对象
     * @return 修改结果
     */
    @PutMapping
    public ResponseEntity update(@RequestBody UserCategoryApply userCategoryApply) {
        return ResponseEntity.success(this.userCategoryApplyService.updateById(userCategoryApply));
    }

    /**
     * 删除数据
     *
     * @param idList 主键结合
     * @return 删除结果
     */
    @DeleteMapping
    public ResponseEntity delete(@RequestParam("idList") List<Long> idList) {
        return ResponseEntity.success(this.userCategoryApplyService.removeByIds(idList));
    }
}
