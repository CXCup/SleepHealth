package com.whx.sleephealth.mine;

import org.achartengine.chart.PointStyle;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYSeriesRenderer;

/**
 *  图表类,根据添加的元数据绘制曲线图
 */
public class ChartTable {
    private XYSeries mSeries;
    private XYSeriesRenderer mRender;

    public ChartTable(String title) {
        mSeries  = new XYSeries(title);
        mRender  = new XYSeriesRenderer();
        mRender.setPointStyle(PointStyle.CIRCLE);
        mRender.setFillPoints(true);
        mRender.setDisplayChartValues(true);
        mRender.setDisplayChartValuesDistance(5);
        mRender.setChartValuesTextSize(15);
    }

    public void add(double x, double y) {
        if(!ignore(x,y)) {
            mSeries.add(x, y);
        }
    }

    public void remove(int index) {
        mSeries.remove(index);
    }

    public void clear() {
        mSeries.clear();
    }

    public boolean ignore(double x, double y) {
        if( x==0.0 || y==0.0 ) {
            return true;
        }
        return false;
    }

    public XYSeries getSeries() {
        return mSeries;
    }

    public XYSeriesRenderer getRender() {
        return mRender;
    }
}
