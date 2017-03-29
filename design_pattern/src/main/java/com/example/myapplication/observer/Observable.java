package com.example.myapplication.observer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangzhu on 2017/3/29.
 * email:zhu.wang@utsoft.cn
 * func:
 */

public class Observable implements IObservable {
    private List<Iobserver> iobservers = new ArrayList<>();

    @Override
    public void add(Iobserver iobserver) {
        iobservers.add(iobserver);
    }

    @Override
    public void remove(Iobserver iobserver) {
        if (iobservers.contains(iobserver)) {
            iobservers.remove(iobserver);
        }
    }

    @Override
    public void clear() {
        iobservers.clear();
    }

    @Override
    public void notifa_change() {
        for (Iobserver iobserver : iobservers) {
            iobserver.upadte("去吃饭了");
        }
    }

}
