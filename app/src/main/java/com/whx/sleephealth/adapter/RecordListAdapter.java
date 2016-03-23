package com.whx.sleephealth.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.whx.sleephealth.R;

import java.util.List;

/**
 * Created by whx on 2016/3/22.
 */
public class RecordListAdapter extends BaseAdapter{

    Context mContext;
    List<RecordListItem> datas;

    public RecordListAdapter(Context context){
        mContext = context;
    }
    public void setDatas(List<RecordListItem> datas){
        this.datas = datas;
    }
    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        MHolder holder;

        if(null == convertView){
            holder = new MHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.record_item,null);
            holder.date = (TextView)convertView.findViewById(R.id.date);
            holder.startTime = (TextView)convertView.findViewById(R.id.start_time);
            holder.time = (TextView)convertView.findViewById(R.id.sleep_long);
            holder.eff = (TextView)convertView.findViewById(R.id.efficiency);

            convertView.setTag(holder);

        }else{
            holder = (MHolder)convertView.getTag();
        }

        holder.date.setText(datas.get(position).getDate());
        holder.startTime.setText(datas.get(position).getStart_time());
        holder.time.setText(datas.get(position).getTimelong());
        holder.eff.setText(datas.get(position).getEff());

        return convertView;
    }
    private class MHolder{
        public TextView date;
        public TextView startTime;
        public TextView time;
        public TextView eff;
    }
}
