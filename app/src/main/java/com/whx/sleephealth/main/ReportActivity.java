package com.whx.sleephealth.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.whx.sleephealth.R;
import com.whx.sleephealth.tieshi.MLog;

/**
 * Created by whx on 2016/3/5.
 */
public class ReportActivity extends Activity{

    TextView title;
    ProgressBar effProgressBar,awakeProgressBar,lightSleepBar,deepSleepBar;
    Button btn_sug,awakeTimeBrn,lightTime,deepTime;
    TextView efficiencyText,awakeText,lightText,deepText,awakeTime,sleepTime,totoalTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.report);

        init();

    }
    private void init(){
        title = (TextView)findViewById(R.id.title);
        title.setText("睡眠报告");

        effProgressBar = (ProgressBar)findViewById(R.id.efficiency_bar);
        awakeProgressBar = (ProgressBar)findViewById(R.id.awake_bar);
        lightSleepBar = (ProgressBar)findViewById(R.id.light_sleep_bar);
        deepSleepBar = (ProgressBar)findViewById(R.id.deep_sleep_bar);

        efficiencyText = (TextView)findViewById(R.id.efficiency_text);
        awakeText = (TextView)findViewById(R.id.awake_text);
        lightText = (TextView)findViewById(R.id.light_sleep_text);
        deepText = (TextView)findViewById(R.id.deep_sleep_text);
        awakeTime = (TextView)findViewById(R.id.awake_time);
        sleepTime = (TextView)findViewById(R.id.sleep_time);
        totoalTime = (TextView)findViewById(R.id.total_time);

        btn_sug = (Button) findViewById(R.id.btn_sug);
        btn_sug.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(ReportActivity.this,SuggestionActivity.class);
                ReportActivity.this.startActivity(intent);
            }
        });
        awakeTimeBrn = (Button)findViewById(R.id.awake_time2);
        lightTime = (Button)findViewById(R.id.light_sleep_time);
        deepTime = (Button)findViewById(R.id.deep_sleep_time);

        setValues();
    }
    private void setValues(){
        effProgressBar.setProgress(90);
        //effProgressBar.setProgressDrawable(getResources().getDrawable(R.drawable.pro));
        efficiencyText.setText("90%");

        totoalTime.setText("8 h 23 min");
        sleepTime.setText("7 h 32 min");
        awakeTime.setText("51 min");

        awakeTimeBrn.setText("51min");
        awakeProgressBar.setProgress(10);
        awakeText.setText("10%");

        lightTime.setText("3h15min");
        lightSleepBar.setProgress(39);
        lightText.setText("39%");

        deepTime.setText("4h17min");
        deepSleepBar.setProgress(51);
        deepText.setText("51%");

        Log.d(MLog.TAG, Work.time + "");
        Log.d(MLog.TAG,Work.wakeTime+"");
    }
}
