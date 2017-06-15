package com.example.hb_we.keap;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.PopupMenu;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Cxy on 2017/5/9.
 */

public class Move_diray_main extends AppCompatActivity {
    private String monthmenu;
    private String yearmenu;
    private ListView listview;
    private Move_DayAdapter adapter = null;
    private ArrayList<Object> data = new ArrayList<>();
    private ArrayList<Object> anotherData = new ArrayList<>();
    public static final Calendar calendar = Calendar.getInstance();
    public static final int day = calendar.get(Calendar.DAY_OF_MONTH);
    public static final int monthPresent = calendar.get(Calendar.MONTH) + 1;
    public static final int yearPresent = calendar.get(Calendar.YEAR);
    Move_DetailList moveDetailList = new Move_DetailList();
    private Button monthButton;
    private Button yearButton;
    private ImageButton backButton;


    public static String[] Array_month = new String[]
            {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();//隐藏标题栏
        setContentView(R.layout.diary_main);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        listview = (ListView) findViewById(R.id.list_view);
        monthButton = (Button) findViewById(R.id.month);
        yearButton = (Button) findViewById(R.id.year);
        backButton = (ImageButton) findViewById(R.id.returnButton);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent back_to_move_mainActivity = new Intent(Move_diray_main.this,Move_MainActivity.class);
                startActivity(back_to_move_mainActivity);
                finish();
            }
        });


        Intent intent = getIntent();
        final int itemClicked = intent.getIntExtra("itemClicked", 0);
        final String passContent = intent.getStringExtra("content");


        if ((ArrayList<Object>) getObject("cxydaygram.dat") != null) {
            data = (ArrayList<Object>) getObject("cxydaygram.dat");
        }

        if (data.size() == 0) {
            for (int i = 1; i <= day; i++) {
                data.add(null);
            }
            adapter = new Move_DayAdapter(Move_diray_main.this, data);
            listview.setAdapter(adapter);
        } else {
            adapter = new Move_DayAdapter(Move_diray_main.this, data);
            listview.setAdapter(adapter);
        }
        if (intent.getStringExtra("week") != null && intent.getStringExtra("date") != null) {
            moveDetailList.setWeek(intent.getStringExtra("week"));
            moveDetailList.setDate(intent.getStringExtra("date"));
            moveDetailList.setContent(intent.getStringExtra("content"));

            data.set(itemClicked, moveDetailList);
            saveObject("cxydaygram.dat");
            adapter = new Move_DayAdapter(Move_diray_main.this, data);
            listview.setAdapter(adapter);
        }

        listview.setSelection(adapter.getCount() - 1);
        listview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Move_diray_main.this);
                builder.setMessage("This diary will be deleted from the list.");
                builder.setTitle("Warning");
                builder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        data.set(position, null);
                        saveObject("cxydaygram.dat");
                        adapter = new Move_DayAdapter(Move_diray_main.this, data);
                        listview.setAdapter(adapter);
                        listview.setSelection(adapter.getCount() - 1);
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.create().show();
                return true;
            }
        });

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(Move_diray_main.this, Move_ActivityEdit.class);
                intent.putExtra("position", (day - (position + 1)));
                intent.putExtra("itemClicked", position);
                intent.putExtra("itemLength", listview.getCount());
                if (data.get(position) != null) {
                    Object[] obj = data.toArray();
                    for (int i = 0; i < data.size(); i++) {
                        if (i == position) {
                            moveDetailList = (Move_DetailList) obj[i];
                        }
                    }
                    intent.putExtra("passContent", moveDetailList.getContent());
                }
                startActivity(intent);
            }
        });

        String month = String.valueOf(calendar.get(Calendar.MONTH));
        for (int i = 0; i <= 11; i++) {
            String j = String.valueOf(i);
            if (month.equals(j)) {
                month = Array_month[i];
                break;
            }
        }

        monthButton.setText(month + "  |");
        monthButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupMonth(monthButton);
            }
        });
        String year = String.valueOf(calendar.get(Calendar.YEAR));
        yearButton.setText(year + "    |");
        yearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupYear(yearButton);
            }
        });
    }

    public void showPopupYear(View v) {
        PopupMenu pop = new PopupMenu(Move_diray_main.this, v);
        // 加载一个 R.menu.menu_control
        pop.getMenuInflater().inflate(R.menu.yearchange, pop.getMenu());
        pop.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                yearmenu = (String) item.getTitle();
                yearButton.setText(yearmenu);
                return false;
            }
        });
        pop.show();
    }

    public void showPopupMonth(View v) {
        PopupMenu pop = new PopupMenu(Move_diray_main.this, v);
        // 加载一个 R.menu.menu_control
        pop.getMenuInflater().inflate(R.menu.monthchange, pop.getMenu());
        pop.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                monthmenu = (String) item.getTitle();
                monthButton.setText(monthmenu);
                switch (item.getItemId()) {
                    case R.id.Jan:
                        monthButton.setText(monthmenu);
                        if (monthPresent == 1 && yearPresent == Integer.parseInt(yearButton.getText().toString().substring(0, 4))) {
                            nowdays();
                        } else {
                            cMonth(1, 31);
                        }

                        break;
                    case R.id.Feb:
                        monthButton.setText(monthmenu);
                        if (monthPresent == 2 && yearPresent == Integer.parseInt(yearButton.getText().toString().substring(0, 4))) {
                            nowdays();
                        } else {
                            if (Integer.parseInt(yearButton.getText().toString().substring(0, 4)) % 4 == 0
                                    &&
                                    Integer.parseInt(yearButton.getText().toString().substring(0, 4)) % 100 != 0
                                    ||
                                    Integer.parseInt(yearButton.getText().toString().substring(0, 4)) % 400 == 0) {
                                cMonth(2, 29);
                            } else {
                                cMonth(2, 28);
                            }
                        }
                        break;
                    case R.id.Mar:
                        monthButton.setText(monthmenu);
                        if (monthPresent == 3 && yearPresent == Integer.parseInt(yearButton.getText().toString().substring(0, 4))) {
                            nowdays();
                        } else {
                            cMonth(3, 31);
                        }
                        break;
                    case R.id.Apr:
                        monthButton.setText(monthmenu);
                        if (monthPresent == 4 && yearPresent == Integer.parseInt(yearButton.getText().toString().substring(0, 4))) {
                            nowdays();
                        } else {
                            cMonth(4, 30);
                        }
                        break;
                    case R.id.May:
                        monthButton.setText(monthmenu);
                        if (monthPresent == 5 && yearPresent == Integer.parseInt(yearButton.getText().toString().substring(0, 4))) {
                            nowdays();
                        } else {
                            cMonth(5, 31);
                        }
                        break;
                    case R.id.Jun:
                        monthButton.setText(monthmenu);
                        if (monthPresent == 6 && yearPresent == Integer.parseInt(yearButton.getText().toString().substring(0, 4))) {
                            nowdays();
                        } else {
                            cMonth(6, 30);
                        }
                        break;
                    case R.id.Jul:
                        monthButton.setText(monthmenu);
                        if (monthPresent == 7 && yearPresent == Integer.parseInt(yearButton.getText().toString().substring(0, 4))) {
                            nowdays();
                        } else {
                            cMonth(7, 31);
                        }
                        break;
                    case R.id.Aug:
                        monthButton.setText(monthmenu);
                        if (monthPresent == 8 && yearPresent == Integer.parseInt(yearButton.getText().toString().substring(0, 4))) {
                            nowdays();
                        } else {
                            cMonth(8, 31);
                        }
                        break;
                    case R.id.Sep:
                        monthButton.setText(monthmenu);
                        if (monthPresent == 9 && yearPresent == Integer.parseInt(yearButton.getText().toString().substring(0, 4))) {
                            nowdays();
                        } else {
                            cMonth(9, 30);
                        }
                        break;
                    case R.id.Oct:
                        monthButton.setText(monthmenu);
                        if (monthPresent == 10 && yearPresent == Integer.parseInt(yearButton.getText().toString().substring(0, 4))) {
                            nowdays();
                        } else {
                            cMonth(10, 31);
                        }
                        break;
                    case R.id.Nov:
                        monthButton.setText(monthmenu);
                        if (monthPresent == 11 && yearPresent == Integer.parseInt(yearButton.getText().toString().substring(0, 4))) {
                            nowdays();
                        } else {
                            cMonth(11, 30);
                        }
                        break;
                    case R.id.Dec:
                        monthButton.setText(monthmenu);
                        if (monthPresent == 12 && yearPresent == Integer.parseInt(yearButton.getText().toString().substring(0, 4))) {
                            nowdays();
                        } else {
                            cMonth(12, 31);
                        }
                        break;
                    default:
                        break;
                }
                return true;
            }
        });
        pop.show();
    }

    public void nowdays() {
        if ((ArrayList<Object>) getObject("cxydaygram.dat") != null) {
            data = (ArrayList<Object>) getObject("cxydaygram.dat");
        }
        if (data.size() != 0) {
            adapter = new Move_DayAdapter(Move_diray_main.this, data);
            listview.setAdapter(adapter);
            listview.setSelection(listview.getCount() - 1);
        }
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(Move_diray_main.this, Move_TimeChange.class);
                intent.putExtra("month", monthButton.getText().toString());
                intent.putExtra("year", yearButton.getText().toString().substring(0, 4));
                intent.putExtra("position", position);

                if (data.get(position) != null) {
                    Object[] obj = data.toArray();
                    for (int i = 0; i < data.size(); i++) {
                        if (i == position) {
                            moveDetailList = (Move_DetailList) obj[i];
                        }
                    }
                    intent.putExtra("passContent", moveDetailList.getContent());
                }
                startActivity(intent);
            }
        });
    }

    private void saveObject(String name) {
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        try {
            fos = this.openFileOutput(name, MODE_PRIVATE);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(data);
        } catch (Exception e) {
            e.printStackTrace();
            //这里是保存文件产生异常
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    //fos流关闭异常
                    e.printStackTrace();
                }
            }
            if (oos != null) {
                try {
                    oos.close();
                } catch (IOException e) {
                    //oos流关闭异常
                    e.printStackTrace();
                }
            }
        }
    }

    private Object getObject(String name) {
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        try {
            fis = this.openFileInput(name);
            ois = new ObjectInputStream(fis);
            return ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
            //这里是读取文件产生异常
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    //fis流关闭异常
                    e.printStackTrace();
                }
            }
            if (ois != null) {
                try {
                    ois.close();
                } catch (IOException e) {
                    //ois流关闭异常
                    e.printStackTrace();
                }
            }
        }
        //读取产生异常，返回null
        return null;
    }

    public void cMonth(final int monthPass, int dayTotal) {
        if (anotherData.size() == 0) {
            for (int i = 0; i < dayTotal; i++) {
                anotherData.add(null);
            }
            adapter = new Move_DayAdapter(Move_diray_main.this, anotherData);
            listview.setAdapter(adapter);
        } else {
            adapter = new Move_DayAdapter(Move_diray_main.this, anotherData);
            listview.setAdapter(adapter);
        }
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(Move_diray_main.this, Move_TimeChange.class);
                intent.putExtra("year", yearButton.getText().toString().substring(0, 4));
                intent.putExtra("position", position);
                intent.putExtra("month", monthPass);
                startActivity(intent);
            }
        });
    }

}
