package com.example.hb_we.keap;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Calendar;
import java.util.TimeZone;

/**
 * Created by Cxy on 2017/5/9.
 */

public class Move_ActivityEdit extends Activity {
    private TextView time;
    private EditText editContent;
    private Button clock;
    private Button done;
    private int hour;
    private int minute;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.edit);
        Intent intent = getIntent();
        final int position = intent.getIntExtra("position", 0);
        final int itemClicked = intent.getIntExtra("itemClicked", 0);
        final int itemLength = intent.getIntExtra("itemLength", 0);
        final String passContent = intent.getStringExtra("passContent");
        time = (TextView) findViewById(R.id.time);
        editContent = (EditText) findViewById(R.id.edit);
        editContent.setText(passContent);
        clock = (Button) findViewById(R.id.clock);
        done = (Button) findViewById(R.id.done);
        final String data = getDate(position);
        final String[] cArray0 = data.split("/");
        final String[] cArray1 = data.split(" ");

        if (cArray0[0].equals("Sunday ")) {
            SpannableStringBuilder style=new SpannableStringBuilder(data);
            int start = data.indexOf("Sunday ");
            int end = start + "Sunday ".length();
            style.setSpan(new ForegroundColorSpan(Color.RED), start, end, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
            time.setText(style);
        } else {
            time.setText(data);
        }

        clock.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                String s = null;
                int index = editContent.getSelectionStart();
                Editable editable = editContent.getText();
                long time = System.currentTimeMillis();
                final Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(time);
                hour = calendar.get(Calendar.HOUR);
                minute = calendar.get(Calendar.MINUTE);
                calendar.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
                if(calendar.get(Calendar.AM_PM) ==0)
                {
                    s = "AM" + hour + ":" + minute + "分";
                }
                else
                {
                    s = "PM" + hour + ":" + minute + "分";
                }
                editable.insert(index, s);
            }
        });

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Move_ActivityEdit.this, Move_diray_main.class);
                intent.putExtra("itemClicked", itemClicked);
                intent.putExtra("itemLength", itemLength);
                intent.putExtra("week", cArray0[0].substring(0, 3));
                intent.putExtra("date", cArray1[3]);
                intent.putExtra("content", editContent.getText().toString());
                startActivity(intent);
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static String getDate(int position){
        final Calendar calendar = Calendar.getInstance();
        String[] Array_month = new String[] {"January", "February", "March", "April",
                "May", "June", "July", "August", "September", "October", "November", "December"};
        calendar.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        String year = String.valueOf(calendar.get(Calendar.YEAR)); // 获取当前年份
        String month = String.valueOf(calendar.get(Calendar.MONTH));// 获取当前月份
        for (int i = 0; i < 12; i++) {
            String j = String.valueOf(i);
            if (month.equals(j)) {
                month = Array_month[i];
                break;
            }
        }
        String date = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH) - position);// 获取当前月份的日期号码
        int number;
        if (calendar.get(Calendar.DAY_OF_WEEK) - (position % 7) <= 0) {
            number = calendar.get(Calendar.DAY_OF_WEEK) + (7 - position % 7);
        } else {
            number = calendar.get(Calendar.DAY_OF_WEEK) - (position % 7);
        }
        String week = String.valueOf(number);
        if("1".equals(week)){
            week ="Sunday";
        }else if("2".equals(week)){
            week ="Monday";
        }else if("3".equals(week)){
            week ="Tuesday";
        }else if("4".equals(week)){
            week ="Wednesday";
        }else if("5".equals(week)){
            week ="Thursday";
        }else if("6".equals(week)){
            week ="Friday";
        }else if("7".equals(week)){
            week ="Saturday";
        }
        return week + " / " + month + " " + date + " / " + year;
    }
}
