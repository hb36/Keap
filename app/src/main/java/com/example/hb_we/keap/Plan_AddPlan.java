package com.example.hb_we.keap;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Windows on 2017/5/8.
 */

public class Plan_AddPlan extends Activity {

    SQLiteDatabase db = Plan_MainActivity.dbHelper.getWritableDatabase();

    TextView add_activity = null;
    ListView activity_listView = null;
    Button save = null;
    Button start = null;
    EditText edit_planName = null;
    TextView total = null;

    private List<Plan_MyActivities> activity_list = new ArrayList<Plan_MyActivities>();

    //计划名
    public static String table_name = null;

    //总卡路里数
    int total_k = 0;

    //所添加的运动项目
    String activity1 = null;
    String activity2 = null;
    String activity3 = null;
    String activity4 = null;
    String activity5 = null;

    //组数
    String number = null;
    String group1 = null;
    String group2 = null;
    String group3 = null;
    String group4 = null;
    String group5 = null;


    @Override
    public void onBackPressed() {
        Intent intent3 = new Intent(Plan_AddPlan.this,Plan_MainActivity.class);
        startActivity(intent3);
        super.onBackPressed();
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(getWindow().FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); // 隐藏状态栏
        setContentView(R.layout.add_plan);

        //获得新添加的运动数据
        Intent intent = getIntent();
        activity1 = intent.getStringExtra("data1");
        activity2 = intent.getStringExtra("data2");
        activity3 = intent.getStringExtra("data3");
        activity4 = intent.getStringExtra("data4");
        activity5 = intent.getStringExtra("data5");


        //点击“添加运动”按钮，系统跳转到添加运动界面（Plan_AddActivity.class）
        add_activity = (TextView)findViewById(R.id.add_activity);
        add_activity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(Plan_AddPlan.this,Plan_AddActivity.class);
                intent1.putExtra("from","Plan_AddPlan");
                startActivity(intent1);
            }
        });

        //点击“保存”按钮，系统保存当前计划的信息到数据库，计算当前总卡路里
        save = (Button)findViewById(R.id.save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //计算该计划总卡路里
                total_k = calculate();

                //保存数据到数据库
                edit_planName = (EditText)findViewById(R.id.edit_planName);
                table_name = edit_planName.getText().toString();

                ContentValues values = new ContentValues();
                values.put("name",table_name);

                values.put("activity1",activity1);
                values.put("group1",activity_list.get(0).getNumber());

                values.put("activity2",activity2);
                values.put("group2",activity_list.get(1).getNumber());

                values.put("activity3",activity3);
                values.put("group3",activity_list.get(2).getNumber());

                values.put("activity4",activity4);
                values.put("group4",activity_list.get(3).getNumber());

                values.put("activity5",activity5);
                values.put("group5",activity_list.get(4).getNumber());

                values.put("total_k",total_k);

                db.insert("Plan",null,values);
                values.clear();
                Toast.makeText(Plan_AddPlan.this,"保存新计划成功",Toast.LENGTH_SHORT).show();

                //“保存”按钮消失，“开始运动”按钮出现,显示总卡路里数
                save.setVisibility(View.GONE);
                start.setVisibility(View.VISIBLE);
                total = (TextView)findViewById(R.id.total);
                total.setText(String.valueOf(total_k));
                add_activity.setVisibility(View.GONE);
                Button add = (Button)findViewById(R.id.add);
                add.setVisibility(View.GONE);
            }
        });

        //点击“START”按钮，系统跳转到开始运动界面(StartActivity.class)
        start = (Button)findViewById(R.id.start);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(Plan_AddPlan.this,EXE_ExerciseActivityFirst.class);
                intent2.putExtra("plan_name", edit_planName.getText().toString());
                startActivity(intent2);
            }
        });

        //活动列表
        initActivity();
        final Plan_ActivityAdapter activityAdapter = new Plan_ActivityAdapter(Plan_AddPlan.this,R.layout.activity_list_item,activity_list);
        activity_listView = (ListView)findViewById(R.id.activity_list);
        activity_listView.setAdapter(activityAdapter);
        //设置每一项活动的点击事件：点击该活动，弹出对话框，可修改组数
        activity_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final Plan_MyActivities activities = activity_list.get(position);
                Plan_MyGroupDialog.OnSureClickListener listener1 = new Plan_MyGroupDialog.OnSureClickListener(){
                    public void getText(String string)
                    {
                        number = string;
                        Log.d("Plan_AddPlan","运动组数"+number);
                        activities.setNumber(number);
                        activityAdapter.notifyDataSetChanged();
                    }
                };
                Plan_MyGroupDialog group_Dialog = new Plan_MyGroupDialog(Plan_AddPlan.this,listener1);
                group_Dialog.show();

            }
        });

    }


    //初始化活动列表数据
    private void initActivity(){
        Plan_MyActivities a1 = new Plan_MyActivities(activity1,group1);
        activity_list.add(a1);

        Plan_MyActivities a2 = new Plan_MyActivities(activity2,group2);
        activity_list.add(a2);

        Plan_MyActivities a3 = new Plan_MyActivities(activity3,group3);
        activity_list.add(a3);

        Plan_MyActivities a4 = new Plan_MyActivities(activity4,group4);
        activity_list.add(a4);

        Plan_MyActivities a5 = new Plan_MyActivities(activity5,group5);
        activity_list.add(a5);

    }

    //计算该计划的总卡路里
    public int calculate(){
        int total = 0;

        int activity1_k = 0;
        int activity2_k = 0;
        int activity3_k = 0;
        int activity4_k = 0;
        int activity5_k = 0;

        int group1 =  Integer.parseInt(activity_list.get(0).getNumber());
        int group2 =  Integer.parseInt(activity_list.get(1).getNumber());
        int group3 =  Integer.parseInt(activity_list.get(2).getNumber());
        int group4 =  Integer.parseInt(activity_list.get(3).getNumber());
        int group5 =  Integer.parseInt(activity_list.get(4).getNumber());


        //获得activity1的卡路里
        String table = "Activity";
        String [] colums =new String[]{"k"};
        String selection = "name = ?";
        String [] selectionArgs = new String[]{activity1};
        Cursor cursor = db.query(table, colums, selection, selectionArgs, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                int k = cursor.getInt(cursor.getColumnIndex("k"));
                activity1_k = k * group1 ;

            } while (cursor.moveToNext());
            cursor.close();
        };

        //获得activity2的卡路里
        selectionArgs = new String[]{activity2};
        cursor = db.query(table, colums, selection, selectionArgs, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                int k = cursor.getInt(cursor.getColumnIndex("k"));
                activity2_k = k * group2 ;

            } while (cursor.moveToNext());
            cursor.close();
        };

        //获得activity3的卡路里
        selectionArgs = new String[]{activity3};
        cursor = db.query(table, colums, selection, selectionArgs, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                int k = cursor.getInt(cursor.getColumnIndex("k"));
                activity3_k = k * group3 ;

            } while (cursor.moveToNext());
            cursor.close();
        };

        //获得activity4的卡路里
        selectionArgs = new String[]{activity4};
        cursor = db.query(table, colums, selection, selectionArgs, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                int k = cursor.getInt(cursor.getColumnIndex("k"));
                activity4_k = k * group4 ;

            } while (cursor.moveToNext());
            cursor.close();
        };

        //获得activity5的卡路里
        selectionArgs = new String[]{activity5};
        cursor = db.query(table, colums, selection, selectionArgs, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                int k = cursor.getInt(cursor.getColumnIndex("k"));
                activity5_k = k * group5 ;

            } while (cursor.moveToNext());
            cursor.close();
        };

        //计算总卡路里
        total = activity1_k + activity2_k +activity3_k +activity4_k + activity5_k;

        return  total;
    }



}
