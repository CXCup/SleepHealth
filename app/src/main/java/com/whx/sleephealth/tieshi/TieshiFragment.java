package com.whx.sleephealth.tieshi;

import android.app.LauncherActivity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.whx.sleephealth.R;
import com.whx.sleephealth.adapter.ListViewAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by whx on 2016/1/18.
 */
public class TieshiFragment extends Fragment {

    private ListView lv;
    private View view;
    private List<Map<String, Object>> data;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.tieshi,container,false);
        lv = (ListView) view.findViewById(R.id.listview);
        data = getData();
        ListViewAdapter adapter = new ListViewAdapter(getActivity(),data);
        lv.setAdapter(adapter);
        return view;
    }

    private List<Map<String, Object>> getData()
    {
        List<Map<String, Object>> list = new ArrayList<>();
        Map<String, Object> map;
        for(int i=0;i<10;i++)
        {
            map = new HashMap<>();
            map.put("img", R.mipmap.listview);
            map.put("title", "睡前这样玩手机才是正确姿势");
            map.put("info", "如果实在无法拒绝躺着玩手机......那怎样才能把伤害降到最低？看看这9小招！");
            list.add(map);
        }
        return list;
    }

}

