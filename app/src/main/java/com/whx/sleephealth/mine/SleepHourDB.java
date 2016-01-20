package com.whx.sleephealth.mine;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class SleepHourDB {
    protected static final String TAG = SleepHourDB.class.getSimpleName();

    protected static final int DB_VERSION = 1;
    protected static final String DB_NAME = "sleephour_db";
    protected static final String DB_PRIMARY_KEY = "_id";
    protected static final String DB_TABLE_NAME = "sleephour";

    protected static final String DB_TABLE_COLUMN_WEIGHT = "sleephour";
    protected static final String DB_TABLE_COLUMN_DATE   = "record_date";

    protected static final String DB_DEFAULT_ORDERBY = DB_TABLE_COLUMN_DATE + " DESC";

    protected DatabaseHelper mDBHelper;
    protected SQLiteDatabase mDB;
    protected OnDBDataChangeListener mDBDataChangeListener;

    protected static final SleepHourDB mInstance = new SleepHourDB();

    private final String DB_TABLE_CREATE_SQL = "create table " + DB_TABLE_NAME + " ( _id integer primary key autoincrement, "
            + DB_TABLE_COLUMN_WEIGHT + " text not null, "
            + DB_TABLE_COLUMN_DATE + " integer );";

    public interface OnDBDataChangeListener {
        public void onDBDataChanged();
    }

    public static class SleepHour {
        public long key = -1;
        public String value;
        public long date;
    }

    protected class DatabaseHelper extends SQLiteOpenHelper {
        public DatabaseHelper(Context context,String dbName, int dbVersion ) {
            super(context, dbName , null, dbVersion);
        }
        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(DB_TABLE_CREATE_SQL);
        }
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + DB_TABLE_NAME);
            onCreate(db);
        }
    }

    private SleepHourDB() {};

    public static SleepHourDB getInstance() {
        return mInstance;
    }

    public void setOnDBDataChangeListener(OnDBDataChangeListener listener) {
        mDBDataChangeListener = listener;
        if(listener!=null) {
            listener.onDBDataChanged();
        }
    }

    public boolean open(Context context) {
        try {
            mDBHelper = new DatabaseHelper(context,DB_NAME,DB_VERSION);
            mDB = mDBHelper.getWritableDatabase();
        }
        catch( SQLException e ) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public void close() {
        mDB.close();
        mDBHelper.close();
    }

    public String makeCondition(int position) {
        long key = getkey(position,null);
        if( key == -1 ) {
            return null;
        }
        String condition = DB_PRIMARY_KEY + "=" + "\'" + key + "\'";
        return condition;
    }

    public String makeCondition(int position,String condition) {
        long key = getkey(position,condition);
        if( key == -1 ) {
            return null;
        }
        String conditions = DB_PRIMARY_KEY + "=" + "\'" + key + "\'";
        return conditions;
    }

    public String makeCondition(long startdate,long enddate) {
        String condition = DB_TABLE_COLUMN_DATE + " >= " + startdate + " AND ";
        condition += (DB_TABLE_COLUMN_DATE + " < " + enddate );
        return condition;
    }

    public String makeCondition(String condition1,String condition2) {
        String condition = condition1 + " AND " + condition2;
        return condition;
    }

    public int size() {
        return size(null);
    }

    public int size(String condition) {
        Cursor mCursor = mDB.query(DB_TABLE_NAME,new String[]{DB_PRIMARY_KEY},condition,null,null,null,
                null, null);
        int size = mCursor.getCount();
        mCursor.close();
        return size;
    }

    public boolean insert(SleepHour sleephour) {
        ContentValues values = new ContentValues();
        values.put(DB_TABLE_COLUMN_WEIGHT, sleephour.value);
        values.put(DB_TABLE_COLUMN_DATE, sleephour.date);
        sleephour.key = mDB.insert(DB_TABLE_NAME,null,values);
        if( sleephour.key == -1 ) {
            Log.e(TAG,"db insert fail!");
            return false;
        }
        if( mDBDataChangeListener!=null ) {
            mDBDataChangeListener.onDBDataChanged();
        }
        return true;
    }

    public boolean update(SleepHour sleephour) {
        if( sleephour.key == -1 ) {
            return false;
        }
        ContentValues values = new ContentValues();
        values.put(DB_TABLE_COLUMN_WEIGHT, sleephour.value);
        values.put(DB_TABLE_COLUMN_DATE, sleephour.date);
        String condition = DB_PRIMARY_KEY + "=" + "\'" + sleephour.key + "\'";
        if( !update(values,condition,null) ) {
            return false;
        }
        if( mDBDataChangeListener!=null ) {
            mDBDataChangeListener.onDBDataChanged();
        }
        return true;
    }

    protected boolean update(ContentValues values, String whereClause, String[] whereArgs) {
        int rows = mDB.update(DB_TABLE_NAME,values, whereClause, whereArgs);
        if( rows <= 0 ) {
            Log.d(TAG,"db update fail!");
            return false;
        }
        return true;
    }

    public boolean delete(int position) {
        return delete(makeCondition(position));
    }

    public boolean delete(int position,String condition) {
        return delete(makeCondition(position,condition));
    }

    public boolean delete(String condition) {
        return delete(condition,null);
    }

    protected boolean delete(String whereClause, String[] whereArgs) {
        int rows = mDB.delete(DB_TABLE_NAME,whereClause,whereArgs);
        if( rows <= 0 ) {
            Log.e(TAG,"db delete fail!");
            return false;
        }
        if( mDBDataChangeListener!=null ) {
            mDBDataChangeListener.onDBDataChanged();
        }
        return true;
    }

    public boolean clear() {
        return delete(null,null);
    }

    public boolean clear(String condition) {
        return delete(condition,null);
    }

    public SleepHour get(int position) {
        return get(position,null);
    }

    public SleepHour get(int position,String condition) {
        Cursor cursor = mDB.query(DB_TABLE_NAME,null,condition,null,null,null,
                DB_DEFAULT_ORDERBY,null);
        List<SleepHour> weights = extract(position,cursor);
        if( weights.isEmpty() ) {
            return null;
        }
        return weights.get(0);
    }

    public List<SleepHour> query() {
        Cursor cursor = mDB.query(DB_TABLE_NAME,null,null,null,null,null,
                DB_DEFAULT_ORDERBY,null);
        return extract(0,cursor);
    }

    public List<SleepHour> query(String condition) {
        Cursor cursor = mDB.query(DB_TABLE_NAME,null,condition,null,null,null,
                DB_DEFAULT_ORDERBY,null);
        return extract(0,cursor);
    }

    public List<SleepHour> query(int offset,int limit) {
        return query(null,offset,limit);
    }

    public List<SleepHour> query(String condition,int offset,int limit) {
        Cursor cursor = mDB.query(DB_TABLE_NAME,null,condition,null,null,null,
                DB_DEFAULT_ORDERBY, offset + "," + limit);
        return extract(0,cursor);
    }

    protected List<SleepHour> extract(int position, Cursor cursor) {

        List<SleepHour> weights = new ArrayList<SleepHour>();
        if( cursor == null || cursor.getCount() <= position ) {
            return weights;
        }

        cursor.moveToFirst();
        cursor.moveToPosition(position);

        do {
            SleepHour weight = new SleepHour();
            weight.key = cursor.getLong(cursor.getColumnIndex(DB_PRIMARY_KEY));
            weight.value = cursor.getString(cursor.getColumnIndex(DB_TABLE_COLUMN_WEIGHT));
            weight.date = cursor.getLong(cursor.getColumnIndex(DB_TABLE_COLUMN_DATE));
            weights.add(weight);
        }while(cursor.moveToNext());

        cursor.close();

        return weights;
    }

    protected long getkey(int position,String condition) {
        long key = -1;
        Cursor cursor = mDB.query(true,DB_TABLE_NAME, new String[]{DB_PRIMARY_KEY},condition,null,null,null,
                DB_DEFAULT_ORDERBY, null);
        if (cursor != null && cursor.getCount() > 0 ) {
            cursor.moveToPosition(position);
            key = cursor.getLong(cursor.getColumnIndex(DB_PRIMARY_KEY));
            cursor.close();
        }
        return key;
    }
}
