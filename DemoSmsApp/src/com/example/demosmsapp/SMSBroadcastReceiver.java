package com.example.demosmsapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.Toast;

public class SMSBroadcastReceiver extends BroadcastReceiver
{
	//File ob;
	//DBAdapter db=new ;
	
	@Override
	public void onReceive(Context context, Intent intent) {
		DBAdapter db=new DBAdapter(context);
		db.open();
		// TODO Auto-generated method stub
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
        		Toast.makeText(context,"Error :"+e,Toast.LENGTH_SHORT).show();
        	}
        }*/
		Bundle bundle = intent.getExtras();        
        SmsMessage[] msgs = null;
        String text = ""; 
        String no = "";
        if (bundle != null)
        {
           Object[] pdus = (Object[]) bundle.get("pdus");
            msgs = new SmsMessage[pdus.length];            
            for (int i=0; i<msgs.length; i++){
                msgs[i] = SmsMessage.createFromPdu((byte[])pdus[i]);  
                if(i==0)
                {
                	no+=msgs[i].getOriginatingAddress();
                	no=no.substring(3);
                }
               text +=msgs[i].getDisplayMessageBody().toString();
                        
             }
            long id=db.insertSms(no, text, "2");
        }
	}
}
