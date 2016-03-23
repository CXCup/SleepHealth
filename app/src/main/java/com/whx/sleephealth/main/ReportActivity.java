package com.whx.sleephealth.main;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.whx.sleephealth.R;
import com.whx.sleephealth.tieshi.MLog;

import java.io.File;
import java.io.RandomAccessFile;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

/**
 * Created by whx on 2016/3/5.
 */
public class ReportActivity extends Activity{

    private TextView title;
    private ProgressBar effProgressBar,awakeProgressBar,lightSleepBar,deepSleepBar;
    private Button btn_sug,awakeTimeBrn,lightTime,deepTime;
    private TextView efficiencyText,awakeText,lightText,deepText,awakeTime,sleepTime,totoalTime;

    private int eff,sleep_min,awake_min,total;

    private int step = 1;

    //0是睡，1是醒
    private int[] states = {0,1};
    private int[] obs1;

    private double[] start_p = {0,1};

    private double[][] trans_p = {
            {0.7839,0.2161},
            {0.2357,0.7643}
    };
    private double[][] emit_p = {
            {0.2,0.8},
            {0.8,0.2}
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.report);

//        receiver = new MReceiver();
//        IntentFilter intentFilter = new IntentFilter();
//        intentFilter.addAction("android.intent.action.MReceiver");
//        registerReceiver(receiver,intentFilter);

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

        if(getIntent().getFlags() == 1){
            new Thread(){
                @Override
                public void run() {
                    test();
                }
            }.start();
        }

    }

    private void setValues(long start,long end,int total_min,int sleep_min,int awake_min,int eff,int light,int deep){
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");

        effProgressBar.setProgress(eff);
        //effProgressBar.setProgressDrawable(getResources().getDrawable(R.drawable.pro));
        efficiencyText.setText(eff+"%");
        if(eff>=60){
            efficiencyText.setTextColor(Color.parseColor("#42ae3a"));
        }

        Date d1 = new Date(start);
        String s1 = formatter.format(d1);
        Date d2 = new Date(end);
        String s2 = formatter.format(d2);

        totoalTime.setText(s1+"-"+s2);

        sleepTime.setText(sleep_min / 60 + " h " + sleep_min % 60 + " min");
        awakeTime.setText(awake_min/60 +" h "+awake_min%60+" min");

        if(awake_min>60){
            awakeTimeBrn.setText(awake_min/60 +"h"+awake_min%60+"min");
        }else{
            awakeTimeBrn.setText(awake_min%60+"min");
        }
        awakeProgressBar.setProgress((int) ((float) awake_min / total_min * 100));
        awakeText.setText((int) ((float) awake_min / total_min * 100) + "%");

        if(light>60){
            lightTime.setText(light/60 +"h"+light%60+"min");
        }else{
            lightTime.setText(light%60+"min");
        }
        lightSleepBar.setProgress((int) ((float) light / total_min * 100));
        lightText.setText((int) ((float) light/total_min*100)+"%");

        if(deep>60){
            deepTime.setText(deep/60 +"h"+deep%60+"min");
        }else{
            deepTime.setText(deep%60+"min");
        }
        deepSleepBar.setProgress((int)((float)deep/total_min*100));
        deepText.setText((int) ((float) deep/total_min*100)+"%");

    }

    int sleepCount=0,wakeCount=0;

//    private class MReceiver extends BroadcastReceiver{
//        @Override
//        public void onReceive(Context context, Intent intent) {
//
//        }
//    }
    private void test(){
        obs1 = new int[Work.obs.size()];
        for(int i=0;i<Work.obs.size();i++){
//            Log.d(MLog.TAG,""+Work.obs.get(i));

            obs1[i] = Work.obs.get(i);
        }
        int[] s;
        s = Viterbi.compute(obs1,states,start_p,trans_p,emit_p);

        int j = 0;
        Set<String> keys = Work.obs0.keySet();

        for (String ss :keys){
            Work.obs0.put(ss,s[j++]);
        }

        //将结果写入到文件
        try{
            File file = new File(SleepTimeService.file);
            RandomAccessFile raf = new RandomAccessFile(file,"rw");
            raf.seek(file.length());

            raf.writeChars(Work.obs0+"");

        }catch (Exception e){
            Log.d(MLog.TAG,e.getMessage());
        }
        //打印结果
        for (int i : s){
            if(0 == i){
                sleepCount ++;
            }else{
                wakeCount ++;
            }
        }
        total = s.length;
        eff = (int)((float)sleepCount/total*100);
        sleep_min = sleepCount/step;
        awake_min = wakeCount/step;

        Log.d(MLog.TAG,"sleep is "+eff+"%");

        handler.sendEmptyMessage(1);


    }

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(1 == msg.what){
                setValues(MainFragment.startTime,CloseAlarmActivity.stopTime,(sleep_min+awake_min),
                        sleep_min,awake_min,eff,(int)(total*0.6),(int)(total*0.3));
            }
        }
    };
    @Override
    protected void onDestroy() {

        //unregisterReceiver(receiver);
        super.onDestroy();
    }
}
