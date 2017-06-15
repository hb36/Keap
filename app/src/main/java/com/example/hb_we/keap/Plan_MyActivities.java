package com.example.hb_we.keap;

/**
 * Created by Windows on 2017/5/9.
 */

public class Plan_MyActivities {

    private String name;
    private String number;

    public Plan_MyActivities(String name, String number){
        this.name = name;
        this.number = number;
    }

    public String getName(){
        return name;
    }

    public String getNumber(){
        return number;
    }

    public void setName(String new_name){
        this.name = new_name;
    }

    public void setNumber(String new_number){
        this.number = new_number;
    }
}
