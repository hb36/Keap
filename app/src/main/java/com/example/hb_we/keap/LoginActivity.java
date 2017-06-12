package com.example.hb_we.keap;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class LoginActivity extends Activity {

    private UserDataManager mUserDataManager;

    private EditText inputUserName;
    private EditText inputPwd;
    private EditText input;
    private ImageButton clearName;
    private ImageButton clearPwd;
    private CheckBox autoLogin;
    private CheckBox rememberPwd;
    private Button login;
    private Button register;

    private SharedPreferences login_sp;

    public static final int CLEAR_TEXT = 1;
    private Handler handler = new Handler(){
        public void handleMessage(Message msg){
            switch (msg.what){
                case CLEAR_TEXT:
                    input.setText("");
                    break;
                default:
                    break;
            }
        }
    };
    protected void setInputNull(EditText input){
        this.input = input;
        new Thread(new Runnable() {
            @Override
            public void run() {
                Message message = new Message();
                message.what = CLEAR_TEXT;
                handler.sendMessage(message);
            }
        }).start();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        inputUserName = (EditText) findViewById(R.id.username_edit);
        clearName = (ImageButton) findViewById(R.id.clearName);
        inputPwd= (EditText) findViewById(R.id.pwdEdittext);
        clearPwd = (ImageButton) findViewById(R.id.clearPwd);
        autoLogin = (CheckBox) findViewById(R.id.autoLogin);
        rememberPwd = (CheckBox) findViewById(R.id.rememberPwd);
        login = (Button) findViewById(R.id.login);
        register = (Button) findViewById(R.id.register);

        login_sp = getSharedPreferences("userInfo", 0);
        String name=login_sp.getString("USER_NAME", "");
        String pwd =login_sp.getString("PASSWORD", "");
        boolean choseRemember =login_sp.getBoolean("mRememberCheck", false);
        boolean choseAutoLogin =login_sp.getBoolean("mAutologinCheck", false);
        //如果上次选了记住密码，那进入登录页面也自动勾选记住密码，并填上用户名和密码
        if(choseRemember){
            inputUserName.setText(name);
            inputPwd.setText(pwd);
            rememberPwd.setChecked(true);
            //如果上次选了自动登录，那进入登录页面也自动勾选记住密码，并自动登录跳转到个人信息界面
            if(choseAutoLogin){
                autoLogin.setChecked(true);
                Intent intent = new Intent(LoginActivity.this,InformationActivity.class);
                LoginActivity.this.startActivity(intent);
            }
        }
        //采用OnClickListener方法设置不同按钮按下之后的监听事件
        clearName.setOnClickListener(mListener);
        clearPwd.setOnClickListener(mListener);
        login.setOnClickListener(mListener);
        register.setOnClickListener(mListener);

        if (mUserDataManager == null) {
            mUserDataManager = new UserDataManager(this);
            mUserDataManager.openDataBase();                              //建立本地数据库
        }
    }

    View.OnClickListener mListener = new View.OnClickListener() {                  //不同按钮按下的监听事件选择
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.clearName:            //清除输入用户名的监听事件
                    setInputNull(inputUserName);
                    break;
                case R.id.clearPwd:             //清除输入密码的监听事件
                    setInputNull(inputPwd);
                    break;
                case R.id.register:                            //登录界面的注册按钮
                    Intent intent_Login_to_Register = new Intent(LoginActivity.this,RegisterActivity.class) ;    //切换MainActivity至RegisterActivity
                    startActivity(intent_Login_to_Register);
                    finish();
                    break;
                case R.id.login:                              //登录界面的登录按钮
                    login();
                    break;

            }
        }
    };

    public void login() {                                              //登录按钮监听事件
        if (isUserNameAndPwdValid()) {
            String userName = inputUserName.getText().toString().trim();    //获取当前输入的用户名和密码信息
            String userPwd = inputPwd.getText().toString().trim();
            SharedPreferences.Editor editor =login_sp.edit();
            int result=mUserDataManager.findUserByNameAndPwd(userName, userPwd);
            if(result==1){                                             //返回1说明用户名和密码均正确
                //保存用户名和密码
                editor.putString("USER_NAME", userName);
                editor.putString("PASSWORD", userPwd);

                //是否记住密码
                if(rememberPwd.isChecked()){
                    editor.putBoolean("rememberPwd", true);
                }else{
                    editor.putBoolean("rememberPwd", false);
                }
                editor.commit();

                Intent intent = new Intent(LoginActivity.this,InformationActivity.class) ;    //切换LoginActivity至InformationActivity
                intent.putExtra("extra_name",userName);
                startActivity(intent);
                finish();
                Toast.makeText(this, getString(R.string.login_success),Toast.LENGTH_SHORT).show();//登录成功提示
            }else if(result==0){
                Toast.makeText(this, getString(R.string.login_fail),Toast.LENGTH_SHORT).show();  //登录失败提示
            }
        }
    }

    public boolean isUserNameAndPwdValid() {
        if (inputUserName.getText().toString().trim().equals("")) {
            Toast.makeText(this, getString(R.string.account_empty),
                    Toast.LENGTH_SHORT).show();
            return false;
        } else if (inputPwd.getText().toString().trim().equals("")) {
            Toast.makeText(this, getString(R.string.pwd_empty),
                    Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
    @Override
    protected void onResume() {
        if (mUserDataManager == null) {
            mUserDataManager = new UserDataManager(this);
            mUserDataManager.openDataBase();
        }
        super.onResume();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
    @Override
    protected void onPause() {
        if (mUserDataManager != null) {
            mUserDataManager.closeDataBase();
            mUserDataManager = null;
        }
        super.onPause();
    }

}
