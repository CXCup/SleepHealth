package com.whx.sleephealth.main;

import android.content.Context;
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
import java.util.Date;
import java.util.List;
import java.util.TimerTask;

/**
 * Created by whx on 2016/3/6.
 */
public class Work1 extends TimerTask implements SensorEventListener {

    private SensorManager sensorManager;

    private Context mContext;

    public static List<Date> time,wakeTime;

    public static List<Float> datas;

    SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日    HH:mm:ss     ");

    public Work1(Context context){

        this.mContext = context;

        time = new ArrayList<>();
        wakeTime = new ArrayList<>();

        datas = new ArrayList<>();
    }

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what == 1){
                sensorManager.unregisterListener(Work1.this);
            }
        }
    };

    @Override
    public void run() {


        sensorManager = (SensorManager)mContext.getSystemService(Context.SENSOR_SERVICE);
        sensorManager.registerListener(this, sensorManager.
                getDefaultSensor(Sensor.TYPE_ACCELEROMETER), sensorManager.SENSOR_DELAY_GAME);

    }
    int i = 0;
    final float alpha = 0.8f;
    float[] gravity = new float[3];
    float[] linear_acceleration = new float[3];

    @Override
    public void onSensorChanged(SensorEvent event) {

        float[] values = event.values;

        gravity[0] = alpha * gravity[0] + (1 - alpha) * values[0];
        gravity[1] = alpha * gravity[1] + (1 - alpha) * values[1];
        gravity[2] = alpha * gravity[2] + (1 - alpha) * values[2];

        linear_acceleration[0] = values[0] - gravity[0];
        linear_acceleration[1] = values[1] - gravity[1];
        linear_acceleration[2] = values[2] - gravity[2];

        float data = (float)Math.sqrt(linear_acceleration[0] * linear_acceleration[0] +
                linear_acceleration[1] * linear_acceleration[1] + linear_acceleration[2] *
                linear_acceleration[2]);

        datas.add(data);
        Log.d(MLog.TAG,data+"");

        handler.sendEmptyMessage(1);

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
