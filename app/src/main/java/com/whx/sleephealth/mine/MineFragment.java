package com.whx.sleephealth.mine;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.whx.sleephealth.R;

/**
 * Created by whx on 2016/1/18.
 */
public class MineFragment extends Fragment implements View.OnClickListener{

    private View view;
    private LinearLayout record,helpSleep,alarm;
    private ImageView touxiang;
    private TextView userName;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.mine,container,false);

        initContents();
        return view;
    }

    private void initContents(){
        record = (LinearLayout)view.findViewById(R.id.record);
        helpSleep = (LinearLayout)view.findViewById(R.id.help);
        alarm = (LinearLayout)view.findViewById(R.id.alarm);

        record.setOnClickListener(this);
        helpSleep.setOnClickListener(this);
        alarm.setOnClickListener(this);


    }
    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.record:
                Intent mChart = new Intent(getActivity(),ChartActivity.class);
                startActivity(mChart);
                break;
            case R.id.help:
                break;
            case R.id.alarm:
                break;
        }
    }
}
