package com.whx.sleephealth.main;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by whx on 2016/3/23.
 */
public class SQLHelper extends SQLiteOpenHelper{

    public SQLHelper(Context context,String name,int version){
        super(context,name,null,version);

    }
    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
