package com.aliapp.rent.controller;


import com.aliapp.rent.entity.po.Product;
import com.aliapp.rent.entity.po.TenancyCombo;
import com.aliapp.rent.entity.vo.ComboVo;
import com.aliapp.rent.entity.vo.EditComboListVo;
import com.aliapp.rent.entity.vo.EditComboVo;
import com.aliapp.rent.entity.vo.TenancyComboVo;
import com.aliapp.rent.service.ProductService;
import com.aliapp.rent.service.TenancyComboService;
import com.aliapp.rent.util.ResponseEntity;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * (TenancyCombo)表控制层
 *
 * @author makejava
 * @since 2021-04-10 13:08:01
 */
@RestController
@RequestMapping("tenancyCombo")
public class TenancyComboController {
    /**
     * 服务对象
     */
    @Resource
    private TenancyComboService tenancyComboService;

    @Resource
    private ProductService productService;

    /**
     * 查询所有数据
     *
     * @return 所有数据
     */
    @GetMapping
    public ResponseEntity selectAll(TenancyComboVo vo) {
        TenancyCombo tenancyCombo = new TenancyCombo();
        BeanUtils.copyProperties(vo, tenancyCombo);
        List<TenancyCombo> list =
                this.tenancyComboService.list(new QueryWrapper<>(tenancyCombo));
        return ResponseEntity.success(list);
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public ResponseEntity selectOne(@PathVariable Serializable id) {
        return ResponseEntity.success(this.tenancyComboService.getById(id));
    }

    /**
     * 新增数据
     *
     * @return 新增结果
     */
    @PostMapping
    public ResponseEntity insert(@RequestBody TenancyComboVo vo) {
        //新增前查询跟pid有关套餐信息
        HashMap<String, Object> map = new HashMap<>();
        map.put("pid", vo.getPid());
        List<TenancyCombo> tenancyCombos = tenancyComboService.listByMap(map);
        //是否有之前未写完的值，必须保证套餐数是一样的
        boolean hasValue = false;
        int temp = 0;
        if (tenancyCombos != null && tenancyCombos.size() > 0) {
            hasValue = true;
        }

        //获取租期
        List<TenancyCombo> list = new ArrayList<>();
        String tenancy = vo.getTenancy();
        String[] days = tenancy.split(",");

        //总库存
        int total = 0;

        //拼接套餐名
        StringBuilder sb = new StringBuilder();
        for (ComboVo comboVo : vo.getList()) {
            //累加库存
            total += comboVo.getInventory();
            for (int i = 0; i < days.length; i++) {
                TenancyCombo tenancyCombo = new TenancyCombo();
                if (hasValue) {
                    tenancyCombo.setId(tenancyCombos.get(temp).getId());
                    temp++;
                }
                tenancyCombo.setTid(Integer.parseInt(days[i]));
                tenancyCombo.setInventory(comboVo.getInventory());
                tenancyCombo.setPid(vo.getPid());
                tenancyCombo.setCid(comboVo.getName());
                tenancyCombo.setRent(comboVo.getRents().get(i));
                tenancyCombo.setDeposite(comboVo.getDeposite());
                tenancyCombo.setBuyOutAmount(comboVo.getBuyOutAmount());
                list.add(tenancyCombo);
            }
            sb.append(comboVo.getName());
            sb.append(",");
        }
        this.tenancyComboService.saveOrUpdateBatch(list);

        //更新商品总库存
        UpdateWrapper<Product> uw = new UpdateWrapper<>();
        uw.set("inventory", total);
        uw.eq("id", vo.getPid());
        productService.update(uw);

        sb.deleteCharAt(sb.length() - 1);
        int[] ints = Arrays.stream(days).mapToInt(Integer::parseInt).toArray();
        Arrays.sort(ints);

        QueryWrapper<TenancyCombo> queryWrapper = new QueryWrapper<>();
        //获取最小租金
        queryWrapper.select("min(rent) rent");
        queryWrapper.eq("pid", vo.getPid());
        queryWrapper.eq("tid", ints[ints.length - 1]);
        TenancyCombo one = tenancyComboService.getOne(queryWrapper);

        QueryWrapper<TenancyCombo> qw = new QueryWrapper<>();
        //获取最大押金
        qw.select("max(deposite) deposite");
        qw.eq("pid", vo.getPid());
        TenancyCombo one1 = tenancyComboService.getOne(qw);

        Product product = new Product();
        product.setId(vo.getPid());
        product.setCids(sb.toString());
        product.setPriceDay(one.getRent() / ints[ints.length - 1]);
        product.setDeposite(one1.getDeposite());
        productService.updateById(product);

        return ResponseEntity.success();
    }

    /**
     * 编辑套餐时获取信息
     */
    @GetMapping("editGet")
    public ResponseEntity editGet(TenancyComboVo vo) {
        Integer pid = vo.getPid();
        Product product = productService.getById(pid);
        //获取套餐合集
        String[] combos = product.getCids().split(",");
        //获取所有套餐
        TenancyCombo tenancyCombo = new TenancyCombo();
        BeanUtils.copyProperties(vo, tenancyCombo);
        List<TenancyCombo> list = tenancyComboService.list(new QueryWrapper<>(tenancyCombo));
        //组合成新的实体

        ArrayList<EditComboVo> resList = new ArrayList<>();
        //遍历每个套餐名
        for (String i : combos) {
            EditComboVo editComboVo = new EditComboVo();
            editComboVo.setCombo(i);
            ArrayList<EditComboListVo> list1 = new ArrayList<>();
            for (TenancyCombo t : list) {
                if (i.equals(t.getCid())) {
                    editComboVo.setDeposite(t.getDeposite());
                    editComboVo.setBuyOutAmount(t.getBuyOutAmount());
                    editComboVo.setInventory(t.getInventory());
                    list1.add(new EditComboListVo(t.getTid(), t.getRent()));
                }
            }
            editComboVo.setList(list1);
            resList.add(editComboVo);
        }
        return ResponseEntity.success(resList);
    }

    /**
     * 编辑套餐信息
     *
     * @return 修改结果
     */
    @PutMapping
    public ResponseEntity update(@RequestBody TenancyComboVo vo) {
        List<EditComboVo> editList = vo.getEditList();
        //更新套餐信息
        //新增前查询跟pid有关套餐信息
        TenancyCombo tenancyCombo = new TenancyCombo();
        BeanUtils.copyProperties(vo, tenancyCombo);
        List<TenancyCombo> tenancyCombos = tenancyComboService.list(new QueryWrapper<>(tenancyCombo));
        //必须保证套餐数是一样的
        int temp = 0;

        //新增编辑信息
        ArrayList<TenancyCombo> list = new ArrayList<>();
        for (EditComboVo i : editList) {
            for (EditComboListVo j : i.getList()) {
                TenancyCombo combo = new TenancyCombo();
                combo.setId(tenancyCombos.get(temp).getId());
                temp++;
                combo.setPid(vo.getPid());
                combo.setInventory(i.getInventory());
                combo.setDeposite(i.getDeposite());
                combo.setCid(i.getCombo());
                combo.setBuyOutAmount(i.getBuyOutAmount());

                combo.setRent(j.getRent());
                combo.setTid(j.getTid());

                list.add(combo);
            }
        }
        return ResponseEntity.success(tenancyComboService.saveOrUpdateBatch(list));
    }

    /**
     * 删除数据
     *
     * @param idList 主键结合
     * @return 删除结果
     */
    @DeleteMapping
    public ResponseEntity delete(@RequestParam("idList") List<Long> idList) {
        return ResponseEntity.success(this.tenancyComboService.removeByIds(idList));
    }
}
