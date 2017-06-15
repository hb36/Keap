package com.example.hb_we.keap;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Windows on 2017/5/9.
 */

public class Plan_ActivityAdapter extends ArrayAdapter<Plan_MyActivities> {

    private int resourceId;

    public Plan_ActivityAdapter(Context context, int textViewResouceId, List<Plan_MyActivities> objects){
        super(context,textViewResouceId,objects);
        resourceId = textViewResouceId;
    }

    @Override
    public View getView(int position,View convertView,ViewGroup parent){
        Plan_MyActivities myActivities = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceId,null);
        TextView name = (TextView)view.findViewById(R.id.name);
        TextView number = (TextView)view.findViewById(R.id.number);
        name.setText(myActivities.getName());
        number.setText(myActivities.getNumber());
        return view;
    }
}
