package com.example.myapplication.strategy;


import android.content.Context;
import android.widget.Toast;

/**
 * Created by wangzhu on 2017/3/29.
 * email:zhu.wang@utsoft.cn
 * func:
 */

public class BySubway implements IgotoWork {
    private Context context;

    public BySubway(Context context) {
        this.context = context;
    }

    @Override
    public void goto_work() {
        Toast.makeText(context, "地铁最方便，但是成本高", Toast.LENGTH_SHORT).show();
    }
}
