package com.example.hb_we.keap;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

/**
 * Created by Cxy on 2017/5/8.
 */

public class Move_article_list extends Plan_MainActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.article_list);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN); // 隐藏状态栏
        Intent intent_2=getIntent();
        TextView art_title1=(TextView)findViewById(R.id.art_title1);
        TextView art_title2=(TextView)findViewById(R.id.art_title2);
        TextView art_title3=(TextView)findViewById(R.id.art_title3);
        TextView art_title4=(TextView)findViewById(R.id.art_title4);
        TextView art_title5=(TextView)findViewById(R.id.art_title5);
        art_title1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2=new Intent(Move_article_list.this,Move_article1.class);
                startActivity(intent2);
            }
        });
        art_title2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent3=new Intent(Move_article_list.this,Move_article2.class);
                startActivity(intent3);
            }
        });
        art_title3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent4=new Intent(Move_article_list.this,Move_article3.class);
                startActivity(intent4);
            }
        });
        art_title4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent5=new Intent(Move_article_list.this,Move_article4.class);
                startActivity(intent5);
            }
        });
        art_title5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent6=new Intent(Move_article_list.this,Move_article5.class);
                startActivity(intent6);
            }
        });

    }

}
