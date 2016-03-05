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

import java.util.Date;

/**
 * Created by whx on 2016/1/18.
 */
public class MainFragment extends Fragment implements View.OnClickListener,TimePicker.OnTimeChangedListener{

    private View view;

    private TextView alarmText;
    private Button startBtn;
    private TimePicker timePicker;
    private int startMin,endMin,startHour,endHour;

    public static String AlarmText;
    public static Intent intent;
    public static long startTime;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.activity_main_new,container,false);
        //initContents();
        init();
        return view;
    }

    private void init(){
        startBtn = (Button)view.findViewById(R.id.start);
        startBtn.setOnClickListener(this);

        alarmText = (TextView)view.findViewById(R.id.alarm_text);

        timePicker = (TimePicker)view.findViewById(R.id.alarm_time);
        timePicker.setIs24HourView(true);
        timePicker.setOnTimeChangedListener(this);

        getAlarmtext(timePicker.getCurrentHour(), timePicker.getCurrentMinute());
        AlarmText = "闹钟："+ startHour +" : "+startMin+" - "+endHour+" : "+endMin;
        alarmText.setText(AlarmText);
    }

    private float getSleepLength(){
        float x = 8*60;

        return x;
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.start:
                Date date = new Date();
                startTime = date.getTime();

                intent = new Intent(getContext(),SleepTimeService.class);
                getActivity().startService(intent);

                Intent intent1 = new Intent(getActivity(),CloseAlarmActivity.class);
                startActivity(intent1);
                break;
        }
    }

    @Override
    public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
        //Log.d(MLog.TAG,hourOfDay+" : "+minute);

        getAlarmtext(hourOfDay,minute);

        AlarmText = "闹钟："+ startHour +" : "+startMin+" - "+endHour+" : "+endMin;
        alarmText.setText(AlarmText);
    }
    private void getAlarmtext(int hourOfDay,int minute){
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
    }
}
