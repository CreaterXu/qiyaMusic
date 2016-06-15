package com.skystudio.qiya.pojo;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Administrator on 2016/4/30.
 * 推荐消息实体类
 */
public class Message implements Serializable {
    private int id;
    private ArrayList<String> to_person = null;
    private String context;//推荐内容

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<String> getTo_person() {
        return to_person;
    }

    public void setTo_person(ArrayList<String> to_person) {
        this.to_person = to_person;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    @Override
    public String toString() {
        return "this is message " + getContext();
    }
}
