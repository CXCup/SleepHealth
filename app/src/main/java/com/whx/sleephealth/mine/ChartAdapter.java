package com.whx.sleephealth.mine;

public abstract class ChartAdapter {
    public abstract boolean isEmpty();
    public abstract int getMinXScale();  //最小x刻度
    public abstract int getMaxXScale();  //最大x刻度
    public abstract String getXScaleUnit(); //x刻度的单位
    public abstract double getYScale(int x); //获取指定x刻度的y值
}
