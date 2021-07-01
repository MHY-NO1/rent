package com.aliapp.rent;

import com.aliapp.rent.service.ProductService;
import com.aliapp.rent.util.AlipayUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;

@SpringBootTest
class RentApplicationTests {

    @Resource
    ProductService productService;

    @Test
    void contextLoads() {

        // int[] ints = {1, 2};
        // String tids = "8,3,7";
        // String join = ArrayUtil.join(ints, ",");
        // System.out.println(join);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        AlipayUtils.transfer(dateFormat.format(new Date()) + (int) Math.random() * 10000, "0.01", "2088422297752145");

        return;
    }

}
