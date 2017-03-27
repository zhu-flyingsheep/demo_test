package com.example.wangzhu.myapplication.bean;

/**
 * Created by wangzhu on 2017/3/27.
 * email:zhu.wang@utsoft.cn
 * func:
 */

public class Car<T> {
    private String type = "aodi";

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    private String price = "10w";

    public T getCar() {
        return car;
    }

    public void setCar(T car) {
        this.car = car;
    }

    private T car;

    public Car(T t) {
        this.car = t;
    }

}
