package com.whx.sleephealth.main;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by whx on 2016/3/23.
 */
public class SQLHelper extends SQLiteOpenHelper{

    private final String CREATE_TABLE_SQL = "create table record(_id integer primary key "+
            "autoincrement, date , start_time integer,end_time integer,total_time integer,"+
            "eff integer,sleep_time integer,wake_time integer,light_time integer,deep_time integer)";

    public SQLHelper(Context context,String name,int version){
        super(context, name, null, version);

    }
    @Override
    public void onCreate(SQLiteDatabase db) {

        //第一次使用数据库时建表
        db.execSQL(CREATE_TABLE_SQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public boolean insert(SQLiteDatabase db,String date,long s,long e,int t,int eff,int sl,int w,int l,int d){
//        db.execSQL("insert into record values("+date+","+s+","+e+","+t+","+eff+","+
//        sl+","+w+","+l+","+d+");");

        ContentValues values = new ContentValues();
        values.put("date",date);
        values.put("start_time",s);
        values.put("end_time",e);
        values.put("total_time",t);
        values.put("eff",eff);
        values.put("sleep_time",sl);
        values.put("wake_time",w);
        values.put("light_time",l);
        values.put("deep_time",d);

        if(db.insert("record",null,values) != -1){
            return true;
        }else{
            return false;
        }
    }
    public boolean delete(SQLiteDatabase db,String date,long start){
        if(0 != db.delete("record","date = ? and start_time = ?",new String[]{date,start+""})){
            return true;
        }else{
            return false;
        }
    }
}
