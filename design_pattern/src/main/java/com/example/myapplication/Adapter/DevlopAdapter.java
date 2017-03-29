package com.example.myapplication.Adapter;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by wangzhu on 2017/3/29.
 * email:zhu.wang@utsoft.cn
 * func:
 */

public class DevlopAdapter implements IAndroidengineer {
    private IosEngineer iosEngineer;
    private Context context;

    public DevlopAdapter(Context context) {
        this.context = context;
    }

    @Override
    public void devlop_android_app() {
        Toast.makeText(context, "开发安卓app", Toast.LENGTH_SHORT).show();

    }

    public void devlop_ios_app() {
        if (null != iosEngineer)
            iosEngineer.devlop_ios_app();
    }

    public IosEngineer getIosEngineer() {
        return iosEngineer;
    }

    public void setIosEngineer(IosEngineer iosEngineer) {
        this.iosEngineer = iosEngineer;
    }
}
