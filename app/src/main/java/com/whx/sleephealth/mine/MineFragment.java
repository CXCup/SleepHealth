package com.whx.sleephealth.mine;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.whx.sleephealth.R;
import com.whx.sleephealth.adapter.MyAdapter;

/**
 * Created by whx on 2016/1/18.
 */
public class MineFragment extends Fragment{

    private View view;
    private ListView listView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.mine,container,false);

        initContents();
        return view;
    }

    private void initContents(){
        listView = (ListView)view.findViewById(R.id.my_item_list);
        MyAdapter myAdapter = new MyAdapter(getActivity());
        listView.setAdapter(myAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        Intent mChart = new Intent(getActivity(),ChartActivity.class);
                        startActivity(mChart);
                        break;
                    case 1:
                        break;
                    case 2:
                        Intent mHelp = new Intent(getActivity(),HelpItemActivity.class);
                        startActivity(mHelp);
                        break;
                }
            }
        });
    }

}
