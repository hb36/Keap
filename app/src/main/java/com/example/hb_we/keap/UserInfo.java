package com.example.hb_we.keap;

/**
 * Created by hb_we on 2017/6/11.
 */

public class UserInfo {
    private int userId;                       //用户账户
    private String userName;                  //用户名
    private String userSex;
    private String userBirthYear;
    private String userHeight;
    private String userWeight;

    public int getUserId(){
        return userId;
    }

    //获取用户名
    public String getUserName() {             //获取用户名
        return userName;
    }

    //设置用户名
    public void setUserName(String userName) {  //输入用户名
        this.userName = userName;
    }

    public String getUserSex() {             //获取用户性别
        return userSex;
    }

    //设置用户性别
    public void setUserSex(String userSex) {  //选择用户性别
        this.userSex = userSex;
    }

    public String getUserBirthYear() {             //获取用户出生年份
        return userBirthYear;
    }

    //设置用户身高
    public void setUserBirthYear(String userBirthYear) {  //输入用户出生年份
        this.userBirthYear = userBirthYear;
    }

    public String getUserHeight() {             //获取用户身高
        return userHeight;
    }

    //设置用户身高
    public void setUserHeight(String userHeight) {  //输入用户身高
        this.userHeight = userHeight;
    }

    public String getUserWeight() {             //获取用户体重
        return userWeight;
    }
    //设置用户体重
    public void setUserWeight(String userWeight) {  //输入用户体重
        this.userWeight = userWeight;
    }


    public UserInfo(String userName,String userSex,String userBirthYear,
                                String userHeight,String userWeight){
        this.userName = userName;
        this.userSex = userSex;
        this.userBirthYear = userBirthYear;
        this.userHeight = userHeight;
        this.userWeight = userWeight;
    }

}
