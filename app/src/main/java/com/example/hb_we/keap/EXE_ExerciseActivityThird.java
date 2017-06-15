package com.example.hb_we.keap;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class EXE_ExerciseActivityThird extends AppCompatActivity {

    private TextView total_k_text;
    Button daka = null;
    Button goon = null;

    private int total_k;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();//隐藏标题栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); // 隐藏状态栏
        setContentView(R.layout.activity_exercise_third);

        total_k_text = (TextView) findViewById(R.id.total_k);
        Intent intent = getIntent();
        total_k = intent.getIntExtra("total_k", 0);
        total_k_text.setText("  共消耗       " + total_k + "       卡路里");

        daka = (Button)findViewById(R.id.daka);
        daka.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(EXE_ExerciseActivityThird.this,Move_MainActivity.class);
                startActivity(intent1);
            }
        });

        goon = (Button)findViewById(R.id.goon);
        goon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(EXE_ExerciseActivityThird.this,Plan_MainActivity.class);
                startActivity(intent2);
            }
        });
    }
}
