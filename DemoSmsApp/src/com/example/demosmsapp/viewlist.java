package com.example.demosmsapp;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class viewlist extends Activity implements OnItemClickListener,OnItemLongClickListener
{
	ListView lv;
	//File ob;
	//DBAdapter db=new DBAdapter(this);
	ArrayList<String> al;
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.viewlist);
		
		lv = (ListView)findViewById(R.id.listView1);
		
		//ob = new File(Environment.getExternalStorageDirectory(),"MessageLog");
		//if(ob.exists())
		//{
			display();
		//}
	}
	public void display()
	{
		DBAdapter db=new DBAdapter(this);
		db.open();
		al = new ArrayList<String>();
		try
		{		    	
			/*String fname = Environment.getExternalStorageDirectory().toString()+"/MessageLog";
			File actual = new File(fname);
			for( File f : actual.listFiles())
			{
				if(!(f.getName().equals("log.txt")))
				{
					al.add(f.getName().substring(4,f.getName().indexOf(".")));
				}
			}*/
			Cursor c=db.getAllSms();
			if (c != null ) {
	    		if  (c.moveToFirst()) {
	    			do {
	    				String text = c.getString(0);
	    				al.add(text);
	    				
	    			}while (c.moveToNext());
	    		}
			}
		
			ArrayAdapter<String> aa = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,al);
			lv.setAdapter(aa);
			lv.setOnItemClickListener(this);
			lv.setOnItemLongClickListener(this);
		}
		catch(Exception e)
		{
			Toast.makeText(this,"Error :"+e,Toast.LENGTH_SHORT).show();
		}
		db.close();

	}
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		Intent i = new Intent(this,showchat.class);
		String item = ((TextView)arg1).getText().toString();
		//Toast.makeText(getBaseContext(), item, Toast.LENGTH_LONG).show();
		i.putExtra("number",item);
		startActivity(i);
	}
	
	@Override
	public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		// TODO Auto-generated method stub
		//DBAdapter db=new DBAdapter(this);
		//db.open();
		final  String item=((TextView)arg1).getText().toString();
		AlertDialog.Builder ad = new AlertDialog.Builder(this);
		
		ad.setTitle("Delete Chat");
		ad.setMessage("Do you want to delete?");
		ad.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				// TODO Auto-generated method stub
				String number = item;
				
				
				/*String filename =Environment.getExternalStorageDirectory().toString()+"/MessageLog/"+"log_"+number+".txt";
				File ob = new File(filename);
				ob.delete();
				al.clear();*/
				DBAdapter db=new DBAdapter(viewlist.this);
				db.open();
				db.deleteConv(number);
				
				display();
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
