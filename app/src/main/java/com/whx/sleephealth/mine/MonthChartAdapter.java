package com.whx.sleephealth.mine;

/**
 * Created by 王琦 on 2016/1/20.
 */
import com.whx.sleephealth.CommonUtil;
import com.whx.sleephealth.mine.SleepHourDBHelper;

public class MonthChartAdapter extends ChartAdapter{
    private int mChartYear;

    public MonthChartAdapter(int year) {
        mChartYear = year;
    }

    @Override
    public boolean isEmpty() {
        return SleepHourDBHelper.isEmpty(mChartYear);
    }

    @Override
    public double getYScale(int month) {
        return SleepHourDBHelper.getWeightAverage(mChartYear, month-1);
    }

    @Override
    public int getMinXScale() {
        return CommonUtil.MinMonth;
    }

    @Override
    public int getMaxXScale() {
        return CommonUtil.MaxMonth;
    }

    @Override
    public String getXScaleUnit() {
        return CommonUtil.MonthUnit;
    }
}
