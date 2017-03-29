package com.example.myapplication.Chaino_Responsibility;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by wangzhu on 2017/3/29.
 * email:zhu.wang@utsoft.cn
 * func:
 */

public class Student extends Myhandler {
    private int diffcult = 3;
    private Context context;

    public Student(Context context) {
        this.context = context;
    }

    @Override
    public void hand_request(int condition) {
        if (condition > diffcult) {
            myhandler.hand_request(condition);
        } else {
            Toast.makeText(context, "困难不大，学生解决", Toast.LENGTH_SHORT).show();
        }
    }
}
