package com.example.hb_we.keap;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class EXE_ExerciseActivitySecond extends AppCompatActivity {

    private TextView activityNameText;

    private ImageView backgroundImg;

    private String activityName;

    private String planName;

    private TextView activityDetailText;

    private String detail;

    private int i;

    private int pic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();//隐藏标题栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); // 隐藏状态栏
        setContentView(R.layout.activity_exercise_second);

        activityNameText = (TextView) findViewById(R.id.textView2);
        backgroundImg = (ImageView) findViewById(R.id.backgroud_img);
        activityDetailText = (TextView) findViewById(R.id.activity_detail);

        Intent intent = getIntent();
        activityName = intent.getStringExtra("activity_name");
        planName = intent.getStringExtra("plan_name");
        detail = intent.getStringExtra("detail");
        i = intent.getIntExtra("i", 0);
        pic = intent.getIntExtra("pic", 0);

        activityNameText.setText(activityName);
        backgroundImg.setImageResource(pic);
        activityDetailText.setText(detail);

        Button back = (Button) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EXE_ExerciseActivitySecond.this, EXE_ExerciseActivityFirst.class);
                intent.putExtra("plan_name", planName);
                intent.putExtra("i", i);
                startActivity(intent);
            }
        });
    }
}
