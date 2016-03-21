package com.whx.sleephealth.main;

import android.app.Activity;
import android.content.Intent;
import android.gesture.GestureOverlayView;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.DigitalClock;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.whx.sleephealth.R;
import com.whx.sleephealth.tieshi.MLog;

import java.io.File;
import java.io.RandomAccessFile;
import java.util.Date;
import java.util.Set;


/**
 * Created by whx on 2016/3/3.
 */
public class CloseAlarmActivity extends Activity{

    private LinearLayout layout1,layout2;
    private TextView alarmText;
    private GestureDetector gestureDetector;
    private WindowManager windowManager;
    RelativeLayout.LayoutParams layoutParams1,layoutParams2;
    private float layout1Y,layout2Y;
    private DigitalClock digitalClock;
    private RelativeLayout layout;
    private Point p;

    public static long stopTime;
    boolean flag;

    //0是睡，1是醒
    int[] states = {0,1};
    int[] obs1;

    double[] start_p = {0,1};

    double[][] trans_p = {
            {0.8431,0.1569},
            {0.4380,0.5620}
    };
    double[][] emit_p = {
            {0.2,0.8},
            {0.8,0.2}
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.close_alarm);

        init();

        p = new Point();
        windowManager.getDefaultDisplay().getSize(p);
    }
    private void init(){

        windowManager = getWindowManager();

        gestureDetector = new GestureDetector(this,new GestureListener());

        layout = (RelativeLayout)findViewById(R.id.c_r);
        layout1 = (LinearLayout)findViewById(R.id.c_l1);
        layout2 = (LinearLayout)findViewById(R.id.c_l2);

        digitalClock = (DigitalClock)findViewById(R.id.show_time);

        alarmText = (TextView)findViewById(R.id.c_alarm_text);
        alarmText.setText(MainFragment.AlarmText);

//        layoutParams1 = new RelativeLayout.LayoutParams()
//        layoutParams2 = layout2.getLayoutParams();

        flag = true;
    }

    public static void setLayoutY(View view,int y)
    {
        ViewGroup.MarginLayoutParams margin=new ViewGroup.MarginLayoutParams(view.getLayoutParams());
        margin.setMargins(margin.leftMargin,y, margin.rightMargin, y+margin.height);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(margin);
        view.setLayoutParams(layoutParams);
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
//        switch (event.getAction() & MotionEvent.ACTION_MASK) {
//            case MotionEvent.ACTION_UP:
//                Log.d(MLog.TAG,"hahaha"+event.getY());
//                break;
//        }
        return gestureDetector.onTouchEvent(event);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(KeyEvent.KEYCODE_BACK == keyCode) {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addCategory(Intent.CATEGORY_HOME);
            startActivity(intent);
            return true;
        }
        return false;
    }
