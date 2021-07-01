package com.aliapp.rent.service.impl;

import com.aliapp.rent.dao.VipDao;
import com.aliapp.rent.entity.po.Vip;
import com.aliapp.rent.service.VipService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * (Vip)表服务实现类
 *
 * @author makejava
 * @since 2021-05-12 15:29:52
 */
@Service("vipService")
public class VipServiceImpl extends ServiceImpl<VipDao, Vip> implements VipService {
    /**
     * 计算日期相差天数
     *
     * @param smdate
     * @param bdate
     * @return
     * @throws ParseException
     */
    @Override
    public int daysBetween(Date smdate, Date bdate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            smdate = sdf.parse(sdf.format(smdate));
            bdate = sdf.parse(sdf.format(bdate));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(smdate);
        long time1 = cal.getTimeInMillis();
        cal.setTime(bdate);
        long time2 = cal.getTimeInMillis();
        long betweenDays = (time2 - time1) / (1000 * 3600 * 24);
        return Integer.parseInt(String.valueOf(betweenDays));
    }
}
