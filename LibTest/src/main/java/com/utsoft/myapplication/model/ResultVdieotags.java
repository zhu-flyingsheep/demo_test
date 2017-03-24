package com.utsoft.myapplication.model;

import java.util.List;

/**
 * Created by hyong on 2017/3/7.
 * func:
 */

public class ResultVdieotags {

    /**
     * flag : 1
     */

    private int flag;
    /**
     * code :
     * modifyDate : 2016-10-14 01:08:20
     * sortIndex : 1
     * tagIndex : 33
     * showType : 3
     * pid :
     * id : 57ff4f04abf6eba7e7ae5a33
     * tagName : LIVE
     * createDate : 2016-10-14 01:08:20
     * child : []
     */

    private List<ResultBean> result;

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }


}
