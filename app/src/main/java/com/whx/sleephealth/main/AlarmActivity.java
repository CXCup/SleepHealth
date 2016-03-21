package com.whx.sleephealth.main;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.whx.sleephealth.R;

/**
 * Created by whx on 2016/3/9.
 */
public class AlarmActivity extends Activity{

    private Button closeAlarm;
    private MediaPlayer alarlmPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);

        alarlmPlayer = MediaPlayer.create(this,R.raw.alarm02);
        alarlmPlayer.setLooping(true);
        alarlmPlayer.start();

        closeAlarm = (Button)findViewById(R.id.close_alarm);
        closeAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                alarlmPlayer.stop();
                finish();
            }
        });
    }
}
