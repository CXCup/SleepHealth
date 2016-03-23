package com.whx.sleephealth.adapter;

/**
 * Created by whx on 2016/3/23.
 */
public class RecordListItem {
    private String date;
    private String start_time;
    private String timelong;
    private String eff;

    public String getTimelong() {
        return timelong;
    }
    public void setTimelong(String timelong) {
        this.timelong = timelong;
    }
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getEff() {
        return eff;
    }

    public void setEff(String eff) {
        this.eff = eff;
    }

}
