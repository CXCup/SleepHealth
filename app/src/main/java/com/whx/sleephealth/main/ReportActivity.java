package com.whx.sleephealth.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.whx.sleephealth.R;

/**
 * Created by whx on 2016/3/5.
 */
public class ReportActivity extends Activity{

    TextView title;
    ProgressBar progressBar;
    Button btn_sug;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.report);

        init();

        btn_sug = (Button) findViewById(R.id.btn_sug);
        btn_sug.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(ReportActivity.this,SuggestionActivity.class);
                ReportActivity.this.startActivity(intent);
            }
        });
    }
    private void init(){
        title = (TextView)findViewById(R.id.title);
        title.setText("睡眠报告");

        progressBar = (ProgressBar)findViewById(R.id.efficiency_bar);
        progressBar.setProgress(80);
    }
}
