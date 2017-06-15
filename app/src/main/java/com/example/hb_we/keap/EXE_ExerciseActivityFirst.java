package com.example.hb_we.keap;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import pl.droidsonroids.gif.GifImageView;

public class EXE_ExerciseActivityFirst extends AppCompatActivity {

    //60秒
    private static final long COUNT_DOWN_TIME = 60 * 1000;
    //倒计时器
    private CountDownTimer timer;
    private Button timerButton;
    private long curTime = 0;
    //标记是否暂停
    private boolean isPause = false;
    //标记是不是第一次点击计时按钮
    private boolean isFirstTime = true;
    //记录点击次数
    private int clickTime = -1;
    pl.droidsonroids.gif.GifImageView la;
    private int m;
    int pic = 0;
    String detail = null;
    Cursor cursor_activity = null;
    Cursor cursor_plan = null;
    public static final int UPDATE_GIF = 1;
    private int i = 1;
    String planName;
    String activity;
    SQLiteDatabase db;
    Button thePre;
    Button theNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();//隐藏标题栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); // 隐藏状态栏
        setContentView(R.layout.activity_exercise_first);

        Intent intent = getIntent();
        planName = intent.getStringExtra("plan_name");
        i = intent.getIntExtra("i", 1);

        Button description = (Button) findViewById(R.id.description);
        thePre = (Button) findViewById(R.id.the_pre);
        theNext = (Button) findViewById(R.id.the_next);

        la = (GifImageView) findViewById(R.id.la);

        timerButton = (Button) findViewById(R.id.begin_pause);

        db = Plan_MainActivity.dbHelper.getWritableDatabase();

        String table_plan = "Plan";
        String[] columns_plan = new String[]{
                "activity1", "group1",
                "activity2", "group2",
                "activity3", "group3",
                "activity4", "group4",
                "activity5", "group5",
                "total_k"
        };
        String selection = "name = ?";
        String[] selectionArgs = new String[]{planName};
        cursor_plan = db.query(table_plan, columns_plan, selection, selectionArgs, null, null, null);
        cursor_plan.moveToFirst();
        activity = cursor_plan.getString(cursor_plan.getColumnIndex("activity" + String.valueOf(i)));

        String table_activity = "Activity";
        String selection_activity = "name = ?";
        String[] selectionArgs_activity = new String[]{activity};
        cursor_activity = db.query(table_activity, null, selection_activity, selectionArgs_activity, null, null, null);

        cursor_activity.moveToFirst();

        search();

        theNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (i == 5) {
                    Intent intent = new Intent(EXE_ExerciseActivityFirst.this, EXE_ExerciseActivityThird.class);
                    intent.putExtra("total_k", cursor_plan.getInt(cursor_plan.getColumnIndex("total_k")));
                    startActivity(intent);
                    i = 0;
                    m = 0;
                    pic = 0;
                    detail = null;
                    planName = null;
                }

                timerButton.setText("计时开始");
                isFirstTime = true;
                curTime = 0;
                clickTime = -1;
                i++;
                m = cursor_plan.getInt(cursor_plan.getColumnIndex("group" + String.valueOf(i)));

                activity = cursor_plan.getString(cursor_plan.getColumnIndex("activity" + String.valueOf(i)));

                String table_activity = "Activity";
                String selection_activity = "name = ?";
                String[] selectionArgs_activity = new String[]{activity};
                cursor_activity = db.query(table_activity, null, selection_activity, selectionArgs_activity, null, null, null);

                if (cursor_activity != null) {
                    cursor_activity.moveToFirst();
                }

