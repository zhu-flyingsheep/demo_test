package com.example.myapplication.Chaino_Responsibility;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by wangzhu on 2017/3/29.
 * email:zhu.wang@utsoft.cn
 * func:
 */

public class Schoolmaster extends Myhandler {
    private int diffcult = 10;
    private Context context;

    public Schoolmaster(Context context) {
        this.context = context;
    }

    @Override
    public void hand_request(int condition) {
        if (condition > diffcult) {
            Toast.makeText(context, "不再传递，无法解决", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "困难最大，校长解决", Toast.LENGTH_SHORT).show();
        }
    }
}
