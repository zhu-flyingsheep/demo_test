package com.example.myapplication.states;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by wangzhu on 2017/3/29.
 * email:zhu.wang@utsoft.cn
 * func:
 */

public class Married implements Istate {
    public Context context;

    public Married(Context context) {
        this.context = context;
    }

    @Override
    public void looking_for_girlfriend() {
        Toast.makeText(context, "已婚不能出轨", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void fuck_with_girlfriend() {
        Toast.makeText(context, "已婚可以和老婆不可描述", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void eating_samethins() {
        Toast.makeText(context, "已婚和老婆一起吃东西", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void is_alone() {
        Toast.makeText(context, "不是一个人", Toast.LENGTH_SHORT).show();

    }
    @Override
    public void start() {
        eating_samethins();
        is_alone();
        looking_for_girlfriend();
        fuck_with_girlfriend();
    }
}
