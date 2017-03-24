package com.utsoft.myapplication.model;

import java.util.List;

/**
 * Created by wangzhu on 2017/3/7.
 * func:
 */

public class VideoTag {

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

    private String code;
    private String modifyDate;
    private int sortIndex;
    private int tagIndex;
    private int showType;
    private String pid;
    private String id;
    private String tagName;
    private String createDate;
    private List<?> child;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(String modifyDate) {
        this.modifyDate = modifyDate;
    }

    public int getSortIndex() {
        return sortIndex;
    }

    public void setSortIndex(int sortIndex) {
        this.sortIndex = sortIndex;
    }

    public int getTagIndex() {
        return tagIndex;
    }

    public void setTagIndex(int tagIndex) {
        this.tagIndex = tagIndex;
    }

    public int getShowType() {
        return showType;
    }

    public void setShowType(int showType) {
        this.showType = showType;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public List<?> getChild() {
        return child;
    }

    public void setChild(List<?> child) {
        this.child = child;
    }
}
