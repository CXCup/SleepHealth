package com.whx.sleephealth;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by 王琦 on 2016/1/20.
 */
public class CommonUtil {
    public static final int MinYear = 2013;
    public static final int MaxYear = 2020;
    public static final String YearUnit = "年";

    public static final int MinMonth = 1;
    public static final int MaxMonth = 12;
    public static final String MonthUnit = "月";

    public static final int MinDay = 1;
    public static final int MaxDay = 31;
    public static final String DayUnit = "号";

    public static final int ChartModeYear  = 0;
    public static final int ChartModeMonth = 1;
    public static final int ChartModeDay   = 2;

    public static final int UserSexMale   = 0;
    public static final int UserSexFemale = 1;

    private static final int BACK_KEY_EXIT_TIME_PEROID = 1000;
    private static long mLastBackKeyPressedTime = 0;
    public static boolean onExitProcess(Context context) {
        if( System.currentTimeMillis() - mLastBackKeyPressedTime >  BACK_KEY_EXIT_TIME_PEROID ) {
            mLastBackKeyPressedTime = System.currentTimeMillis();
            Toast.makeText(context,context.getString(R.string.another_press_to_exit), Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}
