package com.example.myapplication.Chaino_Responsibility;

/**
 * Created by wangzhu on 2017/3/29.
 * email:zhu.wang@utsoft.cn
 * func:
 */

public abstract class Myhandler {
    public Myhandler getMyhandler() {
        return myhandler;
    }

    public void setMyhandler(Myhandler myhandler) {
        this.myhandler = myhandler;
    }

    protected Myhandler myhandler;

    public abstract void hand_request(int condition);

}
