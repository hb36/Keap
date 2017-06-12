package com.example.hb_we.keap;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by hb_we on 2017/5/11.
 */

public class EditInformationActivity extends Activity {

    private Button confirm;
    private Button cancel;
    private TextView username;
    private TextView sex;
    private TextView birthYear;
    private TextView height;
    private TextView weight;
    private String name;
    private TextView getUserName;
    private Spinner setSex;
    private Spinner setBirthYear;
    private Spinner setHeight;
    private Spinner setWeight;

    private UserDataManager mUserDataManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_information_edit);

        confirm = (Button) findViewById(R.id.confirm_button);
        cancel = (Button) findViewById(R.id.cancel_button);
        username = (TextView) findViewById(R.id.username);
        sex = (TextView) findViewById(R.id.user_sex);
        birthYear = (TextView) findViewById(R.id.user_birth_year);
        height = (TextView) findViewById(R.id.user_height);
        weight = (TextView) findViewById(R.id.user_weight);
        getUserName = (TextView) findViewById(R.id.username_edit);
        setSex = (Spinner) findViewById(R.id.user_set_sex);
        setBirthYear = (Spinner) findViewById(R.id.user_set_birth_year);
        setHeight = (Spinner) findViewById(R.id.user_set_height);
        setWeight = (Spinner) findViewById(R.id.user_set_weight);

        ArrayAdapter<CharSequence> SexAdapter = ArrayAdapter.createFromResource(
                this, R.array.sex,
                android.R.layout.simple_spinner_dropdown_item);
        setSex.setAdapter(SexAdapter);

        ArrayAdapter<CharSequence> BirthAdapter = ArrayAdapter.createFromResource(
                this, R.array.birth_year,
                android.R.layout.simple_spinner_dropdown_item);
        setBirthYear.setAdapter(BirthAdapter);
        setBirthYear.setSelection(50,true);

        ArrayAdapter<CharSequence> HeightAdapter = ArrayAdapter.createFromResource(
                this, R.array.height,
                android.R.layout.simple_spinner_dropdown_item);
        setHeight.setAdapter(HeightAdapter);
        setHeight.setSelection(30,true);

        ArrayAdapter<CharSequence> WeightAdapter = ArrayAdapter.createFromResource(
                this, R.array.weight,
                android.R.layout.simple_spinner_dropdown_item);
        setWeight.setAdapter(WeightAdapter);
        setWeight.setSelection(10,true);

        if (mUserDataManager == null) {
            mUserDataManager = new UserDataManager(this);
            mUserDataManager.openDataBase();                              //建立本地数据库
        }
        name = getIntent().getStringExtra("extra_name");
        getUserName.setText(name);

        confirm.setOnClickListener(mSaveListener);
        cancel.setOnClickListener(mSaveListener);

    }
    View.OnClickListener mSaveListener = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            switch(v.getId()){
                case R.id.confirm_button:
                    saveInformation();
                    break;
                case R.id.cancel_button:
                    Intent it = new Intent(EditInformationActivity.this,
                            InformationActivity.class);
                    it.putExtra("extra_name",name);
                    startActivity(it);
                    finish();
                default:
                    break;
            }
        }
    };

    public void saveInformation(){
        String userName = getUserName.getText().toString().trim();
        String userSex = setSex.getSelectedItem().toString();
        String userBirthYear = setBirthYear.getSelectedItem().toString();
        String userHeight = setHeight.getSelectedItem().toString();
        String userWeight = setWeight.getSelectedItem().toString();
        mUserDataManager.openDataBase();
        UserInfo userInfo = new UserInfo(userName,userSex,userBirthYear,userHeight,userWeight);

        long flag = mUserDataManager.insertUserInfo(userInfo);
        if (flag==-1) {
            Toast.makeText(EditInformationActivity.this, "保存失败",
                    Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(EditInformationActivity.this,"保存成功",
                    Toast.LENGTH_SHORT).show();
            Intent it = new Intent(EditInformationActivity.this,
                    InformationActivity.class);
            it.putExtra("extra_name",userName);
            startActivity(it);
            finish();
        }

    }
}