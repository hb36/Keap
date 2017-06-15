package com.example.hb_we.keap;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Cxy on 2017/5/9.
 */

public class Move_DayAdapter extends BaseAdapter {

    private static final int TYPE_First = 1;
    private static final int TYPE_Second = 0;
    private Context context;
    private ArrayList<Object> data = null;

    public Move_DayAdapter(Context context, ArrayList<Object> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        if (data.get(position) instanceof Move_DetailList) {
            return TYPE_First;
        } else if (data.get(position) == null) {
            return TYPE_Second;
        } else {
            return super.getItemViewType(position);
        }

    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        int type = getItemViewType(position);
        ViewHolder1 holder1 = null;
        if(convertView == null){
            switch (type){
                case TYPE_First:
                    holder1 = new ViewHolder1();
                    convertView = LayoutInflater.from(context).inflate(R.layout.frame, parent, false);
                    holder1.week = (TextView) convertView.findViewById(R.id.week);
                    holder1.date = (TextView) convertView.findViewById(R.id.date);
                    holder1.content = (TextView) convertView.findViewById(R.id.content);
                    convertView.setTag(R.id.tag_one, holder1);
                    break;
                case TYPE_Second:
                    convertView = LayoutInflater.from(context).inflate(R.layout.blackdot, parent, false);
                    break;
            }
        }else{
            switch (type){
                case TYPE_First:
                    holder1 = (ViewHolder1) convertView.getTag(R.id.tag_one);
                    break;
                case TYPE_Second:
                    break;
            }
        }

        Object obj = data.get(position);
        switch (type){
        case TYPE_First:
        Move_DetailList moveDetailList = (Move_DetailList) obj;
        if(moveDetailList != null){
            if (moveDetailList.getWeek().equals("SUN")) {
                holder1.week.setTextColor(android.graphics.Color.RED);
            }
            holder1.week.setText(moveDetailList.getWeek());
            holder1.date.setText(moveDetailList.getDate());
            holder1.content.setText(moveDetailList.getContent());
        }
        break;
        case TYPE_Second:
        break;
    }
    return convertView;
}

private static class ViewHolder1{
    TextView week;
    TextView date;
    TextView content;
}

}
