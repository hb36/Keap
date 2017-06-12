package com.example.hb_we.keap;

/**
 * Created by hb_we on 2017/6/4.
 */

public class UserData {
    private int userId;                       //用户账户
    private String userName;                  //用户名
    private String userPwd;                   //用户密码

    public int pwdresetFlag = 0;

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

    //获取用户密码
    public String getUserPwd() {                //获取用户密码
        return userPwd;
    }

    //设置用户密码
    public void setUserPwd(String userPwd) {     //输入用户密码
        this.userPwd = userPwd;
    }

    public UserData(String userName, String userPwd) {  //这里只采用用户名和密码
        super();
        this.userName = userName;
        this.userPwd = userPwd;
    }

}
