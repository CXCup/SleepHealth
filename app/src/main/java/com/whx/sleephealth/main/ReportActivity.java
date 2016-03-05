package com.whx.sleephealth.main;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.whx.sleephealth.R;

/**
 * Created by whx on 2016/3/5.
 */
public class ReportActivity extends Activity{

    TextView title;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.report);

        init();
    }
    private void init(){
        title = (TextView)findViewById(R.id.title);
        title.setText("睡眠报告");

        progressBar = (ProgressBar)findViewById(R.id.efficiency_bar);
        progressBar.setProgress(80);
    }
}
