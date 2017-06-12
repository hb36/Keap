package com.example.hb_we.keap;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

/**
 * Created by hb_we on 2017/5/10.
 */

public class RegisterActivity extends Activity {

    private EditText setUserName;
    private EditText setPwd;
    private EditText pwdConfirm;
    private EditText input;
    private ImageButton clearUsernameButton;
    private ImageButton clearPwdButton;
    private ImageButton clearConfirmPwdButton;
    private Button nextStepButton;
    private Button cancelButton;

    private UserDataManager mUserDataManager;

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
    protected void clearInput(EditText input){
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
        setContentView(R.layout.activity_register);

        setUserName = (EditText) findViewById(R.id.username_edit);
        setPwd = (EditText) findViewById(R.id.password_edit);
        pwdConfirm = (EditText) findViewById(R.id.password_confirm);
        clearUsernameButton = (ImageButton) findViewById(R.id.clearUsername);
        clearPwdButton = (ImageButton) findViewById(R.id.clearPwd);
        clearConfirmPwdButton = (ImageButton) findViewById(R.id.clearConfirmPwd);
        nextStepButton = (Button) findViewById(R.id.next_step_button);
        cancelButton = (Button) findViewById(R.id.cancel_button);

        clearUsernameButton.setOnClickListener(m_register_Listener);
        clearPwdButton.setOnClickListener(m_register_Listener);
        clearConfirmPwdButton.setOnClickListener(m_register_Listener);
        nextStepButton.setOnClickListener(m_register_Listener);      //注册界面两个按钮的监听事件
        cancelButton.setOnClickListener(m_register_Listener);

        if (mUserDataManager == null) {
            mUserDataManager = new UserDataManager(this);
            mUserDataManager.openDataBase();                              //建立本地数据库
        }
    }

    View.OnClickListener m_register_Listener = new View.OnClickListener() {    //不同按钮按下的监听事件选择
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.clearUsername:            //清除输入用户名的监听事件
                    clearInput(setUserName);
                    break;
                case R.id.clearPwd:             // 清除输入密码的监听事件
                    clearInput(setPwd);
                    break;
                case R.id.clearConfirmPwd:      //  清除确认密码的监听事件
                    clearInput(pwdConfirm);
                    break;
                case R.id.next_step_button:                       //确认按钮的监听事件
                    register_check();
                    break;
                case R.id.cancel_button:                     //取消按钮的监听事件,由注册界面返回登录界面
                    Intent intent_Register_to_Login = new Intent(RegisterActivity.this, LoginActivity.class);    //切换User Activity至Login Activity
                    startActivity(intent_Register_to_Login);
                    finish();
                    break;
                default:
                    break;
            }
        }
    };

    public void register_check() {                                //确认按钮的监听事件
        if (isUserNameAndPwdValid()) {
            String userName = setUserName.getText().toString().trim();
            String userPwd = setPwd.getText().toString().trim();
            String userPwdCheck = pwdConfirm.getText().toString().trim();
            //检查用户是否存在
            int result = mUserDataManager.findUserByName(userName);
            //用户已经存在时返回，给出提示文字
            if (result > 0) {
                Toast.makeText(this, getString(R.string.name_already_exist), Toast.LENGTH_SHORT).show();
                return;
            }
            if (!userPwd.equals(userPwdCheck)) {     //两次密码输入不一样
                Toast.makeText(this, getString(R.string.pwd_not_the_same), Toast.LENGTH_SHORT).show();
                return;
            } else {
                UserData mUser = new UserData(userName, userPwd);
                mUserDataManager.openDataBase();
                long flag = mUserDataManager.insertUserData(mUser); //新建用户信息
                if (flag == -1) {
                    Toast.makeText(this, getString(R.string.register_fail), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, getString(R.string.register_success), Toast.LENGTH_SHORT).show();
                    Intent intent_Register_to_Edit = new Intent(RegisterActivity.this, EditInformationActivity.class);    //切换RegisterActivity至EditInformationActivity
                    intent_Register_to_Edit.putExtra("extra_name",userName);
                    startActivity(intent_Register_to_Edit);
                    finish();
                }
            }
        }
    }

    public boolean isUserNameAndPwdValid() {
        if (setUserName.getText().toString().trim().equals("")) {
            Toast.makeText(this, getString(R.string.account_empty),
                    Toast.LENGTH_SHORT).show();
            return false;
        } else if (setPwd.getText().toString().trim().equals("")) {
            Toast.makeText(this, getString(R.string.pwd_empty),
                    Toast.LENGTH_SHORT).show();
            return false;
        } else if (pwdConfirm.getText().toString().trim().equals("")) {
            Toast.makeText(this, getString(R.string.pwd_check_empty),
                    Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

}


