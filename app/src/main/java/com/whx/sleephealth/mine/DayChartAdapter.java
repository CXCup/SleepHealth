package com.whx.sleephealth.mine;

import com.whx.sleephealth.CommonUtil;
import com.whx.sleephealth.mine.SleepHourDBHelper;

public class DayChartAdapter extends ChartAdapter{
    private int mChartYear;
    private int mChartMonth;

    public DayChartAdapter(int year,int month) {
        mChartYear  = year;
        mChartMonth = month;
    }

    @Override
    public boolean isEmpty() {
        return SleepHourDBHelper.isEmpty(mChartYear, mChartMonth);
    }

    @Override
    public double getYScale(int day) {
        return SleepHourDBHelper.getWeightAverage(mChartYear,mChartMonth,day);
    }

    @Override
    public int getMinXScale() {
        return CommonUtil.MinDay;
    }

    @Override
    public int getMaxXScale() {
        return CommonUtil.MaxDay;
    }

    @Override
    public String getXScaleUnit() {
        return CommonUtil.DayUnit;
    }
}
