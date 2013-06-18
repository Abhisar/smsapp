package com.example.demosmsapp;

import java.io.File;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class sendsms extends Activity
{
	EditText et1,et2;
	File ob;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sendsms);
		et1 = (EditText)findViewById(R.id.editText1);
		et2 = (EditText)findViewById(R.id.EditText01);
		
		/*ob = new File(Environment.getExternalStorageDirectory(),"MessageLog");
		if(!ob.exists())
        {
        	ob.mkdirs();
        	try
        	{
        		File writeData = new File(ob,"log.txt");
        		FileWriter fwrite = new FileWriter(writeData);
        		fwrite.write("message log"); 
        		fwrite.close();
        	}
        	catch(Exception e)
        	{
        		Toast.makeText(this,"Error :"+e,Toast.LENGTH_SHORT).show();
        	}
        }*/
	}
	public void sendSms(View v)
	{
		
        
		String number = et1.getText().toString();
		String msg = et2.getText().toString();
		if(number.length()==0)
		{
			Toast.makeText(this,"Enter Mobile Number",Toast.LENGTH_SHORT).show();
			et1.requestFocus();
		}
		else if(msg.length()==0)
		{
			Toast.makeText(this,"Message field is blank",Toast.LENGTH_SHORT).show();
			et2.requestFocus();
		}
		else
		{
			/* try
			{		    
				msg = msg.replaceAll("[\\t\\n\\r]"," ");
				String fname = Environment.getExternalStorageDirectory().toString()+"/MessageLog";
				File actual = new File(fname);
				for( File f : actual.listFiles())
				{
					File writeData = new File(ob,"log_"+number+".txt");
					FileWriter fwrite = new FileWriter(writeData,true);
			    	fwrite.write("s,"+msg+"\n"); 
			    	fwrite.close();
			    	break;
				}
				
			}
			catch(Exception e)
			{
				Toast.makeText(this,"Error :"+e,Toast.LENGTH_SHORT).show();
			} */
			DBAdapter db=new DBAdapter(this);
			db.open();
			long id;
			id=db.insertSms(number,msg , "1");
			db.close();
	        
			SmsManager sms = SmsManager.getDefault();
			sms.sendTextMessage(number, null, msg, null, null);
			Toast.makeText(this,"Message send Successfully",Toast.LENGTH_SHORT).show();
			Intent i = new Intent(this,MainActivity.class);
			startActivity(i);
		}
	}
}
