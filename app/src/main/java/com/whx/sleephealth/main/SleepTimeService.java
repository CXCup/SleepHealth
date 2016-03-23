package com.whx.sleephealth.main;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.os.IBinder;
import android.os.PowerManager;
import android.support.annotation.Nullable;
import android.util.Log;

import com.whx.sleephealth.tieshi.MLog;

import java.util.Timer;

/**
 * Created by whx on 2016/3/3.
 */
public class SleepTimeService extends Service{

    Timer timer;
    PowerManager.WakeLock mWakeLock;// 电源锁

    public static String file;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {

        super.onCreate();
        timer = new Timer();
        //每隔两秒执行一次
        timer.schedule(new Work(this),0,60000);

        try {
            file = Environment.getExternalStorageDirectory().getCanonicalPath() + "/test.txt";
        }catch (Exception e){

        }
        acquireWakeLock();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Log.d(MLog.TAG, "service start");

        return START_STICKY;
    }

    @Override
    public void onDestroy() {

        //datas = Work.datas;
        timer.cancel();

//        sendBroadcast(intent);

//        for(int i = 0;i<datas.size();i++){
//            Log.d("------------",datas.get(i).getDate()+"--"+datas.get(i).getValue());
//        }

        releaseWakeLock();

        Log.d(MLog.TAG,"service stopped");
        super.onDestroy();
    }

    /**
     * 申请电源锁
     */
    private void acquireWakeLock() {
        if (null == mWakeLock) {
            PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
            mWakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK
                    | PowerManager.ON_AFTER_RELEASE, "DService");
            if (null != mWakeLock) {
                mWakeLock.acquire();
            }
        }
    }

    /**
     * 释放电源锁
     */
    private void releaseWakeLock() {
        if (null != mWakeLock) {
            mWakeLock.release();
            mWakeLock = null;
        }
    }
}
