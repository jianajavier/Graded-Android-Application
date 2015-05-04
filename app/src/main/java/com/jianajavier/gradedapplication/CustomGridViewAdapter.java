package com.jianajavier.gradedapplication;

import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import java.util.ArrayList;

/**
 * Created by jianajavier on 15-04-28.
 */
public class CustomGridViewAdapter extends ArrayAdapter<String> {

    private Context mContext;
    int layoutResourceId;

    private String[] text = {};
    //private Integer[] mThumbIds = {};

    public CustomGridViewAdapter(Context c, int resource, String[] text) {
        super(c, resource, text);
        this.layoutResourceId = resource;
        this.mContext = c;
        this.text = text;
    }

    @Override
    public int getCount() {
        return text.length + 1;
    }

//    @Override
//    public int getItem(int position) {
//        return position;
//    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View myView = convertView;
        ViewHolder holder = null;

        //TextView tv = new TextView(mContext);
        //Button btn = new Button(mContext);

        if (convertView == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
            myView = inflater.inflate(R.layout.custom_grid, null);

            myView.setTag(holder);

            holder.tv = (TextView) myView.findViewById(R.id.label);
            holder.coursebtn = (Button) myView.findViewById(R.id.grid_item_btn);
        } else {
            holder = (ViewHolder) myView.getTag();
        }

            if (position <= text.length - 1){
                holder.coursebtn.setVisibility(View.INVISIBLE);
                holder.tv.setText(text[position]);
                holder.tv.setTextSize(11);
            }

            if (position == text.length) {
                holder.tv.setVisibility(View.GONE);
                holder.coursebtn.setText("+");
                holder.coursebtn.setVisibility(View.VISIBLE);
                //return holder.btn;
            }

        return myView;
    }

    static class ViewHolder {
        TextView tv;
        Button coursebtn;
    }
}