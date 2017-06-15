package com.example.hb_we.keap;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Plan_MainActivity extends Activity {

    public int PLAN = 0;
    public static final int NEW_PLAN = 0;
    public static final int EXIST_PLAN = 1;

    TextView add_plan = null;
    ListView plan_listView = null;
    Button diary = null;
    Button information = null;

    public static Plan_MyDatabaseHelper dbHelper;
    public String wenhongbiao;


    //计划表名称
    String name = null;

    public List<String> plan_list = new ArrayList<String >();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(getWindow().FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); // 隐藏状态栏
        setContentView(R.layout.activity_main);



        //
        Intent getName = getIntent();
        wenhongbiao = getName.getStringExtra("extra_name");
//        Toast.makeText(Plan_MainActivity.this,wenhongbiao,Toast.LENGTH_SHORT).show();

        //创建数据库,运动表,计划表
        dbHelper = new Plan_MyDatabaseHelper(this,"keapDatabase.db",null,1);
        dbHelper.getWritableDatabase();

        //点击“信息”按钮，跳转到信息界面
        information = (Button)findViewById(R.id.information);
        information.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent10 = new Intent(Plan_MainActivity.this,InformationActivity.class);
                intent10.putExtra("extra_name",wenhongbiao);
                startActivity(intent10);
            }
        });


        //点击“动态”按钮，跳转到动态界面
        diary = (Button)findViewById(R.id.diary);
        diary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent9 = new Intent(Plan_MainActivity.this,Move_MainActivity.class);
                intent9.putExtra("extra_name",wenhongbiao);
                startActivity(intent9);
            }
        });

        //点击“添加计划”按钮跳转到编辑新计划页面（Plan_AddPlan.class）
        add_plan = (TextView)findViewById(R.id.add_plan);
        add_plan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PLAN = NEW_PLAN;
                Intent intent1 = new Intent(Plan_MainActivity.this,Plan_AddPlan.class);
                startActivity(intent1);
            }
        });

        //读取数据库中计划表的名称
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.query("Plan", null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                name = cursor.getString(cursor.getColumnIndex("name"));
                initPlan();

            } while (cursor.moveToNext());
            cursor.close();
        };



        final ArrayAdapter<String> plan_adapter = new ArrayAdapter<String>
                (this,android.R.layout.simple_list_item_1,plan_list);


        plan_listView = (ListView) findViewById(R.id.plan_list);
        plan_listView.setAdapter(plan_adapter);

        //长按某一项计划，弹出对话框“是否删除”
        plan_listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            public boolean onItemLongClick(AdapterView<?> arg0, View view,
                                           final int position, long id) {
                //弹出对话框，确认是否删除
                AlertDialog.Builder builder = new AlertDialog.Builder(Plan_MainActivity.this);
                builder.setTitle("是否删除该计划?");
                //删除
                builder.setPositiveButton("是", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        SQLiteDatabase db = dbHelper.getWritableDatabase();
                        db.delete("Plan","name = ?",new String[]{plan_list.get(position)});
                        plan_list.remove(plan_list.get(position));
                        plan_adapter.notifyDataSetChanged();
                        Toast.makeText(Plan_MainActivity.this,"成功删除该计划",Toast.LENGTH_SHORT).show();

                    }
                });
                //取消
                builder.setNegativeButton("否", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(Plan_MainActivity.this,"取消删除",Toast.LENGTH_SHORT).show();

                    }
                });
                builder.show();

                return true;
            }
        });


        //点击某一项计划，显示该计划的详细运动项目
        plan_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                // TODO Auto-generated method stub
                Intent intent2 = new Intent(Plan_MainActivity.this,Plan_APlan.class);
                intent2.putExtra("plan_name", plan_list.get(arg2));
                startActivity(intent2);

            }
        });

    }
    //初始化活动列表数据
    private void initPlan(){
        plan_list.add(name);
    }
}
