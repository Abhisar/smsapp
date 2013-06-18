package com.example.demosmsapp;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class showchat extends ListActivity implements OnItemLongClickListener //implements OnItemLongClickListener
{	
	private ArrayList<String> results = new ArrayList<String>();
	private ArrayList<Integer> ids = new ArrayList<Integer>();
	//File ob;
	//ListView lv;
	//ArrayList<String> al;
	String number;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.showchat);
		//lv = (ListView)findViewById(R.id.listView1);
		//al = new ArrayList<String>();
		//ob = new File(Environment.getExternalStorageDirectory(),"MessageLog");
		Bundle ob = getIntent().getExtras();
		number = ob.getString("number");
		display(number);
	}
	public void display(String number)
	{
		DBAdapter db=new DBAdapter(this);
		db.open();
		String nums=number;
		Cursor c=db.getConv(nums);
		if (c != null ) {
	    		if  (c.moveToFirst()) {
	    			do {
	    				String text = c.getString(0);
	    				String flag=c.getString(1);
	    				String num=c.getString(2);
	    				int id=c.getInt(3);
	    				if(flag.equals("1"))
	    				{
	    					results.add("ME:"+text);
	    					ids.add(id);
	    				}
	    				else
	    				{
	    					results.add(num+":"+text);
	    					ids.add(id);
	    				}
	    			}while (c.moveToNext());
	    		} 
	    	
			
	    		TextView tView = new TextView(this);
	    		  getListView().addHeaderView(tView);
	    	        
	    	        setListAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, results));
	    	        getListView().setTextFilterEnabled(true);
	    	        ListView lv = getListView();

	    	        lv.setOnItemLongClickListener(this);
			db.close();
		}
		
	}
	public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int arg2,long arg3) {
		// TODO Auto-generated method stub
		//DBAdapter db=new DBAdapter(this);
		//db.open();
		//final  String item=((TextView)arg1).getText().toString();
		final int x=arg2;
		final int idl=ids.get(x-1);
		AlertDialog.Builder ad = new AlertDialog.Builder(this);
		
		ad.setTitle("Delete Chat");
		ad.setMessage("Do you want to delete?");
		ad.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				// TODO Auto-generated method stub
				//String number = item;
				/*if (number.startsWith("ME:"))
				{
					number=number.substring(3);
				}
				else
				{
					number=number.substring(11);
				}
				Toast.makeText(getBaseContext(), number, Toast.LENGTH_LONG).show();*/
				
				
				DBAdapter db=new DBAdapter(showchat.this);
				db.open();
				db.deleteParticularText(idl);
				db.close();
				Intent i = new Intent(showchat.this,viewlist.class);
				startActivity(i);
				
				//display();
			}
		});
		ad.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				// TODO Auto-generated method stub
			}
		});
		ad.show();

		return true;
	}
	
	

}
