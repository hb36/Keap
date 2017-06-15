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
 * Created by Windows on 2017/6/8.
 */

public class Plan_APlan extends Activity {

    SQLiteDatabase db = Plan_MainActivity.dbHelper.getWritableDatabase();

    ListView change_listView = null;
    TextView change_activity = null;
    Button change = null;
    TextView change1 = null;
    Button start = null;
    TextView total = null;
    EditText change_name = null;


    //运动组数
    String change_number = null;

    //运动项目列表
    private List<Plan_MyActivities> change_list = new ArrayList<Plan_MyActivities>();
    Plan_ActivityAdapter changeAdapter = null;

    //计划表的详细数据
    String new_name = null;
    String plan_name = null;

    String activity1 = null;
    int group1 = 0;

    String activity2 = null;
    int group2 = 0;

    String activity3 = null;
    int group3 = 0;

    String activity4 = null;
    int group4 = 0;

    String activity5 = null;
    int group5 = 0;

    int total_k = 0;


    @Override
    public void onBackPressed() {
        Intent intent3 = new Intent(Plan_APlan.this,Plan_MainActivity.class);
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

        //设置布局
        change = (Button)findViewById(R.id.save);
        change.setVisibility(View.GONE);

        change_activity = (TextView)findViewById(R.id.add_activity);
        change_activity.setVisibility(View.GONE);

        change1 = (TextView) findViewById(R.id.change);
        change1.setVisibility(View.VISIBLE);

        start = (Button)findViewById(R.id.start);
        start.setVisibility(View.VISIBLE);

        total = (TextView)findViewById(R.id.total);

        //点击“编辑计划”按钮，进入编辑页面
        change1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                change1.setVisibility(View.GONE);
                start.setVisibility(View.GONE);
                total.setText("?");

                change_activity.setVisibility(View.VISIBLE);
                change.setVisibility(View.VISIBLE);

            }
        });


        //获得从MainAcvitity.class获得的计划名
        Intent intent = getIntent();
        plan_name = intent.getStringExtra("plan_name");

        //设置计划名
        change_name = (EditText)findViewById(R.id.edit_planName);
        change_name.setText(plan_name);


        //从数据库中获得该计划的详细信息
        String table = "Plan";
        String [] colums =new String[]{
                "activity1","group1",
                "activity2","group2",
                "activity3","group3",
                "activity4","group4",
                "activity5","group5",
                "total_k"
        };
        String selection = "name = ?";
        String [] selectionArgs = new String[]{plan_name};
        Cursor cursor = db.query(table, colums, selection, selectionArgs, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                activity1 = cursor.getString(cursor.getColumnIndex("activity1"));
                activity2 = cursor.getString(cursor.getColumnIndex("activity2"));
                activity3 = cursor.getString(cursor.getColumnIndex("activity3"));
                activity4 = cursor.getString(cursor.getColumnIndex("activity4"));
                activity5 = cursor.getString(cursor.getColumnIndex("activity5"));

                group1 = cursor.getInt(cursor.getColumnIndex("group1"));
                group2 = cursor.getInt(cursor.getColumnIndex("group2"));
                group3 = cursor.getInt(cursor.getColumnIndex("group3"));
                group4 = cursor.getInt(cursor.getColumnIndex("group4"));
                group5 = cursor.getInt(cursor.getColumnIndex("group5"));

                total_k = cursor.getInt(cursor.getColumnIndex("total_k"));

                total.setText(String.valueOf(total_k));
                initActivity();

            } while (cursor.moveToNext());
            cursor.close();
        };

        //设置listView
        change_listView = (ListView)findViewById(R.id.activity_list);
        changeAdapter = new Plan_ActivityAdapter(Plan_APlan.this,R.layout.activity_list_item,change_list);
        change_listView.setAdapter(changeAdapter);

        //设置每一项活动的点击事件：点击该活动，弹出对话框，可修改组数
        change_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final Plan_MyActivities change_activities = change_list.get(position);
                Plan_MyGroupDialog.OnSureClickListener listener2 = new Plan_MyGroupDialog.OnSureClickListener(){
                    public void getText(String string)
                    {
                        change_number = string;
                        Log.d("Plan_AddPlan","运动组数"+change_number);
                        change_activities.setNumber(change_number);
                        changeAdapter.notifyDataSetChanged();
                    }
                };
                Plan_MyGroupDialog change_Dialog = new Plan_MyGroupDialog(Plan_APlan.this,listener2);
                change_Dialog.show();

            }
        });

        //点击“添加运动”按钮，系统跳转到添加运动界面（Plan_AddActivity.class）
        change_activity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent8 = new Intent(Plan_APlan.this,Plan_AddActivity.class);
                intent8.putExtra("from","Plan_APlan");
                startActivityForResult(intent8,1);

            }
        });

        //点击“START”按钮，系统跳转到开始运动界面(StartActivity.class)
        start = (Button)findViewById(R.id.start);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent3 = new Intent(Plan_APlan.this,EXE_ExerciseActivityFirst.class);
                intent3.putExtra("plan_name", plan_name);
                startActivity(intent3);
            }
        });



        //点击“保存”按钮，系统保存修改过的计划信息到数据库，计算新总卡路里
        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //计算该计划总卡路里
                total_k = calculate();

                //获得新或者旧的计划名字
                new_name = change_name.getText().toString();

                //将修改过的数据存储到数据库中
                ContentValues values = new ContentValues();
                values.put("name",new_name);

                values.put("activity1",activity1);
                values.put("group1",change_list.get(0).getNumber());

                values.put("activity2",activity2);
                values.put("group2",change_list.get(1).getNumber());

                values.put("activity3",activity3);
                values.put("group3",change_list.get(2).getNumber());

                values.put("activity4",activity4);
                values.put("group4",change_list.get(3).getNumber());

                values.put("activity5",activity5);
                values.put("group5",change_list.get(4).getNumber());

                values.put("total_k",total_k);

                db.update("Plan",values,"name = ?",new String[]{plan_name});
                Toast.makeText(Plan_APlan.this,"修改计划成功",Toast.LENGTH_SHORT).show();

                //“保存”按钮消失，“开始运动”按钮出现,显示总卡路里数
                change.setVisibility(View.GONE);
                start.setVisibility(View.VISIBLE);
                change_activity.setVisibility(View.GONE);
                Button add = (Button)findViewById(R.id.add);
                add.setVisibility(View.GONE);
                total.setText(String.valueOf(total_k));

            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode,int resultCode,Intent data){
        Toast.makeText(Plan_APlan.this,"come",Toast.LENGTH_SHORT).show();
        Log.d("Plan_APlan","requestCode:"+requestCode);
        Log.d("Plan_APlan","RESULT_OK:"+RESULT_OK);
        //switch (requestCode){
            //case 1:
                //if(requestCode == RESULT_OK){
                    //获得新添加的运动数据
                    activity1 = data.getStringExtra("data1");
                    activity2 = data.getStringExtra("data2");
                    activity3 = data.getStringExtra("data3");
                    activity4 = data.getStringExtra("data4");
                    activity5 = data.getStringExtra("data5");

                    change_list.clear();
                    initActivity();
                    changeAdapter.notifyDataSetChanged();
                //}
                //break;
            //default:
        //}
    }


    //初始化活动列表数据
    private void initActivity(){
        Plan_MyActivities c1 = new Plan_MyActivities(activity1,String.valueOf(group1));
        change_list.add(c1);

        Plan_MyActivities c2 = new Plan_MyActivities(activity2,String.valueOf(group2));
        change_list.add(c2);

        Plan_MyActivities c3 = new Plan_MyActivities(activity3,String.valueOf(group3));
        change_list.add(c3);

        Plan_MyActivities c4 = new Plan_MyActivities(activity4,String.valueOf(group4));
        change_list.add(c4);

        Plan_MyActivities c5 = new Plan_MyActivities(activity5,String.valueOf(group5));
        change_list.add(c5);

    }

    //计算新计划的总卡路里
    public int calculate(){
        int total = 0;

        int activity1_k = 0;
        int activity2_k = 0;
        int activity3_k = 0;
        int activity4_k = 0;
        int activity5_k = 0;

        int group1 =  Integer.parseInt(change_list.get(0).getNumber());
        int group2 =  Integer.parseInt(change_list.get(1).getNumber());
        int group3 =  Integer.parseInt(change_list.get(2).getNumber());
        int group4 =  Integer.parseInt(change_list.get(3).getNumber());
        int group5 =  Integer.parseInt(change_list.get(4).getNumber());


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
