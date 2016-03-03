package com.whx.sleephealth.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;

import com.whx.sleephealth.R;
import com.whx.sleephealth.tieshi.MLog;

/**
 * Created by whx on 2016/1/18.
 */
public class MainFragment extends Fragment implements View.OnClickListener,TimePicker.OnTimeChangedListener{

    private View view;
    private CircleProgressView circleProgressView;
    private ImageButton preNight,nextNight;
    private TextView baogao,suggestion;

    private TextView alarmText;
    private Button startBtn;
    private TimePicker timePicker;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.activity_main_new,container,false);
        //initContents();
        init();
        return view;
    }

    private void initContents(){
        circleProgressView = (CircleProgressView)view.findViewById(R.id.circleView);
        circleProgressView.setMaxValue(360);
        circleProgressView.setValue(0);
        circleProgressView.setValueAnimated(getSleepLength()/4f);
        circleProgressView.setText((int)(getSleepLength()/60)+" h "+(int)(getSleepLength()%60)+" m");

        preNight = (ImageButton)view.findViewById(R.id.pre_night);
        preNight.setOnClickListener(this);

        nextNight = (ImageButton)view.findViewById(R.id.nxt_night);
        nextNight.setOnClickListener(this);

        baogao = (TextView)view.findViewById(R.id.baogao);
        suggestion = (TextView)view.findViewById(R.id.suggestion);
        suggestion.setOnClickListener(this);

    }

    private void init(){
        startBtn = (Button)view.findViewById(R.id.start);
        startBtn.setOnClickListener(this);

        alarmText = (TextView)view.findViewById(R.id.alarm_text);

        timePicker = (TimePicker)view.findViewById(R.id.alarm_time);
        timePicker.setIs24HourView(true);
        timePicker.setOnTimeChangedListener(this);
    }

    private float getSleepLength(){
        float x = 8*60;

        return x;
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.start:
                Intent intent = new Intent(getActivity(),SuggestionActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
        //Log.d(MLog.TAG,hourOfDay+" : "+minute);
        int startMin,endMin,startHour,endHour;
        if(minute<10){
            startMin = minute + 50;
            endMin = minute + 10;
            startHour = hourOfDay -1;
            endHour = hourOfDay;
        }else if(minute >= 50){
            startMin = minute - 10;
            endMin = minute - 50;
            startHour = hourOfDay;
            endHour = hourOfDay + 1;
        }else{
            startMin = minute - 10;
            endMin = minute + 10;
            startHour = hourOfDay;
            endHour = hourOfDay;
        }
        alarmText.setText("闹钟："+ startHour +" : "+startMin+" - "+endHour+" : "+endMin);
    }
}
