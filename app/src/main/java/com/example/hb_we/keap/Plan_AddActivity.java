package com.example.hb_we.keap;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Windows on 2017/5/8.
 */

public class Plan_AddActivity extends Activity {

    TextView sure = null;

    Spinner shoubi = null;
    Spinner tuntui = null;
    Spinner yaofu = null;
    Spinner jianbei = null;
    Spinner lashen = null;

    //手臂
    List<String> shoubi_list = new ArrayList<String>();
    String data1 = null;
    //臀腿
    List<String> tuntui_list = new ArrayList<String>();
    String data2 = null;
    //腰腹
    List<String> yaofu_list = new ArrayList<String>();
    String data3 = null;
    //肩背
    List<String> jianbei_list = new ArrayList<String>();
    String data4 = null;
    //拉伸
    List<String> lashen_list = new ArrayList<String>();
    String data5 = null;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(getWindow().FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); // 隐藏状态栏
        setContentView(R.layout.add_activity);


        //从数据库的运动表中导入数据
        int id = 0;          //运动id
        String name = null;  //运动名字
        int k = 0;           //单组卡路里
        int pic = 0;         //图片id

        SQLiteDatabase db = Plan_MainActivity.dbHelper.getWritableDatabase();
        Cursor cursor = db.query("Activity",null,null,null,null,null,null);
        if(cursor.moveToFirst()){
            do{
                id = cursor.getInt(cursor.getColumnIndex("id"));
                name = cursor.getString(cursor.getColumnIndex("name"));
                k = cursor.getInt(cursor.getColumnIndex("k"));
                pic =cursor.getInt(cursor.getColumnIndex("pic"));

                Log.d("ExerciseActiviyFirst","运动id"+id);
                Log.d("ExerciseActiviyFirst","运动名字"+name);
                Log.d("ExerciseActiviyFirst","单组卡路里"+k);
                Log.d("ExerciseActiviyFirst","图片id"+pic);
                Log.d("ExerciseActiviyFirst","===========================================");

                if (id <= 6 ){
                    //id 1-6 属于肩背锻炼
                    jianbei_list.add(name);
                }
                else if (id <= 11){
                    //id 7-11 属于拉伸锻炼
                    lashen_list.add(name);
                }
                else if (id <= 18){
                    //id 12-18 属于手臂锻炼
                    shoubi_list.add(name);
                }
                else if (id <= 25){
                    //id 19-25 属于臀腿锻炼
                    tuntui_list.add(name);
                }
                else{
                    //id 26-34 属于腰腹锻炼
                    yaofu_list.add(name);
                }

            }while(cursor.moveToNext());
            cursor.close();
        };

        //设置spinner

        //手臂
        ArrayAdapter<String> shoubi_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_checked,shoubi_list);
        shoubi_adapter.setDropDownViewResource(android.R.layout.simple_list_item_checked);
        shoubi = (Spinner) findViewById(R.id.shoubi);
        shoubi.setAdapter(shoubi_adapter);
        shoubi.setSelection(0, true);

        //臀腿
        ArrayAdapter<String> tuntui_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_checked,tuntui_list);
        tuntui_adapter.setDropDownViewResource(android.R.layout.simple_list_item_checked);
        tuntui = (Spinner) findViewById(R.id.tuntui);
        tuntui.setAdapter(tuntui_adapter);

        //腰腹
        ArrayAdapter<String> yaofu_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_checked,yaofu_list);
        yaofu_adapter.setDropDownViewResource(android.R.layout.simple_list_item_checked);
        yaofu = (Spinner) findViewById(R.id.yaofu);
        yaofu.setAdapter(yaofu_adapter);

        //肩背
        ArrayAdapter<String> jianbei_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_checked,jianbei_list);
        jianbei_adapter.setDropDownViewResource(android.R.layout.simple_list_item_checked);
        jianbei = (Spinner) findViewById(R.id.jianbei);
        jianbei.setAdapter(jianbei_adapter);

        //拉伸
        ArrayAdapter<String> lashen_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_checked,lashen_list);
        lashen_adapter.setDropDownViewResource(android.R.layout.simple_list_item_checked);
        lashen = (Spinner) findViewById(R.id.lashen);
        lashen.setAdapter(lashen_adapter);



        //设置spinner的点击事件
        final List<String> activity_list = new ArrayList<String>();

        //手臂
        shoubi.setSelection(0, true);
        //给Spinner添加事件监听
        shoubi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            //当选中某一个数据项时触发该方法
            /*
             * parent接收的是被选择的数据项所属的 Spinner对象，
             * view参数接收的是显示被选择的数据项的TextView对象
             * position接收的是被选择的数据项在适配器中的位置
             * id被选择的数据项的行号
             */
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,int position, long id) {
                //System.out.println(spinner==parent);//true
                //System.out.println(view);
                //String data = adapter.getItem(position);//从适配器中获取被选择的数据项
                //String data = list.get(position);//从集合中获取被选择的数据项
                data1 = (String)shoubi.getItemAtPosition(position);//从spinner中获取被选择的数据
                Toast.makeText(Plan_AddActivity.this, data1, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });


        //臀腿
        tuntui.setSelection(0, true);
        tuntui.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,int position, long id) {
                data2 = (String)tuntui.getItemAtPosition(position);//从spinner中获取被选择的数据
                Toast.makeText(Plan_AddActivity.this, data2, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });

        //腰腹
        yaofu.setSelection(0, true);
        yaofu.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,int position, long id) {
                data3 = (String)yaofu.getItemAtPosition(position);//从spinner中获取被选择的数据
                Toast.makeText(Plan_AddActivity.this, data3, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });

        //肩背
        jianbei.setSelection(0, true);
        jianbei.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,int position, long id) {
                data4 = (String)jianbei.getItemAtPosition(position);//从spinner中获取被选择的数据
                Toast.makeText(Plan_AddActivity.this, data4, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });

        //拉伸
        lashen.setSelection(0, true);
        lashen.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,int position, long id) {
                data5 = (String)lashen.getItemAtPosition(position);//从spinner中获取被选择的数据
                Toast.makeText(Plan_AddActivity.this, data5, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });

        //点击“确定添加”按钮，系统返回到添加计划界面（Plan_AddPlan.class）
        sure = (TextView)findViewById(R.id.sure);
        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //确定将要跳往哪个活动
                Intent intent0 = getIntent();
                String from = intent0.getStringExtra("from");
                Log.d("Plan_AddActivity","我来自："+from);

                if(from.equals("Plan_AddPlan")){
                    Intent intent = new Intent(Plan_AddActivity.this, Plan_AddPlan.class);
                    intent.putExtra("data1", data1);
                    intent.putExtra("data2", data2);
                    intent.putExtra("data3", data3);
                    intent.putExtra("data4", data4);
                    intent.putExtra("data5", data5);
                    startActivity(intent);
                }
                else {
                    Intent intent = new Intent();
                    intent.putExtra("data1", data1);
                    intent.putExtra("data2", data2);
                    intent.putExtra("data3", data3);
                    intent.putExtra("data4", data4);
                    intent.putExtra("data5", data5);
                    setResult(RESULT_OK,intent);
                    finish();
                }
            }
        });


    }
}
