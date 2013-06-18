package com.example.demosmsapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBAdapter {
	static final String KEY_ID="_id";
	static final String KEY_NUM="number";
    static final String KEY_TEXT="contents";
    static final String KEY_FLAG="flag";
    
    static final String DATABASE_NAME="smsapp";
    static final String DATABASE_TABLE="sms";
    static final int DATABASE_VERSION=1;
    
    static final String DATABASE_CREATE="create table sms(_id integer primary key autoincrement, number text not null,contents text not null,flag text not null );";
    final Context context;
    //SMSBroadcastReceiver smsBroadcastReceiver;
    DatabaseHelper DBHelper;
    SQLiteDatabase db;
    public DBAdapter(Context ctx)
    {
    	this.context=ctx;
    	DBHelper=new DatabaseHelper(context);
    	
    }
    
	private static class DatabaseHelper extends SQLiteOpenHelper
    {
    	DatabaseHelper(Context context)
    	{
    		super(context,DATABASE_NAME,null,DATABASE_VERSION);
    	}
    	
		public void onCreate(SQLiteDatabase db)
    	{
    		try{
    			db.execSQL(DATABASE_CREATE);
    		}catch (SQLException e){
    			e.printStackTrace();
    		}
    	}
    	public void onUpgrade(SQLiteDatabase db,int oldVersion,int newVersion)
    	{
    		//Log.w(TAG,"upgrading from version:"+oldVersion+"to"+newVersion);
    		db.execSQL("DROP TABLE IF EXISTS sms");
    	}
    }
    public DBAdapter open() throws SQLException
    {
    	db=DBHelper.getWritableDatabase();
    	return this;
    }
    public void close()
    {
    	DBHelper.close();
    }
    public long insertSms(String number,String contents,String flag)
    {
    	ContentValues initialValues=new ContentValues();
    	initialValues.put(KEY_NUM,number);
    	initialValues.put(KEY_TEXT,contents);
    	initialValues.put(KEY_FLAG,flag);
    	return db.insert(DATABASE_TABLE,null,initialValues);
    	
    }
    public Cursor getAllSms()
    {
    	return db.query(true,DATABASE_TABLE,new String[]{KEY_NUM},null,null,KEY_NUM,null,null,null);
    }
    public Cursor getConv(String num)
    { 
    	
    	Cursor mCursor= db.query(true,DATABASE_TABLE,new String[]{KEY_TEXT,KEY_FLAG,KEY_NUM,KEY_ID},KEY_NUM + "=" +num ,null,null,null,"flag ASC",null);
    	if(mCursor !=null){
    		mCursor.moveToFirst();
    	}
    	return mCursor;
    }
    public boolean deleteConv(String del)
    {
    	return db.delete(DATABASE_TABLE, "number="+del, null)>0;
    }
    public boolean deleteParticularText(int del)
    {
    	return db.delete(DATABASE_TABLE, "_id="+del, null)>0;
    }
    
}
