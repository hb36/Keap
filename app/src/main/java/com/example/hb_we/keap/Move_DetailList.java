package com.example.hb_we.keap;

import java.io.Serializable;

/**
 * Created by Cxy on 2017/5/9.
 */

public class Move_DetailList implements Serializable {
    private static final long serialVersionUID = -3450064362986273896L;
    private String week;
    private String date;
    private String detail;

    public String getWeek() {
        return this.week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    public String getDate() {
        return this.date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getContent() {
        return this.detail;
    }

    public void setContent(String content) {
        this.detail= content;
    }

}
