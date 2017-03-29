package com.example.myapplication.Chaino_Responsibility;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by wangzhu on 2017/3/29.
 * email:zhu.wang@utsoft.cn
 * func:
 */

public class Teacher extends Myhandler {
    private int diffcult = 7;
    private Context context;
    public Teacher(Context context) {
        this.context = context;
    }
    @Override
    public void hand_request(int condition) {
        if (condition > diffcult) {
            myhandler.hand_request(condition);
        } else {
            Toast.makeText(context, "困难适中，老师解决", Toast.LENGTH_SHORT).show();
        }
    }
}
