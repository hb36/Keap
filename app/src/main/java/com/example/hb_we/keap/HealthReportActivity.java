package com.example.hb_we.keap;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.Calendar;

public class HealthReportActivity extends Activity {

    private TextView healthScore;
    private TextView BMI;
    private TextView BMIRank;
    private TextView fatRate;
    private TextView fatRateRank;
    private TextView figureRank;

    private ImageButton backBtn;
    private ImageButton refreshBtn;

    private String name;
    private String userName;
    private String[] BMIString = {"偏轻", "健康", "超重", "肥胖"};
    private String[] fatRateString = {"瘦", "偏瘦", "标准", "微胖", "肥胖"};
    private String[] figureString = {"瘦弱", "精瘦", "强健", "虚胖", "肥胖"};

    private UserDataManager mUserDataManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); // 隐藏状态栏
        setContentView(R.layout.activity_health_report);

        healthScore = (TextView) findViewById(R.id.score);
        BMI = (TextView) findViewById(R.id.BMI);
        BMIRank = (TextView) findViewById(R.id.String_BMI);
        fatRate = (TextView) findViewById(R.id.fatRate);
        fatRateRank = (TextView) findViewById(R.id.fatRateString);
        figureRank = (TextView) findViewById(R.id.figureRank);
        backBtn = (ImageButton) findViewById(R.id.back_to_info);
        refreshBtn = (ImageButton) findViewById(R.id.btn_refresh);

        Intent getName = getIntent();
        name = getName.getStringExtra("extra_name");
        if (name != null) {
            userName = name;
        } else {
            name = userName;
        }


        backBtn.setOnClickListener(mListener);
        refreshBtn.setOnClickListener(mListener);

    }

    View.OnClickListener mListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.back_to_info:
                    Intent health_to_info = new Intent(HealthReportActivity.this,
                            InformationActivity.class);
                    health_to_info.putExtra("extra_name", name);
                    startActivity(health_to_info);
                    break;
                case R.id.btn_refresh:
                    refresh(name);
                    break;
                default:
                    break;
            }
        }
    };

    //刷新
    private void refresh(String userName) {
        String userSex = null;
        String userBirthYear = null;
        String userHeight = null;
        String userWeight = null;
        int bmi;
        int fr;
        int bmiNum;
        int frNum;
        int figureNum;
        int scoreNum;

        mUserDataManager = new UserDataManager(this);
        Cursor cursor = mUserDataManager.findInfoByName(userName);
        if (cursor.moveToFirst()) {
            do {
                userSex = cursor.getString(cursor.getColumnIndex(UserDataManager.USER_SEX));
                userBirthYear = cursor.getString(cursor.getColumnIndex(UserDataManager.USER_BIRTHYEAR));
                userHeight = cursor.getString(cursor.getColumnIndex(UserDataManager.USER_HEIGHT));
                userWeight = cursor.getString(cursor.getColumnIndex(UserDataManager.USER_WEIGHT));
            } while (cursor.moveToNext());
        }
        bmi = calcuBMI(userHeight, userWeight);
        BMI.setText(Integer.toString(bmi));
        fr = calcuFatRate(bmi, userSex, userBirthYear);
        fatRate.setText(Integer.toString(fr) + "%");
        bmiNum = calcuBMIRank(bmi);
        BMIRank.setText("(" + BMIString[bmiNum] + ")");
        frNum = calcuFatRateRank(fr);
        fatRateRank.setText("(" + fatRateString[frNum] + ")");
        figureNum = calcuFigureRank(bmiNum, frNum);
        figureRank.setText(figureString[figureNum]);
        scoreNum = calcuScore(figureNum);
        healthScore.setText(Integer.toString(scoreNum));
    }

    //计算BMI
    private int calcuBMI(String userHeight, String userWeight) {
        double height = Double.valueOf(userHeight).intValue();
        height = height / 100;
        double weight = Double.valueOf(userWeight).intValue();
        int bmi = (int) (weight / (height * height));
        return bmi;
    }

    //计算体脂率
    private int calcuFatRate(int bmi, String userSex, String userBirthYear) {
        int birthYear = Integer.valueOf(userBirthYear).intValue();
        int sexNum;
        int year;

        Calendar c = Calendar.getInstance();//首先要获取日历对象
        year = c.get(Calendar.YEAR); // 获取当前年份

        if (userSex.equals("男")) {
            sexNum = 1;
        } else {
            sexNum = 0;
        }

        int fr = (int) (1.2 * bmi + 0.23 * (year - birthYear) - 5.4 - 10.8 * sexNum);
        return fr;
    }

    //判定ＢＭＩ类型
    private int calcuBMIRank(int bmi) {
        int bmiRank = 0;
        if (bmi < 18) {
            bmiRank = 0;
        } else if (bmi >= 18 && bmi < 24) {
            bmiRank = 1;
        } else if (bmi >= 24 && bmi < 28) {
            bmiRank = 2;
        } else if (bmi >= 28) {
            bmiRank = 3;
        }
        return bmiRank;
    }

    //判定fatRate类型
    private int calcuFatRateRank(int fatRate) {
        int fatRateRank = 0;
        if (fatRate < 11) {
            fatRateRank = 0;
        } else if (fatRate >= 11 && fatRate < 16) {
            fatRateRank = 1;
        } else if (fatRate >= 16 && fatRate < 21) {
            fatRateRank = 2;
        } else if (fatRate >= 21 && fatRate < 26) {
            fatRateRank = 3;
        } else if (fatRate >= 26) {
            fatRateRank = 4;
        }
        return fatRateRank;
    }

    //判定身材类型
//    private String[] BMIString = {"偏轻","健康","超重","肥胖"};
//    private String[] fatRateString = {"瘦","偏瘦","标准","微胖","肥胖"};
//    private String[] figureString = {"瘦弱","精瘦","强健","虚胖","肥胖"};
    private int calcuFigureRank(int bmiNum, int frNum) {
        int figureRank = 0;

        if (bmiNum == 0) {
            if (frNum == 0) {
                figureRank = 0;
            } else if (frNum == 1) {
                figureRank = 1;
            } else if (frNum == 2) {
                figureRank = 2;
            } else {
                figureRank = 4;
            }
        } else if (bmiNum == 2) {
            if (frNum <= 2) {
                figureRank = 2;
            } else if (frNum == 3) {
                figureRank = 3;
            } else {
                figureRank = 4;
            }
        } else if (bmiNum == 3) {
            if (frNum <= 3) {
                figureRank = 3;
            } else {
                figureRank = 4;
            }
        }

        return figureRank;
    }

    //计算健康评分
    private int calcuScore(int figureNum) {
        int dudectScore = figureNum - 2;
        dudectScore = Math.abs(dudectScore);
        return 95 - 5 * dudectScore;
    }
}
