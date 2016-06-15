package com.skystudio.qiya.pojo;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/4/30.
 * 朋友实体类
 */
public class Friend implements Serializable {
    private int id;
    private String name;
    private String imgUrl;
    private String autograph;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getAutograph() {
        return autograph;
    }

    public void setAutograph(String autograph) {
        this.autograph = autograph;
    }

    @Override
    public String toString() {
        return "this is friend,his name is" + getName();
    }
}
