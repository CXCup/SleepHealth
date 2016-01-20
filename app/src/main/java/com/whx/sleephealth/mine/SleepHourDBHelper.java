package com.whx.sleephealth.mine;

import java.text.DecimalFormat;
import java.util.List;

import com.whx.sleephealth.Configuration;
import com.whx.sleephealth.mine.DateHelper.DatePeriod;
import com.whx.sleephealth.mine.SleepHourDB.SleepHour;

public class SleepHourDBHelper {
    public static boolean isEmpty() {
        return SleepHourDB.getInstance().size()==0;
    }

    public static boolean isEmpty(int year) {
        DatePeriod period = DateHelper.getYearPeriod(year);
        String condition = SleepHourDB.getInstance().makeCondition(period.begin,period.end);
        return SleepHourDB.getInstance().size(condition)==0;
    }

    public static boolean isEmpty(int year,int month) {
        DatePeriod period = DateHelper.getMonthPeriod(year,month);
        String condition = SleepHourDB.getInstance().makeCondition(period.begin,period.end);
        return SleepHourDB.getInstance().size(condition)==0;
    }

    public static boolean isEmpty(int year,int month,int day) {
        DatePeriod period = DateHelper.getDatePeriod(year,month,day);
        String condition = SleepHourDB.getInstance().makeCondition(period.begin,period.end);
        return SleepHourDB.getInstance().size(condition)==0;
    }

    public static Double getWeightAverage(int year) {
        DatePeriod period = DateHelper.getYearPeriod(year);
        return getWeightAverage(period);
    }

    public static Double getWeightAverage(int year,int month) {
        DatePeriod period = DateHelper.getMonthPeriod(year,month);
        return getWeightAverage(period);
    }

    public static Double getWeightAverage(int year,int month,int day) {
        DatePeriod period = DateHelper.getDatePeriod(year,month,day);
        return getWeightAverage(period);
    }

    public static Double getWeightReduceThisWeek() {
        DatePeriod period = DateHelper.getWeekPeriod(DateHelper.getToday());
        String condition = SleepHourDB.getInstance().makeCondition(period.begin,period.end);
        List<SleepHour> weights = SleepHourDB.getInstance().query(condition);
        if( weights.size() < 2 ) {
            return 0.0;
        }
        return Double.valueOf(weights.get(0).value) - Double.valueOf(weights.get(weights.size()-1).value);
    }

    public static Double getWeightReduceThisMonth() {
        DatePeriod period = DateHelper.getMonthPeriod(DateHelper.getToday());
        String condition = SleepHourDB.getInstance().makeCondition(period.begin,period.end);
        List<SleepHour> weights = SleepHourDB.getInstance().query(condition);
        if( weights.size() < 2 ) {
            return 0.0;
        }
        return Double.valueOf(weights.get(0).value) - Double.valueOf(weights.get(weights.size()-1).value);
    }

    public static Double getWeightAverage(DatePeriod period) {
        String condition = SleepHourDB.getInstance().makeCondition(period.begin,period.end);
        List<SleepHour> weights = SleepHourDB.getInstance().query(condition);
        Double average = 0.0;
        if( weights.isEmpty() ) {
            return average;
        }
        for( int i=0; i<weights.size(); i++) {
            average += Double.valueOf(weights.get(i).value);
        }
        average = average/weights.size();
        DecimalFormat format = new DecimalFormat("0.00");
        average = Double.valueOf(format.format(average).toString());
        return average;
    }

    public static boolean isYestdayRecord() {
        DatePeriod period = DateHelper.getDatePeriod(DateHelper.getYestday());
        String condition = SleepHourDB.getInstance().makeCondition(period.begin,period.end);
        List<SleepHour> weights = SleepHourDB.getInstance().query(condition);
        if(weights.isEmpty()) {
            return false;
        }
        return true;
    }

    public static boolean isTodayRecord() {
        DatePeriod period = DateHelper.getDatePeriod(DateHelper.getToday());
        String condition = SleepHourDB.getInstance().makeCondition(period.begin,period.end);
        List<SleepHour> weights = SleepHourDB.getInstance().query(condition);
        if(weights.isEmpty()) {
            return false;
        }
        return true;
    }

    public static int getContinuousDays() {
        if( !isYestdayRecord() ) {
            if(isTodayRecord()) {
                Configuration.setContinousDays(1);
            }
            else {
                Configuration.setContinousDays(0);
            }
        }
        return Configuration.getContinousDays();
    }

    public static void addContinuousDays() {
        if(!isTodayRecord()) {
            int continuous = Configuration.getContinousDays();
            Configuration.setContinousDays(++continuous);
        }
    }

}
