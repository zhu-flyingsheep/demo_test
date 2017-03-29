package com.example.myapplication.strategy;


import android.content.Context;
import android.widget.Toast;

/**
 * Created by wangzhu on 2017/3/29.
 * email:zhu.wang@utsoft.cn
 * func:
 */

public class Bybike implements IgotoWork {
    private Context context;

    public Bybike(Context context) {
        this.context = context;
    }

    @Override
    public void goto_work() {
        Toast.makeText(context, "骑车累成狗但是灵活不会迟到时间可控", Toast.LENGTH_SHORT).show();
    }
}
