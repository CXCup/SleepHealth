package com.whx.sleephealth.mine;

/**
 * Created by 王琦 on 2016/1/20.
 */

import com.whx.sleephealth.CommonUtil;
import com.whx.sleephealth.mine.SleepHourDBHelper;
public class YearChartAdapter extends ChartAdapter{
    @Override
    public boolean isEmpty() {
        return SleepHourDBHelper.isEmpty();
    }

    @Override
    public double getYScale(int year) {
        return SleepHourDBHelper.getWeightAverage(year);
    }

    @Override
    public int getMinXScale() {
        return CommonUtil.MinYear;
    }

    @Override
    public int getMaxXScale() {
        return CommonUtil.MaxYear;
    }

    @Override
    public String getXScaleUnit() {
        return CommonUtil.YearUnit;
    }
}
