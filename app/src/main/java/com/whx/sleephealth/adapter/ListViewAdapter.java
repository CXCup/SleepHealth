package com.whx.sleephealth.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.whx.sleephealth.R;

import java.util.List;
import java.util.Map;


/**
 * Created by whx on 2016/1/20.
 */
public class ListViewAdapter extends BaseAdapter{

    private LayoutInflater mInflater = null;
    private List<Map<String, Object>> data;

    public ListViewAdapter(Context context,List<Map<String,Object>> data)
    {
        //根据context上下文加载布局
        this.mInflater = LayoutInflater.from(context);

        this.data = data;
    }
    @Override
    public int getCount() {
        //在此适配器中所代表的数据集中的条目数
        return data.size();
    }
    @Override
    public Object getItem(int position) {
        //获取数据集中与指定索引对应的数据项
        return position;
    }
    @Override
    public long getItemId(int position) {
        //获取在列表中与指定索引对应的行id
        return position;
    }

    //获取一个在数据集中指定索引的视图来显示数据
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        //如果缓存convertView为空，则需要创建View
        if(convertView == null)
        {
            holder = new ViewHolder();
            //根据自定义的Item布局加载布局
            convertView = mInflater.inflate(R.layout.listview_item, null);
            holder.img = (ImageView)convertView.findViewById(R.id.img);
            holder.title = (TextView)convertView.findViewById(R.id.tv);
            holder.info = (TextView)convertView.findViewById(R.id.info);
            //将设置好的布局保存到缓存中，并将其设置在Tag里，以便后面方便取出Tag
            convertView.setTag(holder);
        }else
        {
            holder = (ViewHolder)convertView.getTag();
        }
        holder.img.setBackgroundResource((Integer)data.get(position).get("img"));
        holder.title.setText((String)data.get(position).get("title"));
        holder.info.setText((String)data.get(position).get("info"));

        return convertView;
    }
}
