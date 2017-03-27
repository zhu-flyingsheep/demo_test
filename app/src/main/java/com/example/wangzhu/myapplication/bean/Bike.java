package com.example.wangzhu.myapplication.bean;

/**
 * Created by wangzhu on 2017/3/27.
 * email:zhu.wang@utsoft.cn
 * func:
 */

public class Bike<T> extends Car<T> {
    private String can_ride;

    public Bike(T t) {
        super(t);
    }

}
