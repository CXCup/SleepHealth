package com.whx.sleephealth.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.whx.sleephealth.R;

/**
 * Created by whx on 2016/1/18.
 */
public class MainFragment extends Fragment implements View.OnClickListener{

    private View view;
    private CircleProgressView circleProgressView;
    private ImageButton preNight,nextNight;
    private TextView baogao,suggestion;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.main,container,false);
        initContents();
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

    private float getSleepLength(){
        float x = 8*60;

        return x;
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.pre_night:
                break;
            case R.id.nxt_night:
                break;
            case R.id.suggestion:
                break;
        }
    }
}
