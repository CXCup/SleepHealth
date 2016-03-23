package com.whx.sleephealth.mine;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import com.whx.sleephealth.R;
import com.whx.sleephealth.adapter.RecordListAdapter;
import com.whx.sleephealth.adapter.RecordListItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by whx on 2016/3/22.
 */
public class RecordActivity extends Activity{

    private List<RecordListItem> items;
    private RecordListAdapter adapter;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.record);

        listView = (ListView)findViewById(R.id.record_list);
        adapter = new RecordListAdapter(this);
        items = new ArrayList<>();
    }

    @Override
    protected void onStart() {
        super.onStart();

    }
}
