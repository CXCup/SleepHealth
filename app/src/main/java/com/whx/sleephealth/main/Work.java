package com.whx.sleephealth.main;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.whx.sleephealth.tieshi.MLog;

import java.io.File;
import java.io.RandomAccessFile;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimerTask;

/**
 * Created by whx on 2016/3/6.
 */
public class Work extends TimerTask implements SensorEventListener {

    private SensorManager sensorManager;

    private Context mContext;

    private float[] accs = new float[2];
    private long[] times = new long[2];
    public static List<Date> time,wakeTime;

    private boolean isSleep = true;
    private int initAcc = 1;

    public static Map<String,Integer> obs0;

    public static List<Integer> obs;
    public final static int DHigh = 0;
    public final static int DLow = 1;

    SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日    HH:mm:ss     ");

    public Work(Context context){

        this.mContext = context;

        time = new ArrayList<>();
        wakeTime = new ArrayList<>();

        obs = new ArrayList<>();
        obs0 = new HashMap<>();
    }

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what == 1){
                sensorManager.unregisterListener(Work.this);
            }
        }
    };

    @Override
    public void run() {


        sensorManager = (SensorManager)mContext.getSystemService(Context.SENSOR_SERVICE);
        sensorManager.registerListener(this, sensorManager.
                getDefaultSensor(Sensor.TYPE_ACCELEROMETER), sensorManager.SENSOR_DELAY_GAME);

    }
    float acc,acc1;
    int i = 0;

    @Override
    public void onSensorChanged(SensorEvent event) {

        float[] values = event.values;

        Calendar calendar = Calendar.getInstance();
        int h = calendar.get(Calendar.HOUR_OF_DAY);
        int m = calendar.get(Calendar.MINUTE);
        int s = calendar.get(Calendar.SECOND);
        String datet = h +""+ m+s;
//        int date = Integer.parseInt(datet);

        acc1 = acc;
        acc = (float)Math.sqrt(values[0] * values[0] + values[1] * values[1] + values[2] * values[2]);

        if(Math.abs(acc - acc1)>0.153){

            obs0.put(datet, DHigh);

            obs.add(DHigh);
        }else{

            obs0.put(datet, DLow);

            obs.add(DLow);
        }


//        if(1 == initAcc){
//            accs[0] = values[0];
//            initAcc = 0;
//
//            Date curDate = new Date(System.currentTimeMillis());
//            times[0] = curDate.getTime();
//        }else {
//
//
//            try{
//                accs[1] = values[0];
//
//                File targetFile = new File(SleepTimeService.file);
//                RandomAccessFile raf = new RandomAccessFile(targetFile,"rw");
//                raf.seek(targetFile.length());
//
//                Date endDate = new Date(System.currentTimeMillis());
//                times[1] = endDate.getTime();
//
//                Log.d("--------------", "accs[0]=" + accs[0] + " accs[1]=" + accs[1]);
//
//                if(Math.abs(accs[1] - accs[0]) < 0.154f){
//                    Log.d("--------------","times[0]="+times[0]+" times[1]="+times[1]);
//
//                    if(Math.abs(times[1] - times[0]) < 180000){
//                        isSleep = true;
//                        raf.write("user is awaking".getBytes());
//                        //Log.d("----------","user is awaking");
//                    }else{
////                        isSleep = true;
////                        time = times[0]+300000;
//
//                        //Date d = new Date(times[1]);
//
//
//                        if(isSleep){
//
//                            time.add(endDate);
//
//                            String t = formatter.format(endDate);
//                            raf.write(("user is sleeping and time is " + t).getBytes());
//
//                            isSleep = false;
//                        }
//
//
//                        //Log.d("----------","user is sleeping and time is "+t);
//                    }
//                }else{
//
//
//                    //Log.d("--------------","times[0]="+times[0]+" times[1]="+times[1]);
//
//                    if(Math.abs(times[1] - times[0]) < 180000){
//                        //isSleep = true;
//                        raf.write("user is awaking".getBytes());
//                        //Log.d("----------","user is awaking");
//                    }else{
//                        isSleep = true;
//
//                        String time_s = formatter.format(endDate);
//
//                        wakeTime.add(endDate);
//                        raf.write(("user is sleeping and time is " + time_s).getBytes());
//                        //Log.d("----------","user is sleeping and time is "+time);
//                    }
//
//                    times[0] = times[1];
//                }
//
//                accs[0] = accs[1];
//            }catch (Exception e){
//                Log.d("------------",e.getMessage());
//            }
//
//        }

        handler.sendEmptyMessage(1);

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
