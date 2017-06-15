package com.example.hb_we.keap;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Calendar;

/**
 * Created by Cxy on 2017/5/9.
 */

public class Move_TimeChange extends Activity {
    private TextView changeTime;
    private Button changeClock;
    private Button changeDone;
    private EditText editContent;
    private int hour;
    private int minute;

    @Override
    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.timechange);
        Intent intent = getIntent();
        final int mon = intent.getIntExtra("month", 0);
        String year = intent.getStringExtra("year");
        final int yea = Integer.parseInt(year);
        final int position = intent.getIntExtra("position", 0);
        final String data = getDate(position, mon, yea);
        final String[] cArray0 = data.split("/");
        final String[] cArray1 = data.split(" ");
        changeTime = (TextView) findViewById(R.id.time_change);
        editContent = (EditText) findViewById(R.id.edit_change);
        changeClock= (Button) findViewById(R.id.clock_change);
        changeDone = (Button) findViewById(R.id.done_change);

        if (cArray0[0].equals("Sunday ")) {
            SpannableStringBuilder style=new SpannableStringBuilder(data);
            int start = data.indexOf("Sunday ");
            int end = start + "Sunday ".length();
            style.setSpan(new ForegroundColorSpan(Color.RED), start, end, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
            changeTime.setText(style);
        } else {
            changeTime.setText(data);
        }

        editContent.setText(intent.getStringExtra("passContent"));

        changeClock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s;
                int index = editContent.getSelectionStart();
                Editable editable = editContent.getText();
                long time = System.currentTimeMillis();
                final Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(time);
                hour = calendar.get(Calendar.HOUR);
                minute = calendar.get(Calendar.MINUTE);

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

        changeDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Move_TimeChange.this, Move_diray_main.class);
                intent.putExtra("itemClickedAnother", position);
                intent.putExtra("yearChoose", yea);
                intent.putExtra("monthChoose", mon);
                intent.putExtra("weekAnother", cArray0[0].substring(0, 3));
                intent.putExtra("dateAnother", cArray1[3]);
                intent.putExtra("contentAnother", editContent.getText().toString());

                startActivity(intent);
            }
        });
    }

    public static String getDate(int position, int m, int y){

        String[] Array_month = new String[] {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
        String month = "";
        for (int i = 0; i < Array_month.length; i++) {
            if ((m - 1) == i) {
                month = Array_month[i];
            }
        }

        Calendar calendar = Calendar.getInstance();
        //指定年份
        calendar.set(Calendar.YEAR, y);
        //指定月份
        calendar.set(Calendar.MONTH, m - 1);
        // 获取当前月份的日期
        String day = String.valueOf(position + 1);
        String week = "";

        //获取指定年份月份中指定某天是星期几
        calendar.set(Calendar.DAY_OF_MONTH, position + 1);
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        switch (dayOfWeek) {
            case 1:
                week = "Sunday";
                break;
            case 2:
                week = "Monday";
                break;
            case 3:
                week = "Tuesday";
                break;
            case 4:
                week = "Wendesday";
                break;
            case 5:
                week = "Thursday";
                break;
            case 6:
                week = "Friday";
                break;
            case 7:
                week = "Saturday";
                break;
        }
        return week + " / " + month + " " + day + " / " + y;
    }
}
