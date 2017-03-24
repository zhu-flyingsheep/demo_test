package com.utsoft.myapplication.model;

import com.orhanobut.logger.Logger;

import java.util.List;

public class ImageFloder {
    /**
     * 图片的文件夹路径
     */
    private String dir = "";

    /**
     * 第一张图片的路径
     */
    private String firstImagePath = "";

    /**
     * 文件夹的名称
     */
    private String name = "";

    /**
     * 图片的数量
     */
    private int count;

    public List<String> getImage_urls() {
        return image_urls;
    }

    public void setImage_urls(List<String> image_urls) {
        this.image_urls = image_urls;
    }

    private List<String> image_urls;

    public boolean is_choosed() {
        return is_choosed;
    }

    public void setIs_choosed(boolean is_choosed) {
        this.is_choosed = is_choosed;
    }

    public void setName(String name) {
        this.name = name;
    }

    private boolean is_choosed = false;

    public String getDir() {
        return dir;
    }

    public void setDir(String dir) {
        if ("All_Pictures_url".equals(dir)) {
            this.dir = dir;
        } else {
            this.dir = dir;
            int lastIndexOf = this.dir.lastIndexOf("/");
            this.name = this.dir.substring(lastIndexOf);
            Logger.i("filename", name + "\n" + dir);
        }

    }

    public String getFirstImagePath() {
        return firstImagePath;
    }

    public void setFirstImagePath(String firstImagePath) {
        this.firstImagePath = firstImagePath;
    }

    public String getName() {
        return name;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }


}
