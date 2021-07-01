package com.aliapp.rent.controller.admin;


import com.aliapp.rent.entity.po.Admin;
import com.aliapp.rent.entity.vo.AdminVo;
import com.aliapp.rent.service.AdminService;
import com.aliapp.rent.util.ResponseEntity;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * (Admin)表控制层
 *
 * @author makejava
 * @since 2021-05-10 10:58:52
 */
@Controller
@RequestMapping("admin")
public class AdminController {
    /**
     * 服务对象
     */
    @Resource
    private AdminService adminService;

    /**
     * 分页查询所有数据
     *
     * @return 所有数据
     */
    @GetMapping
    @ResponseBody
    public ResponseEntity selectAll(AdminVo vo) {
        Admin admin = new Admin();
        BeanUtils.copyProperties(vo, admin);
        return ResponseEntity.success(adminService.list(new QueryWrapper<>(admin)));
    }

    @GetMapping("{goal}")
    public String fine(@PathVariable String goal) {
        String page = "/admin/" + goal;
        return page;
    }

    /**
     * 注销
     */
    @GetMapping("loginOut")
    public String loginOut(HttpSession session) {
        session.removeAttribute("uid");
        return "redirect:/";
    }

    /**
     * 登录
     */
    @PostMapping("login")
    public ModelAndView login(AdminVo vo, HttpSession session) {
        ModelAndView mv = new ModelAndView();
        Admin admin = new Admin();
        BeanUtils.copyProperties(vo, admin);
        Admin one = adminService.getOne(new QueryWrapper<>(admin));
        if (one != null) {
            //ID存入session
            session.setAttribute("uid", one.getId());
            session.setAttribute("name", one.getName());
            mv.setViewName("admin/index");
        } else {
            mv.addObject("msg", "账号或密码错误！");
            mv.setViewName("login");
        }
        return mv;
    }

    /**
     * 通过主键查询单条数据
     *
     * @return 单条数据
     */
    @GetMapping("one")
    @ResponseBody
    public ResponseEntity selectOne(AdminVo vo) {
        return ResponseEntity.success(this.adminService.getById(vo.getId()));
    }

    /**
     * 新增或编辑数据
     *
     * @return 新增结果
     */
    @PostMapping
    @ResponseBody
    public ResponseEntity insert(AdminVo vo) {
        if ("".equals(vo.getPwd())) {
            vo.setPwd(null);
        }
        Admin admin = new Admin();
        BeanUtils.copyProperties(vo, admin);
        return ResponseEntity.success(this.adminService.saveOrUpdate(admin));
    }

    /**
     * 修改数据
     *
     * @return 修改结果
     */
    @PutMapping
    @ResponseBody
    public ResponseEntity update(AdminVo vo) {
        Admin admin = new Admin();
        BeanUtils.copyProperties(vo, admin);
        return ResponseEntity.success(this.adminService.updateById(admin));
    }

    /**
     * 删除数据
     *
     * @param idList 主键结合
     * @return 删除结果
     */
    @DeleteMapping
    @ResponseBody
    public ResponseEntity delete(@RequestParam("idList") List<Long> idList) {
        return ResponseEntity.success(this.adminService.removeByIds(idList));
    }
}