//    @Override
//    public boolean onTouch(View v, MotionEvent event) {
//        return gestureDetector.onTouchEvent(event);
//    }


    private class GestureListener implements GestureDetector.OnGestureListener{
        @Override
        public boolean onDown(MotionEvent e) {
            return false;
        }

        @Override
        public void onShowPress(MotionEvent e) {

        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            //Log.d(MLog.TAG,"hahaha--"+e.getY());
            return false;
        }
        float a = 1.0f,b = 1.0f;
        int f = 1;
        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {

            if(1 == f){
                layout1Y = layout1.getY();
                layout2Y = layout2.getY();

                f = 0;
            }


//            Log.d(MLog.TAG, "y1 = " + e1.getY() + " y2 = " + e2.getY());


            //layout2.setY(layout2.getY() - distanceY);

            //向上滑
            if(distanceY>0){
                digitalClock.setAlpha(a);
                a = a - 0.01f;

                if(a<=0){
                    a= 0;
                }
                alarmText.setAlpha(b);
                b = b - 0.2f;
                if(b<=0){
                    b=0;
                }
                layout2.setY(layout2.getY() - distanceY);
                layout1.setY(layout1.getY() - distanceY / 2);

            }else{//向下滑
                if(layout1.getY()< layout1Y){
                    layout1.setY(layout1.getY() - distanceY / 2);

                    digitalClock.setAlpha(a);
                    a = a + 0.05f;
                    if(a>1){
                        a = 1;
                    }

                    alarmText.setAlpha(b);
                    b = b + 0.2f;
                    if(b>1){
                        b=1;
                    }
                }
                if(layout2.getY()<layout2Y){
                    layout2.setY(layout2.getY() - distanceY);
                }
            }


            //Log.d(MLog.TAG, "p.y = " + (p.y) + " y = " + (e2.getY() - e1.getY()));
            if((e1.getY() - e2.getY())>(p.y*0.6)){
                if(flag){
                    test();
                    finishActivity();
                }
                return true;
            }else{
//                layout2.setTranslationY(layout2Y);
//                //layout2.setAnimation();
//                layout2.setPivotY(layout2Y);
//                layout2.setRotationY(layout2Y);
//                layout2.setScrollY((int)layout2Y);
//                layout2.setY(layout2Y);

            }
            return true;
        }

        @Override
        public void onLongPress(MotionEvent e) {

        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            //Log.d(MLog.TAG, "Fx = " + velocityX + " Fy = " + velocityY);
            if((0-velocityY)>2000){
                //Log.d(MLog.TAG, e2.getY() + "");

                AnimationSet animationSet = new AnimationSet(true);
                TranslateAnimation translateAnimation = new TranslateAnimation(Animation.ABSOLUTE,layout2.getX(),
                        Animation.ABSOLUTE,layout2.getX(),Animation.ABSOLUTE,layout2Y,Animation.ABSOLUTE,10f);
                translateAnimation.setDuration(3000);
                animationSet.addAnimation(translateAnimation);

                layout2.startAnimation(animationSet);

//                final float e = e2.getY();
//                new Thread(){
//                    @Override
//                    public void run() {
//                        for(float i = e;i>0;i = i - 0.1f) {
//                            Message msg = new Message();
//                            Bundle bundle = new Bundle();
//                            bundle.putFloat("y", i);
//                            msg.setData(bundle);
//                            mhandler.sendMessage(msg);
//                        }
//
//                    }
//                }.start();

                if(flag){
                    test();
                    finishActivity();
                }
            }
            return true;
        }
    }

    private void finishActivity(){

        stopService(MainFragment.intent);

        Date date = new Date();
        stopTime = date.getTime();

        flag = false;

        if(stopTime - MainFragment.startTime < 180000){
            Toast.makeText(this,"时间过短,已取消此次活动",Toast.LENGTH_SHORT).show();

//            Intent intent = new Intent(this,ReportActivity.class);
//            startActivity(intent);

            finish();
        }else{
            Intent intent = new Intent(this,ReportActivity.class);
            startActivity(intent);

            finish();
        }

    }
    Handler mhandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {

            float m = msg.getData().getFloat("y");
            layout2.setY(m);
            layout.invalidate();

        }
    };

    int sleepCount=0,wakeCount=0;

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
        Log.d(MLog.TAG,"sleep is "+(int)((float)sleepCount/s.length*100)+"%");
    }
    public void method(float[] accurs){
        int i,j,len;
        len = accurs.length;

        float[] amods = new float[len];
        i = 0;
        while (i<len){
            if(i>3 && i<len-5){
                amods[i] = 0.04f*accurs[i-4] + 0.04f*accurs[i-3] + 0.2f*accurs[i-2] + 0.2f*accurs[i-1] +
                        2f*accurs[i] + 0.2f*accurs[i+1] + 0.2f*accurs[i+2] + 0.04f*accurs[i+3] +
                        0.04f*accurs[i+4];
            }else{
                amods[i] = accurs[i];
            }
            i++;
        }
        for(int k = 0;k<amods.length;k++){
            Log.d(MLog.TAG,"amods --> "+amods[k]);
        }
    }
}
