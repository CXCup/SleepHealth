package com.whx.sleephealth.main;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.whx.sleephealth.tieshi.MLog;

/**
 * Created by whx on 2016/3/3.
 */
public class SleepTimeService extends Service{
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        Log.d(MLog.TAG,"service started");
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        Log.d(MLog.TAG,"service stopped");
        super.onDestroy();
    }
}
