package com.aliapp.rent.controller;


import cn.hutool.core.util.ArrayUtil;
import com.aliapp.rent.entity.po.*;
import com.aliapp.rent.entity.vo.ProductVo;
import com.aliapp.rent.service.*;
import com.aliapp.rent.util.ResponseEntity;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.BeanUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.*;

/**
 * (Product)表控制层
 *
 * @author makejava
 * @since 2021-04-07 09:33:45
 */
@RestController
@RequestMapping("product")
public class ProductController {
    /**
     * 服务对象
     */
    @Resource
    private ProductService productService;

    @Resource
    private BrandService brandService;

    @Resource
    private PictureService pictureService;

    @Resource
    private UserInfoService userInfoService;

    @Resource
    private VipService vipService;

    @Resource
    private CategoryListService categoryListService;

    public Product copy(ProductVo source) {
        Product target = new Product();
        BeanUtils.copyProperties(source, target);
        return target;
    }

    /**
     * 分页查询所有数据
     *
     * @param page 分页对象
     * @return 所有数据
     */
    @GetMapping
    public ResponseEntity selectAll(Page<ProductVo> page, ProductVo vo) {

        QueryWrapper<Product> qw = new QueryWrapper<>();

        //搜索框值
        qw.like(StringUtils.hasText(vo.getSearchValue()),
                "a.name", vo.getSearchValue()).or().
                like(StringUtils.hasText(vo.getSearchValue()),
                        "a.title", vo.getSearchValue()).or()
                .like(StringUtils.hasText(vo.getSearchValue()),
                        "b.title", vo.getSearchValue()).or()
                .like(StringUtils.hasText(vo.getSearchValue()),
                        "b.name", vo.getSearchValue());

        //过滤条件查询
        //按品牌
        if (vo.getBrand() != null && vo.getBrand().length() > 0) {
            HashMap<String, Object> map = new HashMap<>();
            map.put("name", vo.getBrand());
            List<Brand> brands = brandService.listByMap(map);
            Integer bid = brands.get(0).getId();

            qw.eq("a.bid", bid);
        }

        //产品状态
        qw.eq(vo.getState() != null, "a.state", vo.getState());

        //产品分类
        qw.eq(vo.getClid() != null, "a.clid", vo.getClid());

        //qw.between的condition无效，用if代替
        if (vo.getMinRent() != null && vo.getMaxRent() != null) {
            //按租金区间
            qw.between("a.price_day",
                    vo.getMinRent() - 0.01, vo.getMaxRent() + 0.01);
        }

        if (vo.getMinDeposit() != null && vo.getMaxDeposit() != null) {
            //按押金金区间
            qw.between("a.deposite",
                    vo.getMinDeposit() - 0.01, vo.getMaxDeposit() + 0.01);
        }


        //按物流方式
        qw.like(vo.getLogistics() != null && vo.getLogistics().length() > 0, "a.deliver",
                vo.getLogistics());

        //商品根据会员等级排序
        qw.orderByDesc("a.level");

        switch (vo.getCommodityType()) {
            case 1:
                qw.orderByDesc("a.create_date");
                break;
            case 2:
                qw.orderByDesc("a.price_day");
                break;
            case 3:
                qw.orderByDesc("a.sales");
                break;
        }
        IPage<ProductVo> productVoIPage = this.productService.queryProduct(page, qw);
        return ResponseEntity.success(productVoIPage);
    }

