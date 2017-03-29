package com.example.myapplication.strategy;


import android.content.Context;
import android.widget.Toast;

/**
 * Created by wangzhu on 2017/3/29.
 * email:zhu.wang@utsoft.cn
 * func:
 */

public class ByBus implements IgotoWork {
    private Context context;

    public ByBus(Context context) {
        this.context = context;
    }

    @Override
    public void goto_work() {
        Toast.makeText(context, "坐公交车不累，但是很堵，时间不可控，而且成本比骑车多", Toast.LENGTH_SHORT).show();
    }
}
