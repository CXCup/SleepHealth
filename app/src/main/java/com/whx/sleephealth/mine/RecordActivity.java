package com.whx.sleephealth.mine;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.whx.sleephealth.R;
import com.whx.sleephealth.adapter.MHolder;
import com.whx.sleephealth.adapter.RecordListAdapter;
import com.whx.sleephealth.adapter.RecordListItem;
import com.whx.sleephealth.main.ReportActivity;
import com.whx.sleephealth.main.SQLHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by whx on 2016/3/22.
 */
public class RecordActivity extends Activity implements AdapterView.OnItemClickListener,View.OnLongClickListener{

    private List<RecordListItem> items;
    private RecordListAdapter adapter;
    private SwipeMenuListView listView;
    private TextView title;

    SQLHelper helper = new SQLHelper(this,"my_record.db3",1);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.record);

        title = (TextView)findViewById(R.id.title);
        title.setText("睡眠日志");

        listView = (SwipeMenuListView)findViewById(R.id.record_list);
        listView.setOnItemClickListener(this);
        listView.setOnLongClickListener(this);
        listView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public void onMenuItemClick(int position, SwipeMenu menu, int index) {
                switch (index){
                    case 0:

                        boolean b = helper.delete(helper.getReadableDatabase(), (items.get(position)).getDate(),
                                (items.get(position)).getLstart());
                        if(b){
                            Toast.makeText(RecordActivity.this,"删除成功",Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(RecordActivity.this,"删除失败",Toast.LENGTH_SHORT).show();
                        }
                        items.remove(position);
                        adapter.notifyDataSetChanged();
                        break;
                }
            }
        });

        SwipeMenuCreator creator = new SwipeMenuCreator() {
            @Override
            public void create(SwipeMenu menu) {
                SwipeMenuItem deleteItem = new SwipeMenuItem(getApplicationContext());

                deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9,
                        0x3F, 0x25)));
                // set item width
                deleteItem.setWidth(dp2px(90));
                // set a icon
                deleteItem.setIcon(R.mipmap.ic_delete);

                // add to menu
                menu.addMenuItem(deleteItem);
            }
        };
        listView.setMenuCreator(creator);

        adapter = new RecordListAdapter(this);
        items = new ArrayList<>();

        Cursor cursor = helper.getReadableDatabase().query("record",new String[]{"date",
                "start_time","total_time","eff"},null,null,null,null,null,"10");
        while (cursor.moveToNext()){
            RecordListItem item = new RecordListItem();
            item.setDate(cursor.getString(0));

            Date d = new Date(cursor.getLong(1));
            String start = format.format(d);
            item.setStart_time(start);

            if(cursor.getInt(2)>60){
                item.setTimelong(cursor.getInt(2)/60 + " h "+cursor.getInt(2)%60+" min");
            }else{
                item.setTimelong(cursor.getInt(2)+" min");
            }

            item.setEff(cursor.getString(3)+"%");

            item.setLstart(cursor.getLong(1));

            items.add(item);
        }

        adapter.setDatas(items);
        listView.setAdapter(adapter);
    }

    SimpleDateFormat format = new SimpleDateFormat("HH:MM");
    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


        Intent intent = new Intent(RecordActivity.this, ReportActivity.class);
        intent.setFlags(2);
        intent.putExtra("date", items.get(position).getDate());
        intent.putExtra("start", items.get(position).getLstart());

        startActivity(intent);

        //Toast.makeText(this,position+"",Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onLongClick(View v) {

        return false;
    }

    @Override
    protected void onDestroy() {
        if(helper != null){
            helper.close();
        }
        super.onDestroy();
    }
    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                getResources().getDisplayMetrics());
    }
}