    @GetMapping("list")
    public ResponseEntity list(ProductVo vo) {
        Product product = new Product();
        BeanUtils.copyProperties(vo, product);
        //创建时间  倒序排序
        QueryWrapper<Product> qw = new QueryWrapper<>(product);
        qw.orderByDesc("create_date");

        return ResponseEntity.success(this.productService.list(qw));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public ResponseEntity selectOne(@PathVariable Serializable id) {
        //判断商品是否过期
        Product product = this.productService.getById(id);
        return ResponseEntity.success(product);
    }

//    @GetMapping("myRent")
//    public ResponseEntity myRent(Page<Product> page, ProductVo vo) {
//        Product product = new Product();
//        BeanUtils.copyProperties(vo, product);
//        Page<Product> productPage = productService.page(page, new QueryWrapper<>
//        (product));
//        return ResponseEntity.success(productPage);
//    }

    /**
     * 点击租赁时创建产品，获取该产品ID，后续更新该产品信息
     *
     * @return 新增结果
     */
    @PostMapping
    public ResponseEntity insert(@RequestBody ProductVo vo) {
        //判断是否超过最大租赁数
        UserInfo userInfo = userInfoService.getById(vo.getPublisherId());
        Vip vip = vipService.getById(userInfo.getVid());
        //查询用户发布正在上架的商品
        QueryWrapper<Product> qw = new QueryWrapper<>();
        qw.eq("publisher_id", vo.getPublisherId()).eq("state", 1);
        if (vip.getNum() <= productService.count(qw)) {
            return ResponseEntity.fail("发布租赁数量已达最大限制！");
        }

        //判断之前是否有未写完的商品
        QueryWrapper<Product> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("publisher_id", vo.getPublisherId()).eq("state", 0);
        Product one = productService.getOne(queryWrapper);
        if (one != null) {
            return ResponseEntity.success(one.getId());
        }

        //新增商品
        Product product = this.copy(vo);
        this.productService.saveOrUpdate(product);
        return ResponseEntity.success(product.getId());
    }

    /**
     * 用户选择二级类目,更新商品clid
     */
    @PutMapping("userSel")
    public ResponseEntity userSel(@RequestBody ProductVo vo) {
        Product product = new Product();
        BeanUtils.copyProperties(vo, product);
        productService.updateById(product);
        return ResponseEntity.success();
    }

    /**
     * 用户新增二级类目,更新商品clid
     */
    @PutMapping("userAdd")
    public ResponseEntity userAdd(@RequestBody ProductVo vo) {
        //判断分类名称是否存在
        QueryWrapper<CategoryList> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name", vo.getCname()).eq("state", 1);
        if (categoryListService.getOne(queryWrapper) != null) {
            return ResponseEntity.fail("该分类名已存在！");
        }

        //更新商品clid
        Product product = new Product();
        BeanUtils.copyProperties(vo, product);
        productService.updateById(product);

        //更新二级分类名称
        CategoryList categoryList = categoryListService.getById(vo.getClid());
        categoryList.setName(vo.getCname());
        categoryListService.updateById(categoryList);
        return ResponseEntity.success();
    }

    /**
     * 修改数据
     *
     * @return 修改结果(有租期 ， 套餐等)
     */
    @PutMapping
    public ResponseEntity update(@RequestBody ProductVo vo) {
        String tids = vo.getTids();
        //租期变成字符串
        String[] split = tids.split(",");
        //排序
        int[] ints = Arrays.stream(split).mapToInt(Integer::parseInt).toArray();
        Arrays.sort(ints);
        String s = ArrayUtil.join(ints, ",");
        vo.setTids(s);
        Product product = this.copy(vo);

        //品牌名
        if (StringUtils.hasText(vo.getBrand())) {
            HashMap<String, Object> map = new HashMap<>();
            map.put("name", vo.getBrand());
            Brand brand = brandService.listByMap(map).get(0);
            product.setBid(brand.getId());
        }
        return ResponseEntity.success(this.productService.updateById(product));
    }

    //创建商品成功！更新状态
    @PostMapping("success")
    public ResponseEntity success(@RequestBody ProductVo vo) {
        //获取商品
        Product product = productService.getById(vo.getId());
        product.setState(vo.getState());
        //商品创建时间
        product.setCreateDate(new Date());

        //商品过期时间
        // Calendar instance = Calendar.getInstance();
        // instance.setTime(product.getCreateDate());
        //获取会员等级信息
        // UserInfo userInfo = userInfoService.getById(product.getPublisherId());
        // Vip vip = vipService.getById(userInfo.getVid());
        //设置过期时间
        // instance.add(Calendar.DATE, vip.getDay());
        // product.setExpTime(instance.getTime());

        //更新二级分类为可用状态
        categoryListService.update(new UpdateWrapper<CategoryList>().eq("id", product.getClid()).set("state", 1));
        //更新商品信息
        this.productService.updateById(product);
        //获取分类ID，传至前端，进行跳转
        CategoryList cl = categoryListService.getById(product.getClid());
        return ResponseEntity.success(cl);
    }

    // 后台改变状态
    @PostMapping("ban")
    public ResponseEntity ban(ProductVo vo) {
        Product product = new Product();
        BeanUtils.copyProperties(vo, product);
        return ResponseEntity.success(this.productService.updateById(product));
    }

    //编辑基本信息
    @PutMapping("edit")
    public ResponseEntity edit(@RequestBody ProductVo vo) {
        Product product = new Product();
        BeanUtils.copyProperties(vo, product);

        if (StringUtils.hasText(vo.getBrand())) {
            HashMap<String, Object> map = new HashMap<>();
            map.put("name", vo.getBrand());
            Brand brand = brandService.listByMap(map).get(0);
            product.setBid(brand.getId());
        }
        return ResponseEntity.success(this.productService.updateById(product));
    }

    //设置上传图片的第一张为列表展示图片
    @PutMapping("pic")
    public ResponseEntity pic(@RequestBody ProductVo vo) {
        Product product = this.copy(vo);
        Map<String, Object> map = new HashMap<>();
        map.put("pid", vo.getId());
        map.put("state", 0);
        Picture picture = pictureService.listByMap(map).get(0);
        product.setPicUrl(picture.getPicUrl());
        return ResponseEntity.success(this.productService.updateById(product));
    }

    //商品接口创建（订单中心，目前不需要）
    @PostMapping("itemOpenCreate")
    public ResponseEntity itemOpenCreate(@RequestBody ProductVo vo) {
        Product product = productService.getById(vo.getId());
        String picUrl = vo.getPicUrl();
        //获取materialKey
        Picture picture = new Picture();
        picture.setPicUrl(picUrl);
        picture.setPid(vo.getId());
        Picture picture1 = pictureService.list(new QueryWrapper<>(picture)).get(0);
        productService.updateById(product);
        return ResponseEntity.success();
    }

    /**
     * 删除数据
     *
     * @param idList 主键结合
     * @return 删除结果
     */
    @DeleteMapping
    public ResponseEntity delete(@RequestParam("idList") List<Long> idList) {
        //同时删除图片
        QueryWrapper<Picture> qw = new QueryWrapper<>();
        qw.eq("pid", idList.get(0));
        this.pictureService.remove(qw);

        return ResponseEntity.success(this.productService.removeByIds(idList));
    }
}
