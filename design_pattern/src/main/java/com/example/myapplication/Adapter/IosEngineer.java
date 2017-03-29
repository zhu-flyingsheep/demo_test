package com.example.myapplication.Adapter;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by wangzhu on 2017/3/29.
 * email:zhu.wang@utsoft.cn
 * func:
 */

public class IosEngineer {
    public IosEngineer(Context context) {
        this.context = context;
    }

    private Context context;

    public void devlop_ios_app() {
        Toast.makeText(context, "开发苹果app", Toast.LENGTH_SHORT).show();
    }
}
