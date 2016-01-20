package com.whx.sleephealth.mine;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.whx.sleephealth.Configuration.OnChartModeChangeListener;
import com.whx.sleephealth.R;
import com.whx.sleephealth.Configuration;
import com.whx.sleephealth.CommonUtil;
import com.whx.sleephealth.mine.ChartAdapter;
import com.whx.sleephealth.mine.DayChartAdapter;
import com.whx.sleephealth.mine.MonthChartAdapter;
import com.whx.sleephealth.mine.ChartFolder;
import com.whx.sleephealth.mine.YearChartAdapter;
import com.whx.sleephealth.mine.SleepHourDB;
import com.whx.sleephealth.mine.SleepHourDB.OnDBDataChangeListener;
import com.whx.sleephealth.mine.ChartDateSelector;
import com.whx.sleephealth.mine.ChartDateSelector.OnDateSelectedListener;

import java.util.Calendar;

public class ChartActivity extends Activity implements OnDateSelectedListener,OnChartModeChangeListener
                                                            ,OnDBDataChangeListener{
    private TextView mNoDataTips;
    private ChartDateSelector mDateSeletor;
    private ChartFolder mChartFolder;
    private ChartAdapter mChartAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);

        mNoDataTips = (TextView) findViewById(R.id.NoDataTipView);
        LinearLayout mChartLayout = (LinearLayout)findViewById(R.id.ChartLinearLayout);
        mChartFolder = new ChartFolder(mChartLayout);

        mDateSeletor = new ChartDateSelector(findViewById(R.id.WidgetChartDateSelector));
        mDateSeletor.setChartMode(CommonUtil.ChartModeDay);
        mDateSeletor.setOnDateSelectedListener(this);

        mChartAdapter = new DayChartAdapter(mDateSeletor.getSelectYear(),mDateSeletor.getSelectMonth());
        //onDateSelected();

        Configuration.initialize(this);
        SleepHourDB.getInstance().open(getApplicationContext());
        addTestData();

        Configuration.setOnChartModeChangeListener(this);
    }

    protected void addTestData() {
        Calendar calendar = Calendar.getInstance();
        Double value = 55.0;
        for( int i=0; i<5; i++ ) {
            value = value + 2.0;
            calendar.add(Calendar.DAY_OF_MONTH, -1);
            SleepHourDB.SleepHour weight = new SleepHourDB.SleepHour();
            weight.value = String.valueOf(value);
            weight.date = calendar.getTimeInMillis();
            SleepHourDB.getInstance().insert(weight);
        }
        for( int i=5; i<10; i++ ) {
            value = value - 3.0;
            calendar.add(Calendar.DAY_OF_MONTH, -1);
            SleepHourDB.SleepHour weight = new SleepHourDB.SleepHour();
            weight.value = String.valueOf(value);
            weight.date = calendar.getTimeInMillis();
            SleepHourDB.getInstance().insert(weight);
        }
        Configuration.setContinousDays(10);
        Configuration.setFirstStart(false);
    }
    @Override
    public void onResume() {
        super.onResume();
        SleepHourDB.getInstance().setOnDBDataChangeListener(this);
    }

    @Override
    public void onPause() {
        SleepHourDB.getInstance().setOnDBDataChangeListener(null);
        super.onPause();
    }

    @Override
    public void onDateSelected() {
        switch(mDateSeletor.getChartMode()) {
            case CommonUtil.ChartModeYear:
                mChartAdapter = new YearChartAdapter();
                break;
            case CommonUtil.ChartModeMonth:
                mChartAdapter = new MonthChartAdapter(mDateSeletor.getSelectYear());
                break;
            case CommonUtil.ChartModeDay:
                mChartAdapter = new DayChartAdapter(mDateSeletor.getSelectYear(),mDateSeletor.getSelectMonth());
                break;
            default:
                break;
        }
        mChartFolder.setChartAdapter(mChartAdapter);
        onChartViewUpdate();
    }

    @Override
    public void onDBDataChanged() {
        onChartViewUpdate();
    }

    @Override
    public void onChartModeChanged(int chartmode) {
        mDateSeletor.setChartMode(chartmode);
        onDateSelected();
    }

    protected void onChartViewUpdate() {
        mChartFolder.invalidate();
        mNoDataTips.setVisibility(mChartAdapter.isEmpty()?View.VISIBLE:View.GONE);
    }

//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if( keyCode == KeyEvent.KEYCODE_BACK ) {
//            if( !CommonUtil.onExitProcess(this) ) {
//                return true;
//            }
//        }
//        return super.onKeyDown(keyCode, event);
//    }

}
