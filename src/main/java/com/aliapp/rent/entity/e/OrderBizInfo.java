package com.aliapp.rent.entity.e;

public enum OrderBizInfo {
    //同步用户已履约
    COMPLETE,
    //同步履约已取消 ,不推荐使用
    CLOSED,
    //用户已违约
    VIOLATED
}
