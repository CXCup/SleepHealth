package com.whx.sleephealth.main;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.whx.sleephealth.HttpUtil;
import com.whx.sleephealth.HttpUtils;
import com.whx.sleephealth.R;
import com.whx.sleephealth.tieshi.MLog;

import org.apache.http.Header;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by whx on 2016/3/5.
 */
public class ReportActivity extends Activity{

    private TextView title;
    private ProgressBar effProgressBar,awakeProgressBar,lightSleepBar,deepSleepBar;
    private Button btn_sug,awakeTimeBrn,lightTime,deepTime;
    private TextView efficiencyText,awakeText,lightText,deepText,awakeTime,sleepTime,totoalTime;

    private int eff,light_min,deep_min,awake_min,total;
    SQLHelper helper = new SQLHelper(this,"my_record.db3",1);

    private int step = 30;

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


                Map<String,String> params = new HashMap<>();
                params.put("sleepTime",(light_min+deep_min)+"");
                params.put("lightTime", light_min + "");
                params.put("deepTime", deep_min + "");

                //Log.d(MLog.TAG,(light_min+deep_min)+"");
                String url;
                try{
                   url =  HttpUtil.postRequest("http://114.215.84.64:8080/SleepHealth/suggestion.php",params);
                }catch (Exception e){
                    url = "what the fuck";
                    Log.d(MLog.TAG,e.getMessage());
                }

                Intent intent = new Intent();
                intent.setClass(ReportActivity.this,SuggestionActivity.class);
                intent.putExtra("param",url);
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
        }else if(getIntent().getFlags() == 2){

            Cursor cursor = helper.getReadableDatabase().rawQuery("select * from record where" +
                    " date= ? and start_time=?", new String[]{getIntent().getStringExtra("date"),
                    getIntent().getLongExtra("start", 1) + ""});

            cursor.moveToNext();

            //Log.d(MLog.TAG,cursor+"");

            long start = cursor.getLong(2);
            long end = cursor.getLong(3);
            total = cursor.getInt(4);
            eff = cursor.getInt(5);
            int sleep = cursor.getInt(6);
            awake_min = cursor.getInt(7);
            light_min = cursor.getInt(8);
            deep_min = cursor.getInt(9);

            setValues(start,end,total,sleep,awake_min,eff,light_min,deep_min);
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

        if(sleep_min>60){
            sleepTime.setText(sleep_min / 60 + " h " + sleep_min % 60 + " min");
        }else{
            sleepTime.setText(sleep_min+ " min");
        }

        if(total_min>60){
            awakeTime.setText(total_min/60 +" h "+total_min%60+" min");
        }else{
            awakeTime.setText(total_min+" min");
        }

        if(awake_min>60){
            awakeTimeBrn.setText(awake_min/60 +"h"+awake_min%60+"min");
        }else{
            awakeTimeBrn.setText(awake_min+"min");
        }

        awakeProgressBar.setProgress((int) ((float) awake_min / total_min * 100));
        awakeText.setText((int) ((float) awake_min / total_min * 100) + "%");

        if(light>60){
            lightTime.setText(light/60 +"h"+light%60+"min");
        }else{
            lightTime.setText(light+"min");
        }
        lightSleepBar.setProgress((int) ((float) light / total_min * 100));
        lightText.setText((int) ((float) light/total_min*100)+"%");

        if(deep>60){
            deepTime.setText(deep/60 +"h"+deep%60+"min");
        }else{
            deepTime.setText(deep+"min");
        }
        deepSleepBar.setProgress((int)((float)deep/total_min*100));
        deepText.setText((int) ((float) deep/total_min*100)+"%");

    }

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

        int[] sub;
        for(int i = 0;i<s.length;i+=step){
            sub = Arrays.copyOfRange(s,i,i+step);
            switch (state(sub)){
                case 0:
                    deep_min++;
                    break;
                case 1:
                    light_min++;
                    break;
                case 2:
                    awake_min++;
                    break;
            }
            Log.d(MLog.TAG,sub.length+"");
        }
        total = deep_min+light_min+awake_min;
        eff = (int)((float)(deep_min+light_min)/total*100);
//        sleep_min = sleepCount/step;
//        awake_min = wakeCount/step;

        Log.d(MLog.TAG, "sleep is " + eff + "%");

        handler.sendEmptyMessage(1);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM月dd日");
        Date d1 = new Date(MainFragment.startTime);
        String date = simpleDateFormat.format(d1);

        //保存至数据库
        helper.insert(helper.getReadableDatabase(),date,MainFragment.startTime,
                CloseAlarmActivity.stopTime,total,eff,deep_min+light_min,awake_min,
                light_min,deep_min);
    }

    private int state(int[] arry){
        int num_1=0,num_0=0;
        for(int i:arry){
            if(1 == i){
                num_1++;
            }else{
                num_0++;
            }
        }
        if(num_1>=5){
            return 2;
        }else if(num_1<5&&num_1>0){
            return 1;
        }else{
            return 0;
        }
    }
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(1 == msg.what){
                setValues(MainFragment.startTime,CloseAlarmActivity.stopTime,total,
                        deep_min+light_min,awake_min,eff,light_min,deep_min);
            }
        }
    };
    @Override
    protected void onDestroy() {

        if(helper != null){
            helper.close();
        }
        //unregisterReceiver(receiver);
        super.onDestroy();
    }
}