                pic = cursor_activity.getInt(cursor_activity.getColumnIndex("pic"));
                detail = cursor_activity.getString(cursor_activity.getColumnIndex("detail"));
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Message message = new Message();
                        message.what = UPDATE_GIF;
                        handler.sendMessage(message);
                    }
                }).start();
            }
        });

        thePre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (i == 1) {
                    Toast.makeText(EXE_ExerciseActivityFirst.this,
                            "已经是第一个运动啦", Toast.LENGTH_SHORT).show();
                } else {
                    timerButton.setText("计时开始");
                    isFirstTime = true;
                    curTime = 0;
                    clickTime = -1;
                    i--;
                    m = cursor_plan.getInt(cursor_plan.getColumnIndex("group" + String.valueOf(i)));

                    activity = cursor_plan.getString(cursor_plan.getColumnIndex("activity" + String.valueOf(i)));

                    String table_activity = "Activity";
                    String selection_activity = "name = ?";
                    String[] selectionArgs_activity = new String[]{activity};
                    cursor_activity = db.query(table_activity, null, selection_activity, selectionArgs_activity, null, null, null);

                    if (cursor_activity != null) {
                        cursor_activity.moveToFirst();
                    }

                    pic = cursor_activity.getInt(cursor_activity.getColumnIndex("pic"));
                    detail = cursor_activity.getString(cursor_activity.getColumnIndex("detail"));
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            Message message = new Message();
                            message.what = UPDATE_GIF;
                            handler.sendMessage(message);
                        }
                    }).start();
                }
            }
        });

        description.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EXE_ExerciseActivityFirst.this, EXE_ExerciseActivitySecond.class);
                intent.putExtra("plan_name", planName);
                intent.putExtra("i", i);
                intent.putExtra("pic", pic);
                intent.putExtra("detail", detail);
                intent.putExtra("activity_name", cursor_plan.getString(cursor_plan.getColumnIndex("activity" + String.valueOf(i))));
                startActivity(intent);
            }
        });

        /**
         * 第一个参数表示你倒计时的总时间，毫秒为单位，60秒 = 60 * 1000毫秒
         * 第二个参数表示是间隔多少毫秒倒计时一次（时间-1）
         */

        timerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                thePre.setVisibility(View.GONE);
                theNext.setVisibility(View.GONE);
                if (clickTime == -1) {
                    if (isFirstTime) {
                        initCountDownTimer(5000 * m);
                    }
                    isPause = false;
                    clickTime++;
                    timer.start();
                    isFirstTime = false;
                } else if (clickTime % 2 == 0) {
                    if (!isPause) {
                        isPause = true;
                        clickTime++;
                        timer.cancel();
                    }
                } else if (clickTime % 2 == 1) {
                    if (curTime != 0 && isPause) {
                        //将上次当前剩余时间作为新的时长
                        initCountDownTimer(curTime);
                        clickTime++;
                        timer.start();
                        isPause = false;
                    }
                }
            }
        });
    }

    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case UPDATE_GIF:
                    la.setBackgroundResource(pic);
            }
        }
    };

    private void search() {
        m = cursor_plan.getInt(cursor_plan.getColumnIndex("group" + String.valueOf(i)));
        pic = cursor_activity.getInt(cursor_activity.getColumnIndex("pic"));
        detail = cursor_activity.getString(cursor_activity.getColumnIndex("detail"));
        new Thread(new Runnable() {
            @Override
            public void run() {
                Message message = new Message();
                message.what = UPDATE_GIF;
                handler.sendMessage(message);
            }
        }).start();
    }

    //millisInFuture 倒计时时长单位毫秒
    public void initCountDownTimer(long millisInFuture) {
        timer = new CountDownTimer(millisInFuture, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                curTime = millisUntilFinished;  //当前剩余时间;
                timerButton.setText(EXE_TimeTools.getCountTimeByLong(millisUntilFinished));
            }

            public void onFinish() {
                if (i == 5) {
                    Intent intent = new Intent(EXE_ExerciseActivityFirst.this, EXE_ExerciseActivityThird.class);
                    intent.putExtra("total_k", cursor_plan.getInt(cursor_plan.getColumnIndex("total_k")));
                    startActivity(intent);
                    i = 0;
                    m = 0;
                    pic = 0;
                    detail = null;
                    planName = null;
                }

                thePre.setVisibility(View.VISIBLE);
                theNext.setVisibility(View.VISIBLE);
                timerButton.setText("计时开始");
                isFirstTime = true;
                curTime = 0;
                clickTime = -1;
                i++;
                m = cursor_plan.getInt(cursor_plan.getColumnIndex("group" + String.valueOf(i)));

                activity = cursor_plan.getString(cursor_plan.getColumnIndex("activity" + String.valueOf(i)));

                String table_activity = "Activity";
                String selection_activity = "name = ?";
                String[] selectionArgs_activity = new String[]{activity};
                cursor_activity = db.query(table_activity, null, selection_activity, selectionArgs_activity, null, null, null);

                if (cursor_activity != null) {
                    cursor_activity.moveToFirst();
                }

                pic = cursor_activity.getInt(cursor_activity.getColumnIndex("pic"));
                detail = cursor_activity.getString(cursor_activity.getColumnIndex("detail"));
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Message message = new Message();
                        message.what = UPDATE_GIF;
                        handler.sendMessage(message);
                    }
                }).start();
            }

        };

    }
}