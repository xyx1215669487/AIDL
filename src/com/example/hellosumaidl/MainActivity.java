package com.example.hellosumaidl;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	private IAdditionService service;
	private AdditionServiceConnection connection;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		initService();
		
		Button buttonCalc=(Button) findViewById(R.id.button);
		
		buttonCalc.setOnClickListener(new OnClickListener() {
			TextView result = (TextView) findViewById(R.id.result);
			EditText value1 = (EditText) findViewById(R.id.value1);
			EditText value2 = (EditText) findViewById(R.id.value2);
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(isNumeric(value1.getText().toString()) && 
						isNumeric(value2.getText().toString())){
					int v1,v2,res=-1;
					v1=Integer.parseInt(value1.getText().toString());
					v2=Integer.parseInt(value2.getText().toString());
					
					try {
						Log.e("XIEYUXING", "---->"+(service==null));
						res=service.add(v1, v2);
					} catch (RemoteException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					result.setText(Integer.valueOf(res).toString());
				}else{
					String str1,str2,res="";
					str1=value1.getText().toString();
					str2=value2.getText().toString();
					try {
						res=service.iAdd(str1, str2);
					} catch (RemoteException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					result.setText(res);
				}
			}
		});
	}
	
	public boolean isNumeric(String str)
	{
		if("".equals(str)){
			return false;
		}
		Pattern pattern = Pattern.compile("[0-9]*");
		Matcher isNum = pattern.matcher(str); 
		if( !isNum.matches())
		{
			return false;
		}
		return true;
	}

	class AdditionServiceConnection implements ServiceConnection{

		@Override
		public void onServiceConnected(ComponentName name, IBinder bander) {
			// TODO Auto-generated method stub
			service =IAdditionService.Stub.asInterface((IBinder) bander);
			Toast.makeText(MainActivity.this, "Service Connected", 
					Toast.LENGTH_SHORT).show();
		}

		@Override
		public void onServiceDisconnected(ComponentName name) {
			// TODO Auto-generated method stub
			service=null;
			Toast.makeText(MainActivity.this, "Service disconnected",
					Toast.LENGTH_SHORT).show();
		}
	}

	private void initService(){
		connection = new AdditionServiceConnection();
		Intent intent=new Intent();
		intent.setClassName("com.example.hellosumaidl",
				com.example.hellosumaidl.AdditionService.class.getName());
	boolean ret=bindService(intent, connection, Context.BIND_AUTO_CREATE);
	}
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		unbindService(connection);
		connection=null;
	}
}
