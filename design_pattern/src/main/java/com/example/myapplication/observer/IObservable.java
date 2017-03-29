package com.example.myapplication.observer;

/**
 * Created by wangzhu on 2017/3/29.
 * email:zhu.wang@utsoft.cn
 * func:
 */

public interface IObservable {
    public void add(Iobserver iobserver);

    public void remove(Iobserver iobserver);

    public void clear();

    public void notifa_change();
}
