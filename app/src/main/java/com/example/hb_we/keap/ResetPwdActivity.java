package com.example.hb_we.keap;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ResetPwdActivity extends AppCompatActivity {

    private String name;
    private EditText setUserName;
    private EditText setPwd;
    private EditText setNewPwd;
    private EditText pwdConfirm;
    private Button confirmButton;
    private Button cancelButton;
    private UserDataManager mUserDataManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_reset_pwd);

        setUserName = (EditText) findViewById(R.id.username_edit);
        setPwd = (EditText) findViewById(R.id.old_password_edit);
        setNewPwd = (EditText) findViewById(R.id.new_password_edit);
        pwdConfirm = (EditText) findViewById(R.id.password_confirm);
        confirmButton = (Button) findViewById(R.id.next_step_button);
        cancelButton = (Button) findViewById(R.id.cancel_button);

        name = getIntent().getStringExtra("extra_name");

        confirmButton.setOnClickListener(m_resetpwd_Listener);      //注册界面两个按钮的监听事件
        cancelButton.setOnClickListener(m_resetpwd_Listener);

        if (mUserDataManager == null) {
            mUserDataManager = new UserDataManager(this);
            mUserDataManager.openDataBase();                              //建立本地数据库
        }
    }
    View.OnClickListener m_resetpwd_Listener = new View.OnClickListener() {    //不同按钮按下的监听事件选择
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.next_step_button:                       //确认按钮的监听事件
                    resetpwd_check();
                    break;
                case R.id.cancel_button:                     //取消按钮的监听事件,由注册界面返回登录界面
                    Intent intent_Resetpwd_to_Information = new Intent(ResetPwdActivity.this,InformationActivity.class) ;    //切换Resetpwd Activity至Login Activity
                    intent_Resetpwd_to_Information.putExtra("extra_name",name);
                    startActivity(intent_Resetpwd_to_Information);
                    finish();
                    break;
            }
        }
    };
    public void resetpwd_check() {                                //确认按钮的监听事件
        if (isUserNameAndPwdValid()) {
            String userName = setUserName.getText().toString().trim();
            String userPwd_old = setPwd.getText().toString().trim();
            String userPwd_new = setNewPwd.getText().toString().trim();
            String userPwdCheck = pwdConfirm.getText().toString().trim();
            int result=mUserDataManager.findUserByNameAndPwd(userName, userPwd_old);
            if(result==1){                                             //返回1说明用户名和密码均正确,继续后续操作
                if(!userPwd_new.equals(userPwdCheck)){           //两次密码输入不一样
                    Toast.makeText(this, getString(R.string.pwd_not_the_same),Toast.LENGTH_SHORT).show();
                    return ;
                } else {
                    UserData mUser = new UserData(userName, userPwd_new);
                    mUserDataManager.openDataBase();
                    boolean flag = mUserDataManager.updateUserData(mUser);
                    if (flag == false) {
                        Toast.makeText(this, getString(R.string.resetpwd_fail),Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(this, getString(R.string.resetpwd_success),Toast.LENGTH_SHORT).show();
                        mUser.pwdresetFlag=1;
                        Intent intent_Register_to_Login = new Intent(ResetPwdActivity.this,LoginActivity.class) ;    //切换User Activity至Login Activity
                        startActivity(intent_Register_to_Login);
                        finish();
                    }
                }
            }else if(result==0){                                       //返回0说明用户名和密码不匹配，重新输入
                Toast.makeText(this, getString(R.string.pwd_not_fit_user),Toast.LENGTH_SHORT).show();
                return;
            }
        }
    }
    public boolean isUserNameAndPwdValid() {
        String userName = setUserName.getText().toString().trim();

        //检查用户是否存在
        int count=mUserDataManager.findUserByName(userName);
        //用户不存在时返回，给出提示文字
        if(count==0){
            Toast.makeText(this, getString(R.string.name_not_exist),Toast.LENGTH_SHORT).show();
            return false;
        }
        if (setUserName.getText().toString().trim().equals("")) {
            Toast.makeText(this, getString(R.string.account_empty),Toast.LENGTH_SHORT).show();
            return false;
        }
        if (setPwd.getText().toString().trim().equals("")) {
            Toast.makeText(this, getString(R.string.pwd_old_empty),Toast.LENGTH_SHORT).show();
            return   false;
        }
        if (setNewPwd.getText().toString().trim().equals("")) {
            Toast.makeText(this, getString(R.string.pwd_new_empty),Toast.LENGTH_SHORT).show();
            return   false;
        }
        if(pwdConfirm.getText().toString().trim().equals("")) {
            Toast.makeText(this, getString(R.string.pwd_check_empty), Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}

