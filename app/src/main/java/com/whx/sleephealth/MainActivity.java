package com.whx.sleephealth;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.whx.sleephealth.adapter.ViewPagerAdapter;
import com.whx.sleephealth.main.MainFragment;
import com.whx.sleephealth.mine.MineFragment;
import com.whx.sleephealth.tieshi.TieshiFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends FragmentActivity {

    private ViewPager viewPager;
    private ViewPagerAdapter viewPagerAdapter;

    private LinearLayout m_home,m_mine,m_tieshi;
    private ImageView im_home,im_mine,im_tieshi;
    private TextView txt_home,txt_mine,txt_tieshi;

    private ImageView tabline;

    private int tablineWidth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initContent();
        setTablineWidth();
    }

    private void setTablineWidth(){
        Display display = getWindow().getWindowManager().getDefaultDisplay();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        display.getMetrics(displayMetrics);

        /**
         * 宽度设为屏幕宽度的1/3
         */
        tablineWidth = displayMetrics.widthPixels / 3;

        ViewGroup.LayoutParams lp = tabline.getLayoutParams();
        lp.width = tablineWidth;
        tabline.setLayoutParams(lp);
    }

    private void initContent(){

        viewPager = (ViewPager)findViewById(R.id.view_pager);

        m_home = (LinearLayout)findViewById(R.id.l_home);
        m_mine = (LinearLayout)findViewById(R.id.l_mine);
        m_tieshi = (LinearLayout)findViewById(R.id.l_tieshi);

        im_home = (ImageView)findViewById(R.id.im_home);
        im_mine = (ImageView)findViewById(R.id.im_mine);
        im_tieshi = (ImageView)findViewById(R.id.im_tieshi);

        txt_home = (TextView)findViewById(R.id.txt_home);
        txt_mine = (TextView)findViewById(R.id.txt_mine);
        txt_tieshi = (TextView)findViewById(R.id.txt_tieshi);

        tabline = (ImageView)findViewById(R.id.tabline);

        List<Fragment> list = new ArrayList<>();
        list.add(new MainFragment());
        list.add(new MineFragment());
        list.add(new TieshiFragment());

        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(),list);

        viewPager.setAdapter(viewPagerAdapter);

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                LinearLayout.LayoutParams lp = (android.widget.LinearLayout.LayoutParams)tabline.
                        getLayoutParams();
                lp.leftMargin = (int)((position+positionOffset)*tablineWidth);

                Log.i("-----------------", lp.leftMargin + "");

                tabline.setLayoutParams(lp);
            }

            @Override
            public void onPageSelected(int position) {

                resetTextViewColor();
                resetImageViewColor();

                switch (position){
                    case 0:
                        txt_home.setTextColor(Color.parseColor("#7badea"));
                        //im_home.setImageResource(R.mipmap.home);
                        break;
                    case 1:
                        txt_mine.setTextColor(Color.parseColor("#7badea"));
                        im_mine.setImageResource(R.mipmap.me);
                        break;
                    case 2:
                        txt_tieshi.setTextColor(Color.parseColor("#7badea"));
                        //im_tieshi.setImageResource(R.mipmap.tieshi);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        BottomListener listener = new BottomListener();

        m_mine.setOnClickListener(listener);
        m_home.setOnClickListener(listener);
        m_tieshi.setOnClickListener(listener);

    }

    class BottomListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.l_home:
                    viewPager.setCurrentItem(0,true);
                    break;
                case R.id.l_mine:
                    viewPager.setCurrentItem(1,true);
                    break;
                case R.id.l_tieshi:
                    viewPager.setCurrentItem(2,true);
                    break;
            }
        }
    }
    private void resetTextViewColor(){

        txt_home.setTextColor(Color.parseColor("#A6A6A6"));
        txt_mine.setTextColor(Color.parseColor("#A6A6A6"));
        txt_tieshi.setTextColor(Color.parseColor("#A6A6A6"));
    }

    private void resetImageViewColor(){

        im_mine.setImageResource(R.mipmap.me1);
        //im_home.setImageResource(R.mipmap.home1);
        //im_tieshi.setImageResource(R.mipmap.tieshi1);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
