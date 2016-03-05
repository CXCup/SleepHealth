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
import android.widget.DigitalClock;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.whx.sleephealth.R;
import com.whx.sleephealth.tieshi.MLog;

import java.util.Date;


/**
 * Created by whx on 2016/3/3.
 */
public class CloseAlarmActivity extends Activity{

    private LinearLayout layout1,layout2;
    private TextView alarmText;
    private GestureDetector gestureDetector;
    private WindowManager windowManager;
    RelativeLayout.LayoutParams layoutParams1,layoutParams2;
    float layout1Y,layout2Y;
    private DigitalClock digitalClock;
    private RelativeLayout layout;
    Point p;

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
            Log.d(MLog.TAG,"hahaha--"+e.getY());
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
                a = a - 0.009f;

                if(a<=0){
                    a= 0;
                }
                alarmText.setAlpha(b);
                b = b - 0.2f;
                if(b<=0){
                    b=0;
                }
                setLayoutY(layout2, (int) (layout2.getY() - distanceY));
                layout1.setY(layout1.getY() - distanceY / 2);

            }else{//向下滑
                if(layout1.getY()< layout1Y){
                    layout1.setY(layout1.getY() - distanceY / 2);

                    digitalClock.setAlpha(a);
                    a = a + 0.009f;
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
                    setLayoutY(layout2, (int) (layout2.getY() - distanceY));
                }
            }


            //Log.d(MLog.TAG, "p.y = " + (p.y) + " y = " + (e2.getY() - e1.getY()));
            if((e1.getY() - e2.getY())>(p.y*0.7)){
                finishActivity();
            }else{
//                layout2.setTranslationY(layout2Y);
//                //layout2.setAnimation();
//                layout2.setPivotY(layout2Y);
//                layout2.setRotationY(layout2Y);
//                layout2.setScrollY((int)layout2Y);
//                layout2.setY(layout2Y);

            }
            return false;
        }

        @Override
        public void onLongPress(MotionEvent e) {

        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            //Log.d(MLog.TAG, "Fx = " + velocityX + " Fy = " + velocityY);
            if((0-velocityY)>2000){
                //Log.d(MLog.TAG, e2.getY() + "");

                for(float i = e2.getY();i>0;i = i - 1f){
//                    Message msg = new Message();
//                    Bundle bundle = new Bundle();
//                    bundle.putFloat("y",i);
//                    msg.setData(bundle);
//                    mhandler.sendMessage(msg);
                    setLayoutY(layout2, (int)i);

                    Log.d(MLog.TAG,""+i);
                    try{
                        Thread.sleep(10);
                    }catch (Exception e){

                    }
                }
                finishActivity();
            }
            return true;
        }
    }

    private void finishActivity(){

        stopService(MainFragment.intent);

        Date date = new Date();
        long stopTime = date.getTime();

        if(stopTime - MainFragment.startTime < 180000){
            Toast.makeText(this,"时间过短",Toast.LENGTH_SHORT).show();
        }else{

        }

        finish();
    }
    Handler mhandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {

            float m = msg.getData().getFloat("y");
            layout2.setY(m);
            layout.invalidate();

        }
    };

}
