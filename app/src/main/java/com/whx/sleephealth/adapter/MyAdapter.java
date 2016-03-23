package com.whx.sleephealth.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.whx.sleephealth.R;


/**
 * Created by whx on 2016/2/29.
 */
public class MyAdapter extends BaseAdapter{

    private int[] imagesRes = {R.mipmap.chart,R.mipmap.ic_list,R.mipmap.help};
    private String[] texts = {"统计数据","日志记录","帮助睡眠"};
    private Context mContext;

    public MyAdapter(Context mContext){
        this.mContext = mContext;
    }
    @Override
    public int getCount() {
        return texts.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MyHolder viewHolder;
        if(null == convertView){
            viewHolder = new MyHolder();

            convertView = LayoutInflater.from(mContext).inflate(R.layout.my_item,null);
            viewHolder.imageView = (ImageView)convertView.findViewById(R.id.item_image);


            viewHolder.textView = (TextView)convertView.findViewById(R.id.item_text);


            convertView.setTag(viewHolder);
        }else{
            viewHolder = (MyHolder)convertView.getTag();
        }

        viewHolder.imageView.setImageResource(imagesRes[position]);
        viewHolder.textView.setText(texts[position]);

        return convertView;
    }
    private class MyHolder{
        public ImageView imageView;
        public TextView textView;
    }
}
