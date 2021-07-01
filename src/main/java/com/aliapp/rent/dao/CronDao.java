package com.aliapp.rent.dao;

import org.apache.ibatis.annotations.Select;

public interface CronDao {
    @Select("select cron from cron limit 1")
    public String getCron();
}
