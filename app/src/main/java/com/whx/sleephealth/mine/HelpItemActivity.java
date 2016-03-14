package com.whx.sleephealth.mine;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.whx.sleephealth.R;

/**
 * Created by whx on 2016/3/7.
 */
public class HelpItemActivity extends Activity implements View.OnClickListener{

    TextView title;
    LinearLayout helpMusic,helpArticle,helpMind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_sleep);
        title = (TextView)findViewById(R.id.title);
        title.setText("帮助睡眠");

        init();
    }
    private void init(){
        helpMusic = (LinearLayout)findViewById(R.id.help_music);
        helpMusic.setOnClickListener(this);

        helpArticle = (LinearLayout)findViewById(R.id.help_article);
        helpArticle.setOnClickListener(this);

        helpMind = (LinearLayout)findViewById(R.id.help_mind);
        helpMind.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.help_music:
                break;
            case R.id.help_article:
                break;
            case R.id.help_mind:
                break;
        }
    }
}
